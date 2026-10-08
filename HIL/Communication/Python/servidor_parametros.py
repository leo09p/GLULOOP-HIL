import asyncio
import json
import serial
import websockets

# =====================================================
# CONFIGURACION
# =====================================================

PUERTO_SERIAL = "COM19"
BAUDRATE = 115200

HOST = "0.0.0.0"
PUERTO_WS = 8765


# =====================================================
# ABRIR ESP32
# =====================================================

esp32 = serial.Serial(
    port=PUERTO_SERIAL,
    baudrate=BAUDRATE,
    timeout=0.1
)

esp32.reset_input_buffer()


print("==========================================")
print(" SERVIDOR DE PARAMETROS GLULOOP")
print("==========================================")
print()
print(f"Conectado en {PUERTO_SERIAL}")
print(f"WebSocket puerto: {PUERTO_WS}")
print()
print("Esperando GLULOOP...")
print()


# =====================================================
# CLIENTES
# =====================================================

clientes = set()


# =====================================================
# ENVIAR MENSAJE A GLULOOP
# =====================================================

async def enviar_gluloop(mensaje):

    if not clientes:
        return

    texto = json.dumps(mensaje)

    await asyncio.gather(
        *[
            cliente.send(texto)
            for cliente in list(clientes)
        ],
        return_exceptions=True
    )


# =====================================================
# RECIBIR MENSAJES DESDE GLULOOP
# =====================================================

async def cliente_conectado(websocket):

    clientes.add(websocket)

    print()
    print(">>> GLULOOP CONECTADO <<<")
    print()

    try:

        async for texto in websocket:

            try:

                mensaje = json.loads(texto)

            except json.JSONDecodeError:

                print("JSON invalido recibido desde GLULOOP")
                continue


            # ==========================================
            # PARAMETROS DESDE LA APP
            # ==========================================

            if mensaje.get("tipo") == "parametros":

                try:

                    b0 = float(mensaje["b0"])

                    insulina_basal = float(
                        mensaje["insulina_basal"]
                    )

                    glucosa_basal = float(
                        mensaje["glucosa_basal"]
                    )

                except (
                    KeyError,
                    TypeError,
                    ValueError
                ):

                    print(
                        "ERROR: parametros invalidos "
                        "recibidos desde GLULOOP"
                    )

                    continue


                print()
                print("==========================================")
                print(" PARAMETROS RECIBIDOS DESDE GLULOOP")
                print("==========================================")

                print(
                    f"b0             : {b0:.6f}"
                )

                print(
                    f"Insulina basal : {insulina_basal:.4f}"
                )

                print(
                    f"Glucosa basal  : {glucosa_basal:.4f}"
                )

                print("==========================================")
                print()


                # ======================================
                # ENVIAR A LA TARJETA                #
                # Formato esperado:
                #
                # P,b0,insulinaBasal,glucosaBasal
                # ======================================

                comando = (
                    f"P,"
                    f"{b0},"
                    f"{insulina_basal},"
                    f"{glucosa_basal}\n"
                )


                esp32.write(
                    comando.encode("utf-8")
                )

                esp32.flush()


                print(
                    ">>> ENVIADOS ALA TARJETA <<<"
                )

                print(
                    "Esperando confirmacion de Texas..."
                )

                print()


    except websockets.exceptions.ConnectionClosed:

        pass


    finally:

        clientes.discard(websocket)

        print()
        print(">>> GLULOOP DESCONECTADO <<<")
        print()


# =====================================================
# LEER RESPUESTAS
# =====================================================

async def leer_esp32():

    while True:

        while esp32.in_waiting:

            linea = esp32.readline().decode(
                "utf-8",
                errors="ignore"
            ).strip()


            if not linea:
                continue


            # ==========================================
            # CONFIRMACION REAL DEVUELTA POR TEXAS
            # ==========================================
            #
            # genera:
            #
            # PARAMETROS_TEXAS,b0,insulina,glucosa
            # ==========================================

            if linea.startswith(
                "PARAMETROS_TEXAS,"
            ):

                partes = linea.split(",")


                if len(partes) != 4:

                    print(
                        "Respuesta de parametros invalida:"
                    )

                    print(linea)

                    continue


                try:

                    b0 = float(partes[1])

                    insulina_basal = float(
                        partes[2]
                    )

                    glucosa_basal = float(
                        partes[3]
                    )

                except ValueError:

                    print(
                        "ERROR convirtiendo respuesta "
                        "de Texas"
                    )

                    continue


                # ======================================
                # MOSTRAR EN POWERSHELL
                # ======================================

                print()
                print("==========================================")
                print(" PARAMETROS DEVUELTOS POR TEXAS")
                print("==========================================")

                print(
                    f"b0             : {b0:.6f}"
                )

                print(
                    f"Insulina basal : {insulina_basal:.4f}"
                )

                print(
                    f"Glucosa basal  : {glucosa_basal:.4f}"
                )

                print("==========================================")
                print()

                print(
                    "OK - PARAMETROS CONFIRMADOS "
                    "POR F28379D"
                )

                print()


                # ======================================
                # DEVOLVER CONFIRMACION A GLULOOP
                # ======================================

                mensaje = {

                    "tipo":
                        "parametros_confirmados",

                    "b0":
                        b0,

                    "insulina_basal":
                        insulina_basal,

                    "glucosa_basal":
                        glucosa_basal
                }


                await enviar_gluloop(
                    mensaje
                )


            else:

                # Mostrar mensajes informativos
                # provenientes 

                print(
                    f"TARJETA: {linea}"
                )


        await asyncio.sleep(0)


# =====================================================
# MAIN
# =====================================================

async def main():

    async with websockets.serve(
        cliente_conectado,
        HOST,
        PUERTO_WS
    ):

        await leer_esp32()


# =====================================================
# INICIO
# =====================================================

try:

    asyncio.run(main())


except KeyboardInterrupt:

    print()
    print("Servidor detenido.")


finally:

    if esp32.is_open:

        esp32.close()


    print(
        "Puerto serial cerrado."
    )
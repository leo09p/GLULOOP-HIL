
%% ================================================================
%  ANALISIS DE SIMULACION - METRICAS Y GRAFICAS
%  CONTROL ADRC - PLATAFORMA UVA/PADOVA
%
%  Señales utilizadas:
%   V.mat       -> Glucosa fisiologica
%   g.mat       -> Medicion CGM
%   u.mat       -> Salida directa del ADRC
%   u_insu.mat  -> Insulina aplicada al paciente
%
%  Guarda:
%   - 4 graficas (.png y .fig)
%   - Datos (.mat)
%   - Tabla de datos (.csv)
%   - Tabla de metricas (.csv)
% ================================================================


%% ================================================================
% 1. CONFIGURACION
% ================================================================

% Numero del paciente o identificador de simulacion
paciente = 10130;

% Carpeta principal para guardar resultados
carpeta_base = 'C:\Users\USUARIO\OneDrive\Documentos\ADRC';

% Referencia de glucosa
referencia = 110;  % mg/dL

% Limites glucemicos
limite_TIR_inf = 70;
limite_TIR_sup = 180;

limite_TAR2 = 250;
limite_TBR2 = 54;


%% ================================================================
% 2. CREAR CARPETAS AUTOMATICAMENTE
% ================================================================

nombre_paciente = sprintf('Paciente_%02d',paciente);

carpeta_paciente = fullfile(carpeta_base,nombre_paciente);

carpeta_graficas = fullfile(carpeta_paciente,'Graficas');
carpeta_datos    = fullfile(carpeta_paciente,'Datos');
carpeta_metricas = fullfile(carpeta_paciente,'Metricas');

if ~exist(carpeta_base,'dir')
    mkdir(carpeta_base);
end

if ~exist(carpeta_paciente,'dir')
    mkdir(carpeta_paciente);
end

if ~exist(carpeta_graficas,'dir')
    mkdir(carpeta_graficas);
end

if ~exist(carpeta_datos,'dir')
    mkdir(carpeta_datos);
end

if ~exist(carpeta_metricas,'dir')
    mkdir(carpeta_metricas);
end


%% ================================================================
% 3. CARGAR SEÑALES
% ================================================================

load('V.mat');
load('g.mat');
load('u.mat');
load('u_insu.mat');


%% ================================================================
% 4. EXTRAER TIEMPOS Y DATOS
% ================================================================

% Glucosa fisiologica
tV = V.Time(:)/60;
V_data = squeeze(V.Data);
V_data = V_data(:);

% Medicion CGM
tg = g.Time(:)/60;
g_data = squeeze(g.Data);
g_data = g_data(:);

% Salida directa del ADRC
tu = u.Time(:)/60;
u_data = squeeze(u.Data);
u_data = u_data(:);

% Insulina aplicada
tinsu = u_insu.Time(:)/60;
uinsu_data = squeeze(u_insu.Data);
uinsu_data = uinsu_data(:);


%% ================================================================
% 5. LIMITAR ANALISIS A 24 HORAS
% ================================================================

idxV = tV <= 24;
tV = tV(idxV);
V_data = V_data(idxV);

idxg = tg <= 24;
tg = tg(idxg);
g_data = g_data(idxg);

idxu = tu <= 24;
tu = tu(idxu);
u_data = u_data(idxu);

idxinsu = tinsu <= 24;
tinsu = tinsu(idxinsu);
uinsu_data = uinsu_data(idxinsu);


%% ================================================================
% 6. GRAFICA 1 - GLUCOSA FISIOLOGICA V
% ================================================================

fig1 = figure('Color','w');

plot(tV,V_data, ...
    'Color',[0 0.4470 0.7410], ...
    'LineWidth',1.7);

hold on

yline(70,'--','70 mg/dL', ...
    'Color',[0.85 0.15 0.15], ...
    'LineWidth',1.3);

yline(180,'--','180 mg/dL', ...
    'Color',[0.9 0.45 0.1], ...
    'LineWidth',1.3);

hold off

grid on
box on

xlabel('Tiempo [h]')
ylabel('Glucosa [mg/dL]')
title('Respuesta de glucosa')

xlim([0 24])
ylim([50 200])

legend('Glucosa', ...
       'Limite inferior', ...
       'Limite superior', ...
       'Location','best');

set(gca,'FontSize',10)

savefig(fig1, ...
    fullfile(carpeta_graficas,'01_Glucosa_V.fig'));

exportgraphics(fig1, ...
    fullfile(carpeta_graficas,'01_Glucosa_V.png'), ...
    'Resolution',300);


%% ================================================================
% 7. GRAFICA 2 - MEDICION CGM g
% ================================================================

fig2 = figure('Color','w');

plot(tg,g_data, ...
    'Color',[0 0.4470 0.7410], ...
    'LineWidth',1.4);

hold on

yline(70,'--','70 mg/dL', ...
    'Color',[0.85 0.15 0.15], ...
    'LineWidth',1.3);

yline(180,'--','180 mg/dL', ...
    'Color',[0.9 0.45 0.1], ...
    'LineWidth',1.3);

hold off

grid on
box on

xlabel('Tiempo [h]')
ylabel('Glucosa CGM [mg/dL]')
title('Medicion de glucosa del sensor CGM')

xlim([0 24])
ylim([50 200])

legend('CGM', ...
       'Limite inferior', ...
       'Limite superior', ...
       'Location','best');

set(gca,'FontSize',10)

savefig(fig2, ...
    fullfile(carpeta_graficas,'02_CGM_g.fig'));

exportgraphics(fig2, ...
    fullfile(carpeta_graficas,'02_CGM_g.png'), ...
    'Resolution',300);


%% ================================================================
% 8. GRAFICA 3 - SALIDA DIRECTA ADRC
% ================================================================

fig3 = figure('Color','w');

plot(tu,u_data, ...
    'Color',[0 0.4470 0.7410], ...
    'LineWidth',1.5);

grid on
box on

xlabel('Tiempo [h]')
ylabel('u [U/h]')
title('Salida directa del controlador ADRC')

xlim([0 24])

set(gca,'FontSize',10)

savefig(fig3, ...
    fullfile(carpeta_graficas,'03_Salida_ADRC.fig'));

exportgraphics(fig3, ...
    fullfile(carpeta_graficas,'03_Salida_ADRC.png'), ...
    'Resolution',300);


%% ================================================================
% 9. GRAFICA 4 - INSULINA APLICADA
% ================================================================

fig4 = figure('Color','w');

plot(tinsu,uinsu_data, ...
    'Color',[0 0.4470 0.7410], ...
    'LineWidth',1.5);

grid on
box on

xlabel('Tiempo [h]')
ylabel('Insulina [U/h]')
title('Insulina aplicada al paciente')

xlim([0 24])

set(gca,'FontSize',10)

savefig(fig4, ...
    fullfile(carpeta_graficas,'04_Insulina_Aplicada.fig'));

exportgraphics(fig4, ...
    fullfile(carpeta_graficas,'04_Insulina_Aplicada.png'), ...
    'Resolution',300);


%% ================================================================
% 10. METRICAS DE CONTROL GLUCEMICO
%     CALCULADAS CON g (CGM)
% ================================================================

% Duracion total en horas
duracion_total = tg(end) - tg(1);

% TIR 70-180 mg/dL
TIR_h = trapz(tg, ...
    double(g_data >= limite_TIR_inf & ...
           g_data <= limite_TIR_sup));

TIR_pct = 100*TIR_h/duracion_total;

% TAR >180 mg/dL
TAR180_h = trapz(tg, ...
    double(g_data > limite_TIR_sup));

TAR180_pct = 100*TAR180_h/duracion_total;

% TBR <70 mg/dL
TBR70_h = trapz(tg, ...
    double(g_data < limite_TIR_inf));

TBR70_pct = 100*TBR70_h/duracion_total;

% TAR >250 mg/dL
TAR250_h = trapz(tg, ...
    double(g_data > limite_TAR2));

TAR250_pct = 100*TAR250_h/duracion_total;

% TBR <54 mg/dL
TBR54_h = trapz(tg, ...
    double(g_data < limite_TBR2));

TBR54_pct = 100*TBR54_h/duracion_total;

% Estadisticas de glucosa
glucosa_media = mean(g_data,'omitnan');
glucosa_max   = max(g_data);
glucosa_min   = min(g_data);


%% ================================================================
% 11. ERROR RESPECTO A LA REFERENCIA
% ================================================================

error = referencia - g_data;

% RMSE
RMSE = sqrt(mean(error.^2,'omitnan'));

% IAE
IAE = trapz(tg,abs(error));

% ISE
ISE = trapz(tg,error.^2);

% ITAE
t_error = tg - tg(1);

ITAE = trapz(tg,t_error .* abs(error));


%% ================================================================
% 12. ESFUERZO DE CONTROL
% ================================================================

% Integral absoluta de la salida directa del ADRC
esfuerzo_control = trapz(tu,abs(u_data));


%% ================================================================
% 13. INSULINA TOTAL ADMINISTRADA
% ================================================================

% u_insu expresada en U/h
% tinsu expresado en horas
% Resultado en unidades de insulina (U)

insulina_total = trapz(tinsu,uinsu_data);


%% ================================================================
% 14. MOSTRAR RESULTADOS
% ================================================================

fprintf('\n');
fprintf('=========================================================\n');
fprintf('              RESULTADOS - PACIENTE %02d\n',paciente);
fprintf('=========================================================\n');

fprintf('\nCONTROL GLUCEMICO - CGM\n');
fprintf('---------------------------------------------------------\n');

fprintf('TIR 70-180 mg/dL : %8.2f %%   (%6.2f h)\n', ...
    TIR_pct,TIR_h);

fprintf('TAR >180 mg/dL   : %8.2f %%   (%6.2f h)\n', ...
    TAR180_pct,TAR180_h);

fprintf('TBR <70 mg/dL    : %8.2f %%   (%6.2f min)\n', ...
    TBR70_pct,TBR70_h*60);

fprintf('TAR >250 mg/dL   : %8.2f %%   (%6.2f min)\n', ...
    TAR250_pct,TAR250_h*60);

fprintf('TBR <54 mg/dL    : %8.2f %%   (%6.2f min)\n', ...
    TBR54_pct,TBR54_h*60);

fprintf('\n');

fprintf('Glucosa media    : %8.2f mg/dL\n',glucosa_media);
fprintf('Glucosa maxima   : %8.2f mg/dL\n',glucosa_max);
fprintf('Glucosa minima   : %8.2f mg/dL\n',glucosa_min);

fprintf('\nDESEMPEÑO DEL CONTROLADOR\n');
fprintf('---------------------------------------------------------\n');

fprintf('RMSE : %10.4f mg/dL\n',RMSE);
fprintf('IAE  : %10.4f mg/dL*h\n',IAE);
fprintf('ISE  : %10.4f (mg/dL)^2*h\n',ISE);
fprintf('ITAE : %10.4f mg/dL*h^2\n',ITAE);

fprintf('\nACTUACION\n');
fprintf('---------------------------------------------------------\n');

fprintf('Esfuerzo de control : %10.4f\n',esfuerzo_control);
fprintf('Insulina total      : %10.4f U\n',insulina_total);


%% ================================================================
% 15. TABLA DE METRICAS
% ================================================================

Metricas = table( ...
    paciente, ...
    TIR_pct, ...
    TIR_h, ...
    TAR180_pct, ...
    TAR180_h, ...
    TBR70_pct, ...
    TBR70_h, ...
    TAR250_pct, ...
    TAR250_h, ...
    TBR54_pct, ...
    TBR54_h, ...
    glucosa_media, ...
    glucosa_max, ...
    glucosa_min, ...
    RMSE, ...
    IAE, ...
    ISE, ...
    ITAE, ...
    esfuerzo_control, ...
    insulina_total, ...
    'VariableNames',{ ...
    'Paciente', ...
    'TIR_pct', ...
    'TIR_h', ...
    'TAR180_pct', ...
    'TAR180_h', ...
    'TBR70_pct', ...
    'TBR70_h', ...
    'TAR250_pct', ...
    'TAR250_h', ...
    'TBR54_pct', ...
    'TBR54_h', ...
    'Glucosa_media', ...
    'Glucosa_max', ...
    'Glucosa_min', ...
    'RMSE', ...
    'IAE', ...
    'ISE', ...
    'ITAE', ...
    'Esfuerzo_control', ...
    'Insulina_total_U'});

archivo_metricas = fullfile( ...
    carpeta_metricas, ...
    sprintf('Paciente_%02d_metricas.csv',paciente));

writetable(Metricas,archivo_metricas);


%% ================================================================
% 16. CREAR TABLA DE DATOS
% ================================================================

% Se utiliza el tiempo del CGM como base comun
tiempo = tg;

% Interpolar las señales al tiempo del CGM
V_tabla = interp1( ...
    tV,V_data,tiempo,'linear',NaN);

g_tabla = g_data;

u_tabla = interp1( ...
    tu,u_data,tiempo,'linear',NaN);

uinsu_tabla = interp1( ...
    tinsu,uinsu_data,tiempo,'linear',NaN);

% Crear tabla solo con las cuatro señales
TablaDatos = table( ...
    tiempo, ...
    V_tabla, ...
    g_tabla, ...
    u_tabla, ...
    uinsu_tabla, ...
    'VariableNames',{ ...
    'Tiempo_h', ...
    'V_mgdL', ...
    'g_mgdL', ...
    'u', ...
    'u_insu'});


%% ================================================================
% 17. GUARDAR TABLA CSV
% ================================================================

archivo_csv = fullfile( ...
    carpeta_datos, ...
    sprintf('Paciente_%02d_datos.csv',paciente));

writetable(TablaDatos,archivo_csv);


%% ================================================================
% 18. GUARDAR ARCHIVO MAT MAESTRO
% ================================================================

archivo_mat = fullfile( ...
    carpeta_datos, ...
    sprintf('Paciente_%02d_datos.mat',paciente));

% Estructura de resultados
Resultados = struct;

Resultados.paciente = paciente;

% Glucosa fisiologica
Resultados.V.t = tV;
Resultados.V.data = V_data;

% Medicion CGM
Resultados.g.t = tg;
Resultados.g.data = g_data;

% Salida directa ADRC
Resultados.u.t = tu;
Resultados.u.data = u_data;

% Insulina aplicada
Resultados.u_insu.t = tinsu;
Resultados.u_insu.data = uinsu_data;


%% ================================================================
% 19. GUARDAR METRICAS EN LA ESTRUCTURA
% ================================================================

Resultados.metricas.TIR_pct = TIR_pct;
Resultados.metricas.TIR_h = TIR_h;

Resultados.metricas.TAR180_pct = TAR180_pct;
Resultados.metricas.TAR180_h = TAR180_h;

Resultados.metricas.TBR70_pct = TBR70_pct;
Resultados.metricas.TBR70_h = TBR70_h;

Resultados.metricas.TAR250_pct = TAR250_pct;
Resultados.metricas.TAR250_h = TAR250_h;

Resultados.metricas.TBR54_pct = TBR54_pct;
Resultados.metricas.TBR54_h = TBR54_h;

Resultados.metricas.glucosa_media = glucosa_media;
Resultados.metricas.glucosa_max = glucosa_max;
Resultados.metricas.glucosa_min = glucosa_min;

Resultados.metricas.RMSE = RMSE;
Resultados.metricas.IAE = IAE;
Resultados.metricas.ISE = ISE;
Resultados.metricas.ITAE = ITAE;

Resultados.metricas.esfuerzo_control = esfuerzo_control;
Resultados.metricas.insulina_total = insulina_total;


%% ================================================================
% 20. GUARDAR ARCHIVO MAT
% ================================================================

save(archivo_mat, ...
    'Resultados', ...
    'TablaDatos', ...
    'Metricas');


%% ================================================================
% 21. MENSAJE FINAL
% ================================================================

fprintf('\n=========================================================\n');
fprintf('TODO GUARDADO CORRECTAMENTE\n');
fprintf('=========================================================\n');

fprintf('\nPaciente %02d\n',paciente);

fprintf('\nGraficas:\n%s\n',carpeta_graficas);

fprintf('\nDatos MAT:\n%s\n',archivo_mat);

fprintf('\nTabla CSV:\n%s\n',archivo_csv);

fprintf('\nMetricas:\n%s\n',archivo_metricas);

fprintf('\n=========================================================\n');

%% =========================================================
% PARAMETROS DEL CONTROLADOR ADRC
%% =========================================================

% Tiempo de establecimiento utilizado para la sintonizacion
% NO corresponde al periodo de muestreo.
ctrller.p=0.00326270;
ctrller.i=0.0000050282;
ctrller.d=0.221745;

Ts_set = 180;                    % [min]

Mp = 0.05;

zeta = 0.75;

wn = 4/(zeta*Ts_set);


%% Modelo identificado

T1 =180.6638;                   % [min]

T2 = 72.46;                      % [min]
    
K =-1.2599  ;


%% Ganancia efectiva ADRC

b0 =-0.085;


%% Frecuencia del ESO

wo = 5*wn;


%% =========================================================
% CONTROLADOR PD DEL ADRC
%% =========================================================

Kpp = wn^2;

Kdd = 2*zeta*wn;

Kp = 1.1*Kpp;

Kd = 1.85*Kdd;


%% =========================================================
% ESO CONTINUO ORIGINAL
%
% Se conserva para:
% 1. Mantener compatibilidad con el controlador actual.
% 2. Generar matemáticamente el ESO discreto.
% 3. Poder comparar continuo vs discreto.
%% =========================================================

L2 = 3*wo;

L1 = 3*(wo^2);

L0 = wo^3;


A_obs = [ -L2   1   0;
          -L1   0   1;
          -L0   0   0 ];


B_obs = [  0    L2;
          b0    L1;
           0    L0 ];


C_obs = eye(3);

D_obs = zeros(3,2);


%% =========================================================
% PERIODO DE MUESTREO DEL CONTROLADOR DIGITAL
%
% Cada iteracion representa 1 minuto del paciente.
%% =========================================================

Ts_CTRL = 1;                     % [min]


%% =========================================================
% DISCRETIZACION DEL ESO
%
% Metodo:
% Zero Order Hold (ZOH)
%
% Modelo:
%
% z(k+1) = Ad_ESO*z(k) + Bd_ESO*[u(k); y(k)]
%
%% =========================================================

sysESO_c = ss(A_obs,B_obs,C_obs,D_obs);

sysESO_d = c2d(sysESO_c,Ts_CTRL,'zoh');


Ad_ESO = sysESO_d.A;

Bd_ESO = sysESO_d.B;

Cd_ESO = sysESO_d.C;

Dd_ESO = sysESO_d.D;


%% Verificar estabilidad

polos_ESO = eig(Ad_ESO);

mag_polos_ESO = abs(polos_ESO);


if any(mag_polos_ESO >= 1)

    warning('El ESO discreto no es estable.');

end


%% Condición inicial del ESO

x0_ESO = [0;
          0;
          0];


%% =========================================================
% SATURACION DEL ADRC
%% =========================================================

lim_sat = 7;                     % [U/hr]

lim_sat_neg = -1;                % [U/hr]


ctrller.lim1 = lim_sat;

ctrller.lim2 = lim_sat_neg;


%% =========================================================
% LIMITES SAFE / IOB
%% =========================================================

sw = 5.5;

swneg = 4.4;


ctrller.sw = sw;

ctrller.swneg = swneg;


%% =========================================================
% REFERENCIA
%% =========================================================

reff = -22;

ctrller.r = reff;


%% =========================================================
% ESTIMADOR IOB
%% =========================================================

KDIA = 16.3e-3;                  % [1/min]

ctrller.KDIA = KDIA;


%% Modelo continuo equivalente al bloque de Simulink
%
% dx1/dt = U_PACIENTE/60 - KDIA*x1
%
% dx2/dt = KDIA*(x1-x2)
%
% IOB = x1+x2
%

A_IOB = [ -KDIA      0;
           KDIA    -KDIA ];


B_IOB = [1/60;
         0];


C_IOB = [1 1];

D_IOB = 0;


%% =========================================================
% DISCRETIZACION DEL IOB
%% =========================================================

sysIOB_c = ss(A_IOB,B_IOB,C_IOB,D_IOB);

sysIOB_d = c2d(sysIOB_c,Ts_CTRL,'zoh');


Ad_IOB = sysIOB_d.A;

Bd_IOB = sysIOB_d.B;

Cd_IOB = sysIOB_d.C;

Dd_IOB = sysIOB_d.D;


%% Verificar estabilidad

polos_IOB = eig(Ad_IOB);

mag_polos_IOB = abs(polos_IOB);


if any(mag_polos_IOB >= 1)

    warning('El estimador IOB discreto no es estable.');

end


%% Condiciones iniciales del IOB

x0_IOB = [0;
          0];


%% =========================================================
% GUARDAR PARAMETROS ORIGINALES DEL CONTROLADOR
%% =========================================================

ctrller.Kp = Kp;

ctrller.Kd = Kd;

ctrller.b0 = b0;


%% ESO continuo original

ctrller.A_obs = A_obs;

ctrller.B_obs = B_obs;

ctrller.C_obs = C_obs;

ctrller.D_obs = D_obs;


%% =========================================================
% GUARDAR CONTROLADOR DISCRETO PARA HIL
%% =========================================================

ctrller.HIL.Ts = Ts_CTRL;


%% ESO discreto

ctrller.HIL.Ad_ESO = Ad_ESO;

ctrller.HIL.Bd_ESO = Bd_ESO;

ctrller.HIL.Cd_ESO = Cd_ESO;

ctrller.HIL.Dd_ESO = Dd_ESO;

ctrller.HIL.x0_ESO = x0_ESO;


%% IOB discreto

ctrller.HIL.Ad_IOB = Ad_IOB;

ctrller.HIL.Bd_IOB = Bd_IOB;

ctrller.HIL.Cd_IOB = Cd_IOB;

ctrller.HIL.Dd_IOB = Dd_IOB;

ctrller.HIL.x0_IOB = x0_IOB;


%% Parámetros necesarios posteriormente en BeagleBone

ctrller.HIL.Kp = Kp;

ctrller.HIL.Kd = Kd;

ctrller.HIL.b0 = b0;

ctrller.HIL.lim_sat = lim_sat;

ctrller.HIL.lim_sat_neg = lim_sat_neg;

ctrller.HIL.sw = sw;

ctrller.HIL.swneg = swneg;

ctrller.HIL.KDIA = KDIA;

ctrller.HIL.r = reff;


%% =========================================================
% PARAMETROS ORIGINALES UVA/PADOVA
%% =========================================================

ctrller.corr_tgt=100;

ctrller.corr_thresh=150;

ctrller.corr_CFmeal=1;


%% =========================================================
% MOSTRAR MATRICES HIL
%% =========================================================

disp(' ')
disp('==============================================')
disp('CONTROLADOR DISCRETO PREPARADO PARA HIL')
disp('==============================================')


disp(' ')
disp('--- ESO DISCRETO ---')

disp('Ad_ESO = ')
disp(Ad_ESO)

disp('Bd_ESO = ')
disp(Bd_ESO)

disp('Polos ESO = ')
disp(polos_ESO)

disp('Magnitud polos ESO = ')
disp(mag_polos_ESO)


disp(' ')
disp('--- IOB DISCRETO ---')

disp('Ad_IOB = ')
disp(Ad_IOB)

disp('Bd_IOB = ')
disp(Bd_IOB)

disp('Polos IOB = ')
disp(polos_IOB)

disp('Magnitud polos IOB = ')
disp(mag_polos_IOB)


disp(' ')
disp('--- PARAMETROS ---')

fprintf('Ts controlador = %.2f min\n',Ts_CTRL);

fprintf('Kp = %.10f\n',Kp);

fprintf('Kd = %.10f\n',Kd);

fprintf('b0 = %.10f\n',b0);

fprintf('KDIA = %.10f\n',KDIA);

fprintf('Limites ADRC = [%.2f , %.2f] U/hr\n',...
    lim_sat_neg,lim_sat);

fprintf('SAFE ON  = %.2f\n',sw);

fprintf('SAFE OFF = %.2f\n',swneg);

disp('==============================================')


catch e

    e.message

end


%% =========================================================
% FILTRO DE KALMAN ESTACIONARIO PARA EL SENSOR CGM
%
% SE MANTIENE EN EL PC.
%% =========================================================


%% 1. Parámetros del modelo identificado

K_KF     = K;

T1_KF    = T1;

T2_KF    = 72.46;

theta_KF = 53.3;


%% 2. Periodo de muestreo

Ts_KF = 1;                       % [min]


%% 3. Punto de operación

Gb_KF = ctrller.fastingBG;

BGinit_KF = 110;


%% 4. Modelo continuo de segundo orden

a0_KF = 1/(T1_KF*T2_KF);

a1_KF = (T1_KF + T2_KF)/(T1_KF*T2_KF);

b0_KF = b0+0.02;


Ac_KF = [ 0       1;
         -a0_KF  -a1_KF ];


Bc_KF = [ 0;
          b0_KF ];


Cc_KF = [1 0];

Dc_KF = 0;


%% 5. Discretización ZOH

sysc_KF = ss(Ac_KF,Bc_KF,Cc_KF,Dc_KF);

sysd_KF = c2d(sysc_KF,Ts_KF,'zoh');


Ad_KF = sysd_KF.A;

Bd_KF = sysd_KF.B;

Cd_KF = sysd_KF.C;

Dd_KF = sysd_KF.D;


%% 6. Ruido de medición

sigma_CGM = 9.2;

R_KF = sigma_CGM^2;


%% 7. Ruido de proceso

q1_KF = 1.5;

q2_KF = 0.015;


Q_KF = diag([q1_KF q2_KF]);

Gnoise_KF = eye(2);


%% 8. Ganancia estacionaria de Kalman

[M_KF,P_KF,Z_KF,E_KF] = ...
    dlqe(Ad_KF,Gnoise_KF,Cd_KF,Q_KF,R_KF);


%% 9. Forma predictora

Lpred_KF = Ad_KF*M_KF;


Aobs_KF = Ad_KF - Lpred_KF*Cd_KF;


Bobs_KF = [Bd_KF,Lpred_KF];


Cobs_KF = Cd_KF;


Dobs_KF = zeros(1,2);


%% 10. Estabilidad

polos_KF = eig(Aobs_KF);

magnitud_polos_KF = abs(polos_KF);


if any(magnitud_polos_KF >= 1)

    warning('El filtro de Kalman discreto no es estable.');

end


%% 11. Condición inicial

x0_KF = [BGinit_KF-Gb_KF;
         0];


%% =========================================================
% GUARDAR KALMAN
%% =========================================================

KF.K = K_KF;

KF.T1 = T1_KF;

KF.T2 = T2_KF;

KF.theta = theta_KF;

KF.Ts = Ts_KF;


KF.Gb = Gb_KF;

KF.BGinit = BGinit_KF;


KF.Ac = Ac_KF;

KF.Bc = Bc_KF;

KF.Cc = Cc_KF;

KF.Dc = Dc_KF;


KF.Ad = Ad_KF;

KF.Bd = Bd_KF;

KF.Cd = Cd_KF;

KF.Dd = Dd_KF;


KF.Q = Q_KF;

KF.R = R_KF;


KF.M = M_KF;

KF.Lpred = Lpred_KF;

KF.P = P_KF;

KF.Z = Z_KF;


KF.Aobs = Aobs_KF;

KF.Bobs = Bobs_KF;

KF.Cobs = Cobs_KF;

KF.Dobs = Dobs_KF;


KF.x0 = x0_KF;


KF.polos_dlqe = E_KF;

KF.polos = polos_KF;

KF.mag_polos = magnitud_polos_KF;


%% =========================================================
% GUARDAR KALMAN EN ctrller
%% =========================================================

ctrller.KF = KF;

ctrller.KF.Aobs = KF.Aobs;

ctrller.KF.Bobs = KF.Bobs;

ctrller.KF.Cobs = KF.Cobs;

ctrller.KF.Dobs = KF.Dobs;

ctrller.KF.x0 = KF.x0;

ctrller.KF.Ts = KF.Ts;

end
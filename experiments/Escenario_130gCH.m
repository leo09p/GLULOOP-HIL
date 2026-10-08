%% Crear escenario UVA/Padova
clear;
clc;

nombre_archivo = 'escenario_130g.scn';

fid = fopen(nombre_archivo,'w');

% Duración total de simulación [días]
fprintf(fid,'%%Tsimul=1\n');
fprintf(fid,'%%QTsimul=day\n\n');

% Hora inicial de la simulación
fprintf(fid,'%%simToD=0\n\n');

% Glucosa inicial deseada [mg/dL]
fprintf(fid,'%%BGinit=[110]\n\n');

% Inicio del lazo cerrado [h]
fprintf(fid,'%%Tclosed=0\n');
fprintf(fid,'%%QTclosed=hour\n\n');

% Tiempo de regulación previo [min]
fprintf(fid,'%%Treg=0\n\n');

% Hora de cada comida [h]
fprintf(fid,'%%Tmeals=[6 13 20]\n');

% Unidad de tiempo para las comidas
fprintf(fid,'%%QTmeals=hour\n');

% Carbohidratos de cada comida [g]
fprintf(fid,'%%Ameals=[50 60 50]\n');

% Duración de absorción de la comida [min]
fprintf(fid,'%%Dmeals=[15]\n');

% Tipo de comida (cantidad total de CHO)
fprintf(fid,'%%Qmeals=total\n\n');

% Basal calculado automáticamente por el paciente virtual
fprintf(fid,'%%Qbasal=quest\n');

fclose(fid);

disp(['Escenario creado: ', nombre_archivo]);
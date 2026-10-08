% Crear escenario: prueba_lazoabierto.scn (24 horas, lazo abierto REAL)

scn_folder = 'scenario';
filename = fullfile(scn_folder, 'prueba_lazoabierto.scn');

fid = fopen(filename, 'w');

fprintf(fid, '%%Tsimul=1440\n');      % 24 horas
fprintf(fid, '%%QTsimul=min\n');
fprintf(fid, '%%simToD=0\n\n');

fprintf(fid, '%%BGinit=126.5697\n\n');  %Glucosa basal del paciente

fprintf(fid, '%%Tclosed=1440\n');   % LAZO ABIERTO TODO EL TIEMPO
fprintf(fid, '%%QTclosed=min\n');
fprintf(fid, '%%Treg=0\n\n');

fprintf(fid, '%%Qbasal=quest\n\n');

fprintf(fid, '%%Tbolus=[100]\n');   % impulso en minuto 100
fprintf(fid, '%%QTbolus=min\n');
fprintf(fid, '%%Abolus=[1]\n\n');

fprintf(fid, '%%Tmeals=[]\n');
fprintf(fid, '%%Ameals=[]\n');

fclose(fid);

disp('prueba_lazoabierto.scn (24h, corregido) creado correctamente!');

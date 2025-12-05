@echo off
echo Ejecutando script de limpieza de usuarios...
echo.
echo ADVERTENCIA: Esto eliminara todos los pacientes y medicos registrados
echo Presiona Ctrl+C para cancelar o cualquier tecla para continuar...
pause

mysql -u root hospital < limpiar_usuarios.sql

echo.
echo Script ejecutado correctamente!
pause

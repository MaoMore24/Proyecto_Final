@echo off
echo ====================================================
echo Compilando Sistema de Gestion Hospitalaria
echo ====================================================

REM Buscar NetBeans y ejecutar ant
if exist "C:\Program Files\NetBeans-*\netbeans\extide\ant\bin\ant.bat" (
    for /d %%i in ("C:\Program Files\NetBeans-*") do (
        "%%i\netbeans\extide\ant\bin\ant.bat" clean compile
        goto :end
    )
)

REM Si Ant está en el PATH
where ant >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    ant clean compile
    goto :end
)

echo.
echo ERROR: No se encontro Ant
echo Por favor, compila el proyecto desde NetBeans:
echo 1. Abre el proyecto en NetBeans
echo 2. Click derecho en el proyecto
echo 3. Selecciona "Clean and Build"
echo.
pause

:end
pause

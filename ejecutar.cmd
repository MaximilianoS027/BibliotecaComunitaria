@echo off
echo ========================================
echo  SISTEMA DE BIBLIOTECA - NUEVA VERSION
echo ========================================
echo.
echo Verificando configuracion...

REM Configurar JAVA_HOME para Java 21
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot

REM Verificar que Java esté disponible
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java no encontrado. Verifica que JAVA_HOME esté configurado correctamente.
    pause
    exit /b 1
)

echo.
echo Iniciando aplicacion...
echo.

REM Ejecutar la aplicacion con todas las dependencias
java -cp "target/classes;target/dependency/*" MainNuevo

echo.
echo Aplicacion finalizada.
pause

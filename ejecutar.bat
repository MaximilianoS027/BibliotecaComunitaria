@echo off
echo ========================================
echo  SISTEMA DE BIBLIOTECA - NUEVA VERSION
echo ========================================
echo.
echo Iniciando aplicacion...
echo.

REM Configurar JAVA_HOME
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot

REM Ejecutar la aplicacion
java -cp "target/classes;target/dependency/*" MainNuevo

pause

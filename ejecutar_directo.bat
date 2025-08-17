@echo off
echo ========================================
echo  EJECUTANDO SISTEMA DE BIBLIOTECA
echo ========================================
echo.

set JAVA_HOME=C:\Users\unicom\Downloads\jdk-11.0.28+6
set PATH=%JAVA_HOME%\bin;%PATH%

echo Configuracion:
echo JAVA_HOME: %JAVA_HOME%
echo.

echo Verificando Java...
java -version
echo.

echo Ejecutando con Maven...
C:\Users\unicom\Downloads\apache-maven-3.9.11\bin\mvn.cmd exec:java -Dexec.mainClass=com.pap.Main

echo.
echo Aplicacion terminada.
pause



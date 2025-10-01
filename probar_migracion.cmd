@echo off
echo ========================================
echo  PROBANDO MIGRACION HIBERNATE 6.x + JAKARTA
echo ========================================
echo.

REM Configurar Java 21
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

echo Verificando Java...
java -version
echo.
javac -version
echo.

echo Compilando proyecto...
mvn clean compile

if %errorlevel% equ 0 (
    echo.
    echo ✅ COMPILACION EXITOSA!
    echo.
    echo Probando ejecucion...
    mvn exec:java -Dexec.mainClass="MainNuevo"
) else (
    echo.
    echo ❌ ERROR EN COMPILACION
    echo Revisa los errores arriba
)

pause

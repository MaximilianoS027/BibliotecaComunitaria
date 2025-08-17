@echo off
echo ========================================
echo  COMPILACION Y EJECUCION DIRECTA
echo  Sistema de Gestion de Biblioteca
echo ========================================
echo.

echo Configurando variables de entorno...
set JAVA_HOME=C:\Users\unicom\Downloads\jdk-11.0.28+6
set PATH=%JAVA_HOME%\bin;%PATH%

echo Verificando Java...
java -version
if %ERRORLEVEL% neq 0 (
    echo Error: Java no esta funcionando correctamente
    pause
    exit /b 1
)

echo.
echo Creando directorio de clases compiladas...
if not exist "target\classes" mkdir target\classes

echo.
echo Compilando codigo fuente (version simplificada)...
javac -d target\classes -cp "src\main\java" src\main\java\com\pap\MainSimple.java src\main\java\com\pap\domain\model\abst\*.java src\main\java\com\pap\domain\model\concrete\*.java src\main\java\com\pap\domain\model\enums\*.java src\main\java\com\pap\shared\constants\*.java

if %ERRORLEVEL% neq 0 (
    echo Error en la compilacion
    pause
    exit /b 1
)

echo.
echo Ejecutando aplicacion...
java -cp target\classes com.pap.MainSimple

pause

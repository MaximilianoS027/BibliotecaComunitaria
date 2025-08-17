@echo off
echo ========================================
echo  EJECUCION CON MAVEN (COMPLETO)
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
echo Verificando Maven...
C:\Users\unicom\Downloads\apache-maven-3.9.11\bin\mvn.cmd -version
if %ERRORLEVEL% neq 0 (
    echo Error: Maven no esta funcionando correctamente
    pause
    exit /b 1
)

echo.
echo ========================================
echo Opciones de ejecucion:
echo 1. Compilar y ejecutar (exec:java)
echo 2. Limpiar, compilar y ejecutar
echo 3. Crear JAR ejecutable
echo 4. Ejecutar tests
echo ========================================
echo.

set /p choice="Selecciona una opcion (1-4): "

if "%choice%"=="1" (
    echo.
    echo Compilando y ejecutando con Maven...
    C:\Users\unicom\Downloads\apache-maven-3.9.11\bin\mvn.cmd compile exec:java -Dexec.mainClass=com.pap.Main
) else if "%choice%"=="2" (
    echo.
    echo Limpiando, compilando y ejecutando...
    C:\Users\unicom\Downloads\apache-maven-3.9.11\bin\mvn.cmd clean compile exec:java -Dexec.mainClass=com.pap.Main
) else if "%choice%"=="3" (
    echo.
    echo Creando JAR ejecutable...
    C:\Users\unicom\Downloads\apache-maven-3.9.11\bin\mvn.cmd clean package
    echo.
    echo JAR creado en: target\tarea1-0.0.1-SNAPSHOT.jar
    echo Para ejecutar: java -cp target\tarea1-0.0.1-SNAPSHOT.jar;target\lib\* com.pap.Main
) else if "%choice%"=="4" (
    echo.
    echo Ejecutando tests...
    C:\Users\unicom\Downloads\apache-maven-3.9.11\bin\mvn.cmd test
) else (
    echo Opcion invalida. Ejecutando opcion por defecto...
    C:\Users\unicom\Downloads\apache-maven-3.9.11\bin\mvn.cmd compile exec:java -Dexec.mainClass=com.pap.Main
)

echo.
pause

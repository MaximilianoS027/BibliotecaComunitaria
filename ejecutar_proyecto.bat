@echo off
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
echo Ejecutando proyecto Sistema de Gestion de Biblioteca...
echo.
C:\Users\unicom\Downloads\apache-maven-3.9.11\bin\mvn.cmd -f pom.xml -q -DskipTests=true compile exec:java -Dexec.mainClass=com.pap.Main -Dexec.classpathScope=runtime

pause

@echo off
echo ========================================
echo  EJECUTANDO NUEVA VERSION - REGISTRAR BIBLIOTECARIO
echo ========================================

echo Verificando Java...
java -version

echo.
echo Creando directorio de clases...
if not exist target\classes mkdir target\classes

echo.
echo Compilando nueva estructura...
javac -cp "lib\*" -d target\classes src\main\java\datatypes\*.java src\main\java\excepciones\*.java src\main\java\interfaces\*.java src\main\java\logica\*.java src\main\java\persistencia\*.java src\main\java\presentacion\*.java

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Falló la compilación
    pause
    exit /b 1
)

echo.
echo Copiando recursos...
if not exist target\classes\META-INF mkdir target\classes\META-INF
copy src\main\resources\META-INF\persistence.xml target\classes\META-INF\ >nul 2>&1

echo.
echo Ejecutando aplicación...
java -cp "target\classes;lib\*" presentacion.Principal

echo.
echo Aplicación finalizada.
pause



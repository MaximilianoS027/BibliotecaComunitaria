@echo off
echo ========================================
echo  PROBANDO FUNCIONALIDAD: REGISTRAR BIBLIOTECARIO
echo ========================================

echo.
echo Compilando con Maven...
call mvn clean compile -q

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Falló la compilación
    pause
    exit /b 1
)

echo.
echo Ejecutando aplicación...
echo INSTRUCCIONES:
echo 1. Se abrirá la ventana principal
echo 2. Ir al menú "Bibliotecarios" 
echo 3. Seleccionar "Registrar Bibliotecario"
echo 4. Llenar el formulario y probar
echo.
pause

java -cp "target/classes;lib/*" MainNuevo

echo.
echo Prueba finalizada.
pause

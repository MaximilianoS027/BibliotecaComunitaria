@echo off
echo ========================================
echo  CONFIGURACION DE BASE DE DATOS
echo  Sistema de Gestion de Biblioteca
echo ========================================
echo.

echo 1. Verificando Docker...
docker --version
if %ERRORLEVEL% neq 0 (
    echo ERROR: Docker no esta instalado o no esta funcionando
    echo Por favor instala Docker Desktop desde: https://www.docker.com/products/docker-desktop
    pause
    exit /b 1
)

echo.
echo 2. Creando contenedor PostgreSQL...
echo Comando: docker run --name database-container -e POSTGRES_USER=el-username -e POSTGRES_PASSWORD=la-password -e POSTGRES_DB=database-name -p 5434:5432 -d postgres

docker run --name database-container -e POSTGRES_USER=el-username -e POSTGRES_PASSWORD=la-password -e POSTGRES_DB=database-name -p 5434:5432 -d postgres

if %ERRORLEVEL% neq 0 (
    echo.
    echo El contenedor podria ya existir. Intentando iniciarlo...
    docker start database-container
)

echo.
echo 3. Verificando que el contenedor este ejecutandose...
docker ps | findstr database-container

echo.
echo ========================================
echo  CONFIGURACION COMPLETA
echo ========================================
echo.
echo Datos de conexion para Postico:
echo   Host: localhost
echo   Database: database-name
echo   User: el-username
echo   Password: la-password
echo   Port: 5434
echo   Nickname: Programacion de Aplicaciones - BD Local
echo.
echo Para usar PostgreSQL en tu proyecto, cambia el archivo hibernate.cfg.xml
echo por hibernate-postgresql.cfg.xml
echo.
pause

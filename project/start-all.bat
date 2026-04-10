@echo off
chcp 65001 >nul 2>&1
echo ============================================
echo    Car Owner Service System - Quick Start
echo ============================================
echo.

REM Enter project directory
cd /d "%~dp0"

REM Check Docker
echo [1/6] Checking Docker status...
docker --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Docker not installed!
    echo Please install Docker: https://www.docker.com/products/docker-desktop
    pause
    exit /b 1
)

REM Check and manage Redis container
echo [2/6] Checking Redis container status...
docker ps --filter "name=^redis$" --format "{{.Names}}" | findstr /C:"redis" >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] Redis container is running
) else (
    docker ps -a --filter "name=^redis$" --format "{{.Names}}" | findstr /C:"redis" >nul 2>&1
    if %errorlevel% equ 0 (
        echo [INFO] Starting existing Redis container...
        docker start redis >nul 2>&1
        if %errorlevel% equ 0 (
            echo [OK] Redis container started
        ) else (
            echo [WARNING] Failed to start Redis container
        )
    ) else (
        echo [INFO] Creating Redis container...
        docker run -d --name redis -p 6379:6379 redis:latest >nul 2>&1
        if %errorlevel% equ 0 (
            echo [OK] Redis container created and started
        ) else (
            echo [WARNING] Failed to create Redis container
        )
    )
)

REM Check MySQL
echo [3/6] Checking MySQL status...
mysqladmin ping -h 127.0.0.1 --silent 2>nul
if %errorlevel% neq 0 (
    echo [WARNING] MySQL may not be running. Please ensure MySQL is started.
) else (
    echo [OK] MySQL is running
)

REM Start Spring Boot Backend
echo [4/6] Starting Spring Boot backend service (port 5000)...
echo.
start "Spring Boot Backend" cmd /k "cd /d "%~dp0" && call mvn spring-boot:run"
timeout /t 3 /nobreak >nul

REM Start Admin Frontend
echo [5/6] Starting Admin frontend (port 8081)...
cd /d "%~dp0src\main\resources\static\admin"
if exist "node_modules" (
    start "Admin Frontend" cmd /k "cd /d "%~dp0src\main\resources\static\admin" && npm run dev"
) else (
    echo [WARNING] Admin frontend node_modules not found. Please run: cd admin ^&^& npm install
)

REM Start Client Frontend
echo [6/6] Starting Client frontend (port 8080)...
cd /d "%~dp0..\client_home"
if exist "node_modules" (
    start "Client Frontend" cmd /k "cd /d "%~dp0..\client_home" && npm run serve"
) else (
    echo [WARNING] Client frontend node_modules not found. Please run: cd client_home ^&^& npm install
)

echo.
echo ============================================
echo    All services are starting...
echo ============================================
echo.
echo Access URLs:
echo   - Client Frontend: http://localhost:8080
echo   - Admin Frontend:  http://localhost:8081
echo   - Backend API:    http://localhost:5000/api
echo.
echo Press any key to exit this window...
pause >nul

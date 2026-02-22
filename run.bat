@echo off
title Movie Management System
color 0A

:: --- CAU HINH ---
set SRC_DIR=src
set OUT_DIR=bin
set LIB_DIR=lib
set MAIN_CLASS=vn.MovieManagement.Main
set MAIN_CLASS_FILE=%OUT_DIR%\vn\MovieManagement\Main.class

if exist "%MAIN_CLASS_FILE%" goto RUN_APP

echo [INFO] The system is being set up for the first time. Please wait a few seconds...
if not exist %OUT_DIR% mkdir %OUT_DIR%

javac -d %OUT_DIR% -sourcepath %SRC_DIR% -cp "%LIB_DIR%\*" -encoding UTF-8 %SRC_DIR%\vn\MovieManagement\Main.java

if %errorlevel% neq 0 (
    color 0C
    echo.
    echo [ERROR] Translation error! Please double-check the source code.
    pause
    exit /b %errorlevel%
)

:RUN_APP
java -cp "%OUT_DIR%;%LIB_DIR%\*" %MAIN_CLASS%

echo.
echo [INFO] The program has ended
pause
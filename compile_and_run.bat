@echo off
rem ------------------------------------------------------------
rem compile_and_run.bat – compile seluruh proyek Java dan jalankan Main
rem ------------------------------------------------------------

rem ---------- Settings ----------
rem Path to JDK
set "JDK_PATH=C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot\bin"
set "JAVAC=%JDK_PATH%\javac.exe"
set "JAVA=%JDK_PATH%\java.exe"

rem ---------- Clean previous build ----------
if exist bin (
    echo Removing old build folder...
    rmdir /s /q bin
)
mkdir bin

rem ---------- Locate all source files ----------
dir /s /b *.java > sources.txt

rem ---------- Compile ----------
echo Compiling sources...
"%JAVAC%" -d bin -encoding UTF-8 -classpath . @sources.txt
if errorlevel 1 (
    echo ------------------------------------------------------------
    echo Compilation failed. See messages above.
    del sources.txt
    pause
    exit /b 1
)

rem ---------- Run ----------
echo ------------------------------------------------------------
echo Running application...
"%JAVA%" -cp bin com.koperasi.Main

rem Cleanup temporary file
del sources.txt

pause

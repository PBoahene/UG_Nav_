@echo off
REM === Set Java and JavaFX paths ===

set JAVA_HOME="C:\Program Files\Java\jdk-24"
set JAVAFX_SDK="C:\javafx-sdk-21"

REM === Compile all Java files manually ===

echo Compiling Java files...

%JAVA_HOME%\bin\javac ^
  --module-path %JAVAFX_SDK%\lib ^
  --add-modules javafx.controls ^
  Main.java Graph.java Route.java Sorter.java MapCanvas.java

IF %ERRORLEVEL% NEQ 0 (
    echo Compilation failed. Exiting...
    pause
    exit /b
)

REM === Run the application ===

echo Running UG Navigate...

%JAVA_HOME%\bin\java ^
  --module-path %JAVAFX_SDK%\lib ^
  --add-modules javafx.controls ^
  Main

pause

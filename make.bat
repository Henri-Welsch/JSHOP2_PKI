@echo off
title Building JSHOP
set arg=%1
set domain=%2
set problem=%3
IF "%arg%" == "" (
GOTO BLANK
)
if "%arg%"=="run" (
GOTO RUN
) else (
GOTO COMPILE
)

:COMPILE
echo Setting Classpath ...
set CLASSPATH=%CLASSPATH%;%cd%\antlr.jar
echo %CLASSPATH%
cd src/JSHOP2
echo Parsing JSHOP2.g with antlr
java antlr.Tool JSHOP2.g
echo Compiling Java Classes
javac *.java
cd ..
echo Building Jar
jar cvf JSHOP2.jar JSHOP2/*.class
move JSHOP2.jar ..\bin.build\
cd ..
echo Done
pause
goto END

:RUN
echo Setting Classpath ...
set CLASSPATH=;%cd%\antlr.jar;%cd%\bin.build\JSHOP2.jar
echo %CLASSPATH%
cd application\%domain%
REM Domain
java -cp %CLASSPATH% JSHOP2.InternalDomain %domain%
REM Problem
java -cp %CLASSPATH% JSHOP2.InternalDomain -ra %problem%
javac *.java
java -Xms4g -Xmx8g -Xss4m gui %problem%
cd ..\..
goto END
 
:BLANK
echo usage: make.bat compile or make.bat run domain problem 
pause

:END
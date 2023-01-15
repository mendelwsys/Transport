@echo off
rem
rem This batch file builds and preverifies the code for the demos.
rem it then packages them in a JAR file appropriately.
rem

if "%OS%" == "Windows_NT" setlocal

if "%JAVA_HOME%" == "" (
echo ***** set JAVA_HOME *********
set JAVA_HOME=D:\ProgramFiles\Java\j2sdk1.4.2_16\
)

set JAVA_HOME=D:\ProgramFiles\Java\j2sdk1.4.2_16

if "%WTK_HOME%" == "" (
echo ***** set WTK_HOME ***********
set WTK_HOME=D:\ProgramFiles\WTK22\
)

set LIB_DIR=%WTK_HOME%\lib
set CLDCAPI=%LIB_DIR%\cldcapi10.jar
set MIDPAPI=%LIB_DIR%\midpapi20.jar
set WMA=%LIB_DIR%\wma.jar
set PREVERIFY=%WTK_HOME%\bin\preverify
set JAVAC=javac
set JAR=%JAVA_HOME%\bin\jar
set JAVA=%JAVA_HOME%\bin\java


PUSHD .\classes
%JAVA_HOME%\bin\jar -cvfM ..\multiplexer_midl.jar su\org\coder\multiplexer\*.class su\org\coder\multiplexer\client\*.class su\org\coder\multiplexer\server\*.class su\org\coder\multiplexer\protocols\IChannelFactory.class su\org\coder\multiplexer\protocols\IChannelFactoryImpl.class su\org\coder\multiplexer\protocols\IChannelFactoryImpl$1.class su\org\coder\multiplexer\protocols\IChannelFactoryImpl$2.class su\org\coder\multiplexer\protocols\ILgChannel.class su\org\coder\multiplexer\protocols\ILgChannelImpl.class su\org\coder\multiplexer\protocols\TunelMessage.class su\org\coder\multiplexer\protocols\http3url\HttpTunelMessage2.class
POPD

mkdir tmpclasses
PUSHD .\tmpclasses
%JAR% -xf ..\multiplexer_midl.jar 
rem del /S/Q META-INF
del ..\multiplexer_midl.jar
rem rmdir /S/Q  META-INF
POPD

%PREVERIFY% -classpath %CLDCAPI%;%MIDPAPI%;%WMA%;.\tmpclasses;..\utils\utils_mdl.jar -d .\midletclass .\tmpclasses

PUSHD .\midletclass
%JAR% -cvfM ..\multiplexer_midl.jar su
POPD
rmdir /S/Q .\tmpclasses
rmdir /S/Q .\midletclass

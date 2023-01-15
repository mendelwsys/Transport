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
%JAR% -cvfM ..\utils_mdl.jar su\org\coder\utils\TypeId.class su\org\coder\utils\SysCoderEx.class su\org\coder\utils\String0AHelper.class su\org\coder\utils\SkelRouter.class su\org\coder\utils\ISerializeHelper.class su\org\coder\utils\Integer0AHelper.class su\org\coder\utils\IMetaInfo.class su\org\coder\utils\IProxyParent.class su\org\coder\utils\ILgMessage.class su\org\coder\utils\IInvoker.class su\org\coder\utils\IInterceptorSeq.class su\org\coder\utils\IInterceptor.class su\org\coder\utils\FiFo.class su\org\coder\utils\ExceptionAddInfo0AHelper.class su\org\coder\utils\ExceptionAddInfo.class su\org\coder\utils\DebCounter.class su\org\coder\utils\Constants.class su\org\coder\utils\CallMessageImpl.class su\org\coder\utils\Boolean0AHelper.class su\org\coder\utils\Bukovki.class su\org\coder\utils\SerialUtils.class
POPD

mkdir .\tmpclasses
PUSHD .\tmpclasses
%JAR% -xf ..\utils_mdl.jar 
rem rmdir /S/Q META-INF
del /S/Q ..\utils_mdl.jar
POPD

%PREVERIFY% -classpath %CLDCAPI%;%MIDPAPI%;%WMA%;.\tmpclasses -d .\midletclass .\tmpclasses

PUSHD .\midletclass
%JAR% -cvfM ..\utils_mdl.jar su
POPD
rmdir /S/Q .\tmpclasses
rmdir /S/Q .\midletclass

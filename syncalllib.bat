@echo off
set DEST_DIR=%TOMCAT_HOME%shared\lib
PUSHD utils
call buildjar.bat %DEST_DIR% utils.jar
POPD
PUSHD multiplexer 
call buildjar.bat %DEST_DIR% multiplexer.jar
POPD

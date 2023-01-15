@echo off
PUSHD classes
%JAVA_HOME%bin\jar -cvf  %1\%2 su
POPD

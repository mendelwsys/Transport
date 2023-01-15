rem generate java code for idl files java code depends on util module

java -Dfile.encoding=windows-1251 -jar .\jars\coder.jar .\idl\rctrl.idl  -td..\TstStack\src\ -pkgPrefixsu.org.coder2
java -Dfile.encoding=windows-1251 -jar .\jars\coder.jar .\idl\gpsinterface.idl  -td..\TstStack\src\ -pkgPrefixsu.org.coder2

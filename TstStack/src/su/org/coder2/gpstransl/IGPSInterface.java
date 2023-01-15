package su.org.coder2.gpstransl;

import su.org.coder.utils.SysCoderEx;

import java.io.IOException;

public interface IGPSInterface
{ 

	ClientInfo[] getClientInfo() throws IOException, SysCoderEx; 

	dataStruct get(int idclient,int dotnumber,int timeOut) throws IOException, SysCoderEx; 

	void put(dataStruct message) throws IOException, SysCoderEx; 

	void putmsg(messageStruct strmessage) throws IOException, SysCoderEx; 

	messageStruct getmsg(int idclient,int msgnumber,int timeOut) throws IOException, SysCoderEx; 

	dataStruct[] getHistory(int idclient,int dotnumber,int timeOut) throws IOException, SysCoderEx; 

	void resetHistory(int idclient) throws IOException, SysCoderEx; 

	void ping() throws IOException, SysCoderEx; 

}

package su.org.coder2.chat;

import su.org.coder.utils.SysCoderEx;

import java.io.IOException;

public interface IChatInterface
{ 

	void Put(String message) throws IOException, SysCoderEx, TException1, TException2; 

	String Get(int timeOut) throws IOException, SysCoderEx; 

	void DestroyObject(String1AHolder myarr) throws IOException, SysCoderEx, TException2; 

	String M4(byte arg1_oct,byte[] argarr_in,byte1AHolder argarr_inout) throws IOException, SysCoderEx; 

	byte M5(boolean arg0,short arg2,boolean0AHolder arg3) throws IOException, SysCoderEx; 

}

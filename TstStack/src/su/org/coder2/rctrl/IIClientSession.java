package su.org.coder2.rctrl;

import su.org.coder.utils.SysCoderEx;

import java.io.IOException;

public interface IIClientSession
{ 

	String openSession(String username,String userpass) throws IOException, SysCoderEx; 

	boolean checkSession(String cliname) throws IOException, SysCoderEx; 

	int closeSession(String cliname) throws IOException, SysCoderEx; 

}

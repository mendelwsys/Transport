package su.org.coder.interceptors.authinterceptor;

import su.org.coder.utils.SysCoderEx;

import java.io.IOException;

public interface IAuthenticate
{ 

	String beginAuth(String userName) throws IOException, SysCoderEx; 

	String endAuth(String guessPhrase) throws IOException, SysCoderEx; 

}

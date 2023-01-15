package su.org.coder2.gpstransl;

import su.org.coder.utils.SysCoderEx;

import java.io.IOException;

public interface IGPSClient
{ 

	void startFlow(boolean reconnect) throws IOException, SysCoderEx; 

	void stopFlow() throws IOException, SysCoderEx; 

}

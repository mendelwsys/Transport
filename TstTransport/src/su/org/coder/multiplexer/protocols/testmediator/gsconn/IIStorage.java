package su.org.coder.multiplexer.protocols.testmediator.gsconn;

import su.org.coder.utils.SysCoderEx;

import java.io.IOException;

public interface IIStorage
{ 

	int getCurpos() throws IOException, SysCoderEx; 

	String getstrorage(int i) throws IOException, SysCoderEx; 

	boolean hasMoreElements() throws IOException, SysCoderEx; 

	boolean hasPrevElements() throws IOException, SysCoderEx; 

	String nextElement() throws IOException, SysCoderEx; 

	int setposon(int pos) throws IOException, SysCoderEx; 

	String prevElement() throws IOException, SysCoderEx; 

	int size() throws IOException, SysCoderEx; 

}

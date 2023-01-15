package su.org.coder2.rctrl;

import su.org.coder.utils.SysCoderEx;

import java.io.IOException;

public interface IIStorage
{ 

	int getCurpos(String cliname) throws IOException, SysCoderEx; 

	String getstrorage(String cliname,int i) throws IOException, SysCoderEx; 

	boolean hasMoreElements(String cliname) throws IOException, SysCoderEx; 

	boolean hasPrevElements(String cliname) throws IOException, SysCoderEx; 

	String nextElement(String cliname) throws IOException, SysCoderEx; 

	int setposon(String cliname,int pos) throws IOException, SysCoderEx; 

	String prevElement(String cliname) throws IOException, SysCoderEx; 

	int size(String cliname) throws IOException, SysCoderEx; 

}

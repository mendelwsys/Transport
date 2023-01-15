package su.org.coder2.rctrl;

import su.org.coder.utils.SysCoderEx;

import java.io.IOException;

public interface IIRctrl
{ 

	int setOptions(String cliname,PairOptions[][] options) throws IOException, SysCoderEx; 

	PairOptions[][] getOptions(String cliname) throws IOException, SysCoderEx; 

	int getStatus(String cliname) throws IOException, SysCoderEx; 

	byte[] getInitialViewParams(String cliname,int drwWidth,int drwHeight) throws IOException, SysCoderEx; 

	byte[] getViewParams(String cliname,int drwWidth,int drwHeight) throws IOException, SysCoderEx; 

	int setDrawNames(String cliname,boolean setOn) throws IOException, SysCoderEx; 

	int setSpecAction(String cliname,String action,byte[] params) throws IOException, SysCoderEx; 

	int setSpecActionOff(String cliname) throws IOException, SysCoderEx; 

	int setSpecActionOn(String cliname) throws IOException, SysCoderEx; 

}

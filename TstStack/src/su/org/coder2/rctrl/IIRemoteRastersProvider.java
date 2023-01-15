package su.org.coder2.rctrl;

import su.org.coder.utils.SysCoderEx;

import java.io.IOException;

public interface IIRemoteRastersProvider
{ 

	int dividerRequest(String cliname,byte[] cnv,double[] bp,double2AHolder mp12,double2AHolder pt12,int1AHolder dxdy,int1AHolder szXszY) throws IOException, SysCoderEx; 

	ImagePair getImageRequest(String cliname,double[] dxdy,int[] nxny) throws IOException, SysCoderEx; 

	ImagePair[] getImagesRequest(String cliname,double[] dxdy,int[][] nxny) throws IOException, SysCoderEx; 

	byte[] getLegentRaster(String cliname,int width,int high) throws IOException, SysCoderEx; 

	byte[] getScaleRulerRaster(String cliname,int width,int high) throws IOException, SysCoderEx; 

}

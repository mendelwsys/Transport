package su.org.coder2.rctrl;

import su.org.coder.utils.SysCoderEx;

import java.io.IOException;

public interface IIStatAnalize
{ 

	RetSelRastr getLevelByMousePnt(String cliname,MouseCrd crd) throws IOException, SysCoderEx; 

}

package su.org.coder2.test00.servants;

import su.org.coder.utils.SysCoderEx;
import su.org.coder2.chat.*;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 08.02.2005
 * Time: 17:05:00
 * Service part implementation actually could use on server or client side
 * for call the service use ProxyChatInterface on the other side
 */
public class ChatImpl00 extends SkelChatInterface
{
	public void Put(String message) throws IOException, SysCoderEx
	{
		System.out.println("## Ping with: "+message+" (=ChatImpl00 Put=)");
  	}

	public String Get(int timeOut) throws IOException, SysCoderEx
	{
		System.out.println("## Pong with: "+timeOut+" (=ChatImpl00 Get=)");
		return "ChatImpl00 Get: "+(timeOut - 100);
	}

	public void DestroyObject(String1AHolder myarr) throws IOException, SysCoderEx, TException2
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

//	public int M1(int arg1_lng, double arg2_dbl, String arg3_str) throws IOException, SysCoderEx
//	{
//		System.out.println("M1 Call");
//		System.out.println("arg1_lng:"+arg1_lng);
//		System.out.println("arg2_dbl:"+arg2_dbl);
//		System.out.println("arg3_str:"+arg3_str);
//		return 6969;  //To change body of implemented methods use File | Settings | File Templates.
//	}
//
//	public double M2(int0AHolder arg1_lng, double0AHolder arg2_dbl, String0AHolder arg3_str) throws IOException, SysCoderEx
//	{
//		System.out.println("M2 Call");
//		arg1_lng.value=1;
//		arg2_dbl.value=2.0;
//		arg3_str.value="arg3_str";
//		return 12.1;
//	}
//
//	public String M3(int0AHolder arg1_lng, double0AHolder arg2_dbl, String0AHolder arg3_str) throws IOException, SysCoderEx
//	{
//		System.out.println("M3 Call");
//		System.out.println("arg1_lng:"+arg1_lng.value);
//		System.out.println("arg2_dbl:"+arg2_dbl.value);
//		System.out.println("arg3_str:"+arg3_str.value);
//
//		arg1_lng.value=11;
//		arg2_dbl.value=22.0;
//		arg3_str.value="arg33_str";
//
//		return "Method3";
//	}

	public String M4(byte arg1_oct, byte[] argarr_in, byte1AHolder argarr_inout) throws IOException, SysCoderEx
	{
		System.out.println("Arg0:"+arg1_oct);
		System.out.println("Arr1:");
		for (int i = 0;argarr_in!=null && i < argarr_in.length; i++)
			System.out.println(" "+i+" "+argarr_in[i]);
		System.out.println("Arr2:");
		for (int i = 0; argarr_inout.value!=null && i<argarr_inout.value.length; i++)
			System.out.println(" "+i+" "+argarr_inout.value[i]);
		argarr_inout.value=new byte[150];
		for (int i = 0; argarr_inout.value!=null && i<argarr_inout.value.length; i++)
			argarr_inout.value[i]=(byte) i;

		return "Ok";
	}


	public byte M5(boolean arg0, short arg2, boolean0AHolder arg3) throws IOException, SysCoderEx
	{
		System.out.println("arg0:"+arg0);
		System.out.println("arg2:"+arg2);
		System.out.println("arg3:"+arg3.value);
		arg3.value=!arg3.value;
		return 1;
	}

}

package su.org.coder2.test00.server;

import su.org.coder.multiplexer.TMultiplexerEx;
import su.org.coder.multiplexer.client.ClientConnector;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.IService;
import su.org.coder.utils.SysCoderEx;
import su.org.coder2.chat.*;
import su.org.coder2.test00.Constants;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 20.02.2005
 * Time: 21:37:38
 * To change this template use File | Settings | File Templates.
 */
public class IHiTstImpl4CallBack01
		implements IService
{
	public void initService(Object cfg)
	{
		System.out.println("Init "+this.getClass().getCanonicalName());
	}

	public void startService(ILgChannel chan)
	{
		System.out.println("Start "+this.getClass().getCanonicalName());
		try
		{
			System.out.println("Before Connect to client....");
			ClientConnector cl = new ClientConnector(chan,0);
			System.out.println("Connect to client....Ok");
			System.out.println("ProxyChatInterface.bind....");
			//get service through ProxyChatInterface
			IChatInterface ichat = ProxyChatInterface.bind(cl, Constants.REG_NAME01);
			System.out.println("ProxyChatInterface.bind....Ok");

			sCall(ichat,2);
//			IAuthenticate auth=ProxyAuthenticate.bind (cl, Constants.REG_INTERCEPT);
//			System.out.println("Call auth.beginAuth"+auth.beginAuth("ProbAuth"));
// 			System.out.println("Call auth.endAuth"+auth.endAuth("ProbAuth"));
			sCall(ichat,10);
		}
		catch (TMultiplexerEx tMultiplexerEx)
		{
			System.out.println("Test stoped by TMultiplexerEx exception:"+tMultiplexerEx.getMessage());
		}
		catch (SysCoderEx sysCoderEx)
		{
			System.out.println("Test stoped by SysCoderEx exception:"+sysCoderEx.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("Test stoped by IOException exception:"+e.getMessage());
		}
		catch (TException2 tException2)
		{
			System.out.println("Test stoped by TException2 exception:"+tException2.getMessage());
		}
		catch (TException1 tException1)
		{
			System.out.println("Test stoped by TException1 exception:"+tException1.getMessage());
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	private void sCall(IChatInterface intrf1,int cnt)
			throws IOException, SysCoderEx, TException1, TException2, InterruptedException
	{
		int i=0;
		byte1AHolder arg3 = new byte1AHolder();
		arg3.value= new byte[]{8,9,10,11,12,13,14,15,16};
		System.out.println("M4:"+intrf1.M4((byte) 1,new byte[]{1,2,3,4,5,6,8},arg3));
		System.out.println("Arg3:");
		for (i = 0; arg3.value!=null && i<arg3.value.length; i++)
			System.out.println(" "+i+" "+arg3.value[i]);

		boolean0AHolder arg53 = new boolean0AHolder(false);
		System.out.println("M5:"+intrf1.M5(true,(short) 13,arg53));
		System.out.println("Arg53:"+arg53.value);

//		System.out.println("M1:"+intrf1.M1(0,1.0,"str1"+i));
//		int0AHolder arg1 = new int0AHolder();
//		double0AHolder arg2 = new double0AHolder();
//		String0AHolder arg3 = new String0AHolder();
//		System.out.println("M2:"+intrf1.M2(arg1,arg2,arg3));
//		System.out.println("arg1:"+arg1.value);
//		System.out.println("arg2:"+arg2.value);
//		System.out.println("arg3:"+arg3.value);
//		System.out.println("M3:"+intrf1.M3(arg1,arg2,arg3));
//		System.out.println("arg1:"+arg1.value);
//		System.out.println("arg2:"+arg2.value);
//		System.out.println("arg3:"+arg3.value);

//		for (i=0;i<cnt;i++)
//		{
//			System.out.println("Put message "+i+"...");
//			intrf1.Put("Message"+i);
//			System.out.println("Put message "+i+"...Ok");
//			System.out.println("Get message...");
//			System.out.println(intrf1.Get(150));
//			System.out.println("Get message...Ok");
//			Thread.sleep(1000);
//		 }
	}

}

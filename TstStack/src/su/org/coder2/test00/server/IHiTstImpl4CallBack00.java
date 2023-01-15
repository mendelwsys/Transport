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
public class IHiTstImpl4CallBack00
		implements IService
{
	public void initService(Object cfg)
	{
		System.out.println("Init "+ getClass().getCanonicalName());
	}

	public void startService(ILgChannel chan)
	{
		System.out.println("Start "+getClass().getCanonicalName());
		try
		{
			System.out.println("Before Connect to client....");
			ClientConnector cl = new ClientConnector(chan,0);
			System.out.println("Connect to client....Ok");
			System.out.println("ProxyChatInterface.bind....");
			//get service through ProxyChatInterface
			IChatInterface ichat = ProxyChatInterface.bind(cl, Constants.REG_NAME01);
			System.out.println("ProxyChatInterface.bind....Ok");
			sCall(ichat,5);
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

		for (int i=0;i<cnt;i++)
		{
			System.out.println("Put message "+i+"...");
			intrf1.Put("Ping "+i);
			System.out.println("Put message "+i+"...Ok");
			System.out.println("Get message...");
			System.out.println(intrf1.Get(150));
			System.out.println("Get message...Ok");
			Thread.sleep(1000);
		 }
	}

}

package su.org.coder.multiplexer.server;

import su.org.coder.utils.*;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.TMultiplexerEx;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.02.2005
 * Time: 2:21:48
 * To change this template use File | Settings | File Templates.
 */
class ServerConnector extends  IInterceptorSeq
		implements Runnable
{
	private ILgChannel channel;
	private boolean Terminate=false;


	ServerConnector(ILgChannel ch,IInterceptor interseptor)
	{
		super(interseptor);
		this.channel=ch;
	}

	public void run()
	{
		CallMessageImpl msg=null;
		try
		{
			Vector vc=new Vector();
			vc.addElement(channel);
			System.out.println("Start server job cycle");
			while (!Terminate)
			{
				System.out.println("Before getting message from channel....");
				msg=(CallMessageImpl) channel.get(0);
				System.out.println("Message got");

                if (msg!=null)
				{
					System.out.println("Message is not null so command call begin...");
					CallMessageImpl l_message = invoke(msg,vc);
					if (l_message.command!=Constants.RET_VAL_NORET)
						channel.put(l_message);
					System.out.println("Command call end");
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (SysCoderEx sysCoderEx)
		{
			sysCoderEx.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (TMultiplexerEx tMultiplexerEx)
		{
			System.out.println("ServerConnector stoped Exception: "+tMultiplexerEx.getMessage());
		}
		finally
		{
			Terminate=true;
			channel=null;
		}
	}

}
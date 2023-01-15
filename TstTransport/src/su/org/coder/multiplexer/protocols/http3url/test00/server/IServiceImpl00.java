package su.org.coder.multiplexer.protocols.http3url.test00.server;

import su.org.coder.multiplexer.protocols.IService;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.TunelMessage;
import su.org.coder.multiplexer.TMultiplexerEx;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 20.02.2005
 * Time: 21:37:38
 * To change this template use File | Settings | File Templates.
 */
public class IServiceImpl00 implements IService
{
	public void initService(Object cfg)
	{
		System.out.println("Init su.org.coder.multiplexer.protocols.http3url.test00.server.ServiceImpl00");
	}

	public void startService(ILgChannel chan)
	{
		try
		{
			TunelMessage msg=null;
			do
			{
				msg = (TunelMessage) chan.get(0);
				msg.pPrint();
				chan.put(msg);
			} while (!msg.isFinalMessage());
		}
		catch (TMultiplexerEx tMultiplexerEx)
		{
			tMultiplexerEx.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

}

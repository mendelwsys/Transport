package su.org.coder.multiplexer.server;

import su.org.coder.utils.IInterceptor;
import su.org.coder.multiplexer.protocols.IChannelFactoryImpl;
import su.org.coder.multiplexer.TMultiplexerEx;
import su.org.coder.multiplexer.MultiplexerMessage;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.IChannelFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.02.2005
 * Time: 2:06:17
 */
public class ServerListener
{
	private IInterceptor interseptor;
	private IChannelFactory chf;

	public ServerListener(IInterceptor interseptor, ILgChannel sysChannel,int maxBuffSize,int putTimeOut)
	{
		this.interseptor = interseptor;
		chf = new IChannelFactoryImpl(new MultiplexerMessage(), sysChannel,maxBuffSize,putTimeOut);
		chf.startFactory();
	}

	public ServerListener(IInterceptor interseptor, ILgChannel sysChannel)
	{
		this.interseptor = interseptor;
		chf = new IChannelFactoryImpl(new MultiplexerMessage(), sysChannel);
		chf.startFactory();
	}

	public ServerListener(IInterceptor interseptor, IChannelFactory chf)
	{
		this.interseptor = interseptor;
		this.chf = chf;
	}

	public IChannelFactory getChannelFactory()
	{
		return chf;
	}

	public void startService(String args[])
	{
		try
		{
			while (true)
			{
				System.out.println("Wait for accept it...");
				ILgChannel ch = chf.accept(0);
				System.out.println("Wait for accept it...OK");
				//Стартуем поток для обслуживания этого канала
				new Thread(new ServerConnector(ch, interseptor)).start();
			}
		}
		catch (TMultiplexerEx tMultiplexerEx)
		{
			System.out.println("End of channel accept Exception : " + tMultiplexerEx.getMessage());
		}
	}

}
package su.org.coder.multiplexer.protocols;

import su.org.coder.multiplexer.IMultiplexerable;
import su.org.coder.multiplexer.TMultiplexerEx;

/**
 * Created by IntelliJ IDEA.
 * User: VLADM
 * Date: 02.03.2006
 * Time: 19:51:58
 * To change this template use File | Settings | File Templates.
 */
public class IChannelFactoryServerImpl
	extends IChannelFactoryImpl
{
	public IChannelFactoryServerImpl(IMultiplexerable payLoadFactory, ILgChannel syschannel, int maxBuffSize,
									 int putTimeOut)
	{
		super(payLoadFactory, syschannel, maxBuffSize, putTimeOut);
	}

	public IChannelFactoryServerImpl(IMultiplexerable payLoadFactory, ILgChannel syschannel)
	{
		super(payLoadFactory, syschannel);
	}

	public void setStopFactory(int timeOut) throws TMultiplexerEx
	{
		inStopState=true;
		try
		{
			if (writethread!=null)
			{
				writethread.join(timeOut);
				if (writethread.isAlive())
					throw new TMultiplexerEx("TimeOut of writing thread expired");
				writethread=null;
			}

			if (readthread!=null)
			{
				readthread.join(timeOut);
				if (readthread.isAlive())
					throw new TMultiplexerEx("TimeOut of reading thread expired");
				readthread=null;
			}
		}
		catch (InterruptedException e)
		{
			throw new TMultiplexerEx(e);
		}
	}
}
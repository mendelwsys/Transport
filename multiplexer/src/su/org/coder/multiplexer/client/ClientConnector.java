package su.org.coder.multiplexer.client;

import su.org.coder.utils.*;
import su.org.coder.multiplexer.protocols.IChannelFactoryImpl;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.IChannelFactory;
import su.org.coder.multiplexer.TMultiplexerEx;
import su.org.coder.multiplexer.MultiplexerMessage;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.02.2005
 * Time: 2:31:41
 * To change this template use File | Settings | File Templates.
 */
public class ClientConnector implements IInvoker
{
	private IChannelFactory chf;
	protected ILgChannel channel;
	private short typeID=TypeId.getNextId();


	public ClientConnector(ILgChannel sysChannel,int timeOut,int maxBuffSize,int putTimeOut) throws TMultiplexerEx
	{

		chf=new IChannelFactoryImpl(new MultiplexerMessage(),sysChannel,maxBuffSize,putTimeOut);
		chf.startFactory();
		channel=chf.connect(timeOut);
	}

	public ClientConnector(ILgChannel sysChannel,int timeOut) throws TMultiplexerEx
	{

		chf=new IChannelFactoryImpl(new MultiplexerMessage(),sysChannel);
		chf.startFactory();
		channel=chf.connect(timeOut);
	}

	public ClientConnector (IChannelFactory chf,int timeOut) throws TMultiplexerEx
	{
		this.chf=chf;
		channel=chf.connect(timeOut);
	}

	public IChannelFactory getChannelFactory()
	{
		return chf;
	}

	public CallMessageImpl invoke(CallMessageImpl msg,Vector attr) throws IOException, SysCoderEx
	{
		try
		{             
			channel.put(msg);
			if (msg.command==Constants.INVOKE_VAL_NORET)
				return new CallMessageImpl(Constants.RET_VAL_NORET,msg.typeID,msg.objID,msg.methodCode,null);
			return (CallMessageImpl) channel.get(0);
		}
		catch (Exception e)
		{
			throw new IOException(e.getMessage());
		}
	}

	public boolean isOnService() throws IOException, SysCoderEx
	{
		return (channel.isActualIn() && channel.isActualOut());
	}

	public String getTypeName()
	{
		return "MultClientConnector";
	}

	public short getTypeID()
	{
		return typeID;
	}

}
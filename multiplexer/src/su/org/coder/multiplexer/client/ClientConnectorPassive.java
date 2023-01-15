package su.org.coder.multiplexer.client;

import su.org.coder.utils.*;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.IChannelFactory;
import su.org.coder.multiplexer.TMultiplexerEx;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.02.2005
 * Time: 2:31:41
 * To change this template use File | Settings | File Templates.
 */
public class ClientConnectorPassive
		extends ClientConnector
{
	public ClientConnectorPassive(ILgChannel sysChannel,int timeOut,int maxBuffSize,int putTimeOut) throws TMultiplexerEx
	{
		super(sysChannel,timeOut,maxBuffSize,putTimeOut);
	}

	public ClientConnectorPassive(ILgChannel sysChannel,int timeOut) throws TMultiplexerEx
	{
		super(sysChannel,timeOut);
	}

	public ClientConnectorPassive (IChannelFactory chf,int timeOut) throws TMultiplexerEx
	{
		super(chf,timeOut);

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
		catch (TMultiplexerEx e)
		{
			if (msg.command==Constants.INVOKE_VAL_NORET)
			    return new CallMessageImpl(Constants.RET_VAL_NORET,msg.typeID,msg.objID,msg.methodCode,null);
			throw new IOException(e.getMessage());
		}
	}
}
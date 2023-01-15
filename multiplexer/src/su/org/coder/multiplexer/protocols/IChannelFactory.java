package su.org.coder.multiplexer.protocols;

import su.org.coder.multiplexer.TMultiplexerEx;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 01.03.2005
 * Time: 17:22:48
 * To change this template use File | Settings | File Templates.
 */
public interface IChannelFactory extends ILgChannel
{
	ILgChannel getSysChannel();

	void startFactory();

	ILgChannel accept(int timeOut) throws TMultiplexerEx;

	ILgChannel connect(int timeOut) throws TMultiplexerEx;

	void setStopFactory(int timeOut)  throws TMultiplexerEx;

	
}
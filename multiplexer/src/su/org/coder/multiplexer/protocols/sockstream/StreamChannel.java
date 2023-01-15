package su.org.coder.multiplexer.protocols.sockstream;

import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.TMultiplexerEx;
import su.org.coder.multiplexer.protocols.TunelMessage;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 17.02.2005
 * Time: 22:05:28
 * To change this template use File | Settings | File Templates.
 */
public class StreamChannel implements ILgChannel
{
	private InputStream is;
	private OutputStream os;
	private ITimeOutSetter media;
	private boolean timeOutAsError=false;

	public StreamChannel(InputStream is, OutputStream os, ITimeOutSetter soc,boolean timeoutAsError)
	{
		this.is = is;
		this.os = os;
		this.media = soc;
		this.timeOutAsError = timeoutAsError;
	}


	protected void finalize()
	{
		try
		{
			if (os != null)
				os.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		try
		{
			if (is != null)
				is.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public void put(Object l_message) throws TMultiplexerEx
	{
		try
		{
			if (l_message!=null)
				((TunelMessage)l_message).sendToReceiver(os);
		}
		catch (IOException e)
		{
			throw new TMultiplexerEx(e);
		}
	}


	public Object get(int timeOut) throws TMultiplexerEx
	{
		TunelMessage msg = new TunelMessage();
		try
		{
			if (media!=null)
				media.setTimeOut(timeOut);
			msg.setBySender(is);
			return msg;
		}
		catch (java.net.SocketTimeoutException e)
		{
	        if (timeOutAsError)
				throw new TMultiplexerEx(e);
			System.out.println("Soket time out, soket still valid");
			return null;
		}
		catch (IOException e)
		{
			throw new TMultiplexerEx(e);
		}
	}

	public int getListSize() throws TMultiplexerEx
	{
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public int getRegId()//Возвращает идентификационный номер канала
	{
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public boolean isActualIn()//Возвращает работает канал или нет (можно через него чего нибудь передать или нет)
	{
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public boolean isActualOut()
	{
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public void close() throws TMultiplexerEx
	{
		int Error=0;
		try
		{
			os.close();
		}
		catch (IOException e)
		{
			Error=0x01;
		}
		try
		{
			is.close();
		}
		catch (IOException e)
		{
			Error|=0x02;
		}
		if (Error!=0)
			throw new TMultiplexerEx("ERROR WHILE CLOSE:"+Error);
	}
}
package su.org.coder.multiplexer.protocols.http3url.servlets2;

import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.TMultiplexerEx;
import su.org.coder.multiplexer.protocols.TunelMessage;
import su.org.coder.utils.FiFo;

import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 19.02.2005
 * Time: 15:28:02
 * To change this template use File | Settings | File Templates.
 */
class ServletChannel implements ILgChannel
{
	private FiFo putflow = new FiFo();
	private FiFo getflow = new FiFo();
	private long timeout=10000;

	private class InerChannel implements ILgChannel
	{

		public void put(Object l_message) throws TMultiplexerEx
		{
	        beginchannel=System.currentTimeMillis();
			getflow.put(l_message);
		}

		public Object get(int timeOut) throws TMultiplexerEx
		{
			beginchannel=System.currentTimeMillis();
			return putflow.get(timeOut);
		}

		public int getListSize() throws TMultiplexerEx
		{
			beginchannel=System.currentTimeMillis();
			return putflow.getListSize();
		}

		public int getRegId()//Возвращает идентификационный номер канала
		{
			return 0;  //To change body of implemented methods use File | Settings | File Templates.
		}

		public boolean isActualIn()//Возвращает работает канал или нет (можно через него чего нибудь передать или нет)
		{
			return (System.currentTimeMillis()-beginchannel)<timeout;
		}

		public boolean isActualOut()
		{
			return false;  //To change body of implemented methods use File | Settings | File Templates.
		}

		public void close() throws TMultiplexerEx
		{
			//To change body of implemented methods use File | Settings | File Templates.
		}

	}
	private InerChannel servletSide= new InerChannel();
	private TechChannel techChannel= new TechChannel();

	private volatile long beginchannel=System.currentTimeMillis();

	ServletChannel(int httpchanneID/*TODO Можно дать параметр по времени истечения канала*/)
	{
//    	lstmsg= new HttpTunelMessage2(httpchanneID,0,null);
	}

    public ILgChannel getServletSide()
	{
		return servletSide;
	}

	public void put(Object l_message) throws TMultiplexerEx
	{
		putflow.put(l_message);
	}

	public Object get(int timeOut) throws TMultiplexerEx
	{
		return getflow.get(timeOut);
	}

	public int getListSize() throws TMultiplexerEx
	{
		return getflow.getListSize();
	}

	public int getRegId()//Возвращает идентификационный номер канала
	{
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public boolean isActualIn()
	{
		//System.out.println("IsActual=="+((System.currentTimeMillis()-beginchannel)<timeout));
		return (System.currentTimeMillis() - beginchannel) < timeout;
	}

	public boolean isActualOut()
	{
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public void close() throws TMultiplexerEx
	{
		this.put(new TunelMessage((short) 0,(short) 0,null));
	}

	private class TechChannel implements ILgChannel
	{
		private FiFo fifo=new FiFo();

		public void put(Object l_message) throws TMultiplexerEx
		{
			fifo.put(l_message);
		}

		public Object get(int timeOut) throws TMultiplexerEx
		{
			 return fifo.get(timeOut);
		}

		public int getListSize() throws TMultiplexerEx
		{
			return fifo.getListSize();
		}

		public int getRegId()
		{
			return 0;  //To change body of implemented methods use File | Settings | File Templates.
		}

		public boolean isActualIn()
		{
			return (System.currentTimeMillis()-beginchannel)<timeout;
		}

		public boolean isActualOut()
		{
			return false;  //To change body of implemented methods use File | Settings | File Templates.
		}

		public void close() throws TMultiplexerEx
		{
			//To change body of implemented methods use File | Settings | File Templates.
		}

	}

	public ILgChannel getTechChannel()
	{
		return techChannel;
	}
}
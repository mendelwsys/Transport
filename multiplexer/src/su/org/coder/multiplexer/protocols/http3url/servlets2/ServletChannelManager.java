package su.org.coder.multiplexer.protocols.http3url.servlets2;

import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.TunelMessage;
import su.org.coder.multiplexer.TMultiplexerEx;

import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 19.02.2005
 * Time: 16:25:21
 * To change this template use File | Settings | File Templates.
 */
public class ServletChannelManager
{
	private static ServletChannelManager instance=new ServletChannelManager();
	private ServletChannelManager()
	{
		new Thread()
		{
			public void run()
			{
				while (true)
				{
					// TODO Comment for debugging
					 monitorStep(); //this method provide out of date channel removing
					try
					{
						sleep(1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
					}
				}
			}
		}.start();
	}

	static public ServletChannelManager getInstance()
	{
		return instance;
	}

	private Hashtable channles= new Hashtable();

	private int channelId=10; //TODO Переделать так что бы

	//Have to introduce the channel monitor for delete unusable channels
	//TODO Other variant introduce "still alive" message
	private void monitorStep()
	{
		Enumeration en = channles.keys();
		while (en.hasMoreElements())
		{
			Integer key = (Integer) en.nextElement();
			ServletChannel servletChannel = ((ServletChannel) channles.get(key));
			if (servletChannel!=null && !servletChannel.isActualIn())
			{
				try
				{
					//Послать сообщение о том что канал не действителен
					servletChannel.getServletSide().put(new TunelMessage());
					//Удалить недействительный канал
					System.out.println("remove channel by monitor id: "+key.intValue());
					removeChannelById(key.intValue());
				}
				catch (TMultiplexerEx tMultiplexerEx)
				{
					tMultiplexerEx.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			}
		}
	}

	public ILgChannel getServletChannelById(int id) //TODO Здесь передавать идентификатор с которым проверять свзять с ID
	{
		ServletChannel servletChannel = ((ServletChannel) channles.get(new Integer(id)));
		if (servletChannel!=null)
			return  servletChannel.getServletSide();
		return null;
	}

	public ILgChannel getTechChannelById(int id) //TODO Здесь передавать идентификатор с которым проверять свзять с ID
	{
		ServletChannel servletChannel = ((ServletChannel) channles.get(new Integer(id)));
		if (servletChannel!=null)
			return  servletChannel.getTechChannel();
		return null;
	}

	public ILgChannel getChannelById(int id) //TODO Здесь передавать идентификатор с которым проверять свзять с ID
	{
		return  (ServletChannel) channles.get(new Integer(id));
	}

    synchronized public void removeChannelById(int id)
	{
		channles.remove(new Integer(id));
	}

	synchronized public int createNewChannel() //TODO Здесь можно передавать идентификатор с которым свзать ID
	{
		Integer key = null;
		do
		{
			key = new Integer(channelId++);
		} while (channles.get(key)!=null);
		channles.put(key,new ServletChannel(key.intValue()));
		return key.intValue();
	}
}
package su.org.coder.multiplexer.protocols;

import su.org.coder.utils.FiFo;
import su.org.coder.multiplexer.IMultiplexerable;
import su.org.coder.multiplexer.TMultiplexerEx;

import java.util.Hashtable;
import java.util.Enumeration;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12.02.2005
 * Time: 0:23:04
 * To change this template use File | Settings | File Templates.
 */



public class IChannelFactoryImpl
		implements IChannelFactory
{

	private static final byte CMD_CONNECT = 10;
	private static final byte CMD_DISCONNECT = 15;
	private static final String TUNEL_ERR = "Unknown command";

	private IMultiplexerable payLoadFactory;
	private ILgChannel syschannel;
	private FiFo outMessages = new FiFo(); //Исходящие из объекта сообщения
	private FiFo accepted = new FiFo(); //Мноежство каналов готовых к передачи данных
	private short nextID = 0;

	Hashtable table = new Hashtable(); //Множество очередей входяших в  каналы сообщений
	protected boolean inStopState = true;
	private boolean isActualIn = true;

	protected Thread readthread;
	protected Thread writethread;

	private static final String NOTACTUAL_ERR = "The factory is stoped or not actual";
	private static final String TIMEOUT_ERR = "Connect timeout";
	private int maxBuffSize;
	private int putTimeOut;

	public IChannelFactoryImpl(IMultiplexerable payLoadFactory, ILgChannel syschannel,int maxBuffSize,int putTimeOut)
	{
		this.maxBuffSize = maxBuffSize;
		this.putTimeOut = putTimeOut;
		System.out.println("Call constructor of su.org.coder.multiplexer.protocols.IChannelFactoryImpl");
		this.payLoadFactory = payLoadFactory;
		this.syschannel = syschannel;
	}

	public IChannelFactoryImpl(IMultiplexerable payLoadFactory, ILgChannel syschannel)
	{
		maxBuffSize = -1;
		putTimeOut = 0;
		System.out.println("Call constructor of su.org.coder.multiplexer.protocols.IChannelFactoryImpl");
		this.payLoadFactory = payLoadFactory;
		this.syschannel = syschannel;
	}

	private synchronized short GetNextID()
	{
		do
		{
			++nextID;
		}
		while (table.containsKey(new Integer(nextID)));
		return nextID;
	}

	private void readStep(int timeOut) throws TMultiplexerEx
	{
		TunelMessage msg = (TunelMessage) syschannel.get(timeOut);
		if (msg==null) return;
		if (msg.isFinalMessage())
			throw new TMultiplexerEx("Final message recieved by readStep");
		if (msg.receiverID == 0) //Служебное сообщение читаем комманду
		{
			if (msg.bmessage==null)
				throw new TMultiplexerEx(TUNEL_ERR);
			switch (msg.bmessage[0])
			{
				case CMD_CONNECT://Создать тунельный канал
					{
						short channelID = GetNextID();
						ILgChannelImpl iLgChannel = new ILgChannelImpl(payLoadFactory, this, channelID, msg.senderID);
						table.put(new Integer(channelID), iLgChannel);//Выделить новое соелиненик
						outMessages.put(new TunelMessage(channelID, msg.senderID, null));
						accepted.put(iLgChannel);
					}
					break;
				case CMD_DISCONNECT://Отсоединить тунельный канал
					table.remove(new Integer(msg.senderID));
					break;
			}
		}
		else
		{
			ILgChannelImpl iLgChannel = ((ILgChannelImpl) table.get(new Integer(msg.receiverID)));
			if (iLgChannel == null) //Канал назначения не найден отошлем сообщение об этом обратно
				outMessages.put(new TunelMessage(msg.receiverID, (short) 0, new byte[]{CMD_DISCONNECT}));
			else
				iLgChannel.getSystemPutter().put(msg);
		}
	}

	private void writeStep(int timeOut) throws TMultiplexerEx
	{
		TunelMessage msg = null;
		while (
				(msg = (TunelMessage) outMessages.get(timeOut))!=null
				&&  !inStopState)
		{
			syschannel.put(msg);
			if (msg!=null && msg.isFinalMessage())
			{
				System.out.println("End of channel recieved by write step");
				syschannel.put(null);
				throw new TMultiplexerEx("End of channel recieved by write step");
			}
			if (outMessages.getListSize()==0)
				syschannel.put(null);
		}

	}

	private synchronized void stopFactory()
	{

		if (!isActualIn)
			return;

		System.out.println("Call internal stopFactory");


		isActualIn = false;
		Enumeration en = table.keys();
		while (en.hasMoreElements())
		{
			Integer key = (Integer) en.nextElement();
			ILgChannelImpl iLgChannel = ((ILgChannelImpl) table.get(key));
			try
			{
				if (iLgChannel != null)
				{
					iLgChannel.stopSendingChannel();
					iLgChannel.getSystemPutter().put(new TunelMessage((short) 0, (short) 0, null));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
			table.remove(key); //удаление каналов по обрыву сокетного соединения
		}
	}

	public ILgChannel getSysChannel()
	{
		return syschannel;
	}

	public void startFactory()
	{
		inStopState = false;
		readthread = new Thread()
				{
					public void run()
					{
						try
						{
							while (!inStopState)
								readStep(100);
						}
						catch (TMultiplexerEx tMultiplexerEx)
						{
							System.out.println("read thread stopped by exception:"+tMultiplexerEx.getMessage());
//								tMultiplexerEx.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
						}
						inStopState = true;
						System.out.println("readthread stopping...");
						stopFactory();
						System.out.println("readthread stopping...Ok");
					}
				};
		readthread.start();

		writethread = new Thread()
				{
					public void run()
					{
						try
						{
							while (!inStopState)
								writeStep(100);
						}
						catch (TMultiplexerEx tMultiplexerEx)
						{
							tMultiplexerEx.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
						}
						inStopState = true;
						System.out.println("writethread stopping...Ok");
					}
				};
		writethread.start();
	}

//TODO Сделать обработку получения сообщения завершения соединения, после этого выбросить исключение
	static final int QUNTUM=10;
	public ILgChannel accept(int timeOut) throws TMultiplexerEx
	{

	    int k=0;
		if (timeOut!=0)
			k=(timeOut/QUNTUM)+1;
        ILgChannel iLgChannel=null;
		for (int i=0;(k==0 || i<k) && iLgChannel==null;i++)
		{
			if (inStopState || !isActualIn)
				throw new TMultiplexerEx(NOTACTUAL_ERR);
			iLgChannel = (ILgChannel) accepted.get(QUNTUM);
		}
		return iLgChannel;
	}

	public ILgChannel connect(int timeOut) throws TMultiplexerEx
	{
		if (inStopState || !isActualIn)
			throw new TMultiplexerEx(NOTACTUAL_ERR);

		short channelID = GetNextID();
		ILgChannelImpl iLgChannel = new ILgChannelImpl(payLoadFactory, this, channelID, (short) 0);
		table.put(new Integer(channelID), iLgChannel);
		outMessages.put(new TunelMessage(channelID, (short) 0, new byte[]{CMD_CONNECT}));//Производим соединение канала

		try
		{
			int k=0;
			if (timeOut!=0)
				k=(timeOut/QUNTUM)+1;
			Object retMsg=null;
			for (int i=0;(k==0 || i<k) && retMsg==null;i++)
			{
				if (inStopState || !isActualIn)
					throw new TMultiplexerEx(NOTACTUAL_ERR);
				retMsg=iLgChannel.get(QUNTUM);//Дождемся полной инициализации канала ответным системным сообщением
			}
			if (retMsg==null)
				throw new TMultiplexerEx(TIMEOUT_ERR);
		}
		catch (TMultiplexerEx tMultiplexerEx)
		{
			table.remove(new Integer(channelID));
			throw tMultiplexerEx;
		}
		return iLgChannel;
	}



	public void setStopFactory(int timeOut) throws TMultiplexerEx
	{
        inStopState=true;
		try
		{
			if (writethread!=null)
			{
				writethread.join();//Ожидаем когда кончится поток который пишет в сокет
				writethread=null;
			}

			if (readthread!=null)
			{

				while (readthread.isAlive())
				{
				    if (timeOut<0)
				    {
						throw new TMultiplexerEx("TimeOut of reading thread expired");
					}
					if (timeOut!=0)
					{
						timeOut-=100;
						if (timeOut ==0)
							timeOut=-1;
					}
					Thread.sleep(100);
				}
				readthread=null; //Системный канал не закрывать!!!, поскольку он может пригодится
				//для чего нить еще, например для следующей фабрики
			}
		}
		catch (InterruptedException e)
		{
			throw new TMultiplexerEx(e);
		}
	}

	public void put(Object l_message) throws TMultiplexerEx
	{
		int i=putTimeOut/100;
		while (maxBuffSize>=0 && maxBuffSize<outMessages.getListSize())
		{
			if (i<=0)
				throw new TMultiplexerEx("Putting time out in out messages queue");
			try
			{
				Thread.sleep(100);
				--i;
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}
		outMessages.put(l_message);
	}

	public Object get(int timeOut) throws TMultiplexerEx
	{
		return null;
	}

	public int getListSize() throws TMultiplexerEx
	{
		return 0;
	}

	public int getRegId()//Возвращает идентификационный номер канала
	{
		return 0;
	}

	public boolean isActualIn()//Возвращает работает канал или нет (можно через него чего нибудь передать или нет)
	{
		return isActualIn;
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
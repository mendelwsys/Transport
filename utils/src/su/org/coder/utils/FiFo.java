package su.org.coder.utils;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 15.01.2005
 * Time: 15:15:32
 * To change this template use File | Settings | File Templates.
 */
public class FiFo
{
	//Сделать так что бы блокировалась очередь если FIFO пусто
	Vector m_list;

	public FiFo()
	{
		m_list = new Vector();
	}

	public synchronized int getListSize()
	{
		return m_list.size();
	}

	public int getRegId()
	{
		return 0;
	}

	public boolean isActual()//Возвращает работает канал или нет (можно через него чего нибудь передать или нет)
	{
		return true;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public synchronized void put(Object l_message)
	{
		boolean l_NeedNotify = false;
		if (m_list.size() == 0)
			l_NeedNotify = true;
		m_list.addElement(l_message);
		if (l_NeedNotify)
			notify();
	}

	public synchronized Object get(int TimeOut)
	{
		Object retMessage = null;
		if (m_list.size() == 0)
		{
			try
			{
				wait(TimeOut);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				return null;
			}
		}
		if (m_list.size() > 0)
		{
			retMessage = m_list.firstElement();
			m_list.removeElementAt(0);
		}
		return retMessage;
	}
}

package su.org.coder.multiplexer.protocols;

import su.org.coder.utils.FiFo;
import su.org.coder.utils.ILgMessage;
import su.org.coder.multiplexer.IMultiplexerable;
import su.org.coder.multiplexer.TMultiplexerEx;

import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12.02.2005
 * Time: 18:38:36
 * Имплементация канала мультеплесора
 */
class ILgChannelImpl
		implements ILgChannel
{
	private IMultiplexerable payLoadFactory;
	private FiFo localGetter;
	private ILgChannel localPutter;
	private short channelID;
	private short peerID;
	private boolean wasinit = false;
	private boolean isActualIn = true;
	private boolean isActualOut = true;
	private static final String FINALMSG_ERR = "Getting final message in ILgChannelImpl.get()";
	private static final String CHCLOSED_ERR = "channel closed";

	ILgChannelImpl(IMultiplexerable payLoadFactory, ILgChannel systemGetter, short channelID, short peerID)
	{
		this.payLoadFactory = payLoadFactory;
		this.localGetter = new FiFo();
		this.localPutter = systemGetter;
		if (peerID != 0)
			wasinit = true;

		this.channelID = channelID;
		this.peerID = peerID;
	}


	FiFo getSystemPutter()
	{
		return localGetter;
	}

	void stopSendingChannel()
	{
		isActualIn=false;
	}

	public void put(Object l_message) throws TMultiplexerEx
	{
		if (!isActualIn)
			throw new TMultiplexerEx(CHCLOSED_ERR);
		TunelMessage tunmsg = new TunelMessage(channelID, peerID, null);
		if (l_message != null)
			tunmsg.bmessage = ((ILgMessage) l_message).toByteArray();
		localPutter.put(tunmsg);
	}

	public Object get(int timeOut) throws TMultiplexerEx
	{
		if (!isActualOut)
			throw new TMultiplexerEx(CHCLOSED_ERR);
		TunelMessage tunmsg = (TunelMessage) localGetter.get(timeOut);
		if
		(
			(tunmsg != null && tunmsg.isFinalMessage())
			//|| (tunmsg == null && !wasinit && peerID == 0)
		)
		{
			isActualOut = false;
			throw new TMultiplexerEx(FINALMSG_ERR);
		}

		if (tunmsg == null)
			return null;

		IMultiplexerable retval = null;
		if (tunmsg.bmessage != null)
		{
			retval = payLoadFactory.createEmpty();
			try
			{
				retval.setByByteArry(tunmsg.bmessage,0);
			}
			catch (IOException e)
			{
				throw new TMultiplexerEx(e);
			}
		}
		if (!wasinit && peerID == 0)
		{
			peerID = tunmsg.senderID;
			wasinit = true;
			if (retval==null)
				return new Object();
		}
		return retval;
	}

	public int getListSize() throws TMultiplexerEx
	{
		return localGetter.getListSize();
	}

	public int getRegId()//Возвращает идентификационный номер канала
	{
		return channelID;
	}

	public boolean isActualIn()//Возвращает работает канал или нет (можно через него чего нибудь передать или нет)
	{
		return isActualIn;
	}

	public boolean isActualOut()
	{
		return isActualOut;
	}

	public void close() throws TMultiplexerEx
	{
		localPutter.put(new TunelMessage());//Послать финальное сообщение в систему
	}

}
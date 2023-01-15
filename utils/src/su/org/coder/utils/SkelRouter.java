package su.org.coder.utils;

import java.util.Hashtable;
import java.util.Vector;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.02.2005
 * Time: 0:53:37
 * To change this template use File | Settings | File Templates.
 */
public class SkelRouter implements IInterceptor
{
	Hashtable skels = new Hashtable();

	String0AHelper _string0Ah = new String0AHelper();

	Hashtable online = new Hashtable();

	//private static SkelRouter skelmemeber = new SkelRouter();

	private short sessionIdCounter=10;
	private short typeID=TypeId.getNextId();

	public SkelRouter()
	{
	}

//	public static SkelRouter createSkelRouter()
//	{
////		return skelmemeber;
//		return new SkelRouter();
//	}

	public void registerServant(String regName, IInvoker invoker)
	{
		skels.put(regName, invoker);
	}

	public IInvoker unregisterServant(String regName)
	{
		return (IInvoker) skels.remove(regName);
	}

	public static String[] split2(String src,String spliter)
	{
	    int i=src.indexOf(spliter);
		if (i<0)
			return null;
		String[] retVal= new String[2];
		retVal[0]=src.substring(0,i);
		retVal[1]=src.substring(i+1);
		return retVal;
	}

	private Object syncobject = new Object();
	public CallMessageImpl invoke(CallMessageImpl msg,Vector attr) throws IOException, SysCoderEx
	{
		switch (msg.command)
		{
			case Constants.INIT_VAL:
				{
					String regName$typeName = (String) _string0Ah.createChannelObj(msg.bmessage);
					String[] regNameAtypeName = split2(regName$typeName,"#"); //Сделано для унификации кода для сервера и для мобильника

					IInvoker info = (IInvoker) skels.get(regNameAtypeName[0]);
					if (info == null || info.getTypeName().compareTo(regNameAtypeName[1]) != 0)
						return new CallMessageImpl(Constants.SYSEX_VAL, (short) 0, (short) 0, (byte) 0, _string0Ah.serialChannelObj("Unknown skeleton class"));
                    short sessionIdCounter;
					synchronized (syncobject)
					{
						sessionIdCounter=++(this.sessionIdCounter);
					}

					online.put(new Integer(sessionIdCounter), regNameAtypeName[0]);
					return new CallMessageImpl(Constants.INIT_VAL, info.getTypeID(), sessionIdCounter, (byte) 0, null);
				}
			case Constants.INVOKE_VAL:
			case Constants.INVOKE_VAL_NORET:
				{
					String key = (String) online.get(new Integer(msg.objID));
					IInvoker info = null;
					if (key == null || (info = (IInvoker) skels.get(key)) == null)
						return new CallMessageImpl(Constants.SYSEX_VAL, (short) 0, (short) 0, (byte) 0, _string0Ah.serialChannelObj("Unknown skeleton class"));
					return info.invoke(msg,attr);
				}
		}
		return new CallMessageImpl(Constants.SYSEX_VAL, (short) 0, (short) 0, (byte) 0, _string0Ah.serialChannelObj("received unknown message"));
	}

	public boolean isOnService() throws IOException, SysCoderEx
	{
		return true;
	}

	public String getTypeName()
	{
		return "SkelRouter";
	}

	public short getTypeID()
	{
		return typeID;
	}

	public void registerInterseptor(IInterceptor next)
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public CallMessageImpl preInvoke(CallMessageImpl msg, Vector attr, SysCoderEx ex) throws IOException, SysCoderEx
	{
		return msg;
	}

	public CallMessageImpl postInvoke(CallMessageImpl msg, Vector attr, SysCoderEx ex) throws IOException, SysCoderEx
	{
		return msg;
	}
}

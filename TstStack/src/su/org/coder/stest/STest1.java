package su.org.coder.stest;

import su.org.coder.utils.*;
import su.org.coder.tspiptrasport.server.ServerListener;
import su.org.coder.tspiptrasport.client.ClientConnector;

import java.util.Vector;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: VLADM
 * Date: 27.10.2005
 * Time: 14:35:45
 * To change this template use File | Settings | File Templates.
 */
public class STest1
{
	static final String REG_NAME = "REGN1";
	static final String TYPEN1 = "SERVANT1";
	static final int TYPE_ID1 = 1;
	static final int PORT1 = 1001;


	static private class ServantImpl extends SkelRouter
	{

		public CallMessageImpl invoke(CallMessageImpl msg, Vector attr) throws IOException, SysCoderEx
		{
			Double0AHelper dh=new Double0AHelper();

			double res=((Double)dh.createChannelObj(msg.bmessage)).doubleValue();
			System.out.println("res = " + res);
			                                                 //+1.11111111
			msg.bmessage = dh.serialChannelObj(new Double(res));

			//STest1.unserializeitI(msg);
			//STest1.serializeitI(msg, 200);

			//STest1.unserializeitS(msg);
			//STest1.serializeitS(msg,"ASDFGHJKL");

			return msg;

////			unserializeitI(msg);
////			serializeitI(msg,new Integer(244));
//			unserializeitSh(msg);
//			serializeitSh(msg,new Short((short) 200));
//
////			unserializeitS(msg);
////			serializeitS(msg,"ASDFGHJKL");
//
//			return msg;
		}

//		public boolean isOnService() throws IOException, SysCoderEx
//		{
//			return true;
//		}
//
//		public String getTypeName()
//		{
//			return TYPEN1;
//		}
//
//		public short getTypeID()
//		{
//			return TYPE_ID1;
//		}
	}


	void ServerImpl(String[] args)
	{
//		SkelRouter rt = new SkelRouter();
//		rt.registerServant(REG_NAME,new ServantImpl());
		ServerListener sl = new ServerListener(new ServantImpl(),PORT1,100);
		sl.CheckIt(args);
	}

	void ClientImpl(String[] args)
	{
		try
		{
			ClientConnector cl = new ClientConnector("localhost",PORT1);
			CallMessageImpl msg = new CallMessageImpl();
//			serializeitI(msg,new Integer(101));
//			serializeitS(msg,"QWERTYU");
//			serializeitSh(msg,new Short((short) 101));
			Double0AHelper dh= new Double0AHelper();

			msg.bmessage=dh.serialChannelObj(new Double(-0.12345678E-20));
			msg=cl.invoke(msg,null);

			double dbl=((Double)dh.createChannelObj(msg.bmessage)).doubleValue();

			System.out.println("dbl = " + dbl);

//			unserializeitSh(msg);
//			unserializeitS(msg);
//			unserializeitI(msg);
		}
		catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (SysCoderEx sysCoderEx)
		{
			sysCoderEx.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}



	static Short0AHelper short0AHelper = new Short0AHelper();
	static private void unserializeitSh(CallMessageImpl msg)
			throws IOException
	{
		Short myint=(Short) short0AHelper.createChannelObj(msg.bmessage);
		System.out.println("Received : "+myint);
	}

	static private void serializeitSh(CallMessageImpl msg, Object obj)
			throws IOException
	{
		msg.bmessage=short0AHelper.serialChannelObj(obj);
	}

	static Integer0AHelper integer0AHelper = new Integer0AHelper();
	static private void unserializeitI(CallMessageImpl msg)
			throws IOException
	{
		Integer myint=(Integer) integer0AHelper.createChannelObj(msg.bmessage);
		System.out.println("Received : "+myint);
	}

	static private void serializeitI(CallMessageImpl msg, Object obj)
			throws IOException
	{
		msg.bmessage=integer0AHelper.serialChannelObj(obj);
	}

	static String0AHelper string0AHelper = new String0AHelper();
	static private void unserializeitS(CallMessageImpl msg)
			throws IOException
	{
		String myint=(String) string0AHelper.createChannelObj(msg.bmessage);
		System.out.println("Received : "+myint);
	}

	static private void serializeitS(CallMessageImpl msg, Object obj)
			throws IOException
	{
		msg.bmessage=string0AHelper.serialChannelObj(obj);
	}

	public static void main(String[] args)
	{
		if (args[0].equals("0"))
			new STest1().ServerImpl(args);
		else
			new STest1().ClientImpl(args);
	}
}

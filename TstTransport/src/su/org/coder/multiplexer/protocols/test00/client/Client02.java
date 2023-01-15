package su.org.coder.multiplexer.protocols.test00.client;

import su.org.coder.multiplexer.protocols.IChannelFactoryImpl;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.IChannelFactory;
import su.org.coder.multiplexer.protocols.TunelMessage;
import su.org.coder.multiplexer.protocols.test00.server.IServiceImpl02;
import su.org.coder.multiplexer.protocols.sockstream.StreamChannel;
import su.org.coder.multiplexer.protocols.sockstream.ITimeOutSetterImpl;
import su.org.coder.multiplexer.TMultiplexerEx;
import su.org.coder.multiplexer.MultiplexerMessage;
import su.org.coder.utils.ILgMessage;
import su.org.coder.utils.CallMessageImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 02.03.2005
 * Time: 19:10:00
 * To change this template use File | Settings | File Templates.
 */
public class Client02
{
	private static IChannelFactory chf;
	private static final int PORT = 9002;
	private static final String HOST = "localhost";

	private static String[] split2(String src,String spliter)
	{
	    int i=src.indexOf(spliter);
		if (i<0)
			return null;
		String[] retVal= new String[2];
		retVal[0]=src.substring(0,i);
		retVal[1]=src.substring(i+1);
		return retVal;
	}

	public static void main(String args[])
	{

		int port=PORT;
		String host=HOST;

		if (args.length>=2)
		{
			host=args[0];
			port=Integer.parseInt(args[1]);
		}

		try
		{
			Socket socket = new Socket(host,port);
			OutputStream os = socket.getOutputStream();
			new TunelMessage((short)0,(short)0,IServiceImpl02.class.getName().getBytes()).sendToReceiver(os);
			ILgChannel syschan = new StreamChannel(socket.getInputStream(),os,new ITimeOutSetterImpl(socket),false);

			chf = new IChannelFactoryImpl(new MultiplexerMessage(), syschan,10,200);
			chf.startFactory();
			Thread trd = new Thread()
			{
				public void run()
				{
					try
					{
						ILgMessage msg = null;
						ILgChannel ch = chf.accept(0);
						do
						{
							msg = (ILgMessage) ch.get(0);
							System.out.println("Getting from Channel1 msg:");
							msg.pPrint();
						}
						while (!msg.isFinalMessage());
						System.out.println("Getting from Channel1 final message : END OF GETTING");
					}
					catch (TMultiplexerEx tMultiplexerEx)
					{
						tMultiplexerEx.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
					}
				}
			};
			trd.start();
			ILgChannel ch = chf.connect(0);

//			for (int i = 0; i < 1000; i++)
//				ch.put(new CallMessageImpl((byte) 12, (short) i, (short) 15, (byte) 1, new byte[]{1, 2, 3, 4, 5, 6}));

			for (int i = 0; i < 10; i++)
			{
				ch.put(new CallMessageImpl((byte) 12, (short) i, (short) 15, (byte) 1, new byte[]{1, 2, 3, 4, 5, 6}));
				ILgMessage msg = (ILgMessage) ch.get(0);
				Thread.sleep(100);
				System.out.println("Putting/Getting channel2 msg:");
				msg.pPrint();
			}
			ch.put(new CallMessageImpl());

			ILgMessage msg = (ILgMessage) ch.get(1000);
			if (msg!=null)
				msg.pPrint();
			else
				System.out.println("Message get timeout");

			System.out.println("Put channel2 final message: END OF Channel2 PUTTING");
			trd.join();
			System.out.println("Set Stop factory");
			chf.setStopFactory(1000);
			chf = null;
			System.out.println("Set Stop Ok");

		}
		catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (TMultiplexerEx tMultiplexerEx)
		{
			tMultiplexerEx.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}
}

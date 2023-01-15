package su.org.coder.multiplexer.protocols.test00.client;

import su.org.coder.multiplexer.protocols.IChannelFactoryImpl;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.IChannelFactory;
import su.org.coder.multiplexer.protocols.http3url.client2.Http3UrlChannel;
import su.org.coder.multiplexer.TMultiplexerEx;
import su.org.coder.multiplexer.MultiplexerMessage;
import su.org.coder.utils.ILgMessage;
import su.org.coder.utils.CallMessageImpl;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 02.03.2005
 * Time: 19:10:00
 * To change this template use File | Settings | File Templates.
 */
public class Client01
{
	private static IChannelFactory chf;

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
//		split2("AAAA#XXXXX","#");
//		split2("AAAA#","#");
//		split2("#XXXXX","#");
//		split2("#","#");

		String inUrl = "http://localhost:" + args[0] + "/test00/outsrv";
		String outUrl = "http://localhost:" + args[0] + "/test00/insrv";
		String initUrl = "http://localhost:" + args[0] + "/test00/initsrv";

		Http3UrlChannel syschan = null;
		try
		{
			syschan = new Http3UrlChannel(outUrl, inUrl, initUrl);
			chf = new IChannelFactoryImpl(new MultiplexerMessage(), syschan);
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
			for (int i = 0; i < 100; i++)
			{
				ch.put(new CallMessageImpl((byte) 12, (short) i, (short) 15, (byte) 1, new byte[]{1, 2, 3, 4, 5, 6}));
				ILgMessage msg = (ILgMessage) ch.get(0);
				Thread.sleep(100);
				System.out.println("Putting/Getting channel2 msg:");
				msg.pPrint();
			}
			ch.put(new CallMessageImpl());
			ILgMessage msg = (ILgMessage) ch.get(0);
			msg.pPrint();

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

package su.org.coder.multiplexer.protocols.http3url.test00.client;

import su.org.coder.multiplexer.protocols.http3url.client2.Http3UrlChannel;
import su.org.coder.multiplexer.protocols.TunelMessage;
import su.org.coder.multiplexer.TMultiplexerEx;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 19.02.2005
 * Time: 13:14:40
 * To change this template use File | Settings | File Templates.
 */
public class Client00_JSP
{
	private Http3UrlChannel trans;

	public static final String SERVER_DEF ="localhost/GPSServer/chk1";

	public static void main(String args[]) throws IOException, InterruptedException
	{
		new Client00_JSP().CheckIt(args);
	}

	private void CheckIt(String[] args) throws IOException, InterruptedException
	{

		String inUrl = "http://"+ SERVER_DEF +"/jot.jsp";
		String  outUrl ="http://"+ SERVER_DEF +"/jin.jsp";
		String initUrl ="http://"+ SERVER_DEF +"/tst/tst00/it00.jsp";


		trans= new Http3UrlChannel(outUrl,inUrl,initUrl);
		new Thread()
		{
			public void run()
			{
				try
				{
					TunelMessage retVal;
					int i=0;
					do
					{
						retVal = (TunelMessage) trans.get(0);
//						if (i>100)
						{
							retVal.pPrint();
							i=0;
						}
						i++;
					} while (retVal.senderID!=0 || retVal.receiverID!=0);
					System.out.println("End channel message received");
				}
				catch (TMultiplexerEx tMultiplexerEx)
				{
					tMultiplexerEx.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}

			}
		}.start();

		new Thread()
		{
			public void run()
			{
				try
				{
					for (int i=0;i<871;i++)
					{
						TunelMessage msg = new TunelMessage((byte) (i+1),(short )i,new byte[]{(byte)(2*i),1,3,4,5});
						trans.put(msg);
//						msg.pPrint();
						Thread.sleep(10);
					}
					trans.put(new TunelMessage());//Конец связи
					trans.put(null);//Сброс всего дерьма
					System.out.println("!!!End Sending data!!!");
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
		}.start();

		Thread.sleep(100);
	}
}

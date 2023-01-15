package su.org.coder2.test00.client;

import su.org.coder.multiplexer.TMultiplexerEx;
import su.org.coder.multiplexer.client.ClientConnector;
import su.org.coder.multiplexer.protocols.http3url.client2.Http3UrlChannel;
import su.org.coder.utils.SysCoderEx;
import su.org.coder2.chat.IChatInterface;
import su.org.coder2.chat.ProxyChatInterface;
import su.org.coder2.chat.TException1;
import su.org.coder2.chat.TException2;
import su.org.coder2.test00.Constants;

import java.awt.*;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.02.2005
 * Time: 2:50:51
 * Client over HTTP Direct (through servlet implementation)
 *
 */
public class ClientHttpDirect00
{

	public static void main(String args[]) throws Exception, TException2, TException1
	{
		new ClientHttpDirect00().jobStart(args);
		System.exit(0);
	}

	public void jobStart(String args[]) throws Exception, TException2, TException1
	{
		String inUrl = "http://"+Constants.SERVER+":" + Constants.SERVER_PORT_HTTP + "/test00/outsrv";
		String outUrl = "http://"+Constants.SERVER+":" + Constants.SERVER_PORT_HTTP + "/test00/insrv";
		String initUrl = "http://"+Constants.SERVER+":" + Constants.SERVER_PORT_HTTP + "/test00/initsrvdt";

		Http3UrlChannel syschan = new Http3UrlChannel(outUrl, inUrl, initUrl);

//Предоставляем сервис
		System.out.println("Before Connect to client....");
		ClientConnector cl = new ClientConnector(syschan,0);
		System.out.println("Connect to client....Ok");
		System.out.println("ProxyChatInterface.bind....");

//get service through ProxyChatInterface
		IChatInterface ichat = ProxyChatInterface.bind(cl, Constants.REG_NAME01);
		System.out.println("ProxyChatInterface.bind....Ok");
		sCall(ichat,5);

		syschan.close();
		try
		{
			cl.getChannelFactory().setStopFactory(100);
		} catch (TMultiplexerEx e) {
			e.printStackTrace();
		}

	}

	private void sCall(IChatInterface intrf1,int cnt)
			throws IOException, SysCoderEx, TException1, TException2, InterruptedException
	{

		for (int i=0;i<cnt;i++)
		{
			System.out.println("Put message "+i+"...");
			intrf1.Put("Ping "+i);
			System.out.println("Put message "+i+"...Ok");
			System.out.println("Get message...");
			System.out.println(intrf1.Get(150));
			System.out.println("Get message...Ok");
			Thread.sleep(1000);
		}
	}

}

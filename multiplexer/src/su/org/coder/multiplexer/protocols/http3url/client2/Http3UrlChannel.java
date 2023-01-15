package su.org.coder.multiplexer.protocols.http3url.client2;

import su.org.coder.multiplexer.protocols.http3url.HttpTunelMessage2;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.TunelMessage;
import su.org.coder.multiplexer.TMultiplexerEx;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
//import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 18.02.2005
 * Time: 0:33:53
 * To change this template use File | Settings | File Templates.
 */
public class Http3UrlChannel
		implements ILgChannel
{
	private String outUrl;
	private String proxy;
	private int proxyPort;
	private String inUrl;
	private InputStream is;

	private int channelID;
	private boolean closestatus =false;

	public Http3UrlChannel(String outUrl, String inUrl, String initUrl) throws IOException
	{
		this.inUrl = inUrl;
		this.outUrl = outUrl;

		InputStream initis = new URL(initUrl).openStream();
		HttpTunelMessage2 retVal = new HttpTunelMessage2();
		retVal.setBySender(initis);
		channelID = retVal.httpchanneID;
		initis.close();
	}


	public Http3UrlChannel(String outUrl, String inUrl, String initUrl,String proxy,int proxyPort) throws IOException
	{
		this.inUrl = inUrl;
		this.outUrl = outUrl;
		this.proxy = proxy;
		this.proxyPort = proxyPort;
		URL url;
		if (proxy==null)
			url = new URL(initUrl);
		else
			url= new URL("http",proxy,proxyPort,initUrl);

		InputStream initis = url.openStream();
		HttpTunelMessage2 retVal = new HttpTunelMessage2();
		retVal.setBySender(initis);
		channelID = retVal.httpchanneID;
		initis.close();
	}


	private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	private static final int FLASHSIZE = 31;

	public void put(Object l_message) throws TMultiplexerEx
	{

		if (l_message == null && buffer.size() == 0)
			return;

		byte[] msgarray = null;
		if (l_message != null)
			msgarray = ((TunelMessage) l_message).toByteArray();
		if (msgarray == null || (msgarray.length + buffer.size() > FLASHSIZE))
		{
			sendit();
			buffer.reset();
		}
		try
		{
			if (msgarray != null)
				buffer.write(msgarray);
		}
		catch (IOException e)
		{
			throw new TMultiplexerEx(e.getMessage());
		}
//		if (closestatus)
//			throw new TMultiplexerEx("!!!Channel closed_X!!!");
	}

	private void sendit() throws TMultiplexerEx
	{

		try
		{
			URL url;
			if (proxy==null)
				url = new URL(outUrl);
			else
				url= new URL("http",proxy,proxyPort,outUrl);

			HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();

			urlConnection.setDoOutput(true);
			OutputStream os = urlConnection.getOutputStream();
			HttpTunelMessage2 httpTunelMessage = new HttpTunelMessage2(channelID,0, buffer.toByteArray());
			httpTunelMessage.sendToReceiver(os);
			os.flush();
			InputStream is = null;

			try
			{
				is=urlConnection.getInputStream();
//				byte[] bt = is.readAllBytes();
//				String s = new String(bt);
//				System.out.println("s = " + s);
			}
			catch (IOException e)
			{
				System.out.println("Catch exception on Http3UrlChannel.sendit: "+e.getMessage());
			}
			finally
			{
				try
				{
					if (os!=null)
					os.close();
				}
				catch (IOException e)
				{
				}

				try
				{
					if (is!=null)
						is.close();
				}
				catch (IOException e)
				{
				}

	//			try
	//			{
	//				if (urlConnection!=null)
	//					urlConnection.disconnect();
	//			}
	//			catch (Exception e)
	//			{
	//			}
			}
		}
		catch (IOException e)
		{
			throw new TMultiplexerEx(e);
		}
	}

/*
	private void sendit() throws TMultiplexerEx
	{
		boolean wassend = false;
		HttpTunelMessage2 httpTunelMessage = new HttpTunelMessage2(channelID, buffer.toByteArray());
		for (int i = 0; ; i++)
		{
			try
			{
				HttpURLConnection putConn=null;
				if (putStream == null)
				{
					putConn = (HttpURLConnection) new URL(outUrl).openConnection();
 					putConn.setDoOutput(true);
//					putConn.setDoInput(false);
					putConn.connect();
					putStream = putConn.getOutputStream();
					putConn.getInputStream();
				}
				if (!wassend)
				{
					httpTunelMessage.sendToReceiver(putStream);
					putStream.flush();
				}
				wassend = true;
				break;
			}
			catch (IOException e)
			{
				try
				{
					if (putStream != null)
						putStream.close();
				}
				catch (IOException e1)
				{
				}

				putStream = null;

				if (i > 1)
					throw new TMultiplexerEx(e.getMessage());
			}
		}
	}
*/

	public Object get(int timeOut) throws TMultiplexerEx
	{
		if (closestatus)
			throw new TMultiplexerEx("!!!Channel closed!!!");
		TunelMessage retVal = new TunelMessage();
		for (int i = 0; ; i++)
		{
			try
			{
				if (is == null)
				{

					URL url;
					if (proxy==null)
						url = new URL(inUrl);
					else
						url= new URL("http",proxy,proxyPort,inUrl);


					URLConnection getConn = url.openConnection();
					getConn.setDoOutput(true);
					OutputStream os = getConn.getOutputStream();
					new HttpTunelMessage2(channelID,0, null).sendToReceiver(os);
					os.flush();
					os.close();
					is = getConn.getInputStream();
				}
				retVal.setBySender(is);
				break;
			}
			catch (IOException e)
			{

				if (e instanceof EOFException)
					i = 0;
				try
				{
					if (is != null)
					{
						is.close();
					}
				}
				catch (IOException e1)
				{
				}


				if (is == null || i >= 2 || closestatus)
				{
					throw new TMultiplexerEx(e.getMessage());
				}
				is = null;
				System.out.println("!!!Exception occurs, try again connect to servlet!!!");
			}
		}
		return retVal;
	}

	public int getListSize() throws TMultiplexerEx
	{
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public int getRegId()//Возвращает идентификационный номер канала
	{
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public boolean isActualIn()//Возвращает работает канал или нет (можно через него чего нибудь передать или нет)
	{
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public boolean isActualOut()
	{
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public void close() throws TMultiplexerEx
	{
		this.put(new TunelMessage());
		closestatus =true;
//		try {
//			is.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		//		if (currentthread!=null)
//			currentthread.stop();
	}
}
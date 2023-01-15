package su.org.coder.multiplexer.protocols.http3url.test00.test01;

import su.org.coder.multiplexer.protocols.http3url.HttpTunelMessage2;
import su.org.coder.multiplexer.TMultiplexerEx;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: vladm
 * Date: 16.03.2007
 * Time: 13:27:30
 * To change this template use File | Settings | File Templates.
 */
public class CheckOutPut
{
	public static final String SERVER_DEF ="10.144.31.103/chk1";
			//"10.144.31.103:9005/chk1"; //адрес что бы перехватывать HTTP запросы
	public static String ip="10.144.31.103";


	void testit(String outUrl,ByteArrayOutputStream buffer,int nMsg) throws TMultiplexerEx
	{

		InputStream is = null;
		OutputStream os = null;
		HttpURLConnection urlConnection=null;

		try
		{
			URL url = new URL(outUrl);
			urlConnection=(HttpURLConnection) url.openConnection();

			HttpTunelMessage2 httpTunelMessage = new HttpTunelMessage2(nMsg,0, buffer.toByteArray());

			urlConnection.setDoOutput(true);
			os = urlConnection.getOutputStream();
			httpTunelMessage.sendToReceiver(os);
			os.flush();

			try
			{
				is = urlConnection.getInputStream();
			}
			catch (IOException e)
			{
				System.out.println("!!!Catch it in testit!!!");
			}
		}
		catch (IOException e)
		{
			throw new TMultiplexerEx(e);
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

	Socket sc=null;
	InputStream is = null;
	OutputStream os = null;
	String CUCA="";
	void testit2(String outUrl,ByteArrayOutputStream buffer,int nMsg) throws TMultiplexerEx
	{


		String header1="POST /chk1/jin1.jsp HTTP/1.1\r\n" +
				CUCA+
				"User-Agent: Java/1.4.2_07\r\n" +
				"Host: 10.144.31.103:80\r\n" +
				"Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n" +
				"Connection: keep-alive\r\n" +
				"Content-Type: application/x-www-form-urlencoded\r\n";
		if (CUCA!=null && CUCA.length()>0)
				header1="POST HTTP/1.1\r\n" +
				CUCA+
				"Connection: keep-alive\r\n" +
				"Content-Type: application/x-www-form-urlencoded\r\n";


		try
		{

			HttpTunelMessage2 httpTunelMessage = new HttpTunelMessage2(nMsg,0, buffer.toByteArray());
			byte[] bytes = httpTunelMessage.toByteArray();
			String header2=		"Content-Length: "+ bytes.length+"\r\n" +
					"\r\n";

    		ByteArrayOutputStream bufferALL = new ByteArrayOutputStream();
			bufferALL.write((header1+header2).getBytes());
			bufferALL.write(bytes);


			if (sc==null)
				sc=new Socket(ip,80);

			if (os == null)
				os=sc.getOutputStream();

			os.write(bufferALL.toByteArray());

			os.flush();

			if (is == null)
				is=sc.getInputStream();

			try
			{
				int cnt=0;
				byte[] bt=new byte[100];

				String answer="";

				while ((cnt=is.read(bt))>0)
				{
					answer+=new String (bt,0,cnt);
					System.out.write(bt,0,cnt);
					if (cnt > 40)
						break;
				}

//				if (answer.length()>0)
//				{
//					int startpos=answer.indexOf("Set-Cookie: JSESSIONID=");
//					startpos=answer.indexOf("JSESSIONID=",startpos);
//					int stoppos=answer.indexOf("; ",startpos);
//					if (startpos>=0 && stoppos>=0)
//						CUCA="Cookie: "+answer.substring(startpos,stoppos)+"'\r\n";
//				}


			}
			catch (IOException e)
			{
			}

		}
		catch (IOException e)
		{
			throw new TMultiplexerEx(e);
		}
		finally
		{
//			try
//			{
//				if (os!=null)
//				os.close();
//			}
//			catch (IOException e)
//			{
//			}
//
//			try
//			{
//				if (is!=null)
//					is.close();
//			}
//			catch (IOException e)
//			{
//			}
//
		}

	}


	public static void main(String[] args) throws Exception
	{

		String inUrl = "http://"+ SERVER_DEF +"/jin1.jsp";
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		buffer.write("XYZSDFG".getBytes());
		CheckOutPut put = new CheckOutPut();

		for (int i = 10; i < 100; i++)
		{
			put.testit(inUrl,buffer,i);
			Thread.sleep(2000);
		}

	}
}

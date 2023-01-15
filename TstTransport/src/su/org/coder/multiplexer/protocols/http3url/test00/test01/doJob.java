package su.org.coder.multiplexer.protocols.http3url.test00.test01;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: vladm
 * Date: 16.03.2007
 * Time: 16:36:52
 * To change this template use File | Settings | File Templates.
 */
public class doJob
		implements Runnable
{
	private Socket socket;

	public doJob(Socket socket)
	{

		this.socket = socket;
	}

	public void run()
	{
		try
		{
			InputStream inputStream = socket.getInputStream();
			int cnt=0;
			byte[] bytes=new byte[100];

			while ((cnt=inputStream.read(bytes))>0)
			{

//					System.out.print(new String(bytes,0,cnt));
				for (int i = 0; i < cnt; i++)
					System.out.println(bytes[i]);//+" "+((char)(bytes[i])));
			}
		}
		catch (IOException e)
		{

		}
	}
}

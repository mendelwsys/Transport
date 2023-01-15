package su.org.coder.multiplexer.protocols.http3url.test00.test01;

import java.net.ServerSocket;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: vladm
 * Date: 16.03.2007
 * Time: 16:35:43
 * To change this template use File | Settings | File Templates.
 */
public class ServerList
{
	boolean terminate=false;

    public void start(int port,int maxclibuff)
	{
		terminate=false;
		ServerSocket serverSocket=null;
		try
		{
		    serverSocket = new ServerSocket(port, maxclibuff);
			do
			{
				new Thread(new doJob(serverSocket.accept())).start();
			}while (!terminate);
		}
		catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		finally
		{
			try
			{
				if (serverSocket!=null)
					serverSocket.close();
			}
			catch (IOException e2)
			{

			}
		}

	}
	public static void main(String[] args)
	{
		new ServerList().start(9005,100);
	}
}

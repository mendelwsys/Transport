package su.org.coder.tspiptrasport.server;

import su.org.coder.utils.SkelRouter;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.02.2005
 * Time: 2:06:17
 * To change this template use File | Settings | File Templates.
 */
public class ServerListener
{
	private int port;
	private int maxclibuff;

	private SkelRouter router;

	public ServerListener(SkelRouter router,int port,int maxclibuff)
	{
		this.router=router;
		this.port = port;
		this.maxclibuff = maxclibuff;
	}
	public void CheckIt(String args[])
	{
		ServerSocket l_ServerSocket = null;
		try
		{
			l_ServerSocket = new ServerSocket(port, maxclibuff);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		while (true)
		{
			try
			{
				Socket l_Socket = l_ServerSocket.accept();
				new Thread(new ServerConnector(l_Socket,router)).start();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
    
}

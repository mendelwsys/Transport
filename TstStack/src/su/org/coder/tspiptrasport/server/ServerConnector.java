package su.org.coder.tspiptrasport.server;

import su.org.coder.utils.SkelRouter;
import su.org.coder.utils.CallMessageImpl;
import su.org.coder.utils.SysCoderEx;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.02.2005
 * Time: 2:21:48
 * To change this template use File | Settings | File Templates.
 */
class ServerConnector implements Runnable
{
	private OutputStream os;
	private InputStream is;
	private boolean Terminate=false;
	private SkelRouter router;


	ServerConnector(Socket socket,SkelRouter router) throws IOException
	{
		this.router = router;
		is = socket.getInputStream();
		os = socket.getOutputStream();
	}

	public void run()
	{
		CallMessageImpl msg = new CallMessageImpl();
		try
		{
			while (!Terminate)
			{
				msg.setBySender(is);
				router.invoke(msg,null).sendToReceiver(os);
			}
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

}

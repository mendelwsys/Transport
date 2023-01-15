package su.org.coder.tspiptrasport.client;

import su.org.coder.utils.IInvoker;
import su.org.coder.utils.CallMessageImpl;
import su.org.coder.utils.SysCoderEx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.02.2005
 * Time: 2:31:41
 * To change this template use File | Settings | File Templates.
 */
public class ClientConnector implements IInvoker
{
	private Socket socket;
	private InputStream is;
	private OutputStream os;

	public ClientConnector(String server, int port) throws IOException
	{
		socket = new Socket(server,port);
		is=socket.getInputStream();
		os=socket.getOutputStream();
	}

	public CallMessageImpl invoke(CallMessageImpl msg,Vector attr) throws IOException, SysCoderEx
	{
		msg.sendToReceiver(os);
		CallMessageImpl retmsg = new CallMessageImpl();
		retmsg.setBySender(is);
		return retmsg;
	}

	public boolean isOnService() throws IOException, SysCoderEx
	{
		return true;
	}

	public String getTypeName()
	{
		return "ClientConnector";
	}

	public short getTypeID()
	{
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

}

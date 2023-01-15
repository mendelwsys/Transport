package su.org.coder.multiplexer.protocols.sockstream;

import java.net.Socket;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: VLADM
 * Date: 05.11.2005
 * Time: 17:53:46
 * To change this template use File | Settings | File Templates.
 */
public class ITimeOutSetterImpl
		implements ITimeOutSetter
{
	private Socket socket;

	public ITimeOutSetterImpl (Socket socket)
	{

		this.socket = socket;
	}

	public void setTimeOut(int timeout) throws IOException
	{
		socket.setSoTimeout(timeout);
	}

	public int getTimeOut() throws IOException
	{
		return socket.getSoTimeout();
	}
}

package su.org.coder.multiplexer.protocols.sockstream;

import java.net.SocketException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: VLADM
 * Date: 05.11.2005
 * Time: 17:53:13
 * To change this template use File | Settings | File Templates.
 */
public interface ITimeOutSetter
{
	void setTimeOut(int timeout) throws IOException;
	int getTimeOut() throws IOException;
}

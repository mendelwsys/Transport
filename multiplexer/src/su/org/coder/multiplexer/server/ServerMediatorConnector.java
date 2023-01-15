package su.org.coder.multiplexer.server;

import su.org.coder.utils.SkelRouter;
import su.org.coder.utils.CallMessageImpl;
import su.org.coder.utils.SysCoderEx;
import su.org.coder.multiplexer.protocols.IMediator;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;

/**
 * Created by IntelliJ IDEA.
 * User: vladm
 * Date: 06.07.2009
 * Time: 18:05:46
 * To change this template use File | Settings | File Templates.
 */
public class ServerMediatorConnector
{
	private SkelRouter router;


	public ServerMediatorConnector(SkelRouter router)
	{
		this.router = router;
	}

    public byte[] execute(byte[] inbt) throws IOException, SysCoderEx
    {
        CallMessageImpl msg = new CallMessageImpl();
        msg.setBySender(new ByteArrayInputStream(inbt));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        router.invoke(msg,null).sendToReceiver(bos);
        bos.flush();
        bos.close();
        return bos.toByteArray();
    }
}

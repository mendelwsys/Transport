package su.org.coder.multiplexer.client;

import su.org.coder.utils.IInvoker;
import su.org.coder.utils.CallMessageImpl;
import su.org.coder.utils.SysCoderEx;
import su.org.coder.multiplexer.protocols.IMediator;

import java.io.IOException;
import java.util.Vector;

/**
 * Send objects data through mediator of streams
 */

public class ClientMediatorConnector implements IInvoker
{
    private IMediator mediator;

    public ClientMediatorConnector(IMediator mediator) throws IOException
	{
		this.mediator=mediator;
	}

	public CallMessageImpl invoke(CallMessageImpl msg, Vector attr) throws IOException, SysCoderEx
	{
		msg.sendToReceiver(mediator.getOutputStream());
		CallMessageImpl retmsg = new CallMessageImpl();
		retmsg.setBySender(mediator.getInputStream());
		return retmsg;
	}

	public boolean isOnService() throws IOException, SysCoderEx
	{
		return true;
	}

	public String getTypeName()
	{
		return "ClientMediatorConnector";
	}

	public short getTypeID()
	{
		return 0;
	}

}

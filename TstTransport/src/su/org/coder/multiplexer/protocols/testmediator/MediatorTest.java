package su.org.coder.multiplexer.protocols.testmediator;

import su.org.coder.utils.SkelRouter;
import su.org.coder.utils.SysCoderEx;
import su.org.coder.multiplexer.protocols.IMediator;
import su.org.coder.multiplexer.protocols.testmediator.gsconn.ProxyIStorage;
import su.org.coder.multiplexer.protocols.testmediator.gsconn.IIStorageProxy;
import su.org.coder.multiplexer.client.ClientMediatorConnector;
import su.org.coder.multiplexer.server.ServerMediatorConnector;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: vladm
 * Date: 06.07.2009
 * Time: 18:18:48
 * To change this template use File | Settings | File Templates.
 */
public class MediatorTest
{
    public static void main(String[] args) throws IOException, SysCoderEx
    {
        SkelRouter router = new SkelRouter();
        router.registerServant(ServerImpl.SERVERNAME, new ServerImpl());

        IMediator mediator = new TheMediator(new ServerMediatorConnector(router));
        IIStorageProxy pr = ProxyIStorage.bind(new ClientMediatorConnector(mediator), ServerImpl.SERVERNAME);

        pr.prevElement();
    }
}

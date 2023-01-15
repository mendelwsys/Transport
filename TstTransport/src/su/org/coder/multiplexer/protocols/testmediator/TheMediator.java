package su.org.coder.multiplexer.protocols.testmediator;

import su.org.coder.multiplexer.protocols.IMediator;
import su.org.coder.multiplexer.server.ServerMediatorConnector;
import su.org.coder.utils.SysCoderEx;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: vladm
 * Date: 06.07.2009
 * Time: 18:26:06
 * To change this template use File | Settings | File Templates.
 */
public class TheMediator implements IMediator
{
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private ServerMediatorConnector connmediator;

    public TheMediator(ServerMediatorConnector connmediator)
    {

        this.connmediator = connmediator;
    }
    public InputStream getInputStream() throws IOException, SysCoderEx
    {
        //TODO Здесь передача данных на сервер с помощью команд
        //TODO Возврат бинарных данных
        return new ByteArrayInputStream(connmediator.execute(bos.toByteArray()));
    }

    public OutputStream getOutputStream() throws IOException
    {
        bos.flush();
        bos.close();
        bos.reset();
        return bos;
    }
}

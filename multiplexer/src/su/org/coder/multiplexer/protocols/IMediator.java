package su.org.coder.multiplexer.protocols;

import su.org.coder.utils.SysCoderEx;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: vladm
 * Date: 06.07.2009
 * Time: 17:58:59
 * To change this template use File | Settings | File Templates.
 */
public interface IMediator
{
    InputStream getInputStream() throws IOException, SysCoderEx;
    OutputStream getOutputStream() throws IOException, SysCoderEx;
}

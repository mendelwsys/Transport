package su.org.coder.multiplexer.protocols.testmediator;

import su.org.coder.multiplexer.protocols.testmediator.gsconn.SkelIStorage;
import su.org.coder.utils.SysCoderEx;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: vladm
 * Date: 06.07.2009
 * Time: 18:22:54
 * To change this template use File | Settings | File Templates.
 */
public class ServerImpl extends SkelIStorage
{
    public static final String SERVERNAME="ServerImpl";

    public int getCurpos() throws IOException, SysCoderEx
    {
        System.out.println("getCurpos:");
        return 0;
    }

    public String getstrorage(int i) throws IOException, SysCoderEx
    {
        System.out.println("getstrorage:");
        return null;
    }

    public boolean hasMoreElements() throws IOException, SysCoderEx
    {
        System.out.println("hasMoreElements:");
        return false;
    }

    public boolean hasPrevElements() throws IOException, SysCoderEx
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String nextElement() throws IOException, SysCoderEx
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int setposon(int pos) throws IOException, SysCoderEx
    {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String prevElement() throws IOException, SysCoderEx
    {
        System.out.println("prevElement:");
        return null;
    }

    public int size() throws IOException, SysCoderEx
    {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

package su.org.coder.utils;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: VLADM
 * Date: 11.08.2005
 * Time: 16:11:05
 * To change this template use File | Settings | File Templates.
 */
public interface IProxyParent
{
	boolean isOnService() throws SysCoderEx, IOException;
}

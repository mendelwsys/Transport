package su.org.coder.multiplexer.protocols.http3url.test00.server;

import su.org.coder.multiplexer.protocols.jspservl.managers.InitService;
import su.org.coder.utils.MultiplObjectStorage;

import javax.servlet.ServletConfig;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 06.04.2005
 * Time: 16:49:39
 * To change this template use File | Settings | File Templates.
 */
public class TestService00 extends InitService
{
	protected String initChannelServlet(ServletConfig sc)
	{
		System.out.println("initService: "+TestService00.class.getCanonicalName());
		MultiplObjectStorage.getInstance().registerifempty(new IServiceImpl00());
		String name = IServiceImpl00.class.getName();
		System.out.println("initService className is:"+name);
		return name;
	}
}

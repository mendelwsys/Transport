package su.org.coder.multiplexer.protocols.test00.midletserver;

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
public class TestService01 extends InitService
{
	protected String initChannelServlet(ServletConfig sc)
	{

		System.out.println("initService: su.org.coder.multiplexer.protocols.test00.midletserver.TestService01");
		MultiplObjectStorage.getInstance().registerifempty(new IServiceImpl01());
		String name = IServiceImpl01.class.getName();
		System.out.println("initService className is:"+name);
		return name;
	}
}

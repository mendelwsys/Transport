package su.org.coder2.test00.server;

import su.org.coder.multiplexer.protocols.http3url.servlets2.InitServlet;
import su.org.coder.utils.MultiplObjectStorage;

import javax.servlet.ServletConfig;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 20.02.2005
 * Time: 21:36:53
 * To change this template use File | Settings | File Templates.
 *
 */
public class HiTstCallBackServlet00
		extends InitServlet
{
	protected String initChannelServlet(ServletConfig sc)
	{
		System.out.println("initChannelServlet: "+ HiTstCallBackServlet00.class.getCanonicalName());
		MultiplObjectStorage.getInstance().registerifempty(new IHiTstImpl4CallBack00());
		String name = IHiTstImpl4CallBack00.class.getName();
		System.out.println("initChannelServlet className is:"+name);
		return name;
	}
}

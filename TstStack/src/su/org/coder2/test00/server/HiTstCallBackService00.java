package su.org.coder2.test00.server;

import su.org.coder.multiplexer.protocols.jspservl.managers.InitService;
import su.org.coder.utils.MultiplObjectStorage;

import javax.servlet.ServletConfig;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 16.04.2005
 * Time: 20:02:32
 * To change this template use File | Settings | File Templates.
 */
public class HiTstCallBackService00 extends InitService
{
	protected String initChannelServlet(ServletConfig sc)
	{

		System.out.println("initService: "+ HiTstCallBackService00.class.getName());
		MultiplObjectStorage.getInstance().registerifempty(new IHiTstImpl4CallBack00());
		String name = IHiTstImpl4CallBack00.class.getName();
		System.out.println("initService className is:"+name);
		return name;
	}
}

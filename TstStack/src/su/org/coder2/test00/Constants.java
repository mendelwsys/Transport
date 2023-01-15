package su.org.coder2.test00;

import su.org.coder2.test00.server.IHiTstImpl4CallBack00;
import su.org.coder2.test00.server.IHiTstImpl4CallBack01;
import su.org.coder2.test00.server.IHiTstImpl4Direct00;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 05.02.2005
 * Time: 19:59:59
 * To change this template use File | Settings | File Templates.
 */
public class Constants
{
	public static final String SERVER = "localhost";
	public static final int SERVER_PORT_STREAM = 2001;
	public static final int SERVER_PORT_HTTP = 8080;
	public static final int MAXCLIBUFF = 50;
	public static final String REG_NAME01 = "Class1Name1";
	public static final String REG_NAME02 = "Class2Name2";
	public static final String REG_INTERCEPT = "Intercept1Name1";
	//Only for test purposes on the client side, we may not have access to the implementation class
	//so this names must be same on client and server side
	public static final String SERVICE_NAME00 = IHiTstImpl4CallBack00.class.getCanonicalName();
	public static final String SERVICE_NAME01 = IHiTstImpl4CallBack01.class.getCanonicalName();
	public static final String SERVICE_NAME02 = IHiTstImpl4Direct00.class.getCanonicalName();
}

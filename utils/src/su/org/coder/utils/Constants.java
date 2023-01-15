package su.org.coder.utils;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 31.01.2005
 * Time: 18:04:27
 * To change this template use File | Settings | File Templates.
 */
public class Constants
{
	public static final byte INIT_VAL = 100;
	public static final byte RET_VAL = 101;
	public static final byte INVOKE_VAL = 102;
	public static final byte SYSEX_VAL = 103;
	public static final byte APPEX_VAL = 104;

	/**
	 * Испрользование параметров RET_VAL_NORET и INVOKE_VAL_NORET
	 * должно быть согласовано принимающей и передающей стороной
	 */

	public static final byte RET_VAL_NORET = 105; //Индикатор того что возвращаемое сообщение можно не передавать по каналу
	public static final byte INVOKE_VAL_NORET = 106; //Индикатор того что возврат сообщения на этот вызов может быть не преджусмотрен

	public static final int    ERR_NOTSPEC = 1000;
	public static final String ERR_UNEXP_MSG = "Unexpected message from peer";

	public static final int ERR_TYPEID = 1010;
	public static final String ERR_TYPEID_MSG = "Wrong typeid of object";

	public static final int ERR_OPER = 1020;
	public static final String ERR_OPER_MSG = "The operation is not supported by this object";

	public static final int ERR_BYEXT = 1030;
	public static final String ERR_BYEXT_MSG = "Message supply by extender";

	public static final int ERR_NO_SERVICE=1040;
	public static final String ERR_NO_SERVICE_MSG = "No Service";
}

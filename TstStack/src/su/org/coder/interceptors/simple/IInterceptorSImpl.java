package su.org.coder.interceptors.simple;

import su.org.coder.utils.*;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 22.03.2005
 * Time: 14:09:27
 * To change this template use File | Settings | File Templates.
 */
public class IInterceptorSImpl implements IInterceptor
{
	private short typeID=TypeId.getNextId();

	public void registerInterseptor(IInterceptor next)
	{
		System.out.println("call registerInterseptor");
	}

	public CallMessageImpl preInvoke(CallMessageImpl msg, Vector attr, SysCoderEx ex) throws IOException, SysCoderEx
	{
		System.out.println("call preInvoke");
		return msg;
	}

	public CallMessageImpl postInvoke(CallMessageImpl msg, Vector attr, SysCoderEx ex) throws IOException, SysCoderEx
	{
		System.out.println("call postInvoke");
		return msg;
	}

	public CallMessageImpl invoke(CallMessageImpl msg, Vector attr) throws IOException, SysCoderEx
	{
		System.out.println("call invoke");
		throw new SysCoderEx(Constants.ERR_NOTSPEC,"THIS Simple interceptor: the operation is not support");
	}

	public boolean isOnService() throws IOException, SysCoderEx
	{
		return true;
	}

	public String getTypeName()
	{
		return "SimpleInterceptor";
	}

	public short getTypeID()
	{
		return typeID;
	}
}

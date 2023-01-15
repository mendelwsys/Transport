package su.org.coder.utils;

import java.util.Vector;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 21.03.2005
 * Time: 19:18:12
 * To change this template use File | Settings | File Templates.
 */
public class IInterceptorSeq implements IInterceptor
{
	private IInterceptor impl;//ссылка на имплементацию интерсептора ()
	private IInterceptor next;//указатель на следующий интерсептор

	private short typeID=TypeId.getNextId();

	public IInterceptorSeq(IInterceptor impl)
	{
		this.impl = impl;
	}

	public void registerInterseptor(IInterceptor next)
	{
		impl.registerInterseptor(next);//уведомление о регистрации
		this.next=next;
	}

	public CallMessageImpl preInvoke(CallMessageImpl msg, Vector attr,SysCoderEx ex) throws IOException, SysCoderEx
	{
		return impl.preInvoke(msg, attr,ex);
	}

	public CallMessageImpl postInvoke(CallMessageImpl msg, Vector attr, SysCoderEx ex) throws IOException, SysCoderEx
	{
		return impl.postInvoke(msg, attr, ex);
	}

	public CallMessageImpl invoke(CallMessageImpl msg, Vector attr) throws IOException, SysCoderEx
	{
		try
		{
			return impl.invoke(msg,attr);
		}
		catch (SysCoderEx sysCoderEx)
		{
			SysCoderEx postEx=null;
			CallMessageImpl retVal=null;
//Решение сделано для того что бы можно было не вызывать
//next.invoke(retVal,attr), а преинвоком передать обработку себе
// же в постинвоке

			try
			{
				retVal=preInvoke(msg,attr,sysCoderEx);
				if (next!=null)	retVal=next.invoke(retVal,attr);
			}
			catch (SysCoderEx sysCoderEx1)
			{
				postEx=sysCoderEx1;
			}
			return postInvoke(retVal,attr, postEx);
		}

	}

	public boolean isOnService() throws IOException, SysCoderEx
	{
		return true;
	}

	public String getTypeName()
	{
		return impl.getTypeName();
	}

	public short getTypeID()
	{
		return typeID;
	}
}

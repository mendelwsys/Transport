package su.org.coder2.test00.authenticate;

import su.org.coder.interceptors.authinterceptor.SkelAuthenticate;
import su.org.coder.utils.*;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 22.03.2005
 * Time: 16:29:51
 * To change this template use File | Settings | File Templates.
 */
public class SkelAuthenticateImpl extends SkelAuthenticate
		implements IInterceptor
{
	private String regName;

	private static class AuthEx extends SysCoderEx
	{
		public CallMessageImpl msg;
		public AuthEx(CallMessageImpl msg)
		{
			super(Constants.ERR_BYEXT,Constants.ERR_BYEXT_MSG);
			this.msg = msg;
		}
	}
	public SkelAuthenticateImpl(String regName)
	{
		this.regName = regName;
	}
	public String beginAuth(String userName) throws SysCoderEx
	{
		Vector en = (Vector) getCallCtx();
		for (int i = 0; i < en.size(); i++)
			System.out.println(en.elementAt(i));

		System.out.println("UserName:"+userName);
		return "ok";
	}

	public String endAuth(String guessPhrase) throws IOException, SysCoderEx
	{
		Vector en = (Vector) getCallCtx();
		for (int i = 0; i < en.size(); i++)
			System.out.println(en.elementAt(i));

		System.out.println("guessPhrase");
		return "ok";
	}

	public void registerInterseptor(IInterceptor next)
	{
		System.out.println("call registerInterseptor");
	}

	public CallMessageImpl preInvoke(CallMessageImpl msg, Vector attr,SysCoderEx ex) throws IOException, SysCoderEx
	{
		if ( ex.status == Constants.ERR_TYPEID &&
				msg.command == Constants.INIT_VAL)
		{
//Установим соединение и выдадим исключение
			String regName$typeName = (String) _string0Ah.createChannelObj(msg.bmessage);
			String[] regNameAtypeName = SkelRouter.split2(regName$typeName,"[#]");
			if (regNameAtypeName[0].compareTo(regName) ==0 &&
					getTypeName().compareTo(regNameAtypeName[1]) == 0)
//ObjectId в данном случае один на все интерсепторы, поскольку все вызовы проходят через один инетрсептор
			//Сделаем так что бы
			throw new AuthEx(new CallMessageImpl(Constants.INIT_VAL, getTypeID(), (short)777, (byte) 0, null));
		}
		System.out.println("call preInvoke: "+Counter);
		return msg;
	}

    private int Counter=0;

	public CallMessageImpl postInvoke(CallMessageImpl msg, Vector attr, SysCoderEx ex) throws IOException, SysCoderEx
	{
		if (ex==null)
		{
			System.out.println("call postInvoke :"+Counter);
			Counter++;
			return msg;
		}

		if (ex instanceof AuthEx)
			return ((AuthEx)ex).msg;
		else
			throw ex;
	}

	public boolean isOnService() throws SysCoderEx
	{
		return true;
	}
}

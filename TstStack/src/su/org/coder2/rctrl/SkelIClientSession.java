package su.org.coder2.rctrl;

import su.org.coder.utils.*;

import java.io.*;

import java.util.Vector;


public abstract class SkelIClientSession
		implements IIClientSession ,IInvoker
{
	private static String typeName="IClientSession";
	private short typeID = TypeId.getNextId();
	protected long lastApply=System.currentTimeMillis();
	protected boolean onService=true;
	private IIClientSession impl=this;
	private static ThreadLocal callCtx = new ThreadLocal();

	protected Object getCallCtx()
	{
		return callCtx.get();
	}

	protected void setCallCtx(Object attr)
	{
		callCtx.set(attr);//Установить контекст вызова
	}

	protected ISerializeHelper _string0Ah = new String0AHelper();
	public CallMessageImpl invoke(CallMessageImpl in, Vector attr) throws IOException, SysCoderEx
	{
		entranceNotify();
		try
		{
//Идентификатор typeID уникален для каждого экземпляра класса в пределах процесса
//поэтому невозможно что бы сообщение предназначалось вышестоящим объектам,
//а было обработано по ошибке нижестоящим
		if (in.typeID != typeID)
			throw new SysCoderEx(Constants.ERR_TYPEID, Constants.ERR_TYPEID_MSG); //Здесь нормальная реакция интерсептора
		setCallCtx(attr);
		DataInputStream dis =(in.bmessage!=null)?
				new DataInputStream(new ByteArrayInputStream(in.bmessage, 0, in.bmessage.length)):null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		CallMessageImpl retMessage = new CallMessageImpl(in.command==Constants.INVOKE_VAL?Constants.RET_VAL:Constants.RET_VAL_NORET, in.typeID, in.objID, in.methodCode, null);
		switch (in.methodCode)
		{
			case 1 :
			{
				String username = (String)_string0Ah.createChannelObj(dis);
				String userpass = (String)_string0Ah.createChannelObj(dis);
				String openSession0AretVal = impl.openSession(username,userpass);
				_string0Ah.serialChannelObj(openSession0AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 2 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				boolean checkSession0AretVal = impl.checkSession(cliname);
				dos.writeBoolean(checkSession0AretVal);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 3 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				int closeSession0AretVal = impl.closeSession(cliname);
				dos.write(SerialUtils.serialint32(closeSession0AretVal));
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			default:
				throw new SysCoderEx(Constants.ERR_OPER, Constants.ERR_OPER_MSG);
		}
		return retMessage;
		}
		catch (IOException e)
		{
			setOffLine();
			throw e;
		}
		finally
		{
    		leaveNotify ();
		}
	}

	public String getTypeName()
	{
		return typeName;
	}

	public short getTypeID()
	{
		return typeID;
	}

	public boolean isOnService() throws SysCoderEx
	{
		return onService;
	}
	protected void setOffLine()
	{
		onService=false;
	}
	protected void entranceNotify () throws SysCoderEx
	{
		if (!onService)
			throw new SysCoderEx(Constants.ERR_NO_SERVICE, Constants.ERR_NO_SERVICE_MSG);//Объект отключился
//Установка последнего времени обращения
		lastApply=System.currentTimeMillis();
	}
	protected void leaveNotify ()
	{
		
	}
}

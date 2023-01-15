package su.org.coder2.gpstransl;

import su.org.coder.utils.*;

import java.io.*;

import java.util.Vector;


public abstract class SkelGPSInterface
		implements IGPSInterface ,IInvoker
{
	private static String typeName="GPSInterface";
	private short typeID = TypeId.getNextId();
	protected long lastApply=System.currentTimeMillis();
	protected boolean onService=true;
	private IGPSInterface impl=this;
	private static ThreadLocal callCtx = new ThreadLocal();

	protected Object getCallCtx()
	{
		return callCtx.get();
	}

	protected void setCallCtx(Object attr)
	{
		callCtx.set(attr);//Установить контекст вызова
	}

	protected ISerializeHelper _clientInfo1Ah = new ClientInfo1AHelper();
	protected ISerializeHelper _dataStruct0Ah = new dataStruct0AHelper();
	protected ISerializeHelper _messageStruct0Ah = new messageStruct0AHelper();
	protected ISerializeHelper _dataStruct1Ah = new dataStruct1AHelper();
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
				ClientInfo[] getClientInfo1AretVal = impl.getClientInfo();
				_clientInfo1Ah.serialChannelObj(getClientInfo1AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 2 :
			{
				int idclient = SerialUtils.unserialint(dis,4);
				int dotnumber = SerialUtils.unserialint(dis,4);
				int timeOut = SerialUtils.unserialint(dis,4);
				dataStruct get0AretVal = impl.get(idclient,dotnumber,timeOut);
				_dataStruct0Ah.serialChannelObj(get0AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 3 :
			{
				dataStruct message = (dataStruct)_dataStruct0Ah.createChannelObj(dis);
				impl.put(message);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 4 :
			{
				messageStruct strmessage = (messageStruct)_messageStruct0Ah.createChannelObj(dis);
				impl.putmsg(strmessage);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 5 :
			{
				int idclient = SerialUtils.unserialint(dis,4);
				int msgnumber = SerialUtils.unserialint(dis,4);
				int timeOut = SerialUtils.unserialint(dis,4);
				messageStruct getmsg0AretVal = impl.getmsg(idclient,msgnumber,timeOut);
				_messageStruct0Ah.serialChannelObj(getmsg0AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 6 :
			{
				int idclient = SerialUtils.unserialint(dis,4);
				int dotnumber = SerialUtils.unserialint(dis,4);
				int timeOut = SerialUtils.unserialint(dis,4);
				dataStruct[] getHistory1AretVal = impl.getHistory(idclient,dotnumber,timeOut);
				_dataStruct1Ah.serialChannelObj(getHistory1AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 7 :
			{
				int idclient = SerialUtils.unserialint(dis,4);
				impl.resetHistory(idclient);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 8 :
			{
				impl.ping();
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

package su.org.coder2.chat;

import su.org.coder.utils.*;

import java.io.*;

import java.util.Vector;


public abstract class SkelChatInterface
		implements IChatInterface ,IInvoker
{
	private static String typeName="ChatInterface";
	private short typeID = TypeId.getNextId();
	protected long lastApply=System.currentTimeMillis();
	protected boolean onService=true;
	private IChatInterface impl=this;
	private static ThreadLocal callCtx = new ThreadLocal();

	protected Object getCallCtx()
	{
		return callCtx.get();
	}

	protected void setCallCtx(Object attr)
	{
		callCtx.set(attr);//Установить контекст вызова
	}

	protected ISerializeHelper _tException10Ah = new TException10AHelper();
	protected ISerializeHelper _tException20Ah = new TException20AHelper();
	protected ISerializeHelper _exceptionAddInfo0Ah = new ExceptionAddInfo0AHelper ();
	protected ISerializeHelper _string0Ah = new String0AHelper();
	protected ISerializeHelper _string1Ah = new String1AHelper();
	protected ISerializeHelper _byte1Ah = new byte1AHelper();
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
				String message = (String)_string0Ah.createChannelObj(dis);
				try
				{
					impl.Put(message);
				}
				catch ( TException1 _tException1 )
				{
					retMessage.command = Constants.APPEX_VAL;
					ExceptionAddInfo exAddInfo = new ExceptionAddInfo();
					exAddInfo.exIdentifier = new Byte((byte)0);
					_exceptionAddInfo0Ah.serialChannelObj(exAddInfo ,dos);
					_tException10Ah.serialChannelObj(_tException1 ,dos);
				}
				catch ( TException2 _tException2 )
				{
					retMessage.command = Constants.APPEX_VAL;
					ExceptionAddInfo exAddInfo = new ExceptionAddInfo();
					exAddInfo.exIdentifier = new Byte((byte)1);
					_exceptionAddInfo0Ah.serialChannelObj(exAddInfo ,dos);
					_tException20Ah.serialChannelObj(_tException2 ,dos);
				}
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 2 :
			{
				int timeOut = SerialUtils.unserialint(dis,4);
				String Get0AretVal = impl.Get(timeOut);
				_string0Ah.serialChannelObj(Get0AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 3 :
			{
					String1AHolder myarrHolder=new String1AHolder();
				try
				{
					impl.DestroyObject(myarrHolder);
					_string1Ah.serialChannelObj(myarrHolder.value,dos);
				}
				catch ( TException2 _tException2 )
				{
					retMessage.command = Constants.APPEX_VAL;
					_tException20Ah.serialChannelObj(_tException2 ,dos);
				}
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 4 :
			{
				byte arg1_oct = dis.readByte();
				byte[] argarr_in = (byte[])_byte1Ah.createChannelObj(dis);
				byte[] argarr_inout = (byte[])_byte1Ah.createChannelObj(dis);
					byte1AHolder argarr_inoutHolder=new byte1AHolder(argarr_inout);
				String M40AretVal = impl.M4(arg1_oct,argarr_in,argarr_inoutHolder);
					_byte1Ah.serialChannelObj(argarr_inoutHolder.value,dos);
				_string0Ah.serialChannelObj(M40AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 5 :
			{
				boolean arg0 = dis.readBoolean();
				short arg2 = (short)SerialUtils.unserialint(dis,2);
				boolean arg3 = dis.readBoolean();
					boolean0AHolder arg3Holder=new boolean0AHolder(arg3);
				byte M50AretVal = impl.M5(arg0,arg2,arg3Holder);
					dos.writeBoolean(arg3Holder.value);
				dos.writeByte(M50AretVal);
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

package su.org.coder2.rctrl;

import su.org.coder.utils.*;

import java.io.*;

import java.util.Vector;


public abstract class SkelIRctrl
		implements IIRctrl ,IInvoker
{
	private static String typeName="IRctrl";
	private short typeID = TypeId.getNextId();
	protected long lastApply=System.currentTimeMillis();
	protected boolean onService=true;
	private IIRctrl impl=this;
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
	protected ISerializeHelper _pairOptions2Ah = new PairOptions2AHelper();
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
				String cliname = (String)_string0Ah.createChannelObj(dis);
				PairOptions[][] options = (PairOptions[][])_pairOptions2Ah.createChannelObj(dis);
				int setOptions0AretVal = impl.setOptions(cliname,options);
				dos.write(SerialUtils.serialint32(setOptions0AretVal));
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 2 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				PairOptions[][] getOptions2AretVal = impl.getOptions(cliname);
				_pairOptions2Ah.serialChannelObj(getOptions2AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 3 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				int getStatus0AretVal = impl.getStatus(cliname);
				dos.write(SerialUtils.serialint32(getStatus0AretVal));
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 4 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				int drwWidth = SerialUtils.unserialint(dis,4);
				int drwHeight = SerialUtils.unserialint(dis,4);
				byte[] getInitialViewParams1AretVal = impl.getInitialViewParams(cliname,drwWidth,drwHeight);
				_byte1Ah.serialChannelObj(getInitialViewParams1AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 5 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				int drwWidth = SerialUtils.unserialint(dis,4);
				int drwHeight = SerialUtils.unserialint(dis,4);
				byte[] getViewParams1AretVal = impl.getViewParams(cliname,drwWidth,drwHeight);
				_byte1Ah.serialChannelObj(getViewParams1AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 6 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				boolean setOn = dis.readBoolean();
				int setDrawNames0AretVal = impl.setDrawNames(cliname,setOn);
				dos.write(SerialUtils.serialint32(setDrawNames0AretVal));
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 7 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				String action = (String)_string0Ah.createChannelObj(dis);
				byte[] params = (byte[])_byte1Ah.createChannelObj(dis);
				int setSpecAction0AretVal = impl.setSpecAction(cliname,action,params);
				dos.write(SerialUtils.serialint32(setSpecAction0AretVal));
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 8 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				int setSpecActionOff0AretVal = impl.setSpecActionOff(cliname);
				dos.write(SerialUtils.serialint32(setSpecActionOff0AretVal));
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 9 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				int setSpecActionOn0AretVal = impl.setSpecActionOn(cliname);
				dos.write(SerialUtils.serialint32(setSpecActionOn0AretVal));
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

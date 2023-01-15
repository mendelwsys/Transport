package su.org.coder2.rctrl;

import su.org.coder.utils.*;

import java.io.*;

import java.util.Vector;


public abstract class SkelISearchObjects
		implements IISearchObjects ,IInvoker
{
	private static String typeName="ISearchObjects";
	private short typeID = TypeId.getNextId();
	protected long lastApply=System.currentTimeMillis();
	protected boolean onService=true;
	private IISearchObjects impl=this;
	private static ThreadLocal callCtx = new ThreadLocal();

	protected Object getCallCtx()
	{
		return callCtx.get();
	}

	protected void setCallCtx(Object attr)
	{
		callCtx.set(attr);//Установить контекст вызова
	}

	protected ISerializeHelper _gisObjectPair1Ah = new GisObjectPair1AHelper();
	protected ISerializeHelper _string0Ah = new String0AHelper();
	protected ISerializeHelper _mouseCrd0Ah = new MouseCrd0AHelper();
	protected ISerializeHelper _retSelRastr0Ah = new RetSelRastr0AHelper();
	protected ISerializeHelper _changePos0Ah = new ChangePos0AHelper();
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
				MouseCrd crd = (MouseCrd)_mouseCrd0Ah.createChannelObj(dis);
				GisObjectPair[] getObjectsByMousePnt1AretVal = impl.getObjectsByMousePnt(cliname,crd);
				_gisObjectPair1Ah.serialChannelObj(getObjectsByMousePnt1AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 2 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				String id_lr = (String)_string0Ah.createChannelObj(dis);
				String obj_id = (String)_string0Ah.createChannelObj(dis);
				RetSelRastr getPictureById0AretVal = impl.getPictureById(cliname,id_lr,obj_id);
				_retSelRastr0Ah.serialChannelObj(getPictureById0AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 3 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				String objname = (String)_string0Ah.createChannelObj(dis);
				ChangePos toObjectByName0AretVal = impl.toObjectByName(cliname,objname);
				_changePos0Ah.serialChannelObj(toObjectByName0AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 4 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				String id_lr = (String)_string0Ah.createChannelObj(dis);
				String obj_id = (String)_string0Ah.createChannelObj(dis);
				RetSelRastr selectOrGetPictureById0AretVal = impl.selectOrGetPictureById(cliname,id_lr,obj_id);
				_retSelRastr0Ah.serialChannelObj(selectOrGetPictureById0AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 5 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				String id_lr = (String)_string0Ah.createChannelObj(dis);
				String obj_id = (String)_string0Ah.createChannelObj(dis);
				int selectObjectById0AretVal = impl.selectObjectById(cliname,id_lr,obj_id);
				dos.write(SerialUtils.serialint32(selectObjectById0AretVal));
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 6 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				String id_lr = (String)_string0Ah.createChannelObj(dis);
				String obj_id = (String)_string0Ah.createChannelObj(dis);
				int deselectObjectById0AretVal = impl.deselectObjectById(cliname,id_lr,obj_id);
				dos.write(SerialUtils.serialint32(deselectObjectById0AretVal));
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 7 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				boolean deselectPrev = dis.readBoolean();
				byte[] ids = (byte[])_byte1Ah.createChannelObj(dis);
				int selectObjectsByIds0AretVal = impl.selectObjectsByIds(cliname,deselectPrev,ids);
				dos.write(SerialUtils.serialint32(selectObjectsByIds0AretVal));
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 8 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				boolean deselectPrev = dis.readBoolean();
				byte[] ids = (byte[])_byte1Ah.createChannelObj(dis);
				int selectLargeObjectsByIds0AretVal = impl.selectLargeObjectsByIds(cliname,deselectPrev,ids);
				dos.write(SerialUtils.serialint32(selectLargeObjectsByIds0AretVal));
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 9 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				int deselectAllObjects0AretVal = impl.deselectAllObjects(cliname);
				dos.write(SerialUtils.serialint32(deselectAllObjects0AretVal));
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

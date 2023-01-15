package su.org.coder2.rctrl;

import su.org.coder.utils.*;

import java.io.*;

import java.util.Vector;


public abstract class SkelIRemoteRastersProvider
		implements IIRemoteRastersProvider ,IInvoker
{
	private static String typeName="IRemoteRastersProvider";
	private short typeID = TypeId.getNextId();
	protected long lastApply=System.currentTimeMillis();
	protected boolean onService=true;
	private IIRemoteRastersProvider impl=this;
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
	protected ISerializeHelper _byte1Ah = new byte1AHelper();
	protected ISerializeHelper _double1Ah = new double1AHelper();
	protected ISerializeHelper _double2Ah = new double2AHelper();
	protected ISerializeHelper _int1Ah = new int1AHelper();
	protected ISerializeHelper _imagePair0Ah = new ImagePair0AHelper();
	protected ISerializeHelper _imagePair1Ah = new ImagePair1AHelper();
	protected ISerializeHelper _int2Ah = new int2AHelper();
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
				byte[] cnv = (byte[])_byte1Ah.createChannelObj(dis);
				double[] bp = (double[])_double1Ah.createChannelObj(dis);
					double2AHolder mp12Holder=new double2AHolder();
					double2AHolder pt12Holder=new double2AHolder();
					int1AHolder dxdyHolder=new int1AHolder();
					int1AHolder szXszYHolder=new int1AHolder();
				int dividerRequest0AretVal = impl.dividerRequest(cliname,cnv,bp,mp12Holder,pt12Holder,dxdyHolder,szXszYHolder);
					_double2Ah.serialChannelObj(mp12Holder.value,dos);
					_double2Ah.serialChannelObj(pt12Holder.value,dos);
					_int1Ah.serialChannelObj(dxdyHolder.value,dos);
					_int1Ah.serialChannelObj(szXszYHolder.value,dos);
				dos.write(SerialUtils.serialint32(dividerRequest0AretVal));
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 2 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				double[] dxdy = (double[])_double1Ah.createChannelObj(dis);
				int[] nxny = (int[])_int1Ah.createChannelObj(dis);
				ImagePair getImageRequest0AretVal = impl.getImageRequest(cliname,dxdy,nxny);
				_imagePair0Ah.serialChannelObj(getImageRequest0AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 3 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				double[] dxdy = (double[])_double1Ah.createChannelObj(dis);
				int[][] nxny = (int[][])_int2Ah.createChannelObj(dis);
				ImagePair[] getImagesRequest1AretVal = impl.getImagesRequest(cliname,dxdy,nxny);
				_imagePair1Ah.serialChannelObj(getImagesRequest1AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 4 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				int width = SerialUtils.unserialint(dis,4);
				int high = SerialUtils.unserialint(dis,4);
				byte[] getLegentRaster1AretVal = impl.getLegentRaster(cliname,width,high);
				_byte1Ah.serialChannelObj(getLegentRaster1AretVal,dos);
				retMessage.bmessage = bos.toByteArray();
			}
			break;
			case 5 :
			{
				String cliname = (String)_string0Ah.createChannelObj(dis);
				int width = SerialUtils.unserialint(dis,4);
				int high = SerialUtils.unserialint(dis,4);
				byte[] getScaleRulerRaster1AretVal = impl.getScaleRulerRaster(cliname,width,high);
				_byte1Ah.serialChannelObj(getScaleRulerRaster1AretVal,dos);
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

package su.org.coder2.rctrl;

import su.org.coder.utils.*;

import java.io.*;

public class ProxyIRemoteRastersProvider
		implements IIRemoteRastersProviderProxy ,IMetaInfo
{
	private IInvoker invoker;
	private static String typeName="IRemoteRastersProvider";
	private short typeID;
	private short objID;

	ProxyIRemoteRastersProvider(IInvoker invoker,short typeID,short objID)
	{
		this.invoker = invoker;
		this.typeID = typeID;
		this.objID=objID;
	}

	protected ISerializeHelper _string0Ah = new String0AHelper();
	protected ISerializeHelper _byte1Ah = new byte1AHelper();
	protected ISerializeHelper _double1Ah = new double1AHelper();
	protected ISerializeHelper _double2Ah = new double2AHelper();
	protected ISerializeHelper _int1Ah = new int1AHelper();
	protected ISerializeHelper _imagePair0Ah = new ImagePair0AHelper();
	protected ISerializeHelper _imagePair1Ah = new ImagePair1AHelper();
	protected ISerializeHelper _int2Ah = new int2AHelper();

	public static IIRemoteRastersProviderProxy bind(IInvoker invoker,String regName) throws IOException, SysCoderEx
	{

    	String0AHelper s_string0Ah=new String0AHelper();

    	CallMessageImpl inimsg=new CallMessageImpl(Constants.INIT_VAL,(short) 0,(short) 0,(byte) 0,
				s_string0Ah.serialChannelObj(regName+"#"+typeName));
		CallMessageImpl retmsg=invoker.invoke(inimsg,null);
		switch (retmsg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,retmsg);
			case Constants.INIT_VAL:
				return new ProxyIRemoteRastersProvider(invoker,retmsg.typeID,retmsg.objID);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public int dividerRequest(String cliname,byte[] cnv,double[] bp,double2AHolder mp12,double2AHolder pt12,int1AHolder dxdy,int1AHolder szXszY) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		_byte1Ah.serialChannelObj(cnv,dos);
		_double1Ah.serialChannelObj(bp,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)1,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				mp12.value=(double[][])_double2Ah.createChannelObj(dis);
				pt12.value=(double[][])_double2Ah.createChannelObj(dis);
				dxdy.value=(int[])_int1Ah.createChannelObj(dis);
				szXszY.value=(int[])_int1Ah.createChannelObj(dis);
				return SerialUtils.unserialint(dis,4);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public ImagePair getImageRequest(String cliname,double[] dxdy,int[] nxny) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		_double1Ah.serialChannelObj(dxdy,dos);
		_int1Ah.serialChannelObj(nxny,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)2,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return (ImagePair)_imagePair0Ah.createChannelObj(dis);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public ImagePair[] getImagesRequest(String cliname,double[] dxdy,int[][] nxny) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		_double1Ah.serialChannelObj(dxdy,dos);
		_int2Ah.serialChannelObj(nxny,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)3,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return (ImagePair[])_imagePair1Ah.createChannelObj(dis);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public byte[] getLegentRaster(String cliname,int width,int high) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		dos.write(SerialUtils.serialint32(width));
		dos.write(SerialUtils.serialint32(high));
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)4,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return (byte[])_byte1Ah.createChannelObj(dis);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public byte[] getScaleRulerRaster(String cliname,int width,int high) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		dos.write(SerialUtils.serialint32(width));
		dos.write(SerialUtils.serialint32(high));
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)5,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return (byte[])_byte1Ah.createChannelObj(dis);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
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

	public boolean isOnService()
			throws SysCoderEx, IOException
	{
		return invoker.isOnService();
	}

}

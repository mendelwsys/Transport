package su.org.coder2.rctrl;

import su.org.coder.utils.*;

import java.io.*;

public class ProxyIRctrl
		implements IIRctrlProxy ,IMetaInfo
{
	private IInvoker invoker;
	private static String typeName="IRctrl";
	private short typeID;
	private short objID;

	ProxyIRctrl(IInvoker invoker,short typeID,short objID)
	{
		this.invoker = invoker;
		this.typeID = typeID;
		this.objID=objID;
	}

	protected ISerializeHelper _string0Ah = new String0AHelper();
	protected ISerializeHelper _pairOptions2Ah = new PairOptions2AHelper();
	protected ISerializeHelper _byte1Ah = new byte1AHelper();

	public static IIRctrlProxy bind(IInvoker invoker,String regName) throws IOException, SysCoderEx
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
				return new ProxyIRctrl(invoker,retmsg.typeID,retmsg.objID);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public int setOptions(String cliname,PairOptions[][] options) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		_pairOptions2Ah.serialChannelObj(options,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)1,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return SerialUtils.unserialint(dis,4);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public PairOptions[][] getOptions(String cliname) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)2,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return (PairOptions[][])_pairOptions2Ah.createChannelObj(dis);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public int getStatus(String cliname) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)3,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return SerialUtils.unserialint(dis,4);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public byte[] getInitialViewParams(String cliname,int drwWidth,int drwHeight) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		dos.write(SerialUtils.serialint32(drwWidth));
		dos.write(SerialUtils.serialint32(drwHeight));
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

	public byte[] getViewParams(String cliname,int drwWidth,int drwHeight) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		dos.write(SerialUtils.serialint32(drwWidth));
		dos.write(SerialUtils.serialint32(drwHeight));
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

	public int setDrawNames(String cliname,boolean setOn) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		dos.writeBoolean(setOn);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)6,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return SerialUtils.unserialint(dis,4);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public int setSpecAction(String cliname,String action,byte[] params) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		_string0Ah.serialChannelObj(action,dos);
		_byte1Ah.serialChannelObj(params,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)7,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return SerialUtils.unserialint(dis,4);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public int setSpecActionOff(String cliname) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)8,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return SerialUtils.unserialint(dis,4);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public int setSpecActionOn(String cliname) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)9,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return SerialUtils.unserialint(dis,4);
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

package su.org.coder2.chat;

import su.org.coder.utils.*;

import java.io.*;

public class ProxyChatInterface
		implements IChatInterfaceProxy ,IMetaInfo
{
	private IInvoker invoker;
	private static String typeName="ChatInterface";
	private short typeID;
	private short objID;

	ProxyChatInterface(IInvoker invoker,short typeID,short objID)
	{
		this.invoker = invoker;
		this.typeID = typeID;
		this.objID=objID;
	}

	protected ISerializeHelper _tException10Ah = new TException10AHelper();
	protected ISerializeHelper _tException20Ah = new TException20AHelper();
	protected ISerializeHelper _exceptionAddInfo0Ah = new ExceptionAddInfo0AHelper ();
	protected ISerializeHelper _string0Ah = new String0AHelper();
	protected ISerializeHelper _string1Ah = new String1AHelper();
	protected ISerializeHelper _byte1Ah = new byte1AHelper();

	public static IChatInterfaceProxy bind(IInvoker invoker,String regName) throws IOException, SysCoderEx
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
				return new ProxyChatInterface(invoker,retmsg.typeID,retmsg.objID);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public void Put(String message) throws IOException, SysCoderEx , TException1, TException2
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(message,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL_NORET, typeID, objID, (byte)1,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
			case Constants.RET_VAL_NORET:
				return;
			case Constants.APPEX_VAL:
				{
					DataInputStream dis = new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
					ExceptionAddInfo addInfo = (ExceptionAddInfo) _exceptionAddInfo0Ah.createChannelObj(dis);
					switch (addInfo.exIdentifier.byteValue())
					{
						case 0:
							throw (TException1)_tException10Ah.createChannelObj(dis);
						case 1:
							throw (TException2)_tException20Ah.createChannelObj(dis);
					}
				}
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public String Get(int timeOut) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		dos.write(SerialUtils.serialint32(timeOut));
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)2,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return (String)_string0Ah.createChannelObj(dis);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public void DestroyObject(String1AHolder myarr) throws IOException, SysCoderEx , TException2
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)3,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				myarr.value=(String[])_string1Ah.createChannelObj(dis);
				return;
			case Constants.APPEX_VAL:
				throw (TException2)_tException20Ah.createChannelObj(msg.bmessage);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public String M4(byte arg1_oct,byte[] argarr_in,byte1AHolder argarr_inout) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		dos.writeByte(arg1_oct);
		_byte1Ah.serialChannelObj(argarr_in,dos);
		_byte1Ah.serialChannelObj(argarr_inout.value,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)4,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				argarr_inout.value=(byte[])_byte1Ah.createChannelObj(dis);
				return (String)_string0Ah.createChannelObj(dis);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public byte M5(boolean arg0,short arg2,boolean0AHolder arg3) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		dos.writeBoolean(arg0);
		dos.write(SerialUtils.serialint16(arg2));
		dos.writeBoolean(arg3.value);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)5,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				arg3.value=dis.readBoolean();
				return dis.readByte();
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

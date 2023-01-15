package su.org.coder2.rctrl;

import su.org.coder.utils.*;

import java.io.*;

public class ProxyISearchObjects
		implements IISearchObjectsProxy ,IMetaInfo
{
	private IInvoker invoker;
	private static String typeName="ISearchObjects";
	private short typeID;
	private short objID;

	ProxyISearchObjects(IInvoker invoker,short typeID,short objID)
	{
		this.invoker = invoker;
		this.typeID = typeID;
		this.objID=objID;
	}

	protected ISerializeHelper _gisObjectPair1Ah = new GisObjectPair1AHelper();
	protected ISerializeHelper _string0Ah = new String0AHelper();
	protected ISerializeHelper _mouseCrd0Ah = new MouseCrd0AHelper();
	protected ISerializeHelper _retSelRastr0Ah = new RetSelRastr0AHelper();
	protected ISerializeHelper _changePos0Ah = new ChangePos0AHelper();
	protected ISerializeHelper _byte1Ah = new byte1AHelper();

	public static IISearchObjectsProxy bind(IInvoker invoker,String regName) throws IOException, SysCoderEx
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
				return new ProxyISearchObjects(invoker,retmsg.typeID,retmsg.objID);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public GisObjectPair[] getObjectsByMousePnt(String cliname,MouseCrd crd) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		_mouseCrd0Ah.serialChannelObj(crd,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)1,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return (GisObjectPair[])_gisObjectPair1Ah.createChannelObj(dis);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public RetSelRastr getPictureById(String cliname,String id_lr,String obj_id) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		_string0Ah.serialChannelObj(id_lr,dos);
		_string0Ah.serialChannelObj(obj_id,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)2,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return (RetSelRastr)_retSelRastr0Ah.createChannelObj(dis);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public ChangePos toObjectByName(String cliname,String objname) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		_string0Ah.serialChannelObj(objname,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)3,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return (ChangePos)_changePos0Ah.createChannelObj(dis);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public RetSelRastr selectOrGetPictureById(String cliname,String id_lr,String obj_id) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		_string0Ah.serialChannelObj(id_lr,dos);
		_string0Ah.serialChannelObj(obj_id,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)4,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
				DataInputStream dis=new DataInputStream(new ByteArrayInputStream(msg.bmessage, 0, msg.bmessage.length));
				return (RetSelRastr)_retSelRastr0Ah.createChannelObj(dis);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public int selectObjectById(String cliname,String id_lr,String obj_id) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		_string0Ah.serialChannelObj(id_lr,dos);
		_string0Ah.serialChannelObj(obj_id,dos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL, typeID, objID, (byte)5,
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

	public int deselectObjectById(String cliname,String id_lr,String obj_id) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		_string0Ah.serialChannelObj(id_lr,dos);
		_string0Ah.serialChannelObj(obj_id,dos);
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

	public int selectObjectsByIds(String cliname,boolean deselectPrev,byte[] ids) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		dos.writeBoolean(deselectPrev);
		_byte1Ah.serialChannelObj(ids,dos);
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

	public int selectLargeObjectsByIds(String cliname,boolean deselectPrev,byte[] ids) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		_string0Ah.serialChannelObj(cliname,dos);
		dos.writeBoolean(deselectPrev);
		_byte1Ah.serialChannelObj(ids,dos);
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

	public int deselectAllObjects(String cliname) throws IOException, SysCoderEx 
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

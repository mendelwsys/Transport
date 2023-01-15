package su.org.coder2.rctrl;

import su.org.coder.utils.*;

import java.io.*;

public class ProxyIStatAnalize
		implements IIStatAnalizeProxy ,IMetaInfo
{
	private IInvoker invoker;
	private static String typeName="IStatAnalize";
	private short typeID;
	private short objID;

	ProxyIStatAnalize(IInvoker invoker,short typeID,short objID)
	{
		this.invoker = invoker;
		this.typeID = typeID;
		this.objID=objID;
	}

	protected ISerializeHelper _retSelRastr0Ah = new RetSelRastr0AHelper();
	protected ISerializeHelper _string0Ah = new String0AHelper();
	protected ISerializeHelper _mouseCrd0Ah = new MouseCrd0AHelper();

	public static IIStatAnalizeProxy bind(IInvoker invoker,String regName) throws IOException, SysCoderEx
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
				return new ProxyIStatAnalize(invoker,retmsg.typeID,retmsg.objID);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public RetSelRastr getLevelByMousePnt(String cliname,MouseCrd crd) throws IOException, SysCoderEx 
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
				return (RetSelRastr)_retSelRastr0Ah.createChannelObj(dis);
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

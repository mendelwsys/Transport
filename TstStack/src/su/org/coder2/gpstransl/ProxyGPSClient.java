package su.org.coder2.gpstransl;

import su.org.coder.utils.*;

import java.io.*;

public class ProxyGPSClient
		implements IGPSClientProxy ,IMetaInfo
{
	private IInvoker invoker;
	private static String typeName="GPSClient";
	private short typeID;
	private short objID;

	ProxyGPSClient(IInvoker invoker,short typeID,short objID)
	{
		this.invoker = invoker;
		this.typeID = typeID;
		this.objID=objID;
	}


	public static IGPSClientProxy bind(IInvoker invoker,String regName) throws IOException, SysCoderEx
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
				return new ProxyGPSClient(invoker,retmsg.typeID,retmsg.objID);
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public void startFlow(boolean reconnect) throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		dos.writeBoolean(reconnect);
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
			default:
				throw new SysCoderEx(Constants.ERR_NOTSPEC,Constants.ERR_UNEXP_MSG);
		}
	}

	public void stopFlow() throws IOException, SysCoderEx 
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
		CallMessageImpl msg = new CallMessageImpl(Constants.INVOKE_VAL_NORET, typeID, objID, (byte)2,
				bos.toByteArray());
		msg = invoker.invoke(msg,null);

		switch (msg.command)
		{
			case Constants.SYSEX_VAL:
				throw new SysCoderEx(Constants.SYSEX_VAL,msg);
			case Constants.RET_VAL:
			case Constants.RET_VAL_NORET:
				return;
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

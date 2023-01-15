package su.org.coder2.gpstransl;
import su.org.coder.utils.*;

import java.io.*;

public class messageStruct0AHelper implements ISerializeHelper
{

	public byte[]  serialChannelObj(Object toArray) throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(bos);
	    serialChannelObj(toArray,dos);
		return bos.toByteArray();
	}

	public Object createChannelObj(byte[] fromArray) throws IOException
	{
		if (fromArray==null)
			return null;
		DataInputStream dis=new DataInputStream(new ByteArrayInputStream(fromArray, 0, fromArray.length));
		return createChannelObj(dis);
	}

	ISerializeHelper _string0Ah = new String0AHelper();

	public Object createChannelObj(DataInputStream dis) throws IOException
	{
		if (dis.readByte()==1)
			return null;
		messageStruct st = new messageStruct();
		st.msgnumber = SerialUtils.unserialint(dis,4);
		st.message=(String)_string0Ah.createChannelObj(dis);
		st.clientid = SerialUtils.unserialint(dis,4);
		return st;
	}

	public void serialChannelObj(Object toArray, DataOutputStream dos) throws IOException
	{
		if (toArray==null)
		{
			dos.writeByte(1);
			return;
		}
		dos.writeByte(0);
		messageStruct st = (messageStruct) toArray;
		dos.write(SerialUtils.serialint32(st.msgnumber));
		_string0Ah.serialChannelObj( st.message,dos);
		dos.write(SerialUtils.serialint32(st.clientid));
	}

	public String toPrintableString(Object toPrintable)
	{
		if (toPrintable instanceof messageStruct)
		{
			messageStruct st = (messageStruct) toPrintable;
			return
				"msgnumber:"+st.msgnumber+" "+
				"message:"+st.message+" "+
				"clientid:"+st.clientid+" "+
				"\n";
		}
		else if (toPrintable==null)
			return "toPrintable is null\n";
		return "toPrintable has wrong type\n";
	}
}

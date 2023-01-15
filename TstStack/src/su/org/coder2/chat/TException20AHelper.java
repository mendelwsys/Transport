package su.org.coder2.chat;
import su.org.coder.utils.*;

import java.io.*;

public class TException20AHelper implements ISerializeHelper
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
		TException2 st = new TException2();
		st.Value2=(String)_string0Ah.createChannelObj(dis);
		st.IsActual2 = SerialUtils.unserialint(dis,4);
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
		TException2 st = (TException2) toArray;
		_string0Ah.serialChannelObj( st.Value2,dos);
		dos.write(SerialUtils.serialint32(st.IsActual2));
	}

	public String toPrintableString(Object toPrintable)
	{
		if (toPrintable instanceof TException2)
		{
			TException2 st = (TException2) toPrintable;
			return
				"Value2:"+st.Value2+" "+
				"IsActual2:"+st.IsActual2+" "+
				"\n";
		}
		else if (toPrintable==null)
			return "toPrintable is null\n";
		return "toPrintable has wrong type\n";
	}
}

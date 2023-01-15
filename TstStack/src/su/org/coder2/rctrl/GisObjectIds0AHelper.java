package su.org.coder2.rctrl;
import su.org.coder.utils.*;

import java.io.*;

public class GisObjectIds0AHelper implements ISerializeHelper
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

	ISerializeHelper _string1Ah = new String1AHelper();
	ISerializeHelper _string0Ah = new String0AHelper();

	public Object createChannelObj(DataInputStream dis) throws IOException
	{
		if (dis.readByte()==1)
			return null;
		GisObjectIds st = new GisObjectIds();
		st.id_obj=(String[])_string1Ah.createChannelObj(dis);
		st.id_lr=(String)_string0Ah.createChannelObj(dis);
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
		GisObjectIds st = (GisObjectIds) toArray;
		_string1Ah.serialChannelObj( st.id_obj,dos);
		_string0Ah.serialChannelObj( st.id_lr,dos);
	}

	public String toPrintableString(Object toPrintable)
	{
		if (toPrintable instanceof GisObjectIds)
		{
			GisObjectIds st = (GisObjectIds) toPrintable;
			return
				_string1Ah.toPrintableString( st.id_obj)+
				"id_lr:"+st.id_lr+" "+
				"\n";
		}
		else if (toPrintable==null)
			return "toPrintable is null\n";
		return "toPrintable has wrong type\n";
	}
}

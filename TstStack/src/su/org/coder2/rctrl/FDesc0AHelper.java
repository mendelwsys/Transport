package su.org.coder2.rctrl;
import su.org.coder.utils.*;

import java.io.*;

public class FDesc0AHelper implements ISerializeHelper
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
		FDesc st = new FDesc();
		st.lenght = SerialUtils.unserialint(dis,4);
		st.fname=(String)_string0Ah.createChannelObj(dis);
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
		FDesc st = (FDesc) toArray;
		dos.write(SerialUtils.serialint32(st.lenght));
		_string0Ah.serialChannelObj( st.fname,dos);
	}

	public String toPrintableString(Object toPrintable)
	{
		if (toPrintable instanceof FDesc)
		{
			FDesc st = (FDesc) toPrintable;
			return
				"lenght:"+st.lenght+" "+
				"fname:"+st.fname+" "+
				"\n";
		}
		else if (toPrintable==null)
			return "toPrintable is null\n";
		return "toPrintable has wrong type\n";
	}
}

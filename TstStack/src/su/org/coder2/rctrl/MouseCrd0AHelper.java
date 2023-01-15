package su.org.coder2.rctrl;
import su.org.coder.utils.*;

import java.io.*;

public class MouseCrd0AHelper implements ISerializeHelper
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


	public Object createChannelObj(DataInputStream dis) throws IOException
	{
		if (dis.readByte()==1)
			return null;
		MouseCrd st = new MouseCrd();
		st.yCrd = SerialUtils.unserialint(dis,4);
		st.xCrd = SerialUtils.unserialint(dis,4);
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
		MouseCrd st = (MouseCrd) toArray;
		dos.write(SerialUtils.serialint32(st.yCrd));
		dos.write(SerialUtils.serialint32(st.xCrd));
	}

	public String toPrintableString(Object toPrintable)
	{
		if (toPrintable instanceof MouseCrd)
		{
			MouseCrd st = (MouseCrd) toPrintable;
			return
				"yCrd:"+st.yCrd+" "+
				"xCrd:"+st.xCrd+" "+
				"\n";
		}
		else if (toPrintable==null)
			return "toPrintable is null\n";
		return "toPrintable has wrong type\n";
	}
}

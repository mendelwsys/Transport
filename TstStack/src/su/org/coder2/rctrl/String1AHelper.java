package su.org.coder2.rctrl;
import su.org.coder.utils.*;

import java.io.*;

public class String1AHelper implements ISerializeHelper
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

	ISerializeHelper _string0Ah = new String0AHelper ();


	public Object createChannelObj(DataInputStream dis) throws IOException
	{
		if (dis.readByte()==1)
			return null;
		byte[] buff=new byte[4];
		dis.readFully(buff);
		int length=SerialUtils.unserialint32(buff);
		String[] retArray = new String[length];
		for (int i = 0; i < length; i++)
			retArray[i]=(String)_string0Ah.createChannelObj(dis);
		return retArray; 
	}

	public void serialChannelObj(Object toArray, DataOutputStream dos) throws IOException
	{
		if (toArray==null)
		{
			dos.writeByte(1);
			return;
		}
		dos.writeByte(0);
		String[] st=(String[]) toArray;
		dos.write(SerialUtils.serialint32(st.length));
		for (int i=0;i<st.length;i++)
			_string0Ah.serialChannelObj(st[i], dos);
	}

	public String toPrintableString(Object toPrintable)
	{
		if (toPrintable instanceof String[])
		{
			String retVal = "";
			String[] st=(String[]) toPrintable;
			for (int i=0;i<st.length;i++)
				retVal+=st[i];
			return retVal;
		}
		else if (toPrintable==null)
			return "toPrintable is null\n";
		return "toPrintable has wrong type\n";
	}
}

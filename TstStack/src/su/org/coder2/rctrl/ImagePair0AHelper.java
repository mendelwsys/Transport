package su.org.coder2.rctrl;
import su.org.coder.utils.*;

import java.io.*;

public class ImagePair0AHelper implements ISerializeHelper
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
	ISerializeHelper _byte1Ah = new byte1AHelper();

	public Object createChannelObj(DataInputStream dis) throws IOException
	{
		if (dis.readByte()==1)
			return null;
		ImagePair st = new ImagePair();
		st.coords=(String)_string0Ah.createChannelObj(dis);
		st.pict=(byte[])_byte1Ah.createChannelObj(dis);
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
		ImagePair st = (ImagePair) toArray;
		_string0Ah.serialChannelObj( st.coords,dos);
		_byte1Ah.serialChannelObj( st.pict,dos);
	}

	public String toPrintableString(Object toPrintable)
	{
		if (toPrintable instanceof ImagePair)
		{
			ImagePair st = (ImagePair) toPrintable;
			return
				"coords:"+st.coords+" "+
				_byte1Ah.toPrintableString( st.pict)+
				"\n";
		}
		else if (toPrintable==null)
			return "toPrintable is null\n";
		return "toPrintable has wrong type\n";
	}
}

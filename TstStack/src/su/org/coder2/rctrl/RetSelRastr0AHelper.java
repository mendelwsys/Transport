package su.org.coder2.rctrl;
import su.org.coder.utils.*;

import java.io.*;

public class RetSelRastr0AHelper implements ISerializeHelper
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

	ISerializeHelper _byte1Ah = new byte1AHelper();

	public Object createChannelObj(DataInputStream dis) throws IOException
	{
		if (dis.readByte()==1)
			return null;
		RetSelRastr st = new RetSelRastr();
		st.xP4 = SerialUtils.unserialint(dis,4);
		st.yCrd = SerialUtils.unserialint(dis,4);
		st.xCrd = SerialUtils.unserialint(dis,4);
		st.pict=(byte[])_byte1Ah.createChannelObj(dis);
		st.yP4 = SerialUtils.unserialint(dis,4);
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
		RetSelRastr st = (RetSelRastr) toArray;
		dos.write(SerialUtils.serialint32(st.xP4));
		dos.write(SerialUtils.serialint32(st.yCrd));
		dos.write(SerialUtils.serialint32(st.xCrd));
		_byte1Ah.serialChannelObj( st.pict,dos);
		dos.write(SerialUtils.serialint32(st.yP4));
	}

	public String toPrintableString(Object toPrintable)
	{
		if (toPrintable instanceof RetSelRastr)
		{
			RetSelRastr st = (RetSelRastr) toPrintable;
			return
				"xP4:"+st.xP4+" "+
				"yCrd:"+st.yCrd+" "+
				"xCrd:"+st.xCrd+" "+
				_byte1Ah.toPrintableString( st.pict)+
				"yP4:"+st.yP4+" "+
				"\n";
		}
		else if (toPrintable==null)
			return "toPrintable is null\n";
		return "toPrintable has wrong type\n";
	}
}

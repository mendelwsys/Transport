package su.org.coder2.rctrl;
import su.org.coder.utils.*;

import java.io.*;

public class ChangePos0AHelper implements ISerializeHelper
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

	ISerializeHelper _gisObjectIds1Ah = new GisObjectIds1AHelper();

	public Object createChannelObj(DataInputStream dis) throws IOException
	{
		if (dis.readByte()==1)
			return null;
		ChangePos st = new ChangePos();
		st.dxCrd = SerialUtils.unserialint(dis,4);
		st.objids=(GisObjectIds[])_gisObjectIds1Ah.createChannelObj(dis);
		st.dyCrd = SerialUtils.unserialint(dis,4);
		st.dscale = SerialUtils.unserialdouble(dis);
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
		ChangePos st = (ChangePos) toArray;
		dos.write(SerialUtils.serialint32(st.dxCrd));
		_gisObjectIds1Ah.serialChannelObj( st.objids,dos);
		dos.write(SerialUtils.serialint32(st.dyCrd));
		dos.write(SerialUtils.serializedbl64(st.dscale));
	}

	public String toPrintableString(Object toPrintable)
	{
		if (toPrintable instanceof ChangePos)
		{
			ChangePos st = (ChangePos) toPrintable;
			return
				"dxCrd:"+st.dxCrd+" "+
				_gisObjectIds1Ah.toPrintableString( st.objids)+
				"dyCrd:"+st.dyCrd+" "+
				"dscale:"+st.dscale+" "+
				"\n";
		}
		else if (toPrintable==null)
			return "toPrintable is null\n";
		return "toPrintable has wrong type\n";
	}
}

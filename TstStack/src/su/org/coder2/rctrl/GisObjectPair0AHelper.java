package su.org.coder2.rctrl;
import su.org.coder.utils.*;

import java.io.*;

public class GisObjectPair0AHelper implements ISerializeHelper
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
	ISerializeHelper _string0Ah = new String0AHelper();

	public Object createChannelObj(DataInputStream dis) throws IOException
	{
		if (dis.readByte()==1)
			return null;
		GisObjectPair st = new GisObjectPair();
		st.objids=(GisObjectIds[])_gisObjectIds1Ah.createChannelObj(dis);
		st.nameobj=(String)_string0Ah.createChannelObj(dis);
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
		GisObjectPair st = (GisObjectPair) toArray;
		_gisObjectIds1Ah.serialChannelObj( st.objids,dos);
		_string0Ah.serialChannelObj( st.nameobj,dos);
	}

	public String toPrintableString(Object toPrintable)
	{
		if (toPrintable instanceof GisObjectPair)
		{
			GisObjectPair st = (GisObjectPair) toPrintable;
			return
				_gisObjectIds1Ah.toPrintableString( st.objids)+
				"nameobj:"+st.nameobj+" "+
				"\n";
		}
		else if (toPrintable==null)
			return "toPrintable is null\n";
		return "toPrintable has wrong type\n";
	}
}

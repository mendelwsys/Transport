package su.org.coder2.gpstransl;
import su.org.coder.utils.*;

import java.io.*;

public class dataStruct0AHelper implements ISerializeHelper
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
		dataStruct st = new dataStruct();
		st.absD = SerialUtils.unserialdouble(dis);
		st.deltaD = SerialUtils.unserialdouble(dis);
		st.isWaitPoint = dis.readBoolean();
		st.time = SerialUtils.unserialdouble(dis);
		st.velocity = SerialUtils.unserialdouble(dis);
		st.absS = SerialUtils.unserialdouble(dis);
		st.deltaS = SerialUtils.unserialdouble(dis);
		st.dotnumber = SerialUtils.unserialint(dis,4);
		st.delta = SerialUtils.unserialdouble(dis);
		st.clientid = SerialUtils.unserialint(dis,4);
		st.lasttag = (short)SerialUtils.unserialint(dis,2);
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
		dataStruct st = (dataStruct) toArray;
		dos.write(SerialUtils.serializedbl64(st.absD));
		dos.write(SerialUtils.serializedbl64(st.deltaD));
		dos.writeBoolean(st.isWaitPoint);
		dos.write(SerialUtils.serializedbl64(st.time));
		dos.write(SerialUtils.serializedbl64(st.velocity));
		dos.write(SerialUtils.serializedbl64(st.absS));
		dos.write(SerialUtils.serializedbl64(st.deltaS));
		dos.write(SerialUtils.serialint32(st.dotnumber));
		dos.write(SerialUtils.serializedbl64(st.delta));
		dos.write(SerialUtils.serialint32(st.clientid));
		dos.write(SerialUtils.serialint16(st.lasttag));
	}

	public String toPrintableString(Object toPrintable)
	{
		if (toPrintable instanceof dataStruct)
		{
			dataStruct st = (dataStruct) toPrintable;
			return
				"absD:"+st.absD+" "+
				"deltaD:"+st.deltaD+" "+
				"isWaitPoint:"+st.isWaitPoint+" "+
				"time:"+st.time+" "+
				"velocity:"+st.velocity+" "+
				"absS:"+st.absS+" "+
				"deltaS:"+st.deltaS+" "+
				"dotnumber:"+st.dotnumber+" "+
				"delta:"+st.delta+" "+
				"clientid:"+st.clientid+" "+
				"lasttag:"+st.lasttag+" "+
				"\n";
		}
		else if (toPrintable==null)
			return "toPrintable is null\n";
		return "toPrintable has wrong type\n";
	}
}

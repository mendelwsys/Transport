package su.org.coder.utils;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 01.02.2005
 * Time: 13:47:24
 * Базовый класс для преобразования
 */
public class Short0AHelper implements ISerializeHelper
{
	ByteArrayOutputStream bos = null;
	DataOutputStream dos = null;

	public Object createChannelObj(byte[] fromArray) throws IOException
	{
		if (fromArray == null)
			return null;
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(fromArray, 0, fromArray.length));
		return createChannelObj(dis);
	}

	public Object createChannelObj(DataInputStream dis) throws IOException
	{
		if (dis.readByte() == 1)
			return null;

		byte[] buff=new byte[2];
		dis.readFully(buff);
		return  new Short(SerialUtils.unserialint16(buff));
		//new Short(dis.readShort());
	}

	public byte[] serialChannelObj(Object toArray) throws IOException
	{
		if (bos == null) bos = new ByteArrayOutputStream();
		if (dos == null) dos = new DataOutputStream(bos);

		bos.reset();
		serialChannelObj(toArray, dos);
		return bos.toByteArray();
	}

	public void serialChannelObj(Object toArray, DataOutputStream dos) throws IOException
	{
		if (toArray == null)
		{
			dos.writeByte(1);
			return;
		}
		dos.writeByte(0);
		dos.write(SerialUtils.serialint16(((Short) toArray).shortValue()));
//		dos.writeShort(((Short) toArray).shortValue());
	}

	public String toPrintableString(Object toPrintable)
	{
		if (toPrintable instanceof Short)
			return ((Short) toPrintable).toString() + "\n";
		else if (toPrintable == null)
			return "toPrintable is null\n";
		return "toPrintable has wrong type\n";
	}
}

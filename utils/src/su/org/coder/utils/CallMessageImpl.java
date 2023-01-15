package su.org.coder.utils;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.06.2004
 * Time: 12:42:23
 * Сообщение которое передается между связанными объектами
 */

//Делаем сообщение мультиплексируемым
public class CallMessageImpl implements ILgMessage
{
	public byte command; //Комманда
	public short typeID; //Тип объекта (всего может быть 65536 типов)
	public short objID;//Идентификатор объекта (65536 объктов)
	public byte methodCode;//Код Метода (256 методов)
	public byte[] bmessage; //Параметры вызова

	public CallMessageImpl(CallMessageImpl msg)
	{
		command = msg.command;
		typeID = msg.typeID;
		objID = msg.objID;
		methodCode = msg.methodCode;

		if (msg.bmessage != null)
			System.arraycopy(msg.bmessage, 0, bmessage, 0, msg.bmessage.length);
	}

	public CallMessageImpl()
	{
		command = 0;
		typeID = 0;
		objID = 0;
		methodCode = 0;
		bmessage = null;
	}

	public CallMessageImpl(byte command, short TypeID, short ObjID, byte methodCode, byte[] bmessage)
	{
		this.command = command;
		this.typeID = TypeID;
		this.objID = ObjID;
		this.methodCode = methodCode;
		this.bmessage = bmessage;
	}

	public void sendToReceiver(OutputStream stream) throws IOException
	{
		byte[] forsend = toByteArray();

		try
		{
			stream.write(forsend);
		}
		catch (java.lang.OutOfMemoryError e1)
		{
			System.out.println("Out of Memory Error when sendToReceiver");
		}
	}

	public byte[] toByteArray()
	{
		int szb = 0;
		if (bmessage != null) szb = bmessage.length;

		byte[] forsend = new byte[szb + 1 + 2 + 2 + 1 + 4]; //комманда,TypeObjID,methodCode,sz
		forsend[0] = command;

		for (int i = 0; i < 2; i++) forsend[i + 1] = (byte) ((typeID >> (8 * i)) & 0xFF);

		for (int i = 0; i < 2; i++) forsend[i + 3] = (byte) ((objID >> (8 * i)) & 0xFF);

		forsend[5] = methodCode;

		for (int i = 0; i < 4; i++) forsend[i + 6] = (byte) ((szb >> (8 * i)) & 0xFF);

		for (int i = 0; i < szb; i++) forsend[i + 10] = bmessage[i];
		return forsend;
	}

	public int setByByteArry (byte[] tmp,int offset) throws IOException
	{
		int szb = setHeader(tmp, offset);

		if (tmp.length<offset+szb+10)
			throw new IOException(ERR_BODY);

		if (szb>0)
		{
			bmessage=new byte[szb];
			System.arraycopy(tmp,offset+10,bmessage,0,szb);
		}
		else
		 bmessage=null;

		return offset+szb+10;
	}

	private int setHeader(byte[] tmp, int offset)
			throws IOException
	{

		if (tmp==null || tmp.length<offset+10)
			throw new IOException(ERR_HEAD);

		command = tmp[offset];
		typeID = 0;
		for (int i = 0; i < 2; i++)
		{
			typeID = (short) (typeID & (~(0x00FF << (i * 8))));//Очистка старших битов
			typeID = (short) (typeID | (tmp[offset+i + 1] << (i * 8)));//Пополнение
		}
		objID = 0;
		for (int i = 0; i < 2; i++)
		{
			objID = (short) (objID & (~(0x00FF << (i * 8))));//Очистка старших битов
			objID = (short) (objID | (tmp[offset+i + 3] << (i * 8)));//Пополнение
		}

		methodCode = tmp[offset+5];

		int szb = 0;
		for (int i = 0; i < 4; i++)
		{
			szb = szb & (~(0x00FF << (i * 8)));//Очистка старших битов
			szb = szb | (tmp[offset+i + 6] << (i * 8));//Пополнение
		}
		return szb;
	}

	public int setBySender(InputStream stream) throws IOException, EOFException
	{

		byte[] tmp = new byte[10];
		int w_read = 0;
		try
		{
			while (10 > w_read)
			{
				int wasread = stream.read(tmp, w_read, 10 - w_read);
				if (wasread < 0)
					throw new EOFException();
				w_read += wasread;
			}

			int szb = setHeader(tmp,0);

//          System.out.println("In read body");
			if (szb > 0)
			{
				bmessage = new byte[szb];
				while (szb > w_read - 10)
				{
					int wasread = stream.read(bmessage, w_read - 10, szb - w_read + 10);
					if (wasread < 0)
						throw new EOFException();
					w_read += wasread;
				}
			}
			else
				bmessage = null;
//           System.out.println("In read body complite :"+ String.valueOf(szb));
		}
		catch (OutOfMemoryError e1)
		{
			System.out.println("Out of Memory Error when setBySender Msz:" + tmp[1]);
			return -1;
		}
//        System.out.println("Out setBySender");
		return w_read;
	}

/*
	private int SetHeader2(byte[] tmp)
	{
		command = tmp[0];
		typeID = 0;
		for (int i = 0; i < 2; i++)
		{
			typeID = (short) (typeID & (~(0x00FF << (i * 8))));//Очистка старших битов
			typeID = (short) (typeID | (tmp[i + 1] << (i * 8)));//Пополнение
		}
		objID = 0;
		for (int i = 0; i < 2; i++)
		{
			objID = (short) (objID & (~(0x00FF << (i * 8))));//Очистка старших битов
			objID = (short) (objID | (tmp[i + 3] << (i * 8)));//Пополнение
		}

		methodCode = tmp[5];

		int szb = 0;
		for (int i = 0; i < 4; i++)
		{
			szb = szb & (~(0x00FF << (i * 8)));//Очистка старших битов
			szb = szb | (tmp[i + 6] << (i * 8));//Пополнение
		}
		return szb;
	}
*/

	public String getAsString()
	{
		return String.valueOf(command) + " " + String.valueOf(typeID) + " " + String.valueOf(objID) + " " + String.valueOf(methodCode);
	}

	public void pPrint()
	{
		System.out.println(String.valueOf(command));
		System.out.println(String.valueOf(typeID));
		System.out.println(String.valueOf(objID));
		System.out.println(String.valueOf(methodCode));
		for (int i = 0; bmessage != null && i < bmessage.length; i++)
			System.out.print(String.valueOf(bmessage[i]) + ",");
		System.out.println();
	}
	public boolean isFinalMessage()
	{
		return command == 0 && typeID == 0 &&
				objID==0 && methodCode==0 &&
				bmessage == null;
	}

//	public static void main(String args[]) throws IOException
//	{
//		ILgMessage msg = new CallMessageImpl((byte) 10, (short) 137, (short) 258, (byte) 11, new byte[]{1, 2, 3});
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		msg.sendToReceiver(bos);
//
//		ILgMessage msg2 = new CallMessageImpl();
//		msg2.setBySender(new ByteArrayInputStream(bos.toByteArray()));
//		msg2.pPrint();
//	}
}
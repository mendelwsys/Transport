package su.org.coder.multiplexer.protocols;

import su.org.coder.utils.ILgMessage;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.06.2004
 * Time: 12:42:23
 * Сообщение которое передается между связанными объектами
 */
public class TunelMessage implements ILgMessage
{
	public short senderID; //Идентификатор канала отсылки (всего может быть 65536 типов)
	public short receiverID;//Идентификатор канала назначения (65536 объктов)
	public byte[] bmessage; //Параметры вызова

	public TunelMessage(TunelMessage msg)
	{
		senderID = msg.senderID;
		receiverID = msg.receiverID;
		if (msg.bmessage != null)
			System.arraycopy(msg.bmessage, 0, bmessage, 0, msg.bmessage.length);
	}

	public TunelMessage()
	{
		senderID = 0;
		receiverID = 0;
		bmessage = null;
	}

	public TunelMessage(short senderID, short receiverID, byte[] bmessage)
	{
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.bmessage = bmessage;
	}

	public static int TotalSend;
	public static int TotalRsvd;

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
		int tl = 8;
		if (bmessage != null)
			tl += bmessage.length;
		TotalSend += tl;
	}

	public byte[] toByteArray()
	{
		int szb = 0;
		if (bmessage != null) szb = bmessage.length;

		byte[] forsend = new byte[szb + 2 + 2 + 4]; //комманда,TypeObjID,methodCode,sz

		for (int i = 0; i < 2; i++) forsend[i] = (byte) ((senderID >> (8 * i)) & 0xFF);

		for (int i = 0; i < 2; i++) forsend[i + 2] = (byte) ((receiverID >> (8 * i)) & 0xFF);

		for (int i = 0; i < 4; i++) forsend[i + 4] = (byte) ((szb >> (8 * i)) & 0xFF);

		for (int i = 0; i < szb; i++) forsend[i + 8] = bmessage[i];
		return forsend;
	}

	public int setByByteArry (byte[] tmp,int offset) throws IOException
	{
		if (tmp==null || tmp.length<offset+8)
			throw new IOException(ERR_HEAD);

		int szb=setHeader(tmp,offset);

		if (szb+8+offset>tmp.length)
			throw new IOException(ERR_BODY);

		if (szb>0)
		{
			bmessage=new byte[szb];
			System.arraycopy(tmp,8+offset,bmessage,0,szb);
		}
		return szb+8+offset;
	}

	public int setBySender(InputStream stream) throws IOException, EOFException
	{
//        System.out.println("In setBySender");
		byte[] tmp = new byte[8];
		int w_read = 0;
		try
		{
//            System.out.println("In read head");
			while (8 > w_read)
			{
				int wasread=stream.read(tmp, w_read, 8 - w_read);
				if (wasread<0)
					throw new EOFException("Can't read head of message: stream is closed?");
				w_read +=wasread;
			}
//            System.out.println("read head complite");

			int szb = setHeader(tmp,0);

//          System.out.println("In read body");
			if (szb > 0)
			{
				bmessage = new byte[szb];
				while (szb > w_read - 8)
				{
					int wasread=stream.read(bmessage, w_read - 8, szb - w_read + 8);
					if (wasread<0)
						throw new EOFException();
					w_read +=wasread;
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
		TotalRsvd += w_read;

		return w_read;
	}

	private int setHeader(byte[] tmp,int offset)
	{
		senderID = 0;
		for (int i = 0; i < 2; i++)
		{
			senderID = (short) (senderID & (~(0x00FF << (i * 8))));//Очистка старших битов
			senderID = (short) (senderID | (tmp[i+offset] << (i * 8)));//Пополнение
		}

		receiverID = 0;
		for (int i = 0; i < 2; i++)
		{
			receiverID = (short) (receiverID & (~(0x00FF << (i * 8))));//Очистка старших битов
			receiverID = (short) (receiverID | (tmp[i + offset+2] << (i * 8)));//Пополнение
		}

		int szb = 0;
		for (int i = 0; i < 4; i++)
		{
			szb = szb & (~(0x00FF << (i * 8)));//Очистка старших битов
			szb = szb | (tmp[i +offset+4] << (i * 8));//Пополнение
		}
		return szb;
	}

	public boolean isFinalMessage()
	{
		return senderID == 0 && receiverID == 0 && bmessage == null;
	}

	public String getAsString()
	{
		return String.valueOf(senderID) + " " + String.valueOf(receiverID);
	}

	public void pPrint()
	{
		System.out.println(String.valueOf(senderID));
		System.out.println(String.valueOf(receiverID));
		for (int i = 0; bmessage != null && i < bmessage.length; i++)
			System.out.print(String.valueOf(bmessage[i]) + ",");
		System.out.println();
	}


	private void sendToReceiverTest(OutputStream stream) throws IOException
	{
		int szb = 0;
		if (bmessage != null) szb = bmessage.length;

		byte[] forsend = new byte[szb + 2 + 2 + 4]; //комманда,TypeObjID,methodCode,sz

		for (int i = 0; i < 2; i++) forsend[i] = (byte) ((senderID >> (8 * i)) & 0xFF);

		for (int i = 0; i < 2; i++) forsend[i + 2] = (byte) ((receiverID >> (8 * i)) & 0xFF);

		for (int i = 0; i < 4; i++) forsend[i + 4] = (byte) ((szb >> (8 * i)) & 0xFF);

		for (int i = 0; i < szb; i++) forsend[i + 8] = bmessage[i];

		try
		{

			int i = 5;
			System.out.println("Dev0: 0 Ln0: " + i);
			stream.write(forsend, 0, i);
			Thread.sleep(1000);
			if ((forsend.length - (forsend.length / 2) - i) >= 0)
			{
				System.out.println("Dev1: " + i + " Ln2: " + (forsend.length - (forsend.length / 2) - i));
				stream.write(forsend, i, (forsend.length - (forsend.length / 2) - i));
				Thread.sleep(1000);
				System.out.println("Dev2: " + (forsend.length - (forsend.length / 2)) + " Ln2: " + forsend.length / 2);
				stream.write(forsend, forsend.length - (forsend.length / 2), forsend.length / 2);
			}
			else
			{
				System.out.println("Dev2: " + i + " Ln2: " + (forsend.length - i));
				stream.write(forsend, i, forsend.length - i);
			}
		}
		catch (java.lang.OutOfMemoryError e1)
		{
			System.out.println("Out of Memory Error when sendToReceiver");
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}


	}
}
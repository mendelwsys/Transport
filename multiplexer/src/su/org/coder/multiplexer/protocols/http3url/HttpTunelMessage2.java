package su.org.coder.multiplexer.protocols.http3url;

import su.org.coder.utils.ILgMessage;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.06.2004
 * Time: 12:42:23
 * Сообщение которое передается между связанными объектами
 */
public class HttpTunelMessage2  implements ILgMessage
{
	public int httpchanneID;//Идентификатор канала Http
	public int messageID; //Идентификатор сообщения
	public byte[] bmessage; //Параметры вызова

	public HttpTunelMessage2(HttpTunelMessage2 msg)
	{
		httpchanneID= msg.httpchanneID;
		messageID=msg.messageID;
		if (msg.bmessage != null)
			System.arraycopy(msg.bmessage, 0, bmessage, 0, msg.bmessage.length);
	}

	public HttpTunelMessage2()
	{
		httpchanneID=0;
		messageID=0;
		bmessage = null;
	}

	public HttpTunelMessage2(int httpchanneID,int messageID, byte[] bmessage)
	{
		this.httpchanneID= httpchanneID;
		this.messageID = messageID;
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
		catch (OutOfMemoryError e1)
		{
			System.out.println("Out of Memory Error when sendToReceiver");
		}
		int tl = 12;
		if (bmessage != null)
			tl += bmessage.length;
		TotalSend += tl;
	}

	public byte[] toByteArray()
	{
		int szb = 0;
		if (bmessage != null) szb = bmessage.length;

		byte[] forsend = new byte[szb + 4 + 4 + 4]; //комманда,sz

		for (int i = 0; i < 4; i++) forsend[i] = (byte) ((httpchanneID >> (8 * i)) & 0xFF);

		for (int i = 0; i < 4; i++) forsend[i + 4] = (byte) ((messageID >> (8 * i)) & 0xFF);

		for (int i = 0; i < 4; i++) forsend[i + 8] = (byte) ((szb >> (8 * i)) & 0xFF);

		for (int i = 0; i < szb; i++) forsend[i + 12] = bmessage[i];
		return forsend;
	}

	public int setByByteArry(byte[] tmp, int offset) throws IOException
	{
		throw new IOException (ERR_NOTSUPPORT);
	}

	public int setBySender(InputStream stream) throws IOException, EOFException
	{
		byte[] tmp = new byte[12];
		int w_read = 0;
		try
		{
			while (12 > w_read)
			{
				int wasread=stream.read(tmp, w_read, 12 - w_read);
				if (wasread<0)
					throw new EOFException("EOF in header message");
				w_read +=wasread;
			}

			httpchanneID = 0;
			for (int i = 0; i < 4; i++)
			{
				httpchanneID = (httpchanneID & (~(0x00FF << (i * 8))));//Очистка старших битов
				httpchanneID = (httpchanneID | (tmp[i] << (i * 8)));//Пополнение
			}


			messageID = 0;
			for (int i = 0; i < 4; i++)
			{
				messageID = messageID & (~(0x00FF << (i * 8)));//Очистка старших битов
				messageID = messageID | (tmp[i + 4] << (i * 8));//Пополнение
			}

			int szb = 0;
			for (int i = 0; i < 4; i++)
			{
				szb = szb & (~(0x00FF << (i * 8)));//Очистка старших битов
				szb = szb | (tmp[i + 8] << (i * 8));//Пополнение
			}

			if (szb > 0)
			{
				bmessage = new byte[szb];
				while (szb > w_read - 12)
				{
					int wasread=stream.read(bmessage, w_read - 12, szb - w_read + 12);
					if (wasread<0)
						throw new EOFException("EOF in body message");
					w_read +=wasread;
				}
			}
			else
				bmessage = null;
		}
		catch (OutOfMemoryError e1)
		{
			System.out.println("Out of Memory Error when setBySender Msz:" + tmp[1]);
			return -1;
		}
		TotalRsvd += w_read;
		return w_read;
	}

	public boolean isFinalMessage()
	{
		return httpchanneID ==0 && messageID == 0 && bmessage == null;
	}

	public String getAsString()
	{
		return String.valueOf(httpchanneID)+" "+String.valueOf(messageID);
	}

	public void pPrint()
	{
		System.out.println("httpchanneID: "+String.valueOf(httpchanneID));
		System.out.println("messageID: "+String.valueOf(messageID));
		for (int i = 0; bmessage != null && i < bmessage.length; i++)
			System.out.print(String.valueOf(bmessage[i]) + ",");
		System.out.println();
	}

}
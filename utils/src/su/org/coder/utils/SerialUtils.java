package su.org.coder.utils;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: VLADM
 * Date: 28.10.2005
 * Time: 10:51:31
 * To change this template use File | Settings | File Templates.
 */
public class SerialUtils
{
	private SerialUtils()
	{

	}


	public static byte[] serializedbl64(double dbl)
			throws IOException
	{
		long bits = Double.doubleToLongBits(dbl);

		return serializedint64(bits);
	}

	public static byte[] serializedint64(long bits)
	{
		int i1= (int) (bits&0x0FFFFFFFF);
		int i2= (int) ((bits>>32)&0x0FFFFFFFF);

		byte[] bt1= SerialUtils.serialint32(i1);
		byte[] bt2=SerialUtils.serialint32(i2);


		byte[] bmessage=new byte[bt1.length+bt2.length];

		System.arraycopy(bt1,0,bmessage,0,bt1.length);
		System.arraycopy(bt2,0,bmessage,bt1.length,bt2.length);

		return bmessage;
	}

	public static  double unserializedbl64( byte[] bits)
			throws IOException
	{
		long lbits = unserializedlint64(bits);
		return Double.longBitsToDouble(lbits);
	}

	public static long unserializedlint64(byte[] bits)
	{
		byte[] bt1=new byte[4];
		byte[] bt2=new byte[4];
		for (int i=0;i<4;i++)
		{
			bt1[i]=bits[i];
			bt2[i]=bits[i+4];
		}

		int i1= SerialUtils.unserialint32(bt1);
		int i2=SerialUtils.unserialint32(bt2);

		long lbits=((((long)i2)<<32))|(((long)i1)&0x0ffffffffL);
		return lbits;
	}

//	public static byte[] serialdbl64(double val)
//	{
//		byte[] retVal=new byte[8];
//		for (int i = 0; i < 4; i++)
//			retVal[i] = (byte) ((val >> (8 * i)) & 0xFF);
//		return retVal;
//	}
//
//	public static double unserialdbl64(byte[] array)
//	{
//		double retVal = 0;
//		for (int i = 0; i < 4; i++)
//		{
//			retVal = retVal & (~(0x00FF << (i * 8)));//Очистка старших битов
//			retVal = retVal | (array[i] << (i * 8));//Пополнение
//		}
//		return retVal;
//	}


	public static byte[] serialint32(int val)
	{
		byte[] retVal=new byte[4];
		for (int i = 0; i < 4; i++)
			retVal[i] = (byte) ((val >> (8 * i)) & 0xFF);
		return retVal;
	}

	public static double unserialdouble(DataInputStream dis) throws IOException
	{
		byte[] buff=new byte[8];
		dis.readFully(buff);
		return  unserializedbl64(buff);
	}

	public static long unserialong(DataInputStream dis) throws IOException
	{
		byte[] buff=new byte[8];
		dis.readFully(buff);
		return unserializedlint64(buff);
	}

	public static int unserialint(DataInputStream dis,int szd) throws IOException
	{
		if (szd!=4 && szd!=2)
			throw new IOException("Error in unserialint arguments szd must be 2 or 4");

		byte[] buff=new byte[szd];

		dis.readFully(buff);

		if (szd==4)
			return unserialint32(buff);
		else
			return unserialint16(buff);
	}

	public static int unserialint32(byte[] array)
	{
		int retVal = 0;
		for (int i = 0; i < 4; i++)
		{
			retVal = retVal & (~(0x00FF << (i * 8)));//Очистка старших битов
			retVal = retVal | (array[i] << (i * 8));//Пополнение
		}
		return retVal;
	}

	public static byte[] serialint16(short val)
	{
		byte[] retVal=new byte[2];
		for (int i = 0; i < 2; i++) retVal[i] = (byte) ((val >> (8 * i)) & 0xFF);
		return retVal;
	}

	public static short unserialint16(byte[] array)
	{
		short retVal = 0;
		for (int i = 0; i < 2; i++)
		{
			retVal = (short) (retVal & (~(0x00FF << (i * 8))));//Очистка старших битов
			retVal = (short) (retVal | (array[i] << (i * 8)));//Пополнение
		}
		return retVal;
	}

	public static void main(String[] args)
	{
		short val=0x3223;
		for (int i = 0; i < 2; i++)
			System.out.println(i+" : "+((byte) ((val >> (8 * i)) & 0xFF)));

//		int vali=0x56652332;
//		for (int i = 0; i < 4; i++)
//			System.out.println(i+" : "+((byte) ((vali >> (8 * i)) & 0xFF)));

//		System.out.println("serial: "+(short) 0x2332);
//		byte[] reval16 =serialint16((short) 0x2332);
//		System.out.println("unserial: "+unserialint16(reval16));
//
//
//		System.out.println("serial: "+ 0x56652332);
//		byte[] reval32=serialint32(0x56652332);
//		System.out.println("unserial: "+unserialint32(reval32));
	}
}

package su.org.coder.utils;

/**
 * Created by IntelliJ IDEA.
 * User: VLADM
 * Date: 04.10.2005
 * Time: 16:09:28
 * To change this template use File | Settings | File Templates.
 */
public class Bukovki
{
	static public final String str = "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁйцукенгшщзхъфывапролджэячсмитьбюё";

	static public byte[] inCode(String i_str)
	{
		byte[] retVal = new byte[i_str.length()];
		for (int i = 0; i < i_str.length(); i++)
		{
			char currChar = i_str.charAt(i);
			int pos = str.indexOf(currChar);
			if (pos >= 0)
				retVal[i] = (byte) (pos|0x0080);
			else
				retVal[i] = (byte) currChar;
		}
		return retVal;
	}

	static public String deCode(byte[] bt)
	{
		StringBuffer retVal = new StringBuffer(new String(bt));
		for (int i = 0; i < bt.length; i++)
		{
			byte b = bt[i];
			if ((b&0x80) !=0)
				retVal.setCharAt(i,str.charAt((b&0x007F)));
			else
                retVal.setCharAt(i,(char) b);
		}
        return new String (retVal);
	}
}

package su.org.coder.utils;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 29.03.2005
 * Time: 23:06:21
 * To change this template use File | Settings | File Templates.
 */
public class DebCounter
{
	static Hashtable hs= new Hashtable();
	private DebCounter()
	{
	}

	public static synchronized void setCounter(int id,int cnt)
	{
		hs.put(new Integer(id),new Integer(cnt));
	}
	public static synchronized int getCounter(int id)
	{
		Integer retVal=(Integer) hs.get(new Integer(id));
		if (retVal==null)
		{
			retVal=new Integer(0);
			hs.put(new Integer(id),retVal);
		}
		return retVal.intValue();
	}
}

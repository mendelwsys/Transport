/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 20.02.2005
 * Time: 20:58:35
 * To change this template use File | Settings | File Templates.
 */
package su.org.coder.utils;

public class SinglObjectStorage implements IStorage
{
	private static SinglObjectStorage ourInstance = new SinglObjectStorage();

	public static SinglObjectStorage getInstance()
	{
		return ourInstance;
	}

	private SinglObjectStorage()
	{
	}


	private Object serv;

	public void registerifempty(Object serv)
	{
		if (serv==null)
			this.serv=serv;
	}

	public Object register(Object serv)
	{
		Object retVal = this.serv;
		this.serv=serv;
		return retVal;
	}

	public Object getService(String className)
	{
		return serv;
	}

	public Object remove(String className)
	{
		Object retVal=serv;
		serv=null;
		return retVal;
	}
}

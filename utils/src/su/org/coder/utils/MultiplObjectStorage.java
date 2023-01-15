/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 20.02.2005
 * Time: 20:58:35
 * To change this template use File | Settings | File Templates.
 */
package su.org.coder.utils;
import java.util.Hashtable;

public class MultiplObjectStorage implements IStorage
{
	private static IStorage ourInstance = new MultiplObjectStorage();

	public static IStorage getInstance()
	{
		return ourInstance;
	}

	private MultiplObjectStorage()
	{
	}


	private Hashtable servstor= new Hashtable();

	public void registerifempty(Object serv)
	{
		String name = serv.getClass().getName();
		Object o = servstor.get(name);
		if (o==null)
			servstor.put(name,serv);
	}

	public Object register(Object serv)
	{
		String name = serv.getClass().getName();
		Object retVal= servstor.put(name,serv);
		System.out.println("Register: "+name);
		return retVal;
	}

	public Object getService(String className)
	{
		return servstor.get(className);
	}

	public Object remove(String className)
	{
		return servstor.remove(className);
	}
}

package su.org.coder.utils;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 22.03.2005
 * Time: 17:32:32
 * To change this template use File | Settings | File Templates.
 */
public class TypeId
{
	private TypeId(){}
	private static short objectID;
	public synchronized static short getNextId()
	{
		return ++objectID;
	}
}

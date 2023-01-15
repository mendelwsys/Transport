package su.org.coder.utils;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 25.04.2005
 * Time: 14:49:24
 * To change this template use File | Settings | File Templates.
 */
public interface IStorage
{
	void registerifempty(Object serv);

	Object register(Object serv);

	Object getService(String className);

	Object remove(String className);	
}

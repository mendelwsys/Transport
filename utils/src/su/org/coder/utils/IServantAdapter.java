package su.org.coder.utils;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 10.07.2005
 * Time: 16:40:49
 * To change this template use File | Settings | File Templates.
 */
public interface IServantAdapter extends IInterceptor
{
	void registerServant(Object regName, Class cls);
    Class unregisterServant(Object regName);
	Class getServant(Object regName);
}

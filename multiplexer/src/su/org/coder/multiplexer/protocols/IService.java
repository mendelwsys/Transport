package su.org.coder.multiplexer.protocols;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 20.02.2005
 * Time: 20:50:21
 * To change this template use File | Settings | File Templates.
 */
public interface IService
{
	public void initService(Object cfg);
/**
 * Стартовать обслуживание клиента
 * @param syschan - канал через который мы общаемся с клиентом
 */
	public void startService(ILgChannel syschan);
}

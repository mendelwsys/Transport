package su.org.coder.multiplexer.protocols.jspservl.managers;

import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.IService;
import su.org.coder.multiplexer.protocols.http3url.servlets2.ServletChannelManager;
import su.org.coder.utils.MultiplObjectStorage;
import su.org.coder.utils.MultiplObjectStorage;


import javax.servlet.ServletConfig;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.util.Hashtable;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 26.03.2005
 * Time: 16:25:27
 * To change this template use File | Settings | File Templates.
 */
/**
 * Инициализация имплементации класса обслуживания канала (IService)
 */
public abstract class InitService
{
	private static Hashtable ownhash=new Hashtable();
	private String serviceName;

	/**
 * Если сервелет загружается автоматически один раз сервером приложений, то для НАСЛЕДНИКА этого класса
 * при загрузки из JSP мы вынуждены обсеспечивать одноразовую загрузку этого наследника самостоятельно,
 * что и достигается двумя нижеследующими методами.
 *
 * @param cls класс имплементации InitService,
 * @param sc парметр имплементации
 * @return возврат инстанса
 * @throws IllegalAccessException
 * @throws InstantiationException
 */
	public static InitService getInstance(Class cls,ServletConfig sc) throws IllegalAccessException, InstantiationException
	{
		InitService service = (InitService) ownhash.get(cls.getName());
		if (service==null)
		{
			service = (InitService) cls.newInstance();
			service.init(sc);
			ownhash.put(cls.getName(),service);
		}
		return service;
	}


	public static InitService getInstance(String urlstr,String className,ServletConfig sc) throws IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException
	{
		InitService service = (InitService) ownhash.get(className);
		if (service==null)
		{
			URL[] url={new URL(urlstr)};
			URLClassLoader loader=new URLClassLoader(url);
			Class cls=loader.loadClass(className);
			return getInstance(cls,sc);
		}
		return service;
	}

	public static String reSetit()
	{
		ownhash=null;
		System.gc();
		ownhash=new Hashtable();
		return "The InitService was reset";
	}

	private void init(ServletConfig sc)
	{
		serviceName=initChannelServlet(sc);
		IService service = (IService) MultiplObjectStorage.getInstance().getService(serviceName);
		service.initService(sc);
	}

/**
 * Как правило выполняется регистрация инстанса имплементации (IService)
 * т.о. пользователь должен лпределить только имплементацию класса IService
 * и имплементацию текущего класса в котором будет вызываться регистрация
 * инстанса имплементации IService
 * @param sc
 */
	protected abstract String initChannelServlet(ServletConfig sc);


	private class RunIt implements Runnable
	{
		private ILgChannel chan;
		private int channelId;

		public RunIt(int channelId)
		{
			this.channelId=channelId;
			chan=ServletChannelManager.getInstance().getChannelById(channelId);
			if (chan!=null)
				System.out.println("init of RunIt Ok");
			else
				System.out.println("init of RunIt fail");
		}

		public void run()
		{
			System.out.println("Init of business thread for channelId: "+channelId+ " class name is: "+serviceName);
			IService service = (IService) MultiplObjectStorage.getInstance().getService(serviceName);
			service.startService(chan);
			System.out.println("remove channel by channelId: "+channelId);
			ServletChannelManager.getInstance().removeChannelById(channelId);
			chan=null;
		}
	}

	public void startService(int channelId)
	{
		new Thread(new RunIt(channelId)).start();
	}
}

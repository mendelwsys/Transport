package su.org.coder2.test00.client;

import su.org.coder.utils.*;
import su.org.coder.multiplexer.server.ServerListener;
import su.org.coder.multiplexer.protocols.http3url.client2.Http3UrlChannel;
import su.org.coder2.test00.Constants;
import su.org.coder2.chat.*;
import su.org.coder2.test00.servants.ChatImpl00;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.02.2005
 * Time: 2:50:51
 * Client over HTTP CallBack (through servlet implementation)
 *
 */
public class ClientHttpCallBack00
{
	private ServerListener sl;
	private static SkelRouter router= new SkelRouter();

	public static void main(String args[]) throws Exception, TException2, TException1
	{
		new ClientHttpCallBack00().jobStart(args);
	}


	public void jobStart(String args[]) throws Exception, TException2, TException1
	{
		router.registerServant(Constants.REG_NAME01, new ChatImpl00());

		String inUrl = "http://"+Constants.SERVER+":" + Constants.SERVER_PORT_HTTP + "/test00/outsrv";
		String outUrl = "http://"+Constants.SERVER+":" + Constants.SERVER_PORT_HTTP + "/test00/insrv";
		String initUrl = "http://"+Constants.SERVER+":" + Constants.SERVER_PORT_HTTP + "/test00/initsrvcb";

		Http3UrlChannel syschan = new Http3UrlChannel(outUrl, inUrl, initUrl);
//Предоставляем сервис
//		IInterceptor insr = new IInterceptorSeq(new SkelAuthenticateImpl(Constants.REG_INTERCEPT));
//		insr.registerInterseptor(router);
		sl = new ServerListener(router,syschan);
		new Thread()//Предоставим сервис для доступа к нашему объекту Constants.REG_NAME1 через мултеплексер
		{
			public void run()
			{
    			sl.startService(null);
			}
		}.start();
		Thread.sleep(1000);
//Готовы к обслуживанию входящих запросов на зарегистрированнный объект
//		ClientConnector cl = new ClientConnector(sl.getChannelFactory(),0);
//		//Привязаться к Constants.REG_NAME2
//		IChatInterface intrf1 = ProxyChatInterface.bind(cl, Constants.REG_NAME2);
//		intrf1.Put("Call It to Server");
	}
}

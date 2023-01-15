package su.org.coder2.test00.client;

import su.org.coder.utils.*;
import su.org.coder.multiplexer.server.ServerListener;
import su.org.coder.multiplexer.protocols.sockstream.StreamChannel;
import su.org.coder.multiplexer.protocols.TunelMessage;
import su.org.coder2.test00.Constants;
import su.org.coder2.chat.*;
import su.org.coder2.test00.servants.ChatImpl00;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 04.02.2005
 * Time: 2:50:51
 * Client over TCP Socket
 * Client start server connection, and server call client for serve
 */
public class ClientSocketCallBack00
{
	private ServerListener sl;
	private static SkelRouter router= new SkelRouter();

	public static void main(String args[]) throws Exception, TException2, TException1
	{
		new ClientSocketCallBack00().jobStart(args);
	}


	public static final String optarr[] = {"-pt","-hst"};
	public static final int O_pt=0;
	public static final int O_hst=1;
	static public Hashtable options = new Hashtable();

	static private void TranslateOption(String arg[])
	{
		if (arg == null)
			return;
		for (int i = 0; i < arg.length; i++)
		{
			if (arg[i] != null)
			{
				for (int j = 0; j < optarr.length; j++)
					if (arg[i].startsWith(optarr[j]))
					{
						String opt = arg[i].substring(optarr[j].length());
						options.put(optarr[j], opt);
						break;
					}
			}
		}
	}

	public void jobStart(String args[]) throws Exception
	{
		router.registerServant(Constants.REG_NAME01, new ChatImpl00());

		TranslateOption(args);

		int prt = Integer.parseInt((String)(options.get(optarr[O_pt])));
		Socket sc = new Socket((String)(options.get(optarr[O_hst])),prt);
		OutputStream os = sc.getOutputStream();
		InputStream is = sc.getInputStream();
		new TunelMessage((short)0,(short)0,Constants.SERVICE_NAME00.getBytes()).sendToReceiver(os);

		StreamChannel syschan = new StreamChannel(is,os,null,true);
//Предоставляем сервис
//		IInterceptor insr = new IInterceptorSeq(new SkelAuthenticateImpl(Constants.REG_INTERCEPT));
//		insr.registerInterseptor(router);
//		sl = new ServerListener(insr,syschan);

		sl = new ServerListener(router,syschan);
		new Thread()//Предоставим сервис для доступа к нашему объекту Constants.REG_NAME1 через мултеплексер
		{
			public void run()
			{
				sl.startService(null);
			}
		}.start();
		Thread.sleep(1000);
	}
}

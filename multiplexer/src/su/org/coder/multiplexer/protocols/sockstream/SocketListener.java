package su.org.coder.multiplexer.protocols.sockstream;

import su.org.coder.multiplexer.protocols.TunelMessage;
import su.org.coder.multiplexer.protocols.IService;
import su.org.coder.utils.IStorage;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 25.04.2005
 * Time: 13:21:54
 * To change this template use File | Settings | File Templates.
 */
public class SocketListener implements Runnable
{
	public final static int DEF_SOCK_TIME_OUT=180*1000; //TODO Сделать что бы устанавливалось с помощью опций
	static final boolean TIMEOUT_AS_ERROR = true; //TODO Передлать более высоки уровень, для того что бы ошибки
	//TODO по таймаутут возникали исключительно на уровне коннекторов, после которых мы останавливаем фабрику каналов.

	private int soketTimeOut=DEF_SOCK_TIME_OUT;
	private boolean timeOutAsError=TIMEOUT_AS_ERROR;

	private ServerSocket serverSocket;

    private boolean terminate=true;
	private int port;

	private int maxclibuff;
	private IStorage serviceRouter;


	public SocketListener(int port,int maxclibuff,IStorage serviceRouter)
	{
		this.port = port;
		this.maxclibuff = maxclibuff;
		this.serviceRouter = serviceRouter;
	}

	public SocketListener(int port,int maxclibuff,IStorage serviceRouter,boolean timeOutAsError,int SoketTimeOut)
	{

		this.port = port;
		this.maxclibuff = maxclibuff;
		this.serviceRouter = serviceRouter;
		this.timeOutAsError=timeOutAsError;
		if (timeOutAsError)
			this.soketTimeOut=SoketTimeOut;
	}


	private class doJob implements Runnable
	{
		private Socket sock;

		doJob(Socket sock) throws SocketException
		{
			sock.setSoTimeout(soketTimeOut);
			this.sock = sock;
		}

		public void run()
		{
			InputStream is=null;
			OutputStream os=null;
			try
			{
				is = sock.getInputStream();
				TunelMessage initmsg=new TunelMessage();
				initmsg.setBySender(is);
				String className="";
				if (initmsg.bmessage!=null)
					className=new String (initmsg.bmessage);
				IService serv = (IService) serviceRouter.getService(className);
				if (serv==null)
				{
					System.out.println("getting service for name "+className+" is fail");
					return;
				}
				System.out.println("getting service for name "+className+" Ok");
				serv.initService(null);
				os = sock.getOutputStream();
				ITimeOutSetter soc =null;
				if (!timeOutAsError)
				  soc=new ITimeOutSetterImpl(sock);

				serv.startService(new StreamChannel(is,os,soc,timeOutAsError));

				System.out.println("Service normal exits");
			}
			catch (IOException e)
			{
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
			finally
			{
				try
				{
					if (is!=null)
						is.close();
				}
				catch (IOException e)
				{
					;
				}
				try
				{
					if (os!=null)
						os.close();
				}
				catch (IOException e)
				{
					;
				}
				try
				{
					if (sock!=null)
					sock.close();
				}
				catch (IOException e)
				{
					;
				}
			}
		}
	}

	public void run()
	{
		terminate=false;
		try
		{
		    serverSocket = new ServerSocket(port, maxclibuff);
			do
			{
				new Thread(new doJob(serverSocket.accept())).start();
			}while (!terminate);
		}
		catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		finally
		{
			try
			{
				if (serverSocket!=null)
					serverSocket.close();
			}
			catch (IOException e2)
			{

			}
		}
	}

	public void stop()
	{
		terminate=true;
		try
		{
			serverSocket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}
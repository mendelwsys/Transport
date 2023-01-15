package su.org.coder.multiplexer.protocols.http3url.servlets2;

import su.org.coder.multiplexer.TMultiplexerEx;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.IService;
import su.org.coder.multiplexer.protocols.http3url.HttpTunelMessage2;
import su.org.coder.utils.MultiplObjectStorage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * The simplest possible servlet.
 *
 * @author SomeSuperXXX
 */

public abstract class InitServlet
		extends HttpServlet
{
	private String serviceName;
	public void init(ServletConfig sc)
	{
		serviceName=initChannelServlet(sc);
		IService service = (IService) MultiplObjectStorage.getInstance().getService(serviceName);
		service.initService(sc);
	}

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
			System.out.println("Init of business thread for channelId: "+channelId+" class name is: "+serviceName);
			IService service = (IService) MultiplObjectStorage.getInstance().getService(serviceName);
			service.startService(chan);
			ServletChannelManager.getInstance().removeChannelById(channelId);
			try {
				chan.close();
			} catch (TMultiplexerEx e) {
				e.printStackTrace();
			}
			chan=null;
		}
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		ServletOutputStream os = response.getOutputStream();
		int channelId=ServletChannelManager.getInstance().createNewChannel();
		System.out.println("Init channelId :" + channelId+ " serviceName:"+serviceName);
		new HttpTunelMessage2(channelId,0,null).sendToReceiver(os);
		os.flush();

		new Thread(new RunIt(channelId)).start();

//		PrintWriter out = response.getWriter();
//		out.println("<html>");
//		out.println("<head>");
//		out.println("<title>Message Call </title>");
//		out.println("</head>");
//		out.println("</body>");
//		out.println("</html>");

	}
}

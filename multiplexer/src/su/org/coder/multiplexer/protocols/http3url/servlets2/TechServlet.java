package su.org.coder.multiplexer.protocols.http3url.servlets2;

/*
* Copyright 2004 The Apache Software Foundation
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
/* $Id: TechServlet.java,v 1.1.1.1 2008/11/27 12:56:14 vlad Exp $
 *
 */

import su.org.coder.multiplexer.protocols.http3url.HttpTunelMessage2;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.TMultiplexerEx;

import java.io.*;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * The simplest possible servlet.
 *
 * @author SomeSuperXXXX
 */

public class TechServlet
		extends HttpServlet
{
	private static final int PAR_TIMEOUT = 100000;

	public void init(ServletConfig sc)
	{
		System.out.println("TechServlet.init");
		Enumeration en = sc.getServletContext().getInitParameterNames();
		while (en.hasMoreElements())
		{
			Object o = en.nextElement();
			System.out.println("TechServlet.init_2" + o.toString());
		}


	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		System.out.println("TechServlet begin procedure");
		ServletInputStream is = request.getInputStream();
		HttpTunelMessage2 lgmsg = new HttpTunelMessage2();
		lgmsg.setBySender(is);

		System.out.println("request channelID: "+lgmsg.httpchanneID);

		ILgChannel chan = ServletChannelManager.getInstance().getTechChannelById(lgmsg.httpchanneID);
        if (chan==null)
		{
			try
			{
				Thread.sleep(10000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
			System.out.println("!!!Unknown channel!!!!");
			throw new IOException("!!!Unknown channel!!!!");
		}
		ServletOutputStream os = response.getOutputStream();
		int i=0;
		HttpTunelMessage2 retVal=null;
		long beginwt = System.currentTimeMillis();
		do
		{
			try
			{
				retVal = (HttpTunelMessage2) chan.get(1000);
			}
			catch (TMultiplexerEx tMultiplexerEx)
			{
				tMultiplexerEx.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				throw new IOException(tMultiplexerEx.getMessage());
			}
			if (retVal!=null)
			{
				System.out.println("Before Send technical message to clinet");
				retVal.pPrint();
				retVal.sendToReceiver(os);
				System.out.println("After Send technical message to clinet");
				beginwt = System.currentTimeMillis();
				os.flush();
				i++;
			}
		}
		while((System.currentTimeMillis()-beginwt)<=PAR_TIMEOUT && (retVal==null || !retVal.isFinalMessage()));
		System.out.println("End of sending by tech channel"+i+" messages for serverId "+lgmsg.httpchanneID);
	}
}
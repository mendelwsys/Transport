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
/* $Id: InServlet.java,v 1.1.1.1 2008/11/27 12:56:14 vlad Exp $
 *
 */

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.net.SocketTimeoutException;
import java.util.Locale;
import javax.servlet.*;
import javax.servlet.http.*;

import su.org.coder.multiplexer.protocols.http3url.HttpTunelMessage2;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.TunelMessage;
import su.org.coder.multiplexer.TMultiplexerEx;


/**
 * The simplest possible servlet.
 *
 * @author SomeSuperXXX
 */

public class InServlet
		extends HttpServlet
{
	public void init(ServletConfig sc)
	{
		System.out.println("InServlet.init");
		Enumeration en = sc.getServletContext().getInitParameterNames();
		while (en.hasMoreElements())
		{
			Object o = en.nextElement();
			System.out.println("InServlet.init_2" + o.toString());
		}
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		try {
			System.out.println("InServlet begin procedure");
			ServletInputStream is = request.getInputStream();

			HttpTunelMessage2 lgmsg =null;
			HttpTunelMessage2 lgmsg_ack =null;
			int i=0;
			do
			{
				lgmsg = new HttpTunelMessage2();
				System.out.println("lgmsg.setBySender Before"+i);
				try
				{
						lgmsg.setBySender(is);
						lgmsg_ack= new HttpTunelMessage2(lgmsg.httpchanneID,lgmsg.messageID,null);
				}
				catch (IOException e)
				{ //EOF is normal situation - all messages wos read from channel
					System.out.println("lgmsg.setBySender Exception: "+e.getMessage() + " inst: " + (e instanceof SocketTimeoutException));
					is.close();

					if (lgmsg_ack!=null)
					{
						ILgChannel chan = ServletChannelManager.getInstance().getTechChannelById(lgmsg_ack.httpchanneID);
						try
						{

							if (chan!=null)
							{
								System.out.println("!!!Before send technical message for the channel!!!");
								chan.put(lgmsg_ack);
								System.out.println("!!!After send technical message for the channel!!!");
							}
							else
								System.out.println("ERROR getting chan!!! "+lgmsg_ack.httpchanneID);
						}
						catch (TMultiplexerEx tMultiplexerEx)
						{
							System.out.println("!!!!Error of send technical message for the channel!!!!");
							throw new IOException(tMultiplexerEx.getMessage());
						}
					}
					else
						System.out.println("Error getting message from unkonwn clinet");

					throw e;
				}

				System.out.println("lgmsg.setBySender After"+i);
				i++;
				if (lgmsg != null && lgmsg.bmessage!=null)
				{
					ILgChannel chan = ServletChannelManager.getInstance().getServletChannelById(lgmsg.httpchanneID);
					try
					{
						int offset=0;
						System.out.println("In Servlet:");
						int j=0;
						do
						{
							TunelMessage tm= new TunelMessage();
							offset=tm.setByByteArry(lgmsg.bmessage,offset);
							chan.put(tm);
							j++;
						} while (lgmsg.bmessage.length>offset);
						System.out.println("In Servlet messages:"+j);
					}
					catch (TMultiplexerEx tMultiplexerEx)
					{
						tMultiplexerEx.printStackTrace();
						throw new IOException(tMultiplexerEx.getMessage());
					}
				}

//	HTTP/1.1 200 OK
//	Date: Mon, 27 Jul 2005 12:28:53 GMT
//	Server: Apache/2.2.14 (Win32)
//	Last-Modified: Wed, 22 Jul 2005 19:15:56 GMT
//	Content-Length: 88
//	Content-Type: text/html
//				DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
//				Date today = new Date();
//				String date_str = formatter.format(today);
//				response.setStatus(204);
//				response.setHeader("Date", date_str);
// 				response.setHeader("Content-Length","0");
// 				response.setHeader("Content-Type","text/html");
//				response.flushBuffer();

			}
			while (!lgmsg.isFinalMessage());
		}
		catch (IOException e)
		{ //nothing wrong: all messages was read from channel
		}
	}
}

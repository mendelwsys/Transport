package su.org.coder.multiplexer.protocols.test00.midletserver;

import su.org.coder.multiplexer.protocols.IService;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.IChannelFactoryImpl;
import su.org.coder.multiplexer.TMultiplexerEx;
import su.org.coder.multiplexer.MultiplexerMessage;
import su.org.coder.utils.CallMessageImpl;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 20.02.2005
 * Time: 21:37:38
 * To change this template use File | Settings | File Templates.
 */
public class IServiceImpl01
		implements IService
{
	private IChannelFactoryImpl chf;

	public void initService(Object cfg)
	{
		System.out.println("Init su.org.coder.multiplexer.protocols.test00.midletserver.IServiceImpl01");
	}

	public void startService(ILgChannel chan)
	{
		System.out.println("Start su.org.coder.multiplexer.protocols.test00.midletserver.IServiceImpl01");
		chf = new IChannelFactoryImpl(new MultiplexerMessage(), chan);
		chf.startFactory();
		try
		{
			Thread.sleep(10);

			ILgChannel lgchanput = chf.accept(0);
			CallMessageImpl msg = null;
			long stat=0;
			int i=0;
			do
			{
				msg = (CallMessageImpl) lgchanput.get(50);

				if (msg != null)
				{
					if (msg.command==69 && msg.objID==777 && msg.typeID==777 && msg.methodCode==96)
					{
						if (i>0)
					    	System.out.println("STAT: "+(stat/i));
						stat=0;
						i=0;
					}
					else if (!msg.isFinalMessage())
					{
						if (msg.objID>=0)
						{
							i++;
							stat+=msg.objID;
						}
						if (msg.typeID>=0)
						{
							i++;
							stat+=msg.typeID;
						}
					}

					System.out.println("Getting from Channel2 msg:");
					msg.pPrint();
				}
//				else
//					System.out.println("Getting from Channel2 msg is null");
			}
			while (msg == null || !msg.isFinalMessage());
			if (i>0)
				System.out.println("STAT: "+(stat/i));
			lgchanput.put(new CallMessageImpl());
			Thread.sleep(1000);
			System.out.println("Get from Channel2 final message: END OF GETTING2");
			System.out.println("Set Stop factory");
			chf.setStopFactory(0);
			chf = null;
			System.out.println("Set Stop Ok");
		}
		catch (TMultiplexerEx tMultiplexerEx)
		{
			tMultiplexerEx.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

}

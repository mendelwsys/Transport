package su.org.coder.multiplexer.protocols.test00.server;

import su.org.coder.multiplexer.protocols.IService;
import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.IChannelFactoryImpl;
import su.org.coder.multiplexer.TMultiplexerEx;
import su.org.coder.multiplexer.MultiplexerMessage;
import su.org.coder.utils.CallMessageImpl;
import su.org.coder.utils.ILgMessage;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 20.02.2005
 * Time: 21:37:38
 * To change this template use File | Settings | File Templates.
 */
public class IServiceImpl02
		implements IService
{
	private IChannelFactoryImpl chf;

	public void initService(Object cfg)
	{
		System.out.println("Init su.org.coder.multiplexer.protocols.test00.server.ServiceImpl02");
	}

	public void startService(ILgChannel chan)
	{
		System.out.println("Start su.org.coder.multiplexer.protocols.test00.server.ServiceImpl02");
		chf = new IChannelFactoryImpl(new MultiplexerMessage(), chan);
		chf.startFactory();
		try
		{
			Thread.sleep(10);

			Thread trd = new Thread()
			{
				public void run()
				{
					try
					{
						ILgChannel lgchanput = chf.connect(0);
						for (int i = 0; i < 10; i++)
						{
							ILgMessage l_message = new CallMessageImpl((byte) 100, (short) i, (short) 2, (byte) 11,
									new byte[]{1, 2, 3, 4, 5});
							lgchanput.put(l_message);
							System.out.println("Put to Channel1");
							l_message.pPrint();
							Thread.sleep(1000);
						}
						lgchanput.put(new CallMessageImpl());
						System.out.println("Put to Channel1 final message1 : END OF PUT1");
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
			};
			trd.start();
			ILgChannel lgchanput = chf.accept(0);
			CallMessageImpl msg = null;
			do
			{
				msg = (CallMessageImpl) lgchanput.get(50);
				if (msg != null)
				{
					System.out.println("Getting from Channel2 msg:");
					msg.pPrint();
					msg.command += 20;
					lgchanput.put(msg);
					msg.command -= 20;
				}
//				else
//					System.out.println("Getting from Channel2 msg is null");
			}
			while (msg == null || !msg.isFinalMessage());
			System.out.println("Get from Channel2 final message: END OF GETTING2");
			trd.join();
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

package su.ms.standalone;

import su.org.coder.utils.IStorage;
import su.org.coder.utils.MultiplObjectStorage;
import su.org.coder.multiplexer.protocols.sockstream.SocketListener;
import su.org.coder2.test00.server.IHiTstImpl4CallBack00;
import su.org.coder2.test00.server.IHiTstImpl4CallBack01;
import su.org.coder2.test00.server.IHiTstImpl4Direct00;

import java.util.Hashtable;

public class RunAsChatTestStandAlone
{
	public static final String optarr[] = {"-pt",};
	public static final int O_pt=0;
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

	static public void main(String[] args) throws InterruptedException
	{
    	TranslateOption(args);
		String port=(String) options.get(optarr[O_pt]);
		int iprt = Integer.parseInt(port);
		System.out.println("Before Start Listener port is:"+iprt);

		IStorage storage= MultiplObjectStorage.getInstance();
		SocketListener oldListener=(SocketListener) storage.getService(SocketListener.class.getName());
		if (oldListener==null)
		{
//1. Заполнить сервисами
			storage.registerifempty(new IHiTstImpl4CallBack00());
			storage.registerifempty(new IHiTstImpl4CallBack01());
			storage.registerifempty(new IHiTstImpl4Direct00());
//2. Поместить туда сам соектный объект
			SocketListener socketListener = new SocketListener(iprt,50,storage);
			storage.register(socketListener);
			new Thread(socketListener).start();
			Thread.sleep(1000);
			System.out.println("The Socket  listener was started");
		}
	}
}

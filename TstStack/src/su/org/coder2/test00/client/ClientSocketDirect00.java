package su.org.coder2.test00.client;

import su.org.coder.multiplexer.TMultiplexerEx;
import su.org.coder.multiplexer.client.ClientConnector;
import su.org.coder.multiplexer.protocols.TunelMessage;
import su.org.coder.multiplexer.protocols.sockstream.StreamChannel;
import su.org.coder.utils.SysCoderEx;
import su.org.coder2.chat.*;
import su.org.coder2.gpstransl.IGPSClientProxy;
import su.org.coder2.gpstransl.ProxyGPSClient;
import su.org.coder2.test00.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Hashtable;

public class ClientSocketDirect00
{


    public static void main(String args[]) throws Exception, TException2, TException1
    {
        new ClientSocketDirect00().jobStart(args);
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
        TranslateOption(args);

        int prt = Integer.parseInt((String)(options.get(optarr[O_pt])));
        Socket sc = new Socket((String)(options.get(optarr[O_hst])),prt);
        OutputStream os = sc.getOutputStream();
        InputStream is = sc.getInputStream();
        new TunelMessage((short)0,(short)0,Constants.SERVICE_NAME02.getBytes()).sendToReceiver(os);

        StreamChannel syschan = new StreamChannel(is,os,null,true);

        System.out.println("Before Connect to client....");
        ClientConnector cl = new ClientConnector(syschan,0);
        System.out.println("Connect to client....Ok");

		//Привязаться к Constants.REG_NAME02
        IGPSClientProxy intrf1 = ProxyGPSClient.bind(cl, Constants.REG_NAME02);
		intrf1.startFlow(true);

        //Привязаться к Constants.REG_NAME01
        IChatInterfaceProxy intrf2 = ProxyChatInterface.bind(cl, Constants.REG_NAME01);
        sCall(intrf2,10);
        //Wait before exit
		intrf1.stopFlow();
        //Close system channels, release server resources ( remove server channels, end service threads (in HiTstImpl4Direct.startService))
        try {
            cl.getChannelFactory().setStopFactory(200);
        } catch (TMultiplexerEx e) {
            //
        }
        try {
            syschan.close();
        } catch (Exception e)
        {//
        }

    }

    private void sCall(IChatInterface intrf,int cnt)

    {

        try {
            for (int i=0;i<cnt;i++)
            {
                System.out.println("Put message "+i+"...");
                intrf.Put("Message"+i);
                System.out.println("Put message "+i+"...Ok");
                System.out.println("Get message...");
                System.out.println(intrf.Get(150));
                System.out.println("Get message...Ok");
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SysCoderEx e) {
            e.printStackTrace();
        } catch (TException1 e) {
            e.printStackTrace();
        } catch (TException2 e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

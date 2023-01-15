package su.org.coder2.test00.server;

import su.org.coder.multiplexer.protocols.ILgChannel;
import su.org.coder.multiplexer.protocols.IService;
import su.org.coder.multiplexer.server.ServerListener;
import su.org.coder.utils.SkelRouter;
import su.org.coder2.test00.Constants;
import su.org.coder2.test00.servants.ChatImpl00;
import su.org.coder2.test00.servants.GPSImpl00;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 20.02.2005
 * Time: 21:37:38
 * Skeleton service on server side
 */
public class IHiTstImpl4Direct00
        implements IService
{
    public void initService(Object cfg)
    {
        System.out.println("Init " + getClass().getCanonicalName());
    }

    public void startService(ILgChannel chan)
    {
        System.out.println("Start " + getClass().getCanonicalName());
        //One router on even IHiTstImpl02 service
        SkelRouter router= new SkelRouter();
        router.registerServant(Constants.REG_NAME01, new ChatImpl00());
        router.registerServant(Constants.REG_NAME02, new GPSImpl00());
//Предоставляем сервис
//		IInterceptor insr = new IInterceptorSeq(new SkelAuthenticateImpl(Constants.REG_INTERCEPT));
//		insr.registerInterseptor(router);
//		sl = new ServerListener(insr,syschan);
        ServerListener sl = new ServerListener(router, chan);
        sl.startService(null);
    }
}
package su.org.coder2.test00.servants;

import su.org.coder.utils.SysCoderEx;
import su.org.coder2.gpstransl.SkelGPSClient;

import java.io.IOException;

public class GPSImpl00 extends SkelGPSClient {
    @Override
    public void startFlow(boolean reconnect) throws IOException, SysCoderEx {
        System.out.println("startFlow with reconnect = " + reconnect+" for "+GPSImpl00.class.getCanonicalName());
    }

    @Override
    public void stopFlow() throws IOException, SysCoderEx {
        System.out.println("stopFlow + for "+GPSImpl00.class.getCanonicalName());
    }
}

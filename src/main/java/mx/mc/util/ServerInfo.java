package mx.mc.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author hramirez
 */
public class ServerInfo {

    public static String getIpAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public static String getHostName() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }

}

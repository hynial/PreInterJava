package com.hynial.preinter.net.socket;

import java.io.IOException;
import java.net.Socket;

public class SubHttpProxy extends HttpProxy {
    static private boolean first = true;

    public SubHttpProxy(Socket s) {
        super(s);
    }

    public void writeLog(int c, boolean browser) throws IOException {
        if (first) log.write('*');
        first = false;
        log.write(c);
        if (c == '\n') log.write('*');
    }

    public String processHostName(String url, String host, int port, Socket sock) {
        return host;
    }

    static public void main(String args[]) {
        System.out.println("-----\n");
        HttpProxy.log = System.out;
        HttpProxy.logging = true;
        HttpProxy.startProxy(8088, SubHttpProxy.class);
    }


}

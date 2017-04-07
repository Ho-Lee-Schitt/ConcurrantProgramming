package Week10;

/**
 * Created by cgf13hun on 07/04/2017.
 */

import java.net.InetSocketAddress;
import java.io.*;
import java.util.logging.*;
import com.sun.net.httpserver.*;

public class PrimeServerHttp {

    // static logger
    public static final Logger LOG = Logger.getLogger(PrimeServerHttp.class.getName());

    // start web server
    public static void main(String[] args) throws Exception {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            LOG.info("HTTP: Server started");
            server.createContext("/prime", new PrimeContextHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException ex) {
            LOG.log(Level.ALL, ex.getMessage());
        }
    } // end main

} // PrimeServerHttp

package Week9;

/**
 * Created by Niall Hughes on 25/03/2017.
 */

import java.net.*;
import java.io.*;

public class Question3
{
}

class MultiThreadedEchoServer extends Thread {
    protected Socket s;

    // constructor
    MultiThreadedEchoServer(Socket s) {
        System.out.println("New client.  ");
        this.s = s;
    } // end constructor

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    s.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    s.getOutputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                out.flush();
            } // end while
            System.out.println("Client exit.");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } // end finally
    } // end run

    public static void main(String args[]) throws IOException {
        System.out.println("Starting on port " + args[0]);
        try (ServerSocket server = new ServerSocket(Integer.parseInt(args[0]))) {
            while (true) {
                System.out.println("Waiting");
                Socket client = server.accept();
                System.out.println("Accepted from " + client.getInetAddress());
                MultiThreadedEchoServer clientHandler = new
                        MultiThreadedEchoServer(client);
                clientHandler.start();
            } // end while
        } catch (IOException ex) {
            System.err.println("Unable to connect on specified port");
        }
    } // end main

} // end MultiThreadedEchoServer

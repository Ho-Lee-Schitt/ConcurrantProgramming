package Week8;

/**
 * Created by Niall Hughes on 25/03/2017.
 */

import java.io.*;
import java.net.*;

public class EchoTest {

    public static void main(String[] args) {
        Socket echoSocket = null;
        PrintWriter os = null;
        BufferedReader is = null;
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(
                System.in));
        try {
            echoSocket = new Socket("localhost", 7);
           os = new PrintWriter(new OutputStreamWriter(
                   echoSocket.getOutputStream()));
           is = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Cannot connect to localhost");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O streams");
            e.printStackTrace();
        }

        System.out.println("Connected");
        // you are now connected to the localhost and have established input and
        // output streams
        if (echoSocket != null && os != null && is != null) {
            try {
                String userInput = null; // user input
                while (!(userInput = stdIn.readLine()).isEmpty())// read user input
                {
                    // write the user input to port 7 of localhost
                    os.println(userInput);
                    // read back the returned text from echoserver
                    os.flush(); // needed to force the text to be sent
                    String echoedText = is.readLine();
                    // print the text that was echoed back
                    System.out.println("Echoed Text: " + echoedText);
                } // end while
                os.close();
                is.close();
                echoSocket.close();
            } catch (IOException e) {
                System.err.println("I/O failed to communicate");
            }
        } // end if
    } // end main

} // end EchoTest

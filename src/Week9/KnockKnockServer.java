package Week9;

/**
 * Created by Niall Hughes on 25/03/2017.
 */

//  KnockKnockServer class
import java.net.*;
import java.io.*;

class KnockKnockServer {

    public static void main(String[] args) {

        try {
            // Open a server socket to listen on port 4444
            ServerSocket serverSocket = new ServerSocket(4444);
            try {
                // Wait to accept a connecting client
                Socket clientSocket = serverSocket.accept();
                // Create Input and Output streams to communicate with the client
                try (BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter os = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
                    // Set up the server state
                    System.out.println("Knock Knock Server Waiting");
                    KKState kks = new KKState();
                    String inputLine, outputLine;
                    outputLine = kks.processInput(null);
                    os.println(outputLine);
                    os.flush();
                    while ((inputLine = is.readLine()) != null) {
                        outputLine = kks.processInput(inputLine);
                        os.println(outputLine);
                        os.flush();
                        if (outputLine.equals("Bye.")) {
                            break;
                        }
                    } // end while
                    clientSocket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println("Failed to create I/O streams " + e);
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println("Accept failed on port: " + 4444 + ", " + e);
                System.exit(1);
            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + 4444 + ", " + e);
            System.exit(1);
        } // end catch
    } // end main

} // end KnockKnockServer

class KKState {

    private static final int WAITING = 0;
    private static final int SENTKNOCKKNOCK = 1;
    private static final int SENTCLUE = 2;
    private static final int ANOTHER = 3;

    private static final int NUMJOKES = 5;

    private int state = WAITING;
    private int currentJoke = 0;

    private final String[] clues = {"Turnip", "Little Old Lady", "Atch", "Who", "Who"};
    private final String[] answers = {"Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!",
            "Is there an owl in here?",
            "Is there an echo in here?"};

    String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) // waiting
        {
            theOutput = "Knock! Knock!";
            state = SENTKNOCKKNOCK;
        } else if (state == SENTKNOCKKNOCK) // SENTKNOCKKNOCK
        {
            if (theInput.equalsIgnoreCase("Who's there?")) {
                theOutput = clues[currentJoke];
                state = SENTCLUE;
            } else {
                theOutput = "You're supposed to say \"Who's there?\"! Try again. Knock! Knock!";
            }
        } // end SENTKNOCKKNOCK
        else if (state == SENTCLUE) // SENTCLUE
        {
            if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
                theOutput = answers[currentJoke] + " Want another? (y/n)";
                state = ANOTHER;
            } else {
                theOutput = "You're supposed to say \"" + clues[currentJoke] + " who?\"" + "! Try again. Knock! Knock!";
                state = SENTKNOCKKNOCK;
            }
        } // end SENTCLUE
        else if (state == ANOTHER) // ANOTHER
        {
            if (theInput.equalsIgnoreCase("y")) {
                theOutput = "Knock! Knock!";
                if (currentJoke == (NUMJOKES - 1)) {
                    currentJoke = 0;
                } else {
                    currentJoke++;
                }
                state = SENTKNOCKKNOCK;
            } else {
                theOutput = "K thx Bye.";
                state = WAITING;
            }
        } // end ANOTHER
        return theOutput;
    } // end processInput

} // end KKState

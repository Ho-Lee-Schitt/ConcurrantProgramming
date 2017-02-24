/**
 * Created by cgf13hun on 24/02/2017.
 */

package Week5;

import java.util.concurrent.*;

public class Question2 {
}

/* PillarBox1.java */
class PillarBox1
{
    public static void main(String[] args)
    {
        ThePillarBox singleLetterBox = new ThePillarBox(0);

        Semaphore emptyBox = new Semaphore (1);
        Semaphore fullBox = new Semaphore (0);

        Producer p = new Producer(emptyBox, fullBox, singleLetterBox);
        Consumer c = new Consumer(emptyBox, fullBox, singleLetterBox);
        p.start();
        c.start();
        System.out.println("Threads Producer and Consumer commanded to start");
    } // end main
} // end Pillarbox1

/* Producer.java */
class Producer extends Thread
{
    private  Semaphore pempty;
    private  Semaphore pfull;
    private  ThePillarBox   pillarBox;

    public Producer(Semaphore empty, Semaphore full, ThePillarBox pbox)
    {
        pempty    = empty;
        pfull     = full;
        pillarBox = pbox;
    }

    public void run()
    {
        int round;
        for (round = 0; round < 10; round++)
        {   /* Trying to send a message */
            System.out.println("Producer trying to send, round:" + round);
            try {
                pempty.acquire(); // Wait for box to be empty
            } catch (InterruptedException ie) {System.out.println("Producer Interrupted");}
             /* Copying message */
            pillarBox.putValue(round);
            CDS.idleQuietly(1);
            pfull.release();   // Signal that box is full
            System.out.println("Producer has put, round:" + round);
            CDS.idleQuietly(2);
        } // end for
        System.out.println("Producer finished");
    } // end run
} // end Producer

/* Consumer.java */
class Consumer extends Thread
{
    private  Semaphore pempty;
    private  Semaphore pfull;
    private  ThePillarBox   pillarBox;

    public Consumer(Semaphore empty, Semaphore full, ThePillarBox pbox)
    {
        pempty    = empty;
        pfull     = full;
        pillarBox = pbox;
    } // end constructor

    public void run()
    {
        int round;
        int x;
        for (round = 0; round < 10; round++)
        {   /* Trying to get a message */
            System.out.println("Consumer trying to get, round:" + round);
            try {
                pfull.acquire(); // Wait for box to be full
            } catch (InterruptedException ie) {System.out.println("Consumer Interrupted");}
             /* Reading message */
            x = pillarBox.getValue();
            CDS.idleQuietly(100);
            pempty.release();
            System.out.println("Consumer got, round:" + round + ", " + x);
            CDS.idleQuietly(50);
        } // end for
        System.out.println("Consumer finished");
    } // end run
} // end Consumer

/* ThePillarBox.java */
class ThePillarBox
{
    private int value;

    ThePillarBox(int x)
    {
        value = x;
    }

    public void putValue (int v)
    {
        value = v;
    } // end putValue

    public int getValue ()
    {
        return value;
    } // end getValue

} // end ThePillarBox

package Week5;

import java.util.concurrent.Semaphore;

/**
 * Created by cgf13hun on 24/02/2017.
 */
public class Question3 {
}

/* PillarBox2.java */
class PillarBox2
{
    public static void main(String[] args)
    {
        ThePillarBoxMany singleLetterBox = new ThePillarBoxMany();

        Semaphore emptyBox = new Semaphore (10);
        Semaphore fullBox = new Semaphore (0);

        Producer2 p = new Producer2(emptyBox, fullBox, singleLetterBox);
        Consumer2 c = new Consumer2(emptyBox, fullBox, singleLetterBox);
        p.start();
        c.start();
        System.out.println("Threads Producer and Consumer commanded to start");
    } // end main
} // end Pillarbox2

/* ThePillarBoxMany.java */
class ThePillarBoxMany
{
    private final int SIZE = 10;

    private int[] values = new int[SIZE];
    private int front;
    private int rear;

    ThePillarBoxMany()
    {
        for (int i=0; i<SIZE; i++) values[i]=0;
        front=2;
        rear=2;
    }

    public void putValue (int v)
    {
        values[rear] = v;
        rear = (rear+1) % SIZE;
    } // end putValue

    public int getValue ()
    {
        int temp = values[front];
        front = (front+1) % SIZE;
        return temp;
    } // end getValue

} // end ThePillarBoxMany

/* Producer.java */
class Producer2 extends Thread
{
    private Semaphore pempty;
    private  Semaphore pfull;
    private  ThePillarBoxMany   pillarBox;

    public Producer2(Semaphore empty, Semaphore full, ThePillarBoxMany pbox)
    {
        pempty    = empty;
        pfull     = full;
        pillarBox = pbox;
    }

    public void run()
    {
        int round;
        for (round = 0; round < 50; round++)
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
class Consumer2 extends Thread
{
    private  Semaphore pempty;
    private  Semaphore pfull;
    private  ThePillarBoxMany   pillarBox;

    public Consumer2(Semaphore empty, Semaphore full, ThePillarBoxMany pbox)
    {
        pempty    = empty;
        pfull     = full;
        pillarBox = pbox;
    } // end constructor

    public void run()
    {
        int round;
        int x;
        for (round = 0; round < 50; round++)
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
/**
 * Created by cgf13hun on 24/02/2017.
 */

package Week5;

import java.util.concurrent.*;

public class Question1 {
}

/* CriticalTest2.java */
class CriticalTest2
{
    public static void main(String[] args)
    {
        Semaphore mutex = new Semaphore (1);

        P1 p1 = new P1(mutex);
        P2 p2 = new P2(mutex);
        P3 p3 = new P3(mutex);

        p1.start();
        p2.start();
        p3.start();

        System.out.println("Threads p1 and p2 have been commanded to start");
    } // end main

} // end CriticalTest2


/* P1.java */
class P1 extends Thread
{
    private  Semaphore pmutex;   // reference to shared semaphore

    public P1(Semaphore mutex)
    { // initialise P1 (constructor)
        pmutex = mutex;
    }// end constructor P1

    public void run()
    {  // start P1
        int round;
        for (round = 0; round < 3; round++)
        {   /* Trying to enter the Critical section with P(mutex) */
            System.out.println("P1 waiting to enter CS, round:" + round);
            try {
                pmutex.acquire();
            } catch (InterruptedException ie) {System.out.println("P1-Interrupted waiting to enter");}

            /* In Critical section */
            System.out.println("P1 is in CS, round:" + round);
            CDS.idleQuietly(10);

            /* Exit protocol       */
            pmutex.release();
            System.out.println("P1 is out of CS, round:" + round);
            CDS.idleQuietly(10);
        } // end for
        System.out.println("P1 finished");
    } // end run
} // end P1

/* P2.java */
class P2 extends Thread
{
    private  Semaphore pmutex;   // reference to shared semaphore

    public P2(Semaphore mutex)
    { // initialise P2 (constructor)
        pmutex = mutex;
    }// end constructor P2

    public void run()
    {  // start P2
        int round;
        for (round = 0; round < 3; round++)
        {   /* Trying to enter the Critical section with P(mutex) */
            System.out.println("P2 waiting to enter CS, round:" + round);
            try {
                pmutex.acquire();
            } catch (InterruptedException ie) {System.out.println("P2-Interrupted waiting to enter");}

            /* In Critical section */
            System.out.println("P2 is in CS, round:" + round);
            CDS.idleQuietly(1000);

            /* Exit protocol       */
            pmutex.release();
            System.out.println("P2 is out of CS, round:" + round);
            CDS.idleQuietly(500);
        } // end for
        System.out.println("P2 finished");
    } // end run
} // end P2

/* P3.java */
class P3 extends Thread
{
    private  Semaphore pmutex;   // reference to shared semaphore

    public P3(Semaphore mutex)
    { // initialise P1 (constructor)
        pmutex = mutex;
    }// end constructor P1

    public void run()
    {  // start P1
        int round;
        for (round = 0; round < 3; round++)
        {   /* Trying to enter the Critical section with P(mutex) */
            System.out.println("P3 waiting to enter CS, round:" + round);
            try {
                pmutex.acquire();
            } catch (InterruptedException ie) {System.out.println("P3-Interrupted waiting to enter");}

            /* In Critical section */
            System.out.println("P3 is in CS, round:" + round);
            CDS.idleQuietly(1000);

            /* Exit protocol       */
            pmutex.release();
            System.out.println("P3 is out of CS, round:" + round);
            CDS.idleQuietly(500);
        } // end for
        System.out.println("P3 finished");
    } // end run
} // end P3


//CDS.java - Useful class for Concurrent and Distributed Systems
class CDS {

    public static void idle(int millisecs) {
        Thread mainThread = Thread.currentThread();
        System.out.println(mainThread.getName() + ": About to sleep");
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
        }
        System.out.println(mainThread.getName() + ": Woken up");
    } // end idle

    public static void idleQuietly(int millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
        }
    } // end idleQuietly

} // end CDS

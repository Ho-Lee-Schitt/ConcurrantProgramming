package Week6;

/**
 * Created by cgf13hun on 03/03/2017.
 */
public class Question2 {
}

/* Class BoundedBuffer*/
class BoundedBuffer {
    private int SIZE = 10;
    private int buffer [] = new int[SIZE] ;
    private boolean writable = true;
    private boolean readable = false;
    private int readLocation = 0;
    private int writeLocation = 0;
    private int val;

    public synchronized int get()
    {
        while (!readable)
        {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        writable = true;
        val = buffer[readLocation];
        readLocation = (readLocation+1) % SIZE;
        if (readLocation==writeLocation) readable = false;
        notify();
        return val;
    } // end get

    public synchronized void put(int value)
    {
        while (!writable)
        {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        buffer[writeLocation] = value;
        readable = true;
        writeLocation = (writeLocation+1) % SIZE;
        if (writeLocation==readLocation) writable = false;
        notify();
    } // end put

} // end BoundedBuffer


/* ProducerForBB */
class ProducerForBB extends Thread
{
    private BoundedBuffer bBuffer;

    public ProducerForBB(BoundedBuffer bb)
    {
        bBuffer = bb;
    } // end constructor

    public void run()
    {
        for (int i = 0; i < 20; i++)
        {
            bBuffer.put(i);
            System.out.println("Producer put: " + i);
            try {
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) { }
        } // end for
    } // end run

} // end ProducerForBB


/* ConsumerForBB */
class ConsumerForBB extends Thread
{
    private BoundedBuffer bBuffer;

    public ConsumerForBB(BoundedBuffer bb)
    {
        bBuffer = bb;
    } // end constructor

    public void run()
    {
        int value = 0;
        for (int i = 0; i < 20; i++)
        {
            try {
                sleep((int)(Math.random() * 200));
            } catch (InterruptedException e) { }
            value = bBuffer.get();
            System.out.println("Consumer got: " + value);
        } // end for
    } // end run

} // end ConsumerForBB


//main class to test the BoundedBuffer
class BoundedBufferTest {

    public static void main(String[] args) {
        BoundedBuffer bbb = new BoundedBuffer();
        ProducerForBB p1 = new ProducerForBB(bbb);
        ConsumerForBB c1 = new ConsumerForBB(bbb);
        p1.start();
        c1.start();
    } // end main

} // end BoundedBufferTest

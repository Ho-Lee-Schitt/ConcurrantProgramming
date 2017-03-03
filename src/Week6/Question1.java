package Week6;

/**
 * Created by cgf13hun on 03/03/2017.
 */
public class Question1 {
}

class ProducerConsumerTest
{
    public static void main(String[] args)
    {
        CubbyHole c = new CubbyHole();
        Producer p1 = new Producer(c);
        Consumer c1 = new Consumer(c);
        p1.start();
        c1.start();
    } // end main

} // end ProducerConsumerTest


/* Class CubbyHole */
class CubbyHole
{
    private int contents;
    // this is the condition variable.
    private boolean available = false;

    public synchronized int get()
    {
        while (available == false)
        {
            try {
                wait();
            } catch (InterruptedException e) {  }
        } // end while
        available = false;
        notify();
        return contents;
    } // end get

    public synchronized void put(int value)
    {
        while (available == true)
        {
            try {
                wait();
            } catch (InterruptedException e) {  }
        } // end while
        contents = value;
        available = true;
        notify();
    } // end put

} // end CubbyHole


/* Producer */
class Producer extends Thread
{
    private CubbyHole cubbyhole;

    public Producer(CubbyHole c)
    {
        cubbyhole = c;
    } // end constructor

    public void run()
    {
        for (int i = 0; i < 10; i++)
        {
            cubbyhole.put(i);
            System.out.println("Producer put: " + i);
            try {
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) {  }
        } // end for
    } // end run

} // end Producer





/* Consumer */
class Consumer extends Thread
{
    private CubbyHole cubbyhole;

    public Consumer(CubbyHole c)
    {
        cubbyhole = c;
    } // end constructor

    public void run()
    {
        int value = 0;
        for (int i = 0; i < 10; i++)
        {
            try {
                sleep((int)(Math.random() * 2000));
            } catch (InterruptedException e) {  }
            value = cubbyhole.get();
            System.out.println("Consumer got: " + value);
        } // end for
    } // end run

} // end Consumer

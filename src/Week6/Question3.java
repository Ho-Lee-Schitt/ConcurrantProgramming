package Week6;

/**
 * Created by cgf13hun on 03/03/2017.
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Question3 {
}

/* Class BoundedBuffer2*/
class BoundedBuffer2 {
    private int SIZE = 10;
    private int buffer [] = new int[SIZE] ;
    private int val;
    private int front;
    private int rear;
    private int count;

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public int get() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            val = buffer[front];
            front = (front + 1) % SIZE;
            count--;
            notFull.signal();
            return val;
        } finally {
            lock.unlock();
        }
    } // end get

    public void put (int value) throws InterruptedException {
        lock.lock();
        try {
            while (count == SIZE) {
                notFull.await();
            }
            buffer[rear] = value;
            rear = (rear + 1) % SIZE;
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    } // end put

} // end BoundedBuffer2


/* Consumer */
class Consumer2 extends Thread
{
    private BoundedBuffer2 bBuffer;

    public Consumer2 (BoundedBuffer2 bb)
    {
        bBuffer = bb;
    } // end constructor

    public void run()
    {
        int value = 0;
        for (int i = 0; i < 20; i++)
        {
            try {
                value = bBuffer.get();
            } catch (InterruptedException e) {e.printStackTrace();}
            System.out.println("Consumer got: " + value);
            try {
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) { }
        } // end for
    } // end run
} // end Consumer

/* Producer */
class Producer2 extends Thread
{
    private BoundedBuffer2 bBuffer;

    public Producer2(BoundedBuffer2 bb)
    {
        bBuffer = bb;
    } // end constructor

    public void run()
    {
        for (int i = 0; i < 20; i++)
        {
            try {
                bBuffer.put(i);
            } catch (InterruptedException e1) { e1.printStackTrace(); }
            System.out.println("Producer put: " + i);
            try {
                sleep((int)(Math.random() * 1));
            } catch (InterruptedException e) { }
        } // end for
    } // end run
} // end Producer

//main class to test the BoundedBuffer
class BoundedBufferTest2
{
    public static void main(String[] args)
    {
        BoundedBuffer2 bb = new BoundedBuffer2();
        Producer2 p1 = new Producer2(bb);
        Consumer2 c1 = new Consumer2(bb);
        p1.start();
        c1.start();
    } // end main
} // end BoundedBufferTest

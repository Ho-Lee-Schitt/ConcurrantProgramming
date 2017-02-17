package Week3;

/**
 * Created by cgf13hun on 10/02/2017.
 */
/* RunThreads.java */
class RunThreads
{
    public static void  main(String args[])
    {
        Showline  possibleThread = new Showline(); //create object that can become a thread
        Thread t1 = new Thread ( possibleThread, "Kate" ); // call thread constructor to make thread
        Thread t2 = new Thread ( possibleThread, "John" ); // call thread constructor to make thread
        t1.start(); // start thread
        t2.start(); // start thread
        Thread t3 = new Thread ( possibleThread, "Mary" );
        Thread t4 = new Thread ( possibleThread, "Peter" );
        t3.start();
        t4.start();
    }
}

/* Showline.java */
class Showline implements Runnable {
    public void run() {
        while (true)
            System.out.println(Thread.currentThread().getName()); // gets current threads name and print it
    }
}

package Week3;

/**
 * Created by cgf13hun on 10/02/2017.
 */
class ThreadDemo
{
    public static void main (String [] args)
    {
        MyThread mt1 = new MyThread (0);
        mt1.start ();
        MyThread mt2 = new MyThread (100);
        mt2.start ();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) { }
        for (int i = 0; i < 50; i++)
            System.out.println ("i = " + i + ", i * i = " + i * i);
    }
}
class MyThread extends Thread
{
    private int sleep;
    MyThread (int seconds){
        sleep = seconds;
    }
    public void run ()
    {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) { }
        for (int count = 1, row = 1; row < 20; row++, count++)
        {
            for (int i = 0; i < count; i++)
                System.out.print ('*');
            System.out.print ('\n');
        }
    }
}

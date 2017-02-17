package Week3;

/**
 * Created by cgf13hun on 10/02/2017.
 */
class  SimpleThread1  extends Thread
{
    public  void run()
    {
        for  ( int  count = 0;  count < 4; count++)
            System.out.println( "Message " + count +  "From: " + getName() );
    }
}

class  TestThread1
{
    public static void main( String[]  args )
    {
        SimpleThread1  parallel  = new SimpleThread1();
        System.out.println( "Create the thread");
        parallel.start();
        System.out.println( "Started the thread" );
        System.out.println( "End" );
    }
}


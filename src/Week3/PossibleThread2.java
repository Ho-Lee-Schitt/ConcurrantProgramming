package Week3;

/**
 * Created by cgf13hun on 10/02/2017.
 */
class  PossibleThread2 implements  Runnable
{
    public  void  run()
    {
        for  ( int  count = 0;  count < 4; count++)
            System.out.println( "Message " + count +  " From: " + Thread.currentThread().getName() );
    }
}

class  TestThread2
{
    public static void main( String[]  args )
    {
        PossibleThread2  notAThread  = new PossibleThread2();
        Thread  parallelThread  =  new Thread( notAThread );
        System.out.println( "Create the thread");
        parallelThread.start();
        System.out.println( "Started the thread" );
        System.out.println( "End" );
    }
}


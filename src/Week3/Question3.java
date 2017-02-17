package Week3;

class RunIdlers
{
    public static void  main(String args[])
    {
        Idler  lover = new Idler(8000);
        Idler  hater = new Idler(16000);
        new Thread(lover, "She loves me").start();
        new Thread(hater, "No, she hates me").start();
        while (true) {
            System.out.println("Back at main");
        }
    }
}

class  Idler implements Runnable
{
    private int private_idle;

    Idler (int idleTime) // constructer
    {
        private_idle = idleTime;
    }

    public void  run()
    {
        while (true)
        {
            System.out.println("Current Thread is:" + Thread.currentThread().getName());
            CDS.idle ((int)(Math.random() * (private_idle - 0) + 0));
        }
    } // end run
} // end Idler

// CDS.java - Useful class for Concurrent and Distributed Systems
class  CDS
{
    public static void idle (int millisecs)
    {
        Thread mainThread = Thread.currentThread();
        System.out.println(mainThread.getName() + ": About to sleep");
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) { }
        System.out.println (mainThread.getName() + ": Woken up");
    } // end idle

} // end CD

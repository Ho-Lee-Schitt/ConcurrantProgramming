package Week4;

/**
 * Created by cgf13hun on 17/02/2017.
 */
public class Question2 {
}

/* RegTest2.java */
class Regtest2
{
    public static void main(String[] args)
    {
        AnyData x = new AnyData(0);
        Qprog q = new Qprog(x, "Qprog");
        Rprog r = new Rprog(x, "Rprog");
        q.start();
        r.start();
        System.out.println("Threads q and r have been commanded to start");
        try {
            q.join();
        }  catch  (InterruptedException e) {}
        try {
            r.join();
        }  catch  (InterruptedException e) {}
        System.out.println("x is: " + x.value);
    } // end main

} // end Regtest2


/* Rprog.java */
class Rprog extends Thread
{
    private AnyData localdata;

    public Rprog(AnyData a, String threadName) // constructor
    {
        super(threadName);
        this.localdata = a;
    }

    public void run()
    {
        int reg1;
        CDS.idle(1000);
        reg1 = this.localdata.value;
        System.out.println("Rprog has read x as: " + reg1);
        CDS.idle(1000);
        reg1 = reg1 + 2;
        this.localdata.value = reg1;
        System.out.println("Rprog has copied back: " + reg1 + " to x");
    } // end run

} // end Rprog


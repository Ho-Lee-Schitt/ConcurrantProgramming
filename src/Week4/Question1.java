package Week4;

/* RegTest1.java */
class RegTest1
{
    public static void main(String[] args)
    {
        AnyData x = new AnyData(0);

        Qprog q = new Qprog(x, "Qprog");
        q.start();
        System.out.println("Thread q has been commanded to start");

        //try { q.join(); }
        //catch  (InterruptedException e) {}

        System.out.println("x at end of main is: " + x.value);
    } // end main
} // end RegTest1

/* AnyData.java */
class AnyData
{
    public int value;

    AnyData(int x) // constructor
    {
        value = x;
    }
} // end AnyData

/* Qprog.java */
class Qprog extends Thread
{
    private AnyData localdata;

    public Qprog(AnyData a, String threadName) // constructor
    {
        super(threadName);
        this.localdata = a;
    } // end constructor

    public void run()
    {
        int reg1;

        CDS.idle(2000);

        reg1 = this.localdata.value;
        System.out.println("Qprog has read x as: " + reg1);

        CDS.idle(1000);

        reg1 = reg1 + 1;
        this.localdata.value = reg1;
        System.out.println("Qprog has copied: " + reg1 + " back to x");
    } // end run

} // end Qprog

class Question1
{}
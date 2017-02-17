package Week3;

class TwoThreads
{
    private static boolean done;
    public static class Thread1 extends Thread
    {
        public void run()
        {
            done = false;
            System.out.println("A");
            System.out.println("B");
            done = true;
        }
    }

    public static class Thread2 extends Thread
    {
        public void run()
        {
            while (!done) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) { }
            }
            System.out.println("1");
            System.out.println("2");

        }
    }

    public static void main(String[] args)
    {
        new Thread1().start();
        new Thread2().start();
    }
}

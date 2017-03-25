package Week7;

/**
 * Created by Niall Hughes on 25/03/2017.
 */

import java.util.*;

public class Question2
{
}

class NumberHolder2 implements Runnable {

    private final int CAPACITY = 1000;
    private final ArrayList <Integer> vector = new ArrayList<>(1000);
    private volatile boolean done = false;

    public ArrayList<Integer> getVector() {
        return vector;
    }

    public void shutdown() {
        done = true;
    }

    @Override
    public synchronized void run() {
        Random number = new Random();
        int i = CAPACITY;
        while (!done && i > 0) {
            vector.add(number.nextInt(100));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
            }
            i--;
        }
    } // end run

} //end NumberHolder2

class TestNumberHolder2 {

    public static void printGeneratedNumbers(ArrayList<Integer> thisVector) {
        System.out.println("\nElements in vector:");
        for (Integer i : thisVector) {
            System.out.print(i + " ");
        }
        System.out.println();
    } // end printGeneratedNumbers

    public static void main(String[] args) throws InterruptedException {
        NumberHolder2 holder = new NumberHolder2();
        Thread numberHolderThread = new Thread(holder);
        numberHolderThread.start();
        Thread.sleep(500);
        holder.shutdown(); // stops the thread
        System.out.println("The thread was terminated using the volatile flag method");
        ArrayList<Integer> temp = holder.getVector();
        System.out.println("The vector has the following " + temp.size() + " values:");
        printGeneratedNumbers(temp);
    } // end main
} //end TestNumberHolder2


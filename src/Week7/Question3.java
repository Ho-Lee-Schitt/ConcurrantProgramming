package Week7;

/**
 * Created by Niall Hughes on 25/03/2017.
 */

import java.util.*;

public class Question3
{
}

class NumberHolder3 implements Runnable {

    private final int CAPACITY = 1000;
    private final ArrayList<Integer> vector = new ArrayList<>(1000);

    public ArrayList<Integer> getVector() {
        return vector;
    }

    @Override
    public synchronized void run() {
        Random number = new Random();
        int i = CAPACITY;
        while (!Thread.interrupted() && i > 0) {
            vector.add(number.nextInt(100));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
            }
            i--;
        }
    } // end run

} //end NumberHolder3

class TestNumberHolder3 {

    public static void printGeneratedNumbers(ArrayList<Integer> thisVector) {
        System.out.println("\nElements in vector:");
        for (Integer i : thisVector) {
            System.out.print(i + " ");
        }
        System.out.println();
    } // end printGeneratedNumbers

    public static void main(String[] args) throws InterruptedException {
        NumberHolder3 holder = new NumberHolder3();
        Thread numberHolderThread = new Thread(holder);
        numberHolderThread.start();
        Thread.sleep(500);
        numberHolderThread.interrupt(); // interrupts the thread
        System.out.println("The thread was terminated using the interrupt() method");
        ArrayList<Integer> temp = holder.getVector();
        System.out.println("The vector has the following " + temp.size()
                + " values:");
        printGeneratedNumbers(temp);
    } // end main
} // end TestNumberHolder3

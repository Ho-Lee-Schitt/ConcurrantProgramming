package Week7;

/**
 * Created by cgf13hun on 10/03/2017.
 */

import java.util.*;

public class Question1 {
}

class NumberHolder1 implements Runnable {

    private final int CAPACITY = 1000;

    private final ArrayList<Integer> vector = new ArrayList<>(CAPACITY);

    public ArrayList<Integer> getVector() {
        return vector;
    }

    @Override
    public synchronized void run() {
        Random number = new Random();
        int i = CAPACITY;
        while (i > 0) {
            vector.add(number.nextInt(100));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
            }
            i--;
        }
    } // end run

} //end NumberHolder

class TestNumberHolder1 {

    public static void printGeneratedNumbers(ArrayList<Integer> thisVector) {
        // enumerate the elements in the vector.
        System.out.println("\nElements in vector:");
        for (Integer i : thisVector) {
            System.out.print(i + " ");
        }
        System.out.println();
    } // end printGeneratedNumbers

    public static void main(String[] args) throws InterruptedException {
        NumberHolder1 holder = new NumberHolder1();
        Thread numberHolderThread = new Thread(holder);
        numberHolderThread.start();
        Thread.sleep(1000);
        numberHolderThread.stop();
        System.out.println("The thread was terminated using the deprecated Java stop() method");
        ArrayList<Integer> temp = holder.getVector();
        System.out.println("The vector has the following " + temp.size() + " values:");
        printGeneratedNumbers(temp);
    } // end main
} //end TestNumberHolder1

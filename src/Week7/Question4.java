package Week7;

/**
 * Created by Niall Hughes on 25/03/2017.
 */

import java.util.Random;

public class Question4
{
}

//Class UnSynchBankTest
class UnSynchBankTest {

    public static void main(String[] args) {
        Bank b = new Bank();
        for (int i = 0; i < Bank.NACCOUNTS; i++)
            new TransactionSource(b, i).start();
    }// end main

} // UnSynchBankTest


class Bank {

    public static final int INITIAL_BALANCE = 10000;
    public static final int NACCOUNTS = 10;
    private final long[] accounts;
    private int ntransacts;

    public Bank() {
        accounts = new long[NACCOUNTS];
        for (int i = 0; i < NACCOUNTS; i++) {
            accounts[i] = INITIAL_BALANCE;
        }
        ntransacts = 0;
        test();
    } // end constructor

    public synchronized void transfer(int from, int to, int amount) {
        while (accounts[from] < amount) {
            try {
                wait(); // this wait() replaces sleep()
            } catch (InterruptedException e) {
            }
        }
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % 5000 == 0)
            test();
        notify(); // this notify() is added to notify waiting threads
    }// end transfer


    private void test() {
        long sum = 0;
        for (int i = 0; i < NACCOUNTS; i++) {
            sum += accounts[i];
        }
        System.out.println("Transactions:" + ntransacts + " Sum: " + sum);
    } // end test

} // end Bank

//Class TransactionSource
class TransactionSource extends Thread {
    private final Bank bank;
    private final int from;
    private final Random rnd;

    public TransactionSource(Bank b, int i) {
        from = i;
        bank = b;
        rnd = new Random();
    } // end constructor

    @Override
    public void run() {
        while (true) {
            int to = rnd.nextInt(Bank.NACCOUNTS);
            if (to == from)
                to = (to + 1) % Bank.NACCOUNTS;
            int amount = rnd.nextInt(Bank.INITIAL_BALANCE / 4);
            bank.transfer(from, to, amount);
            try {
                sleep(1);
            } catch (InterruptedException e) {
            }
        } // end while
    } // end run

} // end TransactionSource


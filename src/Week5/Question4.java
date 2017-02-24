package Week5;

/**
 * Created by cgf13hun on 24/02/2017.
 */

import java.util.concurrent.*;

public class Question4 {
}

class HolbyHospital
{
    public static void main(String[] args)
    {
        TreatmentRoom treatmentBeds = new TreatmentRoom(4);

        Semaphore empty = new Semaphore (4);
        Semaphore full  = new Semaphore (0);
        AmbulancePorter ambulancePorter = new AmbulancePorter (empty, full,treatmentBeds);
        WardPorter wardPorter = new WardPorter (empty, full, treatmentBeds);

        ambulancePorter.start();
        wardPorter.start();
        System.out.println("Threads ambulancePorter and wardPorter have been commanded to start");
    }
} // end Holby Hospital

/* AmbulancePorter.java */
class AmbulancePorter extends Thread {
    private Semaphore pempty;
    private Semaphore pfull;
    private TreatmentRoom treatmentBeds;

    public AmbulancePorter(Semaphore empty, Semaphore full, TreatmentRoom beds) {
        pempty = empty;
        pfull = full;
        treatmentBeds = beds;
    }

    public void run() {
		/* Trying to put a patient into the room  */
        for (int round = 0; round < 8; round++) {
            System.out.println("Ambluance Porter trying to put patient:"+ round + " into treatment room");
            try {
                pempty.acquire(); // // Wait for room to be not full
            } catch (InterruptedException ie) {
                System.out.println("Ambluance Porter Interrupted");
            }
			/* putting patient */
            treatmentBeds.putPatient(round);
            CDS.idleQuietly(1000);
            pfull.release(); // Signal that a patient has arrived
            System.out.println("Ambluance Porter has put patient:" + round + " into treatment room");
            CDS.idleQuietly(2000);
        } // end for
        System.out.println("Ambluence Porter finished");
    } // end run
} // end Ambulance Porter

/* WardPorter.java */
class WardPorter extends Thread {
    private Semaphore pempty;
    private Semaphore pfull;
    private TreatmentRoom treatmentBeds;

    public WardPorter(Semaphore empty, Semaphore full, TreatmentRoom beds) {
        pempty = empty;
        pfull = full;
        treatmentBeds = beds;
    } // end constructor

    public void run() {
		/*  Trying to get patient out of room */
        for (int round = 0; round < 8; round++) {
            System.out.println("Ward Porter trying to get, patient:" + round + "  out of treatment room");
            try {
                pfull.acquire(); // // Wait for room to be not empty
            } catch (InterruptedException ie) {
                System.out.println("Ward Porter Interrupted");
            }
			/* Getting patient */
            int patient = treatmentBeds.getPatient();
            CDS.idleQuietly(500);
            pempty.release();
            System.out.println("Ward Porter got patient:" + patient + " from treatment room");
            CDS.idleQuietly(1000);
        }
        System.out.println("Ward Porter finished");
    }
} // end WardPorter


/* TreatmentRoom.java */
class TreatmentRoom {
    // a treatment room of size 4 with patient number values in locations (0..3)
    final int SIZE;

    int[] treatmentRoom;
    int putPtr = 0;
    int getPtr = 0;

    public TreatmentRoom(int bedNum) {
        SIZE = bedNum;
        treatmentRoom = new int[SIZE];
    }

    public void putPatient(int patientNum) {
        treatmentRoom[putPtr] = patientNum;
        putPtr = (putPtr + 1) % SIZE;
    } // end putPatient

    public int getPatient() {
        int value = treatmentRoom[getPtr];
        getPtr = (getPtr + 1) % SIZE;
        return value;
    } // end getPatient

} // end treatmentRoom


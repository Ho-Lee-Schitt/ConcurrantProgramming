package Week9;

//Interface to Product objects - shared between client and server
import java.rmi.Remote;
import java.rmi.RemoteException;

interface Product extends Remote {
    public String getDescription() throws RemoteException;
} // end Product

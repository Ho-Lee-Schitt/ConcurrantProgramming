package Week9;

/**
 * Created by cgf13hun on 31/03/2017.
 */

//This server program creates and registers two Product objects
//with the RMIRegistry under the names "MyToaster" and "MyMicrowave"
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class ProductServer {

    public static void main(String args[]) {

        try {
            // create object
            ProductImpl p1 = new ProductImpl("Blackwell Toaster");

            // export stub
            Product p1Stub = (Product) UnicastRemoteObject.exportObject(p1, 1200);

            // create object
            ProductImpl p2 = new ProductImpl("Belling Microwave");

            // export stub
            Product p2Stub = (Product) UnicastRemoteObject.exportObject(p2, 1200);

            // obtain handle to registry
            Registry registry = LocateRegistry.createRegistry(4000);

            // bind the name “MyToaster” to the object p1
            registry.bind("MyToaster", p1Stub);

            // bind the name “MyMicrowave” to the object p2
            registry.bind("MyMicrowave", p2Stub);

        } catch (Exception e) { }

        System.out.println("ProductServer started waiting ...");
    } // end main

} // end ProductServer

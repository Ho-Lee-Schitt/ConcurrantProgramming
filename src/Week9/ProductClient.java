package Week9;

/**
 * Created by cgf13hun on 31/03/2017.
 */

//This client program connects to the server and call the getDescription()
//in remote fashion on both objects.

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ProductClient {

    public static void main(String[] args) {

        String hostName = "localhost"; // default host name
        // assign host machine name to connect to
        if (args.length != 0) {
            if (args[0] != null)
                hostName = args[0]; // user specified machine
        }

        try {
            // first locate the registry
            Registry registry = LocateRegistry.getRegistry(4000);

            // now locate the stub for each product object
            Product c1Stub = (Product) registry.lookup("MyToaster");
            Product c2Stub = (Product) registry.lookup("MyMicrowave");

            // call the getDescription() method on both objects
            System.out.println(c1Stub.getDescription());
            System.out.println(c2Stub.getDescription());
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        System.exit(0);
    } // end main

} // end ProductClient

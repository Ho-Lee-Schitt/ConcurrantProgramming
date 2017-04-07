package Week10;

/**
 * Created by cgf13hun on 07/04/2017.
 */

import java.io.IOException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Request;
import java.util.Scanner;

public class PrimeClientHttp {

    private static final String BASE_URI = "http://localhost:8080/prime";

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        String numberString;
        do {
            System.out.print("Enter Positive Integer?");
            numberString = userInput.nextLine();
        } while (!numberString.matches("^\\d+"));
        int number = Integer.parseInt(numberString);

        String queryExpression = "Number=" + number;

        try {
            String result = Request.Get(BASE_URI + "?" + queryExpression).execute().returnContent().asString();
            System.out.println("Result = " + result);
        } catch (HttpResponseException e) {
            System.out.println("Error: " + e.getStatusCode());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

package Week10;

/**
 * Created by cgf13hun on 07/04/2017.
 */

// registered handler class for named context
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

public class PrimeContextHandler implements HttpHandler {

    // static logger
    public static final Logger LOG = Logger.getLogger(PrimeContextHandler.class.getName());

    @Override
    public void handle(HttpExchange request) throws IOException {
        LOG.info(request.getRequestMethod() + " " + request.getRequestURI().toString());
        //set to text/html for machine to machine communication
        request.getResponseHeaders().set("Content-Type", "text/html");

        String response = "";
        // handle request type
        if (request.getRequestMethod().equalsIgnoreCase("GET")) {
            response = handleGET(request);
            if (response.equals("")) {
                request.sendResponseHeaders(404, 0); // 404 bad request
            } else {
                request.sendResponseHeaders(200, 0); // 200 Ok
            }
        } else {
            request.sendResponseHeaders(501, 0); //  501 - not implemented
        }
        // write response and close
        request.getResponseBody().write(response.getBytes());
        request.getResponseBody().close();

    }

    // handle a HTTP GET request
    public static String handleGET(HttpExchange request) throws IOException, NumberFormatException {
        Map<String, String> parms = getQueryParameters(request);

        String number;
        number = parms.getOrDefault("Number", "");

        if (!number.equals("")) {
            // call isPrimeNumber function
            Boolean isPrime = isPrimeNumber(Integer.parseInt(number));
            return String.valueOf(isPrime);
        }
        return "";
    }

    // parse request query parameters into a Map
    public static Map<String, String> getQueryParameters(HttpExchange request) {
        Map<String, String> result = new HashMap<>();
        String query = request.getRequestURI().getQuery();
        if (query != null) {
            for (String param : query.split("&")) {
                String pair[] = param.split("=");
                if (pair.length > 1) {
                    result.put(pair[0], pair[1]);
                } else {
                    result.put(pair[0], "");
                }
            }
        }
        return result;
    } // end getQueryParameters

    // utility method to check if a number is prime
    private static boolean isPrimeNumber(int number) {
        if (number == 2 || number == 3) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }
        int sqrt = (int) Math.sqrt(number) + 1;
        for (int i = 3; i < sqrt; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    } // end isPrimeNumber

}// class PrimeContextHandler

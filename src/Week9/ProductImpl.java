package Week9;

/**
 * Created by cgf13hun on 31/03/2017.
 */


// This server implementation class carries out the methods advertised in the
// Product remote interface
public class ProductImpl implements Product {
    private String descr;

    // default constructor
    public ProductImpl() {
        super();
    } // end constructor

    // class constructor
    public ProductImpl(String d)  {
        descr = d;
    } // end constructor

    public String getDescription() {
        return "I am a " + descr + ". Buy me!";
    } // end setDescription

} // end ProductImpl


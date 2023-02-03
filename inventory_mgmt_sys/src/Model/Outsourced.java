package Model;

/**
 * Outsourced inherits from Part(superclass).
 *
 * @author Bradley Harper
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * Class constructor
     * Call superclass Part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {

        super(id, name, price, stock, min, max);

        this.companyName = companyName;
    }


    /**
     * @return the machineId
     */
    public String getCompanyName() {

        return companyName;
    }


    /**
     * @param companyName
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;
    }
}

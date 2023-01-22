package model;

/**
 * Model for outsourced parts
 */
public class Outsource extends Part {
    private String companyName;

    /**
     * Constructor for a new outsourced part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsource(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * getter for company name
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * setter for company name
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

package model;

/**
 * InHouse part model
 */
public class InHouse extends Part {
    private int MachineID;

    /**
     * Constructor for new InHouse part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param MachineID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int MachineID) {
        super(id, name, price, stock, min, max);
        this.MachineID = MachineID;
    }

    /**
     * getter machine ID
     * @return
     */
    public int getMachineID() {
        return MachineID;
    }
    /**
     * setter machine ID
     * @param MachineID
     */
    public void setMachineID(int MachineID) {
        this.MachineID = MachineID;
    }
}

package Model;

/**
 * Inhouse inherits from Part(superclass).
 *
 * @author Bradley Harper
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * Class constructor.
     * Calls superclass Part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {

        super(id, name, price, stock, min, max);

        this.machineId = machineId;
    }

    /**
     * @return the machineId.
     */
    public int getMachineId() {

        return machineId;
    }

    /**
     * @param machineId
     */
    public void setMachineId(int machineId) {

        this.machineId = machineId;
    }
}

package Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Bradley Harper
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    /**
     * Class constructor.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Overload Product constructor, creates an object for associating parts.
     */
    public Product() {

        this(0, null, 0, 0, 0, 0);
    }


    /**
     * @return the id
     */
    public int getId() {

        return id;
    }


    /**
     * @param id the id to set
     */
    public void setId(int id) {

        this.id = id;
    }


    /**
     * @return the name
     */
    public String getName() {

        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {

        this.name = name;
    }


    /**
     * @return the price
     */
    public double getPrice() {

        return price;
    }


    /**
     * @param price the price to set
     */
    public void setPrice(double price) {

        this.price = price;
    }


    /**
     * @return the stock
     */
    public int getStock() {

        return stock;
    }


    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {

        this.stock = stock;
    }


    /**
     * @return the min
     */
    public int getMin() {

        return min;
    }


    /**
     * @param min the min to set
     */
    public void setMin(int min) {

        this.min = min;
    }


    /**
     * @return the max
     */
    public int getMax() {

        return max;
    }


    /**
     * @param max the max to set
     */
    public void setMax(int max) {

        this.max = max;
    }


    /**
     * Adds associated part.
     *
     * @param part
     */
    public void addAssociatedPart(Part part) {

        associatedParts.add(part);
    }


    /**
     * Creates the Part Observable List associatedParts.
     *
     * @param associatedParts
     */
    public Product(ObservableList<Part> associatedParts) {

        this.associatedParts = associatedParts;
    }


    /**
     * Deletes associated part.
     *
     * @param selectedAssociatedPart
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } else
            return false;
    }

    /**
     * Creates the Part Observable List for adding ALL parts when using Modify Part Form
     * on Initialization and saving
     *
     * @param part
     */
    public void addAssociatedPart(ObservableList<Part> part) {

        this.associatedParts.addAll(part);
    }


    /**
     * @return the associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }
}


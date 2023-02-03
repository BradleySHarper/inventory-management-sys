package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.Random;


/**
 * Inventory class contains methods for manipulating data in application.
 *
 * @author Bradley Harper
 */
public class Inventory {

    /**
     * Creates Observable List Part and Product objects for holding all parts and products respectively.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * Adds parts to allParts Observable List.
     *
     * @param newPart
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }


    /**
     * Adds products to allProducts Observable List.
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }


    /**
     * For each loop, used in searching.
     * Finds matches to the id.
     *
     * @param id
     * @return the part
     */
    public static Part lookupPart(int id) {

        ObservableList<Part> allParts = getAllParts();

        for (Part part : allParts) {
            if (part.getId() == id) {
                return part;
            }
        }

        return null;
    }


    /**
     * For each loop, used in searching.
     * Finds matches to the id.
     *
     * @param id
     * @return the product
     */
    public static Product lookupProduct(int id) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product product : allProducts) {
            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }


    /**
     * Observable list used in searching.
     * First searches for name matches using partNameSearch method.
     * Then finds matches to id using lookupPart method.
     *
     * @param search
     * @return the parts
     */
    public static ObservableList<Part> lookupPart(String search) {

        ObservableList<Part> parts = partNameSearch(search);

        if (parts.isEmpty()) {
            try {
                int id = Integer.parseInt(search);
                Part part = lookupPart(id);
                if (part != null)
                    parts.add(part);
            } catch (NumberFormatException e) {
            }
        }

        if (parts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Part not found!");
            alert.showAndWait();
        }

        return parts;
    }


    /**
     * Observable list used in searching.
     * First searches for name matches using prodNameSearch method.
     * Then finds matches to id using lookupProduct method.
     *
     * @param search
     * @return the products
     */
    public static ObservableList<Product> lookupProduct(String search) {

        ObservableList<Product> products = prodNameSearch(search);

        if (products.isEmpty()) {
            try {
                int id = Integer.parseInt(search);
                Product product = lookupProduct(id);
                if (product != null)
                    products.add(product);
            } catch (NumberFormatException e) {
            }
        }

        if (products.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Product not found!");
            alert.showAndWait();
        }

        return products;
    }


    /**
     * Updates selected part.
     * Used by modify Part save handler.
     *
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }


    /**
     * Updates selected product.
     * Used by modify Product save handler.
     *
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct) {

        allProducts.set(index, newProduct);
    }


    /**
     * Deletes selected part.
     *
     * @param selectedPart
     * @return boolean status of selected part removal
     */
    public static boolean deletePart(Part selectedPart) {

        return allParts.remove(selectedPart);
    }


    /**
     * Deletes associated product.
     *
     * @param selectedProduct
     * @return boolean status of selected product removal
     */
    public static boolean deleteProduct(Product selectedProduct) {

        return allProducts.remove(selectedProduct);
    }


    /**
     * @return allParts all parts in Part list
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }


    /**
     * @return allProducts all products in Product list
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }


    /**
     * Searches for part name.
     * Observable list nameMatches holds the matched names.
     * Observable list allParts temp list to loop through to find matches.
     *
     * @param partialName name used in search
     * @return nameMatches search matches
     */
    public static ObservableList<Part> partNameSearch(String partialName) {

        ObservableList<Part> nameMatches = FXCollections.observableArrayList();

        ObservableList<Part> allParts = getAllParts();

        for (Part p1 : allParts) {
            if (p1.getName().contains(partialName)) {
                nameMatches.add(p1);
            }
        }

        return nameMatches;
    }


    /**
     * Searches for product name.
     * Observable list namedProducts holds the matched names.
     * Observable list allProducts temp list to loop through to find matches.
     *
     * @param partialName name used in search
     * @return nameMatches search matches
     */
    public static ObservableList<Product> prodNameSearch(String partialName) {

        ObservableList<Product> nameMatches = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (Product p1 : allProducts) {
            if (p1.getName().contains(partialName)) {
                nameMatches.add(p1);
            }
        }

        return nameMatches;

    }


    /**
     * Method for creating unique ID.
     *
     * @return rand.nextInt(1000)
     */
    public static int partUniqueId() {

        Random rand = new Random();

        return rand.nextInt(1000);

    }

    /**
     * Static method for adding test data.
     */
    static {
        addTestData();
    }


    /**
     * Method for adding specific data for testing.
     */
    public static void addTestData() {

        InHouse part1 = new InHouse(100, "wheel", 9.99, 10, 1, 10, 545);
        Outsourced part2 = new Outsourced(200, "handle", 5.99, 20, 1, 50, "BikesRUs");
        InHouse part3 = new InHouse(300, "seat", 15.99, 15, 1, 20, 223);
        Outsourced part4 = new Outsourced(400, "tire", 5.99, 26, 1, 100, "BikesRUs");
        Product product5 = new Product(500, "bike", 299.99, 4, 1, 10);
        Product product6 = new Product(600, "skateboard", 99.99, 8, 1, 10);
        Product product7 = new Product(700, "scooter", 199.99, 20, 1, 30);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addProduct(product5);
        Inventory.addProduct(product6);
        Inventory.addProduct(product7);
    }


}

package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Model.Inventory.*;


/**
 * Controller controls the Main Part window.
 *
 * @author Bradley Harper
 */
public class MainFormController implements Initializable {

    Stage stage;
    Parent scene;


    /**
     * Declaring variable for the index of the part selected.
     */
    public static int modPartIndex;


    /**
     * Gets the part variable for the index of the product selected.
     *
     * @return modPartIndex an object or null.
     */
    public static int partToModIndex() {

        return modPartIndex;
    }


    /**
     * Declaring variable for the index of the product selected.
     */
    private static int modProdIndex;


    /**
     * Gets the product object selected by the user.
     *
     * @return modProdIndex an object or null.
     */
    public static int prodToModIndex() {

        return modProdIndex;
    }


    /**
     * ID column in the Main Parts table.
     */
    @FXML
    private TableColumn<Part, Integer> mainPartIdCol;


    /**
     * Inventory column in the Main Parts table.
     */
    @FXML
    private TableColumn<Part, Integer> mainPartInvLvlCol;


    /**
     * Name column in the Main Parts table.
     */
    @FXML
    private TableColumn<Part, String> mainPartNameCol;


    /**
     * Price column in the Main Parts table.
     */
    @FXML
    private TableColumn<Part, Double> mainPartCPUCol;


    /**
     * Text field for the Main Parts table search.
     */
    @FXML
    private TextField mainPartsSearchTxt;


    /**
     * Main Parts table.
     */
    @FXML
    private TableView<Part> mainPartsTableView;


    /**
     * Main Products table.
     */
    @FXML
    private TableView<Product> mainProdTableView;


    /**
     * Price column for the Products table.
     */
    @FXML
    private TableColumn<Product, Double> mainProductCPUCol;


    /**
     * ID column for the Products table.
     */
    @FXML
    private TableColumn<Product, Integer> mainProductIdCol;


    /**
     * Inventory column for Products table.
     */
    @FXML
    private TableColumn<Product, Integer> mainProductInvLvlCol;


    /**
     * Name column for Products table.
     */
    @FXML
    private TableColumn<Product, String> mainProductNameCol;


    /**
     * Text field for the Products table search.
     */
    @FXML
    private TextField mainProductsSearchTxt;


    /**
     * Associated with the Add Part button.
     * Opens the Add Part window.
     *
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * Associated with the Add Part button.
     * Opens the Add Part window.
     *
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * Associated with the Delete Part button.
     * Deletes selected item.
     * Includes exception handler if no part selected.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {

        if (!mainPartsTableView.getSelectionModel().isEmpty()) {

            if (confirm("Confirm Delete?"))
                Inventory.deletePart(mainPartsTableView.getSelectionModel().getSelectedItem());

        } else {
            notification("INPUT ERROR", "No part selected!", "Please select a part to be deleted");

        }

    }


    /**
     * Associated with the Delete Product button.
     * Deletes selected item.
     * Includes exception handler if no product selected.
     * Includes exception handler if product has parts associated with it.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {

        Product selectedProduct = mainProdTableView.getSelectionModel().getSelectedItem();

        if (!mainProdTableView.getSelectionModel().isEmpty()) {

            ObservableList<Part> assocParts = selectedProduct.getAllAssociatedParts();

            if (assocParts.size() >= 1) {
                notification("INPUT ERROR", "Product has Associated Parts!", "Cannot delete a Product with associated parts");

            } else if
            ((confirm("Confirm Delete?"))) {

                Inventory.deleteProduct(selectedProduct);
            }
        } else {
            notification("INPUT ERROR", "No Product Selected!", "Please select a product to be deleted");
        }

    }


    /**
     * Associated with Modify Part button.
     * Includes exception handler if no part selected.
     * Gets the part's index.
     * Opens Modify Part window.
     *
     * @throws IOException
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        Part modPart = mainPartsTableView.getSelectionModel().getSelectedItem();

        if (modPart == null) {
            notification("INPUT ERROR", "No part selected!", "Please select a part to be modified");

        } else {

            modPartIndex = getAllParts().indexOf(modPart);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyPartForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }


    /**
     * Associated with Modify Product button.
     * Includes exception handler if no product selected.
     * Gets the product's index.
     * Opens Modify Product window.
     *
     * @throws IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        Product modProduct = mainProdTableView.getSelectionModel().getSelectedItem();

        if (modProduct == null) {
            notification("INPUT ERROR", "No product selected!", "Please select a product to be modified");

        } else {

            modProdIndex = getAllProducts().indexOf(modProduct);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }


    }

    /**
     * Exits program.
     */
    @FXML
    void onActionExit(ActionEvent event) {

        System.exit(0);

    }


    /**
     * Associated with the part search text field.
     * Declares a String variable and passes in it the text entered.
     * Sets the items to parts table using the lookupPart method.
     */
    @FXML
    void getResultsHandler(ActionEvent event) {

        String s = mainPartsSearchTxt.getText();

        mainPartsTableView.setItems(Inventory.lookupPart(s));

    }


    /**
     * Associated with the product search text field.
     * Declares a String variable and passes in it the text entered.
     * Sets the items to parts table using the lookupProduct method.
     */
    @FXML
    void productGetResultHandler(ActionEvent event) {

        String s = mainProductsSearchTxt.getText();

        mainProdTableView.setItems(Inventory.lookupProduct(s));

    }

    /**
     * Exception handler for information.
     */
    public static void notification(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    /**
     * Exception handler for confirmation.
     */
    static boolean confirm(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }


    /**
     * Initializes Main Controller.
     * Sets items in both Parts and Products tables using getAll methods.
     * Sets up both table's column labels.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainProdTableView.setItems(Inventory.getAllProducts());

        mainPartsTableView.setItems(getAllParts());

        mainPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        mainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        mainPartCPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        mainProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        mainProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        mainProductCPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }
}


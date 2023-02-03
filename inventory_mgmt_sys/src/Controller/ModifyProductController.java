package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Controller.MainFormController.*;
import static Controller.MainFormController.notification;
import static Model.Inventory.*;


/**
 * Controller Class controls the Modify Product window.
 *
 * @author Bradley Harper
 */
public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * Declaring variable for the ID.
     */
    private int prodId;

    /**
     * Declaring variable for the index of the part selected.
     */
    int prodIndex = prodToModIndex();

    /**
     * Observable list for holding updated associated parts.
     */
    private ObservableList upParts = FXCollections.observableArrayList();

    /**
     * Observable list for associated parts.
     */
    private static ObservableList<Part> assocPart;


    /**
     * Associated parts table column Price.
     */
    @FXML
    private TableColumn<Part, Double> assocCPUCol;


    /**
     * Associated parts table column Inventory.
     */
    @FXML
    private TableColumn<Part, Integer> assocInvLvlCol;


    /**
     * Associated parts table column ID.
     */
    @FXML
    private TableColumn<Part, Integer> assocPartIdCol;


    /**
     * Associated parts table column Name.
     */
    @FXML
    private TableColumn<Part, String> assocPartNameCol;


    /**
     * Main parts table column Price.
     */
    @FXML
    private TableColumn<Part, Double> mainPartCPUCol;


    /**
     * Main parts table column ID.
     */
    @FXML
    private TableColumn<Part, Integer> mainPartIdCol;


    /**
     * Main parts table column inventory level.
     */
    @FXML
    private TableColumn<Part, Integer> mainPartInvLvlCol;


    /**
     * Main parts table column name.
     */
    @FXML
    private TableColumn<Part, String> mainPartNameCol;


    /**
     * Main parts table.
     */
    @FXML
    private TableView<Part> mainPartsTableView;


    /**
     * Associated parts table.
     */
    @FXML
    private TableView<Part> assocPartsTableView;


    /**
     * Text field for product ID.
     */
    @FXML
    private TextField modProductIdTxt;


    /**
     * Text field for product inventory level.
     */
    @FXML
    private TextField modProductInvTxt;


    /**
     * Text field for product max.
     */
    @FXML
    private TextField modProductMaxTxt;


    /**
     * Text field for product min.
     */
    @FXML
    private TextField modProductMinTxt;


    /**
     * Text field for product name.
     */
    @FXML
    private TextField modProductNameTxt;


    /**
     * Text field for product price.
     */
    @FXML
    private TextField modProductPriceTxt;


    /**
     * Text field for search field.
     */
    @FXML
    private TextField modProductSearchTxt;


    /**
     * Associated with the Add Part button.
     * Assigns selected item to Part object.
     * Adds selected part to list holding associated parts.
     * Resets main Parts table to all parts.
     * Exception handler if no Part selected.
     */
    @FXML
    void onActionAddPartToProduct(ActionEvent event) {

        Part selectedPart = mainPartsTableView.getSelectionModel().getSelectedItem();

        if (!(selectedPart == null)) {
            upParts.add(selectedPart);
            mainPartsTableView.setItems(Inventory.getAllParts());
            assocPartsTableView.setItems(upParts);
        } else {
            notification("Input Error", "No Part Selected!", "Please select a part to add. ");
        }

    }


    /**
     * Associated with the cancel button.
     * Clears the assoc parts table.
     * Returns to the main window.
     *
     * @throws IOException
     */
    @FXML
    void onActionDisplayMain(ActionEvent event) throws IOException {
        upParts.clear();

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * Associated with the Remove associated part button.
     * Removes selected item from associated parts list.
     * Includes exception handler if no part selected.
     */
    @FXML
    void onActionRemoveAssocPart(ActionEvent event) {

        Part selectedPart = assocPartsTableView.getSelectionModel().getSelectedItem();

        if (!assocPartsTableView.getSelectionModel().isEmpty()) {
            if (confirm("Confirm Delete?"))
                upParts.remove(selectedPart);

        } else {
            notification("INPUT ERROR", "No Part Selected!", "Please select a Part to be deleted!");
        }

    }


    /**
     * Associated with the save button.
     * Declares variables used in exception handling.
     * Exception handling for all exceptions.
     * Adds a new product to the Inventory.
     * Adds any added Parts to the product.
     * Returns to the main window.
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) {
        try {
            String partName = modProductNameTxt.getText();
            String partPrice = modProductPriceTxt.getText();
            int partInventory = Integer.parseInt(modProductInvTxt.getText());
            int partMin = Integer.parseInt(modProductMinTxt.getText());
            int partMax = Integer.parseInt(modProductMaxTxt.getText());


            if (partName.isEmpty()) {
                MainFormController.notification("Input Error", "Unable to Update Product!", "Please complete all text fields");

            } else if (partMax < partMin || partMin < 1) {
                MainFormController.notification("Input Error", "Unable to Update Product!", "Min must be less than Max!  Min must be equal to or greater than one!");
            } else if (partInventory < partMin || partInventory > partMax) {
                MainFormController.notification("Input Error", "Unable to Update Product!", "Inventory must be between Minimum and Maximum");
            } else if (!partPrice.matches(".*[0-9].*")) {
                MainFormController.notification("Input Error", "Unable to Update Product!", "Price must be a dollar amount!");

            } else {
                Product product = new Product();
                product.setId(Integer.parseInt(modProductIdTxt.getText()));
                product.setName(modProductNameTxt.getText());
                product.setStock(Integer.parseInt(modProductInvTxt.getText()));
                product.setPrice(Double.parseDouble(modProductPriceTxt.getText()));
                product.setMax(Integer.parseInt(modProductMaxTxt.getText()));
                product.setMin(Integer.parseInt(modProductMinTxt.getText()));
                product.addAssociatedPart(upParts);
                Inventory.updateProduct(prodIndex, product);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();


            }


        } catch (Exception e) {
            MainFormController.notification("Input Error", "Unable to Update Product!", "Inventory, Min, and Max cannot be blank, and must only contain numbers (0-9)");
        }

    }


    /**
     * Associated with the main parts search text field.
     * Declares a String variable and passes in it the text entered.
     * Sets the items to parts table using the lookupPart method.
     */
    @FXML
    void getResultsHandler(ActionEvent event) {

        String s = modProductSearchTxt.getText();
        mainPartsTableView.setItems(lookupPart(s));

    }


    /**
     * Initializes Controller.
     * Populates main parts table.
     * Sets column labels.
     * Sets variable id to a unique number.
     * Sets text for ID field to that unique number.
     * Creates Product object and populates all part fields using the part index of selected item.
     * Gets all associated parts from that product and populates them on the associated parts table.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainPartsTableView.setItems(Inventory.getAllParts());

        mainPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        mainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        mainPartCPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainPartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        assocCPUCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        Product product = getAllProducts().get(prodIndex);
        prodId = getAllProducts().get(prodIndex).getId();
        modProductIdTxt.setText(String.valueOf(prodId));

        modProductNameTxt.setText(product.getName());
        modProductInvTxt.setText(String.valueOf(product.getStock()));
        modProductPriceTxt.setText(String.valueOf(product.getPrice()));
        modProductMinTxt.setText(String.valueOf(product.getMin()));
        modProductMaxTxt.setText(String.valueOf(product.getMax()));
        upParts = product.getAllAssociatedParts();

        assocPartsTableView.setItems(upParts);


    }
}

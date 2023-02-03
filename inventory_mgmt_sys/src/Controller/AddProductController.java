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

import static Model.Inventory.partUniqueId;
import static Controller.MainFormController.*;
import static Model.Inventory.lookupPart;


/**
 * Controller Class controls the Add Product window.
 *
 * @author Bradley Harper
 */
public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;


    /**
     * Text field product ID.
     */
    @FXML
    private TextField addProductIdTxt;


    /**
     * Text field product Inventory.
     */
    @FXML
    private TextField addProductInvTxt;


    /**
     * Text field product Max.
     */
    @FXML
    private TextField addProductMaxTxt;


    /**
     * Text field product Min.
     */
    @FXML
    private TextField addProductMinTxt;


    /**
     * Text field product Name.
     */
    @FXML
    private TextField addProductNameTxt;


    /**
     * Text field product Price.
     */
    @FXML
    private TextField addProductPriceTxt;


    /**
     * Text field product Search.
     */
    @FXML
    private TextField addProductSearchTxt;


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
     * Associated parts table.
     */
    @FXML
    private TableView<Part> assocPartsTableView;


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
     * Main parts table column Inventory.
     */
    @FXML
    private TableColumn<Part, Integer> mainPartInvLvlCol;


    /**
     * Main parts table column Name.
     */
    @FXML
    private TableColumn<Part, String> mainPartNameCol;


    /**
     * Main parts table.
     */
    @FXML
    private TableView<Part> mainPartsTableView;


    /**
     * List for adding associated parts.
     */
    private ObservableList<Part> newParts = FXCollections.observableArrayList();


    /**
     * Variable for ID creation.
     */
    private int id;


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
            newParts.add(selectedPart);
            mainPartsTableView.setItems(Inventory.getAllParts());
            assocPartsTableView.setItems(newParts);

        } else {
            notification("Input Error", "No Part Selected!", "Please select a part to add");
        }

    }


    /**
     * Associated with the cancel button.
     * Returns to the main window.
     *
     * @throws IOException
     */
    @FXML
    void onActionDisplayMain(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * Associated with the main parts search text field.
     * Declares a String variable and passes in it the text entered.
     * Sets the items to parts table using the lookupPart method.
     */
    @FXML
    void getResultsHandler(ActionEvent event) {
        String s = addProductSearchTxt.getText();
        mainPartsTableView.setItems(lookupPart(s));

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
                newParts.remove(selectedPart);

        } else {
            notification("INPUT ERROR", "No Part Selected!", "Please select a Part to be removed");
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

            String prodName = addProductNameTxt.getText();
            String prodPrice = addProductPriceTxt.getText();
            int prodInventory = Integer.parseInt(addProductInvTxt.getText());
            int prodMin = Integer.parseInt(addProductMinTxt.getText());
            int prodMax = Integer.parseInt(addProductMaxTxt.getText());

            if (prodName.isEmpty()) {
                MainFormController.notification("Input Error", "Unable to Save Product!", "Please complete all text fields");

            } else if (prodMax < prodMin || prodMin < 1) {
                MainFormController.notification("Input Error", "Unable to Save Product!", "Min must be less than Max, Min must be equal to or greater than one");

            } else if (prodInventory < prodMin || prodInventory > prodMax) {
                MainFormController.notification("Input Error", "Unable to Save Product!", "Inventory must be between Minimum and Maximum");

            } else if (!prodPrice.matches(".*[0-9].*")) {
                MainFormController.notification("Input Error", "Unable to Save Product!", "Price must be a dollar amount!");

            } else {

                Product product = new Product();
                Inventory.partUniqueId();
                product.setId(id);
                product.setName(addProductNameTxt.getText());
                product.setStock(Integer.parseInt(addProductInvTxt.getText()));
                product.setPrice(Double.parseDouble(addProductPriceTxt.getText()));
                product.setMax(Integer.parseInt(addProductMaxTxt.getText()));
                product.setMin(Integer.parseInt(addProductMinTxt.getText()));

                for (Part part : newParts) {
                    product.addAssociatedPart(part);
                }

                Inventory.addProduct(product);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }

        } catch (Exception e) {
            MainFormController.notification("Input Error", "Unable to Save Product!", "Inventory, Min, and Max cannot be blank, and must only contain numbers (0-9)");
        }

    }


    /**
     * Initializes Controller.
     * Populates main parts table.
     * Sets column labels.
     * Sets variable id to a unique number.
     * Sets text for ID field to that unique number.
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


        id = partUniqueId();

        addProductIdTxt.setText("Auto Gen: " + Inventory.partUniqueId());


    }
}



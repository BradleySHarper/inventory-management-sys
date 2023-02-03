package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.Inventory.partUniqueId;


/**
 * Controller Class controls the Add Part window.
 *
 * @author Bradley Harper
 */
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;
    private int id;

    /**
     * Text field part price.
     */
    @FXML
    private TextField addPartCPUTxt;

    /**
     * Text field part company name or machine Id.
     */
    @FXML
    private TextField addPartCoNameTxt;

    /**
     * Text field part ID.
     */
    @FXML
    private TextField addPartIdTxt;

    /**
     * Text field part inventory.
     */
    @FXML
    private TextField addPartInvTxt;

    /**
     * Text field part max.
     */
    @FXML
    private TextField addPartMaxTxt;

    /**
     * Text field part min.
     */
    @FXML
    private TextField addPartMinTxt;

    /**
     * Text field part name.
     */
    @FXML
    private TextField addPartNameTxt;

    /**
     * Label for part company name or machine ID.
     * Switches dependent on radio button selection.
     */
    @FXML
    private Label addPartSwitchLbl;

    /**
     * Radio button for In house.
     */
    @FXML
    private RadioButton inHouseRBtn;

    /**
     * Radio button for Outsourced.
     */
    @FXML
    private RadioButton outsourcedRBtn;

    /**
     * Associated with the In House radio button.
     * Switches the label to Machine ID.
     */
    @FXML
    void onActionInHouse(ActionEvent event) {

        addPartSwitchLbl.setText("Machine ID");
    }

    /**
     * Associated with the Outsourced radio button.
     * Switches the label to Company Name.
     */
    @FXML
    void onActionOutsourced(ActionEvent event) {

        addPartSwitchLbl.setText("Company Name");
    }

    /**
     * Associated with the cancel button.
     * Returns to the main window.
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
     * Associated with the save button.
     * Adds a new part to the Inventory.
     * Creates either InHouse or Outsourced object.
     * Returns to the main window.
     */
    @FXML
    void onActionSavePart(ActionEvent event) {

        try {
            String partName = addPartNameTxt.getText();
            String partPrice = addPartCPUTxt.getText();
            String partCompanyName = addPartCoNameTxt.getText();

            int partInventory = Integer.parseInt(addPartInvTxt.getText());
            int partMin = Integer.parseInt(addPartMinTxt.getText());
            int partMax = Integer.parseInt(addPartMaxTxt.getText());

            if (partName.isEmpty()) {
                MainFormController.notification("Input Error", "Unable to Save Part!", "Part name is empty!");
            } else if (partMax < partMin || partMin < 1) {
                MainFormController.notification("Input Error", "Unable to Save Part!", "Min must be less than Max!  Min must be equal to or greater than 1!");
            } else if (partInventory < partMin || partInventory > partMax) {
                MainFormController.notification("Input Error", "Unable to Save Part!", "Inventory must be between Minimum and Maximum");
            } else if (partCompanyName.matches(".*[0-9].*") && outsourcedRBtn.isSelected()) {
                MainFormController.notification("Input Error", "Unable to Save Part!", "Company Name must contain characters A-Z");
            } else if ((!partCompanyName.matches(".*[0-9].*") && inHouseRBtn.isSelected())) {
                MainFormController.notification("Input Error", "Unable to Save Part!", "MachineID cannot be blank and must contain numbers only");
            } else if (!partPrice.matches(".*[0-9].*")) {
                MainFormController.notification("Input Error", "Unable to Save Part!", "Price must be a dollar amount!");
            } else if (partCompanyName.isEmpty() && outsourcedRBtn.isSelected()) {
                MainFormController.notification("Input Error", "Unable to Save Part!", "Company Name cannot be blank!");
            } else {

                if (inHouseRBtn.isSelected()) {

                    partUniqueId();
                    String name = addPartNameTxt.getText();
                    double price = Double.parseDouble(addPartCPUTxt.getText());
                    int stock = Integer.parseInt(addPartInvTxt.getText());
                    int max = Integer.parseInt(addPartMaxTxt.getText());
                    int min = Integer.parseInt(addPartMinTxt.getText());
                    int machineId = Integer.parseInt(addPartCoNameTxt.getText());

                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));

                }

                if (outsourcedRBtn.isSelected()) {

                    partUniqueId();
                    String name = addPartNameTxt.getText();
                    double price = Double.parseDouble(addPartCPUTxt.getText());
                    int stock = Integer.parseInt(addPartInvTxt.getText());
                    int max = Integer.parseInt(addPartMaxTxt.getText());
                    int min = Integer.parseInt(addPartMinTxt.getText());
                    String companyName = addPartCoNameTxt.getText();

                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                }

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        } catch (Exception e) {

            MainFormController.notification("Input Error", "Unable to Save Part!", "Inventory, Min, and Max cannot be blank, and must only contain numbers (0-9)");
        }


    }


    /**
     * RUNTIME ERROR during initialization.
     * The error occurred when the program attempted to set text for the ID text field.
     * The fx id for the ID text field was spelled differently in the AddPart Controller compared to
     * the fxml add part form (AddPartIdTxt instead of addPartIdText).
     * The program still ran because the code was consistent throughout the AddPartController.
     * After correcting the discrepancy the program worked as expected.
     * </p>
     * Initializes Controller.
     * Sets variable id to a unique number.
     * Sets text for ID field to that unique number.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        id = Inventory.partUniqueId();

        addPartIdTxt.setText("Auto Gen: " + id);


    }
}

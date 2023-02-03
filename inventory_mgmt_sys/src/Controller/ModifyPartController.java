package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Controller.MainFormController.partToModIndex;
import static Model.Inventory.getAllParts;


/**
 * Controller Class controls the Modify Part window.
 *
 * @author Bradley Harper
 */
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

    /**
     * Declaring variable for the index of the part selected.
     */
    int partIndex = partToModIndex();

    /**
     * Declaring variable for the ID.
     */
    private int partId;


    /**
     * Radio button for In House.
     */
    @FXML
    private RadioButton inHouseRBtn;


    /**
     * Text field for part price.
     */
    @FXML
    private TextField modPartCPUTxt;


    /**
     * Text field for part company name or machine id.
     */
    @FXML
    private TextField modPartCoNameTxt;


    /**
     * Text field for part ID.
     */
    @FXML
    private TextField modPartIdTxt;


    /**
     * Text field for part inventory.
     */
    @FXML
    private TextField modPartInvTxt;


    /**
     * Text field for part max.
     */
    @FXML
    private TextField modPartMaxTxt;


    /**
     * Text field for part min.
     */
    @FXML
    private TextField modPartMinTxt;


    /**
     * Text field for part name.
     */
    @FXML
    private TextField modPartNameTxt;


    /**
     * Label for part company name or machine ID.
     * Switches based on which radio button selected.
     */
    @FXML
    private Label modPartSwitchLbl;


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

        modPartSwitchLbl.setText("Machine ID");

    }


    /**
     * Associated with the Outsourced radio button.
     * Switches the label to Company Name.
     */
    @FXML
    void onActionOutsourced(ActionEvent event) {

        modPartSwitchLbl.setText("Company Name");
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
     * Associated with the save button.
     * Declares variables used in exception handling.
     * Exception handling for all exceptions.
     * Adds a new part to the Part Inventory.
     * Creates either InHouse or Outsourced object.
     * Returns to the main window.
     */
    @FXML
    void onActionSavePart(ActionEvent event) {

        try {
            String partName = modPartNameTxt.getText();
            String partPrice = modPartCPUTxt.getText();
            String partCompanyName = modPartCoNameTxt.getText();
            int partInventory = Integer.parseInt(modPartInvTxt.getText());
            int partMin = Integer.parseInt(modPartMinTxt.getText());
            int partMax = Integer.parseInt(modPartMaxTxt.getText());

            if (partName.isEmpty()) {
                MainFormController.notification("Input Error", "Unable to Save Part!", "Please complete all text fields");

            } else if (partMax < partMin || partMin < 1) {
                MainFormController.notification("Input Error", "Unable to Save Part!", "Min must be less than Max!  Min must be equal to or greater than one!");
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

                    int id = Integer.parseInt(modPartIdTxt.getText());
                    String name = modPartNameTxt.getText();
                    double price = Double.parseDouble(modPartCPUTxt.getText());
                    int stock = Integer.parseInt(modPartInvTxt.getText());
                    int max = Integer.parseInt(modPartMaxTxt.getText());
                    int min = Integer.parseInt(modPartMinTxt.getText());
                    int machineId = Integer.parseInt(modPartCoNameTxt.getText());

                    InHouse updated = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(partIndex, updated);

                }

                if (outsourcedRBtn.isSelected()) {

                    int id = Integer.parseInt(modPartIdTxt.getText());
                    String name = modPartNameTxt.getText();
                    double price = Double.parseDouble(modPartCPUTxt.getText());
                    int stock = Integer.parseInt(modPartInvTxt.getText());
                    int max = Integer.parseInt(modPartMaxTxt.getText());
                    int min = Integer.parseInt(modPartMinTxt.getText());
                    String companyName = modPartCoNameTxt.getText();

                    Outsourced updated = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(partIndex, updated);
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
     * Initializes Controller.
     * Creates Part object and sets all part fields using part index of selected item.
     * Pre selects radio button using inheritance concept instanceOf.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Part part = getAllParts().get(partIndex);
        partId = getAllParts().get(partIndex).getId();
        modPartIdTxt.setText(String.valueOf(part.getId()));
        modPartNameTxt.setText(part.getName());
        modPartInvTxt.setText(String.valueOf(part.getStock()));
        modPartCPUTxt.setText(String.valueOf(part.getPrice()));
        modPartMinTxt.setText(String.valueOf(part.getMin()));
        modPartMaxTxt.setText(String.valueOf(part.getMax()));

        if (part instanceof InHouse) {
            modPartSwitchLbl.setText("Machine ID");
            modPartCoNameTxt.setText(String.valueOf(((InHouse) getAllParts().get(partIndex)).getMachineId()));
            inHouseRBtn.setSelected(true);

        } else {
            modPartSwitchLbl.setText("Company Name");
            modPartCoNameTxt.setText(((Outsourced) getAllParts().get(partIndex)).getCompanyName());
            outsourcedRBtn.setSelected(true);
        }

    }

}

package BHC482Main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This application is an Inventory management system that enables the user to add
 * or modify parts and products.
 * <p>
 * FUTURE ENHANCEMENT In order to enhance productivity, this application could be improved by implementing a comment section
 * for the parts and products.  This would allow a more detailed description of the items if desired.
 *
 * @author Bradley Harper
 */
public class Main extends Application {

    /**
     * Opens main window.
     * Sets title for all windows.
     * sets window dimensions.
     *
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/MainForm.fxml"));
        primaryStage.setTitle("Acme Solutions");
        primaryStage.setScene(new Scene(root, 855, 595));
        primaryStage.show();
    }


    /**
     * This method launches the JavaFX runtime and JavaFX application.
     */
    public static void main(String[] args) {

        launch(args);
    }
}

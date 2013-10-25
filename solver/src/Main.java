import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author David Capper <dmc2@aber.ac.uk>
 */
public class Main extends Application{

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Sudoku Solver");

        try {
            // Load the root layout from the fxml file
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("UI.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            UIController controller =  loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(primaryStage);
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}

package application;

import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
            BorderPane root = (BorderPane) loader.load();
            SampleController controller = loader.getController();
            Scene scene = new Scene(root, 475, 700);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
            // Add event filter to capture key events and pass them to the controller
            scene.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> controller.input(event));
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

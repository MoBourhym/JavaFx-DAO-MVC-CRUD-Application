package ma.enset.mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load MainView with a TabPane
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ma/enset/mvc/MainView.fxml"));
        BorderPane mainView = loader.load();

        // Set up the scene
        Scene scene = new Scene(mainView);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Team and Player Management");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

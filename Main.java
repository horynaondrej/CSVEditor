package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;


/**
 * TO-DO
 * 2) Check open file
 * 3) Check user dialog inserts - mark and coding
 * 4) Close dialog window exception
 */


/**
 * Main class; This app is a simple CSV editor for fast use
 */
public class Main extends Application {

    /**
     * Main thread
     *
     * @param primaryStage
     */
    public void start(Stage primaryStage) {

        Control control = new Control(primaryStage);

        Scene scene = new Scene(control.get_root(), 700.0, 400.0);
        scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());

        primaryStage.setTitle("CSV Editor v1.0.0");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());

    }

    public static void main(String[] args) {

        launch(args);

    }

}

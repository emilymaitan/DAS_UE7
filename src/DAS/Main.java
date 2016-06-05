package DAS;

import DAS.Data.Integers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/gui.fxml"));
        primaryStage.setTitle("DAS - Implementation of Static Concepts");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {

        try {
            Integers.initialize();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        launch(args);
    }
}

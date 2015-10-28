package kode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Kode");

        GridPane root = new GridPane();
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }
}
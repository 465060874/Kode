package kode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application
{
    public static TextEditor editor;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Kode");

        StackPane root = new StackPane();

        editor = new TextEditor();
        root.getChildren().add(editor);

        Scene rootScene = new Scene(root, 400, 300);
        primaryStage.setScene(rootScene);
        primaryStage.show();
        ApplicationMenu menu = new ApplicationMenu(primaryStage);
        root.getChildren().add(menu.createApplicationMenu());
    }
}
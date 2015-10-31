package kode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        // get platform information
        PlatformManager.initialize();

        // create main window
        primaryStage.setTitle("Kode");

        StackPane root = new StackPane();

        Global.editor = new TextEditor();
        root.getChildren().add(Global.editor);

        Scene rootScene = new Scene(root, 400, 300);
        //rootScene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(rootScene);
        primaryStage.show();

        // create and add application menu
        ApplicationMenu menu = new ApplicationMenu(primaryStage);
        root.getChildren().add(menu.createApplicationMenu());
    }
}

class Global
{
    public static TextEditor editor;
}
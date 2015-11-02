package kode;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application
{
    private static int minWindowWidth = 400;
    private static int minWindowHeight = 300;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        // get platform information
        PlatformManager.initialize();

        // what to do before the application is terminated
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent event)
            {
                IO.cleanTempDirectory();

                Platform.exit();
                System.exit(0);
            }
        });

        // create main window
        primaryStage.setTitle("Kode");

        StackPane root = new StackPane();

        Global.editor = new TextEditor();
        root.getChildren().add(Global.editor);

        Scene rootScene = new Scene(root, minWindowWidth, minWindowHeight);
        //rootScene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        primaryStage.setMinWidth(minWindowWidth);
        primaryStage.setMinHeight(minWindowHeight);
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
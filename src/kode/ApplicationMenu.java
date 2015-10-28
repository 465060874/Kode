package kode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Class to create and handle everything related to the menu (menubar)
 * Created by axelkennedal on 2015-10-28.
 */
public class ApplicationMenu
{
    private Stage owner;

    ApplicationMenu(Stage owner)
    {
        this.owner = owner;
    }

    public MenuBar createApplicationMenu()
    {
        Menu fileMenu = new Menu("File");
        MenuItem newFile = new MenuItem("New...");
        newFile.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                // TODO: create dialog for user to pick empty, java template/javafx template
                String template = null;
                File templateFile = new File(Main.class.getResource("../resources/javaTemplate.txt").getPath());
                template = IO.readTextFile(templateFile);
                Global.editor.setText(template);

                // reset the filepath so previous file is not overwritten
                Global.editor.savedAsFile = null;
            }
        });
        MenuItem saveAs = new MenuItem("Save as...");
        saveAs.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                showSaveDialog();
            }
        });
        MenuItem save = new MenuItem("Save");
        save.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (Global.editor.savedAsFile != null)
                {
                    IO.writeTextFile(Global.editor.savedAsFile, Global.editor.getText());
                }
                else
                {
                    // Inform the user that the file has not been saved before and give option to save as
                    Alert savePrompt = new Alert(Alert.AlertType.INFORMATION);
                    savePrompt.setTitle("Save File");
                    savePrompt.setHeaderText("This is your first time saving this file");
                    savePrompt.setContentText("Choose a location");
                    ButtonType saveAs = new ButtonType("Save as...");
                    ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    savePrompt.getButtonTypes().setAll(saveAs, cancel);
                    Optional<ButtonType> userChoice = savePrompt.showAndWait();
                    if (userChoice.get() == saveAs)
                    {
                        showSaveDialog();
                    }
                }
            }
        });
        MenuItem open = new MenuItem("Open...");
        open.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                showOpenDialog();
            }
        });
        fileMenu.getItems().addAll(newFile, saveAs, save, open);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);
        menuBar.setUseSystemMenuBar(true);

        return menuBar;
    }

    private void showSaveDialog()
    {
        //TODO: possibly add extension filters
        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(owner);

        // write the string to a file
        String data = Global.editor.getText();
        boolean success = IO.writeTextFile(file, data);
        if (success)
        {
            Global.editor.savedAsFile = file;
        }
    }

    private void showOpenDialog()
    {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(owner);

        // set the currently open text to the one in the selected file
        Global.editor.setText(IO.readTextFile(file));
        Global.editor.savedAsFile = file;
    }
}

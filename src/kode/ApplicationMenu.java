package kode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
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
                Main.editor.printText();
            }
        });
        fileMenu.getItems().addAll(saveAs, save);

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
        Charset charset = Charset.forName("utf-8");
        String data = Main.editor.textArea.getText();
        byte dataArray[] = data.getBytes();
        Path filePath = file.toPath();
        try
        {
            OutputStream out = new BufferedOutputStream(
                    Files.newOutputStream(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)
            );
            out.write(dataArray, 0, dataArray.length);
            out.flush();
            out.close();
        } catch (IOException e)
        {

        }
    }
}

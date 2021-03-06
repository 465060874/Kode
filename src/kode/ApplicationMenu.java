package kode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * Class to create and handle everything related to the menu (menubar)
 * Created by axelkennedal on 2015-10-28.
 */
public class ApplicationMenu
{
    // the window which owns this window ("parent")
    private Stage owner;

    public enum FileMode {SAVE, OPEN}

    ApplicationMenu(Stage owner)
    {
        this.owner = owner;
    }

    /**
     * Create the application menu, including eventhandlers and hotkeys
     * @return the created MenuBar
     */
    public MenuBar createApplicationMenu()
    {
        /*
         * Create the File menu
         */
        Menu fileMenu = new Menu("File");
        MenuItem newFile = new MenuItem("New...");
        newFile.setAccelerator(KeyCombination.keyCombination(PlatformManager.primaryHotkey + "+N"));
        newFile.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                newFile();
            }
        });
        MenuItem saveAs = new MenuItem("Save as...");
        saveAs.setAccelerator(KeyCombination.keyCombination(PlatformManager.primaryHotkey + "+SHIFT+S"));
        saveAs.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                showFileDialog(FileMode.SAVE);
            }
        });
        MenuItem save = new MenuItem("Save");
        save.setAccelerator(KeyCombination.keyCombination(PlatformManager.primaryHotkey + "+S"));
        save.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                saveFile();
            }
        });
        MenuItem open = new MenuItem("Open...");
        open.setAccelerator(KeyCombination.keyCombination(PlatformManager.primaryHotkey + "+O"));
        open.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                showFileDialog(FileMode.OPEN);
            }
        });
        fileMenu.getItems().addAll(newFile, saveAs, save, open);

        /*
         * Create the Source menu
         */
        Menu sourceMenu = new Menu("Source");
        MenuItem compile = new MenuItem("Compile");
        compile.setAccelerator(KeyCombination.keyCombination(PlatformManager.primaryHotkey + "+SHIFT+C"));
        compile.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                cleanAndCompile();
            }
        });
        MenuItem run = new MenuItem("Run");
        run.setAccelerator(KeyCombination.keyCombination(PlatformManager.primaryHotkey + "+SHIFT+R"));
        run.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                run();
            }
        });
        MenuItem compileAndRun = new MenuItem("Compile & Run");
        compileAndRun.setAccelerator(KeyCombination.keyCombination(PlatformManager.primaryHotkey + "+R"));
        compileAndRun.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                cleanAndCompile();
                run();
            }
        });
        sourceMenu.getItems().addAll(compile, run, compileAndRun);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, sourceMenu);
        menuBar.setUseSystemMenuBar(true);

        return menuBar;
    }

    /**
     * Display a dialog for the user to save or open a file
     * @param mode save or open
     */
    private void showFileDialog(FileMode mode)
    {
        //TODO: possibly add extension filters
        FileChooser chooser = new FileChooser();
        File file = null;
        boolean success = false;

        if (mode == FileMode.SAVE)
        {
            file = chooser.showSaveDialog(owner);

            if (file != null)
            {
                // write the string to a file
                String data = Global.editor.getText();
                success = IO.writeTextFile(file, data);
            }
        }

        else if (mode == FileMode.OPEN)
        {
            file = chooser.showOpenDialog(owner);

            if (file != null)
            {
                // set the currently open text to the one in the selected file
                String newData = IO.readTextFile(file);
                if (newData != null)
                {
                    success = true;
                    Global.editor.setText(newData);
                }
            }
        }

        if (success)
        {
            Global.editor.savedAsFile = file;
        }
    }

    /**
     * Remove potential old artifacts and compile the currently open source code.
     * If the user has not saved the file it will be temporarily saved and compiled.
     * Otherwise the code will be compiled as normal.
     */
    private void cleanAndCompile()
    {
        //TODO: only recompile if source code is newer than compiled file, perhaps create a File object with current source code
        // and compare using .hashCode()?

        // remove old artifacts
        if (Global.editor.savedAsFile != null)
        {
            ShellInterface.cleanArtifacts(Global.editor.savedAsFile.getParentFile());
        }

        // the source code has been saved at a location known by the user
        if (Global.editor.savedAsFile != null && !Global.editor.isTempFile)
        {
            ShellInterface.compileCode(Global.editor.savedAsFile);
        }

        // use temporary source code file on disk
        else
        {
            // rename previous tempfile if one existed and if class name in source code changed
            if (Global.editor.isTempFile)
            {
                String newFileName = SourceProcessor.className(Global.editor.getText()) + ".java";
                File newFile = IO.renameFile(Global.editor.savedAsFile, newFileName);
                IO.writeTextFile(newFile, Global.editor.getText());
                Global.editor.savedAsFile = newFile;
            }

            // create a temporary source code file on disk
            else
            {
                Global.editor.savedAsFile = IO.saveTemp(Global.editor.getText());
            }

            Global.editor.hasCompiledSuccessfully = ShellInterface.compileCode(Global.editor.savedAsFile);
            Global.editor.isTempFile = true;
        }
    }

    private void run()
    {
        if (Global.editor.hasCompiledSuccessfully)
        {
            ShellInterface.runCode(Global.editor.savedAsFile);
        }
        else
        {
            Alert compilationProblem = new Alert(Alert.AlertType.WARNING);
            compilationProblem.setTitle("Could not run");
            compilationProblem.setHeaderText("Source has not successfully been compiled yet");
            compilationProblem.show();
        }
    }

    private void saveFile()
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
                showFileDialog(FileMode.SAVE);
            }
        }
    }

    private void newFile()
    {
        // TODO: create dialog for user to pick empty, java template/javafx template
        String template = null;
        File templateFile = new File(Main.class.getResource("../resources/javaTemplate.txt").getPath());
        template = IO.readTextFile(templateFile);
        Global.editor.setText(template);

        // reset the filepath so previous file is not overwritten
        Global.editor.savedAsFile = null;
    }
}

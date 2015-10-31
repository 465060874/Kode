package kode;

import javafx.scene.control.TextArea;

import java.io.File;

/**
 * Created by axelkennedal on 2015-10-28.
 */
public class TextEditor extends TextArea
{
    // (if saved) contains a reference to the file on disk
    public File savedAsFile;

    TextEditor()
    {
        super();
    }

    public void printText()
    {
        System.out.println(getText());
    }
}
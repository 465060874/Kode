package kode;

import javafx.scene.control.TextArea;

import java.io.File;

/**
 * Created by axelkennedal on 2015-10-28.
 */
public class TextEditor extends TextArea
{
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
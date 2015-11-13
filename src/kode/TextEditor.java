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
    public boolean isTempFile;

    // remember this in order to know if it is possible to run the compiled artifact of the source
    boolean hasCompiledSuccessfully;

    TextEditor()
    {
        super();
    }

    public void printText()
    {
        System.out.println(getText());
    }

}
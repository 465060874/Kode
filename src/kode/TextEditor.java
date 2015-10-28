package kode;

import javafx.scene.control.TextArea;

/**
 * Created by axelkennedal on 2015-10-28.
 */
public class TextEditor extends TextArea
{
    public TextArea textArea;

    TextEditor()
    {
        textArea = new TextArea();
    }

    public void printText()
    {
        System.out.println(textArea.getText());
    }
}
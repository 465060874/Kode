package kode;

import java.io.File;
import java.io.IOException;

/**
 * Class that handles executing command line commands
 * Created by axelkennedal on 2015-10-31.
 */
public class ShellInterface
{
    public static void executeCommand(String command)
    {
        Runtime runtime = Runtime.getRuntime();

        // execute the command
        try
        {
            Process process = runtime.exec(command);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        // TODO: get the output of the command
        System.out.println("Standard output of command \"" + command + "\"");
        String output;

    }

    public static void compileCode(File sourceCode)
    {
        executeCommand("javac " + sourceCode.getAbsolutePath());
    }
}

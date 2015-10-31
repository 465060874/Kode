package kode;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class that handles executing command line commands
 * Created by axelkennedal on 2015-10-31.
 */
public class ShellInterface
{
    public static String executeCommand(String command, File directory)
    {
        System.out.println("Command: \"" + command + "\"");

        // TODO: implement using ProcessBuilder to read in realtime (other thread?)
        Runtime runtime = Runtime.getRuntime();
        String out = null;
        String err = null;
        try
        {
            Process process = runtime.exec(command, null, directory);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(process.getErrorStream()));

            // read the output from the command
            System.out.println("Standard output of the command:\n");
            out = "";
            String s = null;
            while ((s = stdInput.readLine()) != null)
            {
                System.out.println(s);
                out += s;
            }

            // read any errors from the attempted command
            System.out.println("Standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null)
            {
                System.out.println(s);
                err += s;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return out + err;
    }

    /**
     * Compile the specified sourceCode
     * @param sourceCode
     */
    public static void compileCode(File sourceCode)
    {
        executeCommand("javac " + sourceCode.getAbsolutePath(), null);
    }

    /**
     * Run the compiled artifact of the sourceCode, executes it from the parent directory
     * @param sourceCode
     */
    public static String runCode(File sourceCode)
    {
        String compiledName = sourceCode.getName();
        compiledName = compiledName.substring(0, compiledName.lastIndexOf('.'));

        return executeCommand("java " + compiledName, sourceCode.getParentFile());
    }

    public static String javaSDKVersion()
    {
        String javaInfo = executeCommand("java -version", null);
        String SDKVersion = javaInfo.substring(javaInfo.indexOf("\"") + 1, javaInfo.lastIndexOf("\""));
        return SDKVersion;
    }
}

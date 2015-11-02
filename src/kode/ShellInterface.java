package kode;

import java.io.BufferedReader;
import java.io.File;
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
     * @return true if compilation succeeded, false otherwise
     */
    public static boolean compileCode(File sourceCode)
    {
        return "".equals(executeCommand("javac " + sourceCode.getAbsolutePath(), null));
    }

    /**
     * Run the compiled artifact of the sourceCode, executes it from the parent directory
     * @param sourceCode
     */
    public static String runCode(File sourceCode)
    {
        String compiledName = IO.fileNameWithoutExtension(sourceCode);
        return executeCommand("java " + compiledName, sourceCode.getParentFile());
    }

    /**
     * Asks the system for which version on Java the user has installed
     * @return a String contining the Java version number
     */
    public static String javaSDKVersion()
    {
        String javaInfo = executeCommand("java -version", null);
        String SDKVersion = javaInfo.substring(javaInfo.indexOf("\"") + 1, javaInfo.lastIndexOf("\""));
        return SDKVersion;
    }

    /**
     * Clean up (remove) artifacts within a directory
     * @param directory
     */

    public static void cleanArtifacts(File directory)
    {
        for (File file : directory.listFiles())
        {
            if (file.getName().endsWith(".class"))
            {
                file.delete();
            }
        }
    }
}

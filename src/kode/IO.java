package kode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;

/**
 * Class to handle IO of text files
 * Created by axelkennedal on 2015-10-28.
 */
public class IO
{
    /**
     * Read the contents of a text file
     * @param file
     * @return the String contents of the file
     */
    public static String readTextFile(File file)
    {
        String data = null;
        try
        {
            data = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Write data to a file (using a temporary file in the process)
     * @param file to write to
     * @param data text to write
     * @return whether the operation was successful or not
     */

    public static boolean writeTextFile(File file, String data)
    {
        // create temporary file
        Path temp = null;
        try
        {
            temp = Files.createFile(Paths.get(file.getParent() + "/." + file.getName()));
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        // write data to temporary file
        byte dataArray[] = data.getBytes();
        try
        {
            OutputStream out = new BufferedOutputStream(
                    Files.newOutputStream(temp, StandardOpenOption.CREATE)
            );
            out.write(dataArray);
            out.flush();
            out.close();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        // rename temporary file, effectively replacing the old version
        return (renameFile(temp.toFile(), file.getName()) != null);
    }

    /**
     * Save a String temporarily to disk
     * @param data the data to be written to disk
     * @return the generated temporary file
     */
    public static File saveTemp(String data)
    {
        // Generate a a temporary filename
        String tempName = SourceProcessor.className(Global.editor.getText());

        // create the file on disk
        File tempFile = null;
        try
        {
            File tempdir = getTempDirectory();
            tempFile = Files.createFile(Paths.get(tempdir.toString() + "/" + tempName + ".java")).toFile();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        writeTextFile(tempFile, data);

        return tempFile;
    }

    /**
     * Gets the folder where temporary files can be stored
     * @return the folder where temporary files can be stored
     */
    public static File getTempDirectory()
    {
        return new File(Main.class.getResource("../tempfiles/").getPath());
    }

    /**
     * Remove all files in the folder for temporary files
     */
    public static void cleanTempDirectory()
    {
        for (File tempFile : IO.getTempDirectory().listFiles())
        {
            tempFile.delete();
        }
    }

    /**
     *
     * @param fileToRename
     * @param newName
     * @return The new file if successful, otherwise null
     */
    public static File renameFile(File fileToRename, String newName)
    {
        // the old and new name are the same
        String oldName = fileToRename.getName();
        if (fileToRename.getName().equals(newName)) return fileToRename;

        File newFile = new File(fileToRename.getParent() + "/" + newName);
        if (fileToRename.renameTo(newFile))
        {
            return newFile;
        }
        else return null;
    }

    /**
     * Return the filename of a file, without the extension.
     * E.g: folder/file.txt gives the output String "file".
     * @param file file to get the filename from
     * @return
     */
    public static String fileNameWithoutExtension(File file)
    {
        return file.getName().substring(0, file.getName().lastIndexOf('.'));
    }
}

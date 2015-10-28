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
     * @return
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
        try
        {
            Files.move(temp, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

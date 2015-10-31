package kode;

/**
 * Class to handle differences between platforms (OS X, Windows and Linux)
 * Created by axelkennedal on 2015-10-31.
 */
public class PlatformManager
{
    public enum OperatingSystem {OSX, WINDOWS, LINUX}
    private static OperatingSystem currentOS;

    public static String primaryHotkey;

    /**
     * Get all data needed for the rest of the class to function.
     * Should be run before the class is used.
     */
    public static void initialize()
    {
        determineOS();
        determinePrimaryHotKey();
    }

    /**
     * Ask the system for the operating system name, set enum based on it
     */
    private static void determineOS()
    {
        String operatingSystem = System.getProperty("os.name");
        if (operatingSystem.contains("OS X"))
        {
            currentOS = OperatingSystem.OSX;
        }
        else if (operatingSystem.contains("Windows"))
        {
            currentOS = OperatingSystem.WINDOWS;
        }
        else
        {
            currentOS = OperatingSystem.LINUX;
        }
    }

    /**
     * Determine primary hotkey depending on OS
     */
    private static void determinePrimaryHotKey()
    {
        switch (currentOS)
        {
            case OSX:
                primaryHotkey = "Meta";
                break;
            case WINDOWS:
            case LINUX:
                primaryHotkey = "Ctrl";
                break;
        }
    }

    public static OperatingSystem getOS()
    {
        return currentOS;
    }
}

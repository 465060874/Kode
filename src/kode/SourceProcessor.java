package kode;

import java.util.StringTokenizer;

/**
 * Created by axelkennedal on 2015-11-02.
 */
public class SourceProcessor
{

    /**
     * Analyze the source code for a class and return the class name
     * @param sourceCode
     * @return
     */
    public static String className(String sourceCode)
    {
        int classNameStart = sourceCode.indexOf("public class") + "public class".length();
        int classNameEnd = sourceCode.indexOf("{", classNameStart) - 1;
        String name = sourceCode.substring(classNameStart , classNameEnd).trim();
        return name;
    }
}

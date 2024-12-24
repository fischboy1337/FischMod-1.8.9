package de.fischboy.fischmod.api.utils.io;

import lombok.experimental.UtilityClass;

import java.io.*;

@UtilityClass
public class FileUtil {

    public static void write(String content, File file) {
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            printWriter.println(content);
            printWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedReader read(File file) {
        try {
            return new BufferedReader(new FileReader(file));
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

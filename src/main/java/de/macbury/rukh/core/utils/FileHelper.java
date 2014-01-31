package de.macbury.rukh.core.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import de.macbury.rukh.core.Rukh;

public class FileHelper {
  
  public static String load(String filename) throws IOException {
    String content = null;
    File file = new File(filename); 
    FileReader reader = new FileReader(file);
    char[] chars = new char[(int) file.length()];
    reader.read(chars);
    content = new String(chars);
    reader.close();
    return content;
  }
  
  public static void save(String path, String content) throws IOException {
    FileWriter fileWriter = null;
    try {
      File newTextFile = new File(path);
      fileWriter = new FileWriter(newTextFile);
      fileWriter.write(content);
      fileWriter.close();
    } finally {
      fileWriter.close();
    }
  }
  
}

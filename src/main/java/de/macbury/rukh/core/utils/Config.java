package de.macbury.rukh.core.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

import de.macbury.rukh.core.Rukh;

public class Config {
  private int port;
  private int width;
  private int height;
  private boolean fullscreen;
  
  public static Config loadOrInitialize() {
    Config config = Config.load();
    
    if (config == null) {
      config = new Config();
      config.defaults();
      config.save();
    }
    
    return config;
  }
  
  private static Config load() {
    String content;
    try {
      content = FileHelper.load(Rukh.path.config());
      Gson gson      = new Gson();
      return gson.fromJson(content, Config.class);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    
  }

  private void save() {
    Gson gson = new Gson();
    try {
      FileHelper.save(Rukh.path.config(), gson.toJson(this));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void defaults() {
    Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
    width      = screenDimension.width;
    height     = screenDimension.height;
    fullscreen = true;
    port       = 7654;
  }

  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public boolean isFullscreen() {
    return fullscreen;
  }
  
}

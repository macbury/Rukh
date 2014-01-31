package de.macbury.rukh.core.utils;

import java.io.File;

public class Path {
  public Path() {
    (new File(home())).mkdirs();
    (new File(temp())).mkdirs();
  }
  
  public String temp() {
    return home() + "/tmp";
  }
  public String home() {
    return System.getProperty("user.home") + "/.rukh";
  }

  public String tile(String thumb) {
    return temp() + "/tile_" + thumb + ".png";
  }

  public String config() {
    return home() + "/config.json";
  }
}

package de.macbury.rukh.core;

import de.macbury.rukh.core.parell.ParellJobsManager;
import de.macbury.rukh.core.utils.Config;
import de.macbury.rukh.core.utils.Path;

public class Rukh {
  public static GdxContainer application;
  public static Config config;
  public static Path path;
  public static ParellJobsManager workers;

  public static void bootstrap() {
    path        = new Path();
    application = new GdxContainer();
    config      = Config.loadOrInitialize();
    workers     = new ParellJobsManager(10);
  }
}

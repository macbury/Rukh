package de.macbury.rukh;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.macbury.rukh.core.Rukh;
import de.macbury.rukh.core.parell.jobs.ImageDownloader;

public class App {
  public static void main( String[] args ) {
    Rukh.bootstrap();
    
    LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
    cfg.title         = "Rukh";
    cfg.useGL20       = true;
    cfg.forceExit     = true;
    cfg.vSyncEnabled  = true;
    cfg.foregroundFPS = 60;
    cfg.backgroundFPS = 60;
    cfg.resizable     = false;
    
    cfg.fullscreen    = Rukh.config.isFullscreen();
    cfg.width         = Rukh.config.getWidth();
    cfg.height        = Rukh.config.getHeight();

    new LwjglApplication(Rukh.application, cfg);
  }
}

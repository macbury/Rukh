package de.macbury.rukh.core.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.macbury.rukh.core.Rukh;

public class Overlay {
  public static File getWallpaperOverlay() {
    int width   = Gdx.graphics.getWidth();
    int height  = Gdx.graphics.getHeight();
    
    File outputfile = new File(Rukh.path.temp() + "/overlay.png");
    if (!outputfile.exists()) {
      BufferedImage finalWallpaper  = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
      Graphics2D    wallpaperCanvas = finalWallpaper.createGraphics();

      float radius = width / 2f;
      RadialGradientPaint gradient = new RadialGradientPaint(radius, height/2, width * 0.75f, new float[] { 0.15f, 1f }, new Color[] { new Color(0x00000000, true), Color.BLACK });

      wallpaperCanvas.setPaint(gradient);
      wallpaperCanvas.fillRect(0, 0, width, height);

      try {
        ImageIO.write(finalWallpaper, "png", outputfile);
      } catch (IOException e) {
        e.printStackTrace();
      }
      wallpaperCanvas.dispose();
    }

    return outputfile;
  }
  
  public static Image getImage() {
    Image image = new Image(new Texture(Gdx.files.absolute(getWallpaperOverlay().getAbsolutePath())));
    return image;
  }
}

package de.macbury.rukh.core.parell.jobs;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.moviejukebox.imdbapi.model.ImdbList;

import de.macbury.rukh.core.Rukh;
import de.macbury.rukh.core.parell.Job;
import de.macbury.rukh.core.ui.DashboardScreen;

public class DownloadAndCropCover extends Job<String> {
  private ImdbList item;
  
  public DownloadAndCropCover(ImdbList item) {
    super(String.class);
    this.item = item;
  }

  @Override
  public String perform() {
    BufferedImage tempPosterImage = null;
    File outputfile               = new File(Rukh.path.tile(item.getImdbId()));
    
    if (!outputfile.exists()) {
      try {
        URL url = new URL(item.getImage().getUrl());
        tempPosterImage = ImageIO.read(url);
        
        BufferedImage croppedPosterImage = tempPosterImage.getSubimage(0, 0, item.getImage().getWidth(), item.getImage().getWidth());

        BufferedImage finalWallpaper  = new BufferedImage(DashboardScreen.TILE_SIZE, DashboardScreen.TILE_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D    wallpaperCanvas = finalWallpaper.createGraphics();

        wallpaperCanvas.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        wallpaperCanvas.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        wallpaperCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        wallpaperCanvas.drawImage(croppedPosterImage,0, 0, DashboardScreen.TILE_SIZE, DashboardScreen.TILE_SIZE, null);

        try {
          ImageIO.write(finalWallpaper, "png", outputfile);
        } catch (IOException e) {
          e.printStackTrace();
        }

        wallpaperCanvas.dispose();

        wallpaperCanvas     = null;
        tempPosterImage     = null;
        croppedPosterImage  = null;
        finalWallpaper      = null;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    return outputfile.getAbsolutePath();
  }
  


}

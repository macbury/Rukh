package de.macbury.rukh.core.parell.jobs;

import java.awt.image.BufferedImage;

import de.macbury.rukh.core.parell.Job;

public class ImageDownloader extends Job<BufferedImage> {
  
  public ImageDownloader() {
    super(BufferedImage.class);
  }

  @Override
  public BufferedImage perform() {
    try {
      Thread.sleep(7000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
}

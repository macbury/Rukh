package de.macbury.rukh.core.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.moviejukebox.imdbapi.model.ImdbList;

import de.macbury.rukh.core.Rukh;
import de.macbury.rukh.core.parell.jobs.DownloadAndCropCover;
import de.macbury.rukh.core.parell.jobs.GetTop250MovieCoversJob;
import de.macbury.rukh.core.utils.Overlay;

public class DashboardScreen extends Screen {
  public static final int TILE_SIZE               = 128;
  private static final float DEFAULT_FADED_COLOR  = 0.2f;
  private static final String TAG                 = "DashboardScreen";
  private Group wallpaperTilesGroup;
  private Stack<Vector2> tilePositions;

  public DashboardScreen() {
    GetTop250MovieCoversJob job = new GetTop250MovieCoversJob();
    job.setCallback(this, "onDownloadMovies");
    Rukh.workers.enqueue(job);
    
    this.wallpaperTilesGroup = new Group();
    this.wallpaperTilesGroup.setPosition(0, 0);
    this.wallpaperTilesGroup.setWidth(getWidth());
    this.wallpaperTilesGroup.setHeight(getHeight());
    
    addActor(wallpaperTilesGroup);
    addActor(Overlay.getImage());
    
    tilePositions = new Stack<Vector2>();
    
    int cols = Math.round(getWidth() / TILE_SIZE)  +1;
    int rows = Math.round(getHeight() / TILE_SIZE) + 1;
    
    for (int x = 0; x < cols; x++) {
      for (int y = 0; y < rows; y++) {
        tilePositions.push(new Vector2(x*TILE_SIZE,y*TILE_SIZE));
      }
    }

    Collections.shuffle(tilePositions);
  }
  
  public void onDownloadMovies(List<ImdbList> movies, GetTop250MovieCoversJob job) {
    Gdx.app.log(TAG, "Found: "+movies.size());
    Collections.shuffle(movies);
    
    for (ImdbList item : movies) {
      DownloadAndCropCover downloadJob = new DownloadAndCropCover(item);
      downloadJob.setCallback(this, "onDownloadAndCropCover");
      Rukh.workers.enqueue(downloadJob);
    }
  }
  
  public void onDownloadAndCropCover(String filePath, DownloadAndCropCover job) {
    if (!tilePositions.isEmpty()) {
      Gdx.app.log(TAG, "Tile path: "+ filePath);
      final Pixmap pixmap = new Pixmap(Gdx.files.absolute(filePath));
      final Vector2 pos   = tilePositions.pop();
      
      Gdx.app.postRunnable(new Runnable() {
        public void run() {
          Gdx.app.log(TAG, " adding it to scene!");
          Image tile  = new Image(new Texture(pixmap));
          tile.setPosition(pos.x, pos.y);
          tile.setColor(DEFAULT_FADED_COLOR, DEFAULT_FADED_COLOR, DEFAULT_FADED_COLOR, 0);
          wallpaperTilesGroup.addActor(tile);
          tile.addAction(Actions.alpha(1f, 1f));
        }
      });
    }
  }
  
  @Override
  public void onEnter() {
    
  }

  @Override
  public void onExit() {

  }

}

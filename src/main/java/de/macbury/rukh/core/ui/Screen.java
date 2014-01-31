package de.macbury.rukh.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;

public abstract class Screen extends Group {
  
  public Screen() {
    this.setWidth(Gdx.graphics.getWidth());
    this.setHeight(Gdx.graphics.getHeight());
    this.setPosition(0, 0);
  }
  
  public abstract void onEnter();
  public abstract void onExit();
}

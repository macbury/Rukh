package de.macbury.rukh.core.ui.transitions;

import com.badlogic.gdx.scenes.scene2d.Stage;

import de.macbury.rukh.core.ui.Screen;

public abstract class Transition {
  protected Stage stage;

  public abstract void transition(Screen fromScreen, Screen toScreen);

  public void setStage(Stage stage) {
    this.stage = stage;
  }
}

package de.macbury.rukh.core;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.macbury.rukh.core.ui.DashboardScreen;
import de.macbury.rukh.core.ui.Screen;
import de.macbury.rukh.core.ui.transitions.SimpleTransition;
import de.macbury.rukh.core.ui.transitions.Transition;

public class GdxContainer implements ApplicationListener {
  private Stage  stage;
  private Screen currentScreen;
  private DashboardScreen dashBoardScreen;
  private FPSLogger fpsLogger;
  
  public void create() {
    fpsLogger       = new FPSLogger();
    stage           = new Stage();
    dashBoardScreen = new DashboardScreen();
    enter(dashBoardScreen, SimpleTransition.class);
  }
  
  public void enter(Screen nextScreen, Class<? extends Transition> transitionType) {
    try {
      Transition transition = transitionType.newInstance();
      transition.setStage(stage);
      transition.transition(currentScreen, nextScreen);
      currentScreen         = nextScreen;
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
  
  public void dispose() {
    this.stage.dispose();
  }

  public void pause() {}

  public void render() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    
    this.stage.act(Gdx.graphics.getDeltaTime());
    this.stage.draw();
    
    fpsLogger.log();
    
    if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
      Gdx.app.exit();
    }
  }

  public void resize(int width, int height) {
    this.stage.setViewport(width, height, false);
  }

  public void resume() {}
}

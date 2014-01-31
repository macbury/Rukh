package de.macbury.rukh.core.ui.transitions;

import de.macbury.rukh.core.ui.Screen;

public class SimpleTransition extends Transition {

  @Override
  public void transition(Screen fromScreen, Screen toScreen) {
    if (fromScreen != null) {
      fromScreen.toBack();
      fromScreen.setVisible(false);
      fromScreen.remove();
      fromScreen.onExit();
    }
    
    toScreen.onEnter();
    stage.addActor(toScreen);
    toScreen.setVisible(true);
  }

}

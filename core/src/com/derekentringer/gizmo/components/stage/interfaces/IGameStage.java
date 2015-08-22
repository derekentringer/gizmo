package com.derekentringer.gizmo.components.stage.interfaces;

public interface IGameStage {
    void setHudHealthHearts(int hearts);
    void setHudHealth(int health);
    void resetHudShapes();
    void setHudLives(int lives);
    void setTransition(String doorType, boolean transition);
}
package com.derekentringer.gizmo.component.stage.interfaces;

public interface IGameStage {
    void setHudHealthHearts(int hearts);
    void setHudHealth(int health);
    void resetHudShapes();
    void setHudLives(int lives);
    void setCrystalCount(int crystalCount);
    void setTransition(String doorType, boolean transition);
}
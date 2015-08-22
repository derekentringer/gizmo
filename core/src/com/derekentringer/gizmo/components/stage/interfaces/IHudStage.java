package com.derekentringer.gizmo.components.stage.interfaces;

public interface IHudStage {
    void setHudHealthHearts(int hearts);
    void setHudHealth(int health);
    void resetHudShapes();
    void setHudLives(int lives);
    void setTransition(boolean transition);
}
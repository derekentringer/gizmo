package com.derekentringer.gizmo.components.stage.interfaces;

public interface IHudStageDelegate {
    void setHudHealthHearts(int hearts);
    void setHudHealth(int health);
    void resetHudShapes();
    void setHudLives(int lives);
}
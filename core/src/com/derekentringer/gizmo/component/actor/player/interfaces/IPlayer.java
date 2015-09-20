package com.derekentringer.gizmo.component.actor.player.interfaces;

public interface IPlayer {
    void playerIsOffMap(boolean offMap);
    void playerGotHit(int playerHealth);
    void playerZeroLives();
}
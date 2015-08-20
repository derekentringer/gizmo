package com.derekentringer.gizmo.components.actor.player.interfaces;

public interface IPlayer {
    void playerIsOffMap(boolean offMap);
    void playerGotHit(int playerHealth);
    void playerZeroLives();
}
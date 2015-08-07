package com.derekentringer.gizmo.components.actor.player.interfaces;

public interface IPlayerDelegate {

    void playerIsOffMap(boolean offMap);
    void playerGotHit(int playerHealth);
    void playerZeroLives();

}
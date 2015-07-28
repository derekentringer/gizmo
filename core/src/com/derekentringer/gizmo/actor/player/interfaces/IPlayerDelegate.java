package com.derekentringer.gizmo.actor.player.interfaces;

public interface IPlayerDelegate {

    void playerIsOffMap(boolean offMap);
    void playerGotHit(int playerHealth);
    void playerDied();
    void playerZeroLives();

}
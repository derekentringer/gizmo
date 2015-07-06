package com.derekentringer.gizmo.actor.player;

public interface IPlayerDelegate {

    void playerIsOffMap(boolean offMap);
    void playerGotHit(int playerHealth);

}
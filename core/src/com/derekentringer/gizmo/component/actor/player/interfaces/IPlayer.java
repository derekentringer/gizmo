package com.derekentringer.gizmo.component.actor.player.interfaces;

import com.derekentringer.gizmo.model.item.BasePlayerItemModel;

public interface IPlayer {
    void setCurrentlySelectedItemPrimary(BasePlayerItemModel item);
    void setCurrentlySelectedItemSecondary(BasePlayerItemModel item);
    void playerIsOffMap(boolean offMap);
    void playerGotHit(int playerHealth);
    void playerZeroLives();
}
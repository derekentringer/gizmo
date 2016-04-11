package com.derekentringer.gizmo.component.stage.interfaces;

import com.derekentringer.gizmo.model.item.BasePlayerItemModel;

public interface IGameStage {
    void setHudHealthHearts(int hearts);
    void setHudHealth(int health);
    void resetHudShapes();
    void setHudLives(int lives);
    void setCrystalCount(int crystalCount);
    void setTransition(String doorType, boolean transition);
    void setHudSelectedPrimaryItem(BasePlayerItemModel itemActor);
    void setHudSelectedSecondaryItem(BasePlayerItemModel itemActor, int itemNum);
    void updateSelectedSecondaryItemCount(int numItems);
}
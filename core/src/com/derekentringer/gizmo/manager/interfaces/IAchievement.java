package com.derekentringer.gizmo.manager.interfaces;

import com.derekentringer.gizmo.model.enemy.BaseEnemyModel;
import com.derekentringer.gizmo.model.item.BasePlayerItemModel;

public interface IAchievement {
    void playerPickedUpItem(BasePlayerItemModel item);
    void playerKilledEnemy(BaseEnemyModel enemy);
}
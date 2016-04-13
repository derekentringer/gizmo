package com.derekentringer.gizmo.manager.interfaces;

import com.derekentringer.gizmo.model.enemy.BaseEnemyModel;
import com.derekentringer.gizmo.model.item.BasePlayerItemModel;

public interface IAchievement {
    void achievementPlayerPickedUpItem(BasePlayerItemModel item);
    void achievementPlayerKilledEnemy(BaseEnemyModel enemy);
}
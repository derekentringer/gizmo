package com.derekentringer.gizmo.component.actor.enemy.boss.phantom.interfaces;

import com.derekentringer.gizmo.component.actor.BaseActor;

public interface IPhantomBossAttack {
    void phantomBossAddActor(BaseActor actor);
    void phantomBossShakeCamera(boolean shake);
}
package com.derekentringer.gizmo.components.actor.boss.interfaces;

import com.derekentringer.gizmo.components.actor.BaseActor;

public interface IPhantomBossAttack {
    void phantomBossAddActor(BaseActor actor);
    void phantomBossShakeCamera(boolean shake);
}
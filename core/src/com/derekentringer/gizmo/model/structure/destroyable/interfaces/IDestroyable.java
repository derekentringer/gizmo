package com.derekentringer.gizmo.model.structure.destroyable.interfaces;

import com.derekentringer.gizmo.component.actor.BaseActor;

public interface IDestroyable {
    void removeBlockFromMap(BaseActor baseActor);
}
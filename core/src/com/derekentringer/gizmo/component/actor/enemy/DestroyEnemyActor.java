package com.derekentringer.gizmo.component.actor.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.IBaseActor;

public class DestroyEnemyActor extends BaseActor implements IBaseActor {

    private TextureRegion[] mDestroyEnemySprite;
    private Texture mDestroyEnemy;

    public DestroyEnemyActor(Body body) {
        super(body);
        addListener(this);
        mDestroyEnemy = Gizmo.assetManager.get("res/image/character/destroy_enemy.png", Texture.class);
        mDestroyEnemySprite = TextureRegion.split(mDestroyEnemy, 32, 32)[0];

        setAnimation(mDestroyEnemySprite, 1 / 48f);
    }

    @Override
    public void isAnimationFinished(boolean isFinished) {
        if (isFinished == true) {
            setIsPlayingAnimation(false);
        }
    }

}
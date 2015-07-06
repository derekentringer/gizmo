package com.derekentringer.gizmo.actor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.derekentringer.gizmo.actor.data.ObjectData;
import com.derekentringer.gizmo.manager.AnimationManager;
import com.derekentringer.gizmo.util.constant.Constants;

public abstract class BaseActor extends Actor {

    public static final int FACING_RIGHT = 1;
    public static final int FACING_LEFT = 2;

    public int facingDirection;

    protected Body body;
    protected ObjectData userData;
    protected AnimationManager animationManager;
    protected float width;
    protected float height;
    private TextureRegion[] currentTextureRegion;

    public abstract ObjectData getUserData();

    public BaseActor(Body body) {
        this.body = body;
        this.userData = (ObjectData) body.getUserData();
        this.animationManager = new AnimationManager();
    }

    public void setAnimation(TextureRegion[] textureRegion, float delay) {
        //TODO check for same animation here instead of in subclasses
        setCurrentTextureRegion(textureRegion);
        currentTextureRegion = textureRegion;
        animationManager.setFrames(textureRegion, delay);
        width = textureRegion[0].getRegionWidth();
        height = textureRegion[0].getRegionHeight();
    }

    public void update(float delayTime) {
        animationManager.update(delayTime);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(animationManager.getFrame(),
                body.getPosition().x * Constants.PPM - width / 2,
                body.getPosition().y * Constants.PPM - height / 2);
        spriteBatch.end();
    }

    @Override
    public void act (float delta) {
    }

    public void setCurrentTextureRegion(TextureRegion[] textureRegion) {
        currentTextureRegion = textureRegion;
    }

    public TextureRegion[] getCurrentTextureRegion() {
        return currentTextureRegion;
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setFacingDirection(int direction) {
        facingDirection = direction;
    }

    public int getFacingDirection() {
        return facingDirection;
    }

}
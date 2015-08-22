package com.derekentringer.gizmo.components.actor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.derekentringer.gizmo.manager.AnimationManager;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.settings.Constants;

import java.util.ArrayList;

public abstract class BaseActor extends Actor {

    private static final String TAG = BaseActor.class.getSimpleName();

    private ArrayList<IBaseActor> listeners = new ArrayList<IBaseActor>();

    public static final int FACING_RIGHT = 1;
    public static final int FACING_LEFT = 2;

    public BaseModel mBaseModel;

    public int mFacingDirection;

    private boolean mIsPlayingAnimation = true;

    protected Body mBody;
    protected AnimationManager mAnimationManager;
    protected float mWidth;
    protected float mHeight;

    private TextureRegion[] mCurrentTextureRegion;

    public abstract BaseModel getPlayerModel();

    public BaseActor(Body body) {
        mBody = body;
        mBaseModel = (BaseModel) body.getUserData();
        mAnimationManager = new AnimationManager();
    }

    public void addListener(IBaseActor listener) {
        listeners.add(listener);
    }

    public void setAnimation(TextureRegion[] textureRegion, float delay) {
        setCurrentTextureRegion(textureRegion);
        mCurrentTextureRegion = textureRegion;
        mAnimationManager.setFrames(textureRegion, delay);
        mWidth = textureRegion[0].getRegionWidth();
        mHeight = textureRegion[0].getRegionHeight();
    }

    public void update(float delayTime) {
        if(getIsPlayingAnimation()) {
            mAnimationManager.update(delayTime);
            if(mAnimationManager.isAnimationFinished()) {
                setIsAnimationFinished(true);
            }
        }
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(mAnimationManager.getFrame(),
                mBody.getPosition().x * Constants.PPM - mWidth / 2,
                mBody.getPosition().y * Constants.PPM - mHeight / 2);
        spriteBatch.end();
    }

    @Override
    public void act(float delta) {
    }

    public void setCurrentTextureRegion(TextureRegion[] textureRegion) {
        mCurrentTextureRegion = textureRegion;
    }

    public TextureRegion[] getCurrentTextureRegion() {
        return mCurrentTextureRegion;
    }

    public Body getBody() {
        return mBody;
    }

    public Vector2 getPosition() {
        return mBody.getPosition();
    }

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }

    public void setFacingDirection(int direction) {
        mFacingDirection = direction;
    }

    public int getFacingDirection() {
        return mFacingDirection;
    }

    public boolean getIsPlayingAnimation() {
        return mIsPlayingAnimation;
    }

    public void setIsPlayingAnimation(boolean isPlayingAnimation) {
        mIsPlayingAnimation = isPlayingAnimation;
    }

    public void setIsAnimationFinished(boolean isFinished) {
        for(IBaseActor listener : listeners){
            listener.isAnimationFinished(isFinished);
        }
    }

}
package com.derekentringer.gizmo.component.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.derekentringer.gizmo.manager.AnimationManager;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.settings.Constants;

import java.util.ArrayList;

public class BaseActor extends Actor {

    private static final String TAG = BaseActor.class.getSimpleName();

    public static final int FACING_RIGHT = 1;
    public static final int FACING_LEFT = 2;

    protected Body mBody;
    protected AnimationManager mAnimationManager;
    protected float mWidth;
    protected float mHeight;

    private ArrayList<IBaseActor> listeners = new ArrayList<IBaseActor>();
    private BaseModel mBaseModel;
    private boolean mIsPlayingAnimation = true;
    private TextureRegion[] mCurrentTextureRegion;

    private Vector2 mPlayerPosition = new Vector2();

    private int mFacingDirection;

    private boolean mShowHitShader;

    String vertexShader;
    String fragmentShader;
    ShaderProgram shaderProgram;

    public BaseActor(Body body) {
        mBody = body;
        mBaseModel = (BaseModel) body.getUserData();
        mAnimationManager = new AnimationManager();

        vertexShader = Gdx.files.internal("res/glsl/vertex.glsl").readString();
        fragmentShader = Gdx.files.internal("res/glsl/fragment.glsl").readString();
        shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
    }

    public BaseModel getBaseModel() {
        return mBaseModel;
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
        if (getIsPlayingAnimation()) {
            mAnimationManager.update(delayTime);
            if (mAnimationManager.didAnimationRunOnce()) {
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

    public void setCurrentTextureRegion(TextureRegion[] textureRegion) {
        mCurrentTextureRegion = textureRegion;
    }

    public TextureRegion[] getCurrentTextureRegion() {
        return mCurrentTextureRegion;
    }

    public Body getBody() {
        return mBody;
    }

    public void setBody(Body body) {
        mBody = body;
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
        for (IBaseActor listener : listeners) {
            listener.isAnimationFinished(isFinished);
        }
    }

    public Vector2 getPlayerPosition() {
        return mPlayerPosition;
    }

    public void setPlayerPosition(Vector2 playerPosition) {
        mPlayerPosition.x = playerPosition.x;
        mPlayerPosition.y = playerPosition.y;
    }

    public boolean isShowHitShader() {
        return mShowHitShader;
    }

    public void setShowHitShader(boolean showHitShader) {
        mShowHitShader = showHitShader;
    }

}
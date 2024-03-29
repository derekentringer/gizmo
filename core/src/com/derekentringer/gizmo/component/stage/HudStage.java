package com.derekentringer.gizmo.component.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.model.item.BombModel;
import com.derekentringer.gizmo.model.object.PotionLifeModel;
import com.derekentringer.gizmo.component.stage.interfaces.IGameStage;
import com.derekentringer.gizmo.component.stage.interfaces.IHudStage;
import com.derekentringer.gizmo.model.item.BasePlayerItemModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangAmethystModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangBloodStoneModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangEmeraldModel;
import com.derekentringer.gizmo.model.item.boomerang.BoomerangWoodModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.log.GLog;

import java.util.ArrayList;

public class HudStage extends BaseStage implements IGameStage {

    private static final String TAG = HudStage.class.getSimpleName();

    private ArrayList<IHudStage> listeners = new ArrayList<IHudStage>();

    private static final int HUD_PADDING = 10;

    private static final float TIME_TO_FADE = 1;
    private static final int FADE_DELAY = 900;
    private static final String FADE_IN = "FADE_IN";
    private static final String FADE_OUT = "FADE_OUT";
    private static final String FADE_COMPLETE = "FADE_COMPLETE";

    private Vector2 mHudLivesPosition = new Vector2();
    private Vector2 mHudHealthPosition = new Vector2();
    private Vector2 mHudCrystalsPosition = new Vector2();

    private Vector2 mHudCurrentPrimaryItemPosition = new Vector2();
    private Vector2 mHudCurrentSecondaryItemPosition = new Vector2();

    private Texture mHudLivesOne;
    private Texture mHudLivesTwo;
    private Texture mHudLivesThree;
    private Texture mHudLivesFour;
    private Texture mHudLivesFive;

    private Texture mHudHeartsTwo;
    private Texture mHudHeartsThree;
    private Texture mHudHeartsFour;
    private Texture mHudHeartsFive;
    private Texture mHudHeartsSix;
    private Texture mHudHeartsSeven;
    private Texture mHudHeartsEight;
    private Texture mHudHeartsNine;
    private Texture mHudHeartsTen;

    private Texture mHudCurrentItemEmpty;
    private Texture mHudCurrentItemBoomerangWood;
    private Texture mHudCurrentItemBoomerangEmerald;
    private Texture mHudCurrentItemBoomerangAmethyst;
    private Texture mHudCurrentItemBoomerangBloodstone;

    private Texture mHudCurrentItemPotionLife;
    private Texture mHudCurrentItemBomb;

    private Texture mCurrentLivesTexture;
    private Texture mCurrentHealthTexture;

    private Texture mHudCrystalsCount;

    private Texture mHudCurrentPrimaryItemTexture;
    private Texture mHudCurrentSecondaryItemTexture;

    private int mLives;
    private int mHearts;
    private int mCrystals;

    private ShapeRenderer mRedShapeRendererTop;
    private ShapeRenderer mRedShapeRendererBottom;
    private ShapeRenderer mWhiteShapeRendererTop;
    private ShapeRenderer mWhiteShapeRendererBottom;
    private ShapeRenderer mTransitionShapeRenderer;
    private boolean mProjectionMatrixSet;

    private float mInitialWidth;
    private float mRedShapeWidthTop;
    private float mRedShapeWidthBottom;
    private float mRedShapeHeight = 20;

    private String mDoorType;
    private boolean mShowTransition;
    private String mFadeStatus;
    private float mTimeAccumulated;
    private float mNewAlpha;
    private boolean isFadeInAlreadyRun;

    private GlyphLayout mLayoutBlueCrystalCount;
    private String mBlueCrystalStringDisplay;
    private String mInitialCrystalString = "0";

    private GlyphLayout mLayoutSecondaryItemCount;
    private String mSecondaryItemCountStringDisplay = "";
    private String mInitialSecondaryItemCountString = "";

    public HudStage(GameStage gameStage) {
        gameStage.addListener(this);

        mOrthographicCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mOrthographicCamera.update();

        mRedShapeRendererTop = new ShapeRenderer();
        mRedShapeRendererBottom = new ShapeRenderer();
        mWhiteShapeRendererTop = new ShapeRenderer();
        mWhiteShapeRendererBottom = new ShapeRenderer();
        mTransitionShapeRenderer = new ShapeRenderer();
        mProjectionMatrixSet = false;

        mHudLivesOne = Gizmo.getAssetManager().get("res/image/hud/hud_lives_one.png", Texture.class);
        mHudLivesTwo = Gizmo.getAssetManager().get("res/image/hud/hud_lives_two.png", Texture.class);
        mHudLivesThree = Gizmo.getAssetManager().get("res/image/hud/hud_lives_three.png", Texture.class);
        mHudLivesFour = Gizmo.getAssetManager().get("res/image/hud/hud_lives_four.png", Texture.class);
        mHudLivesFive = Gizmo.getAssetManager().get("res/image/hud/hud_lives_five.png", Texture.class);

        mHudHeartsTwo = Gizmo.getAssetManager().get("res/image/hud/hud_hearts_two.png", Texture.class);
        mHudHeartsThree = Gizmo.getAssetManager().get("res/image/hud/hud_hearts_three.png", Texture.class);
        mHudHeartsFour = Gizmo.getAssetManager().get("res/image/hud/hud_hearts_four.png", Texture.class);
        mHudHeartsFive = Gizmo.getAssetManager().get("res/image/hud/hud_hearts_five.png", Texture.class);
        mHudHeartsSix = Gizmo.getAssetManager().get("res/image/hud/hud_hearts_six.png", Texture.class);
        mHudHeartsSeven = Gizmo.getAssetManager().get("res/image/hud/hud_hearts_seven.png", Texture.class);
        mHudHeartsEight = Gizmo.getAssetManager().get("res/image/hud/hud_hearts_eight.png", Texture.class);
        mHudHeartsNine = Gizmo.getAssetManager().get("res/image/hud/hud_hearts_nine.png", Texture.class);
        mHudHeartsTen = Gizmo.getAssetManager().get("res/image/hud/hud_hearts_ten.png", Texture.class);

        mHudCurrentItemEmpty = Gizmo.getAssetManager().get("res/image/hud/hud_current_item_empty.png", Texture.class);
        mHudCurrentItemBoomerangWood = Gizmo.getAssetManager().get("res/image/hud/hud_current_item_boomerang_wood.png", Texture.class);
        mHudCurrentItemBoomerangEmerald = Gizmo.getAssetManager().get("res/image/hud/hud_current_item_boomerang_emerald.png", Texture.class);
        mHudCurrentItemBoomerangAmethyst = Gizmo.getAssetManager().get("res/image/hud/hud_current_item_boomerang_amethyst.png", Texture.class);
        mHudCurrentItemBoomerangBloodstone = Gizmo.getAssetManager().get("res/image/hud/hud_current_item_boomerang_bloodstone.png", Texture.class);

        mHudCurrentItemPotionLife = Gizmo.getAssetManager().get("res/image/hud/hud_current_item_potion_life.png", Texture.class);
        mHudCurrentItemBomb = Gizmo.getAssetManager().get("res/image/hud/hud_current_item_bomb.png", Texture.class);

        mHudCrystalsCount = Gizmo.getAssetManager().get("res/image/hud/hud_blue_crystals.png", Texture.class);

        mHudCurrentPrimaryItemTexture = Gizmo.getAssetManager().get("res/image/hud/hud_current_item_empty.png", Texture.class);
        mHudCurrentSecondaryItemTexture = Gizmo.getAssetManager().get("res/image/hud/hud_current_item_empty.png", Texture.class);

        mCurrentLivesTexture = mHudLivesOne;
        mCurrentHealthTexture = mHudHeartsTwo;

        mHudCurrentPrimaryItemTexture = mHudCurrentItemEmpty;
        mHudCurrentSecondaryItemTexture = mHudCurrentItemEmpty;

        mBitmapFont.getData().setScale(0.3f, 0.3f);
        mLayoutBlueCrystalCount = new GlyphLayout(mBitmapFont, mInitialCrystalString);
        mLayoutSecondaryItemCount = new GlyphLayout(mBitmapFont, mInitialSecondaryItemCountString);
    }

    public void addListener(IHudStage listener) {
        listeners.add(listener);
    }

    @Override
    public void draw() {
        super.draw();

        mSpriteBatch.setProjectionMatrix(mOrthographicCamera.combined);

        if (!mProjectionMatrixSet) {
            mWhiteShapeRendererTop.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
            mWhiteShapeRendererBottom.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
            mRedShapeRendererTop.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
            mRedShapeRendererBottom.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
            mTransitionShapeRenderer.setProjectionMatrix(mSpriteBatch.getProjectionMatrix());
        }

        mWhiteShapeRendererTop.begin(ShapeRenderer.ShapeType.Filled);
        mWhiteShapeRendererTop.setColor(Color.WHITE);
        mWhiteShapeRendererTop.rect(mHudHealthPosition.x, mHudHealthPosition.y + mCurrentHealthTexture.getHeight() - mRedShapeHeight, mCurrentHealthTexture.getWidth() - getBackgroundWidth(mHearts), mRedShapeHeight);
        mWhiteShapeRendererTop.end();

        if (mHearts > 5) {
            mWhiteShapeRendererBottom.begin(ShapeRenderer.ShapeType.Filled);
            mWhiteShapeRendererBottom.setColor(Color.WHITE);
            mWhiteShapeRendererBottom.rect(mHudHealthPosition.x, mHudHealthPosition.y + mCurrentHealthTexture.getHeight() - mRedShapeHeight*2, mCurrentHealthTexture.getWidth() - getBackgroundWidth(mHearts), mRedShapeHeight);
            mWhiteShapeRendererBottom.end();
        }

        mRedShapeRendererTop.begin(ShapeRenderer.ShapeType.Filled);
        mRedShapeRendererTop.setColor(193 / 255f, 0, 0, 1);
        mRedShapeRendererTop.rect(mHudHealthPosition.x + 3, mHudHealthPosition.y + mCurrentHealthTexture.getHeight() - mRedShapeHeight, mRedShapeWidthTop, mRedShapeHeight);
        mRedShapeRendererTop.end();

        if (mHearts > 5) {
            mRedShapeRendererBottom.begin(ShapeRenderer.ShapeType.Filled);
            mRedShapeRendererBottom.setColor(193 / 255f, 0, 0, 1);
            mRedShapeRendererBottom.rect(mHudHealthPosition.x + 3, mHudHealthPosition.y + mCurrentHealthTexture.getHeight() - mRedShapeHeight*2, mRedShapeWidthBottom, mRedShapeHeight);
            mRedShapeRendererBottom.end();
        }

        mSpriteBatch.enableBlending();
        mSpriteBatch.begin();
            mSpriteBatch.draw(mCurrentLivesTexture, mHudLivesPosition.x, mHudLivesPosition.y);
            mSpriteBatch.draw(mCurrentHealthTexture, mHudHealthPosition.x, mHudHealthPosition.y);
            mSpriteBatch.draw(mHudCrystalsCount, mHudCrystalsPosition.x, mHudCrystalsPosition.y);
            mSpriteBatch.draw(mHudCurrentPrimaryItemTexture, mHudCurrentPrimaryItemPosition.x, mHudCurrentPrimaryItemPosition.y);
            mSpriteBatch.draw(mHudCurrentSecondaryItemTexture, mHudCurrentSecondaryItemPosition.x, mHudCurrentSecondaryItemPosition.y);
            mBitmapFont.draw(mSpriteBatch, mBlueCrystalStringDisplay, mHudCrystalsPosition.x + 36, mHudCrystalsPosition.y + 23);
            mBitmapFont.draw(mSpriteBatch, mSecondaryItemCountStringDisplay, mHudCurrentSecondaryItemPosition.x + 20, mHudCurrentSecondaryItemPosition.y + 11);
            mBitmapFont.setColor(1, 1, 1, 1);
        mSpriteBatch.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (mShowTransition) {
            dimTheStageLights(delta);
        }
    }

    private int getBackgroundWidth(int hearts) {
        if (hearts == 2) {
            return 90;
        }
        else if (hearts == 3) {
            return 70;
        }
        else if (hearts == 4) {
            return 50;
        }
        else {
            return 32;
        }
    }

    private void dimTheStageLights(float delta) {
        // fade out
        if (mFadeStatus.equalsIgnoreCase(FADE_OUT)) {
            mTimeAccumulated += delta;
            mNewAlpha = 1 - (mTimeAccumulated / TIME_TO_FADE);
            if (mNewAlpha < 0) {
                mNewAlpha = 0;
            }
            drawOverlay(0, 0, 0, mNewAlpha);
            if (mNewAlpha <= 0) {
                mTimeAccumulated = 0;
                mFadeStatus = FADE_COMPLETE;
            }
        }

        // fade in
        else if (mFadeStatus.equalsIgnoreCase(FADE_IN)) {
            mTimeAccumulated += delta;
            mNewAlpha += (mTimeAccumulated / TIME_TO_FADE);
            if (mNewAlpha > 1) {
                mNewAlpha = 1;
            }
            drawOverlay(0, 0, 0, mNewAlpha);
            if (mNewAlpha >= 1 && !isFadeInAlreadyRun) {
                isFadeInAlreadyRun = true; //this is the PROBLEM
                //fire off listener to load new room
                for(IHudStage listener : listeners){
                    listener.hudFadeInComplete(mDoorType);
                }
                startFadeDelay(delta);
            }
        }

        // fade complete
        if (mFadeStatus.equalsIgnoreCase(FADE_COMPLETE)) {
            GLog.d(TAG, "fading complete");
            mShowTransition = false;
            isFadeInAlreadyRun = false;
        }
    }

    private void startFadeDelay(float delta) {
        // this could be used to adjust
        // the fade back in
        //mTimeAccumulated += delta;
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(FADE_DELAY);
                }
                catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                finally {
                    mFadeStatus = FADE_OUT;
                }
            }
        };
        thread.start();
    }

    private void drawOverlay(float r, float g, float b, float a) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        mTransitionShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        mTransitionShapeRenderer.setColor(r, g, b, a);
        mTransitionShapeRenderer.rect(0, 0, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mTransitionShapeRenderer.end();
    }

    public void setTransition(String doorType, boolean transition) {
        mDoorType = doorType;
        mShowTransition = transition;
        mFadeStatus = FADE_IN;
    }

    @Override
    public void setHudSelectedPrimaryItem(BasePlayerItemModel item) {
        if (item != null && item.getItemType() != null && item.getItemType() != "") {
            GLog.d(TAG, "setHudSelectedPrimaryItem: " + item.getItemType());
            if (item.getItemType().equals(BoomerangWoodModel.BOOMERANG_WOOD)) {
                mHudCurrentPrimaryItemTexture = mHudCurrentItemBoomerangWood;
            }
            else if (item.getItemType().equals(BoomerangEmeraldModel.BOOMERANG_EMERALD)) {
                mHudCurrentPrimaryItemTexture = mHudCurrentItemBoomerangEmerald;
            }
            else if (item.getItemType().equals(BoomerangAmethystModel.BOOMERANG_AMETHYST)) {
                mHudCurrentPrimaryItemTexture = mHudCurrentItemBoomerangAmethyst;
            }
            else if (item.getItemType().equals(BoomerangBloodStoneModel.BOOMERANG_BLOODSTONE)) {
                mHudCurrentPrimaryItemTexture = mHudCurrentItemBoomerangBloodstone;
            }
            else {
                mHudCurrentPrimaryItemTexture = mHudCurrentItemEmpty;
            }
        }
        else {
            mHudCurrentPrimaryItemTexture = mHudCurrentItemEmpty;
        }
    }

    @Override
    public void setHudSelectedSecondaryItem(BasePlayerItemModel item, int numItems) {
        if (item != null && item.getItemType() != null && item.getItemType() != "") {
            GLog.d(TAG, "setHudSelectedSecondaryItem: " + item.getItemType());

            if (item.getItemType().equals(PotionLifeModel.POTION_LIFE)) {
                mHudCurrentSecondaryItemTexture = mHudCurrentItemPotionLife;
            }
            else if (item.getItemType().equals(BombModel.BOMB)) {
                mHudCurrentSecondaryItemTexture = mHudCurrentItemBomb;
            }

            if (numItems > 1) {
                GLog.d(TAG, "select: total secondary items: " + numItems);
                mSecondaryItemCountStringDisplay = Integer.toString(numItems);
            }
            else {
                mSecondaryItemCountStringDisplay = "";
            }

        }
        else {
            mHudCurrentSecondaryItemTexture = mHudCurrentItemEmpty;
        }
    }

    @Override
    public void updateSelectedSecondaryItemCount(int numItems) {
        if (numItems > 1) {
            GLog.d(TAG, "update: total secondary items: " + numItems);
            mSecondaryItemCountStringDisplay = Integer.toString(numItems);
        }
        else {
            mSecondaryItemCountStringDisplay = "";
        }
    }

    public void updateHudLayout(Float scale, Vector2 crop, float gameWidth, float gameHeight) {
        GLog.d(TAG, "updateHudLayout");
        mHudLivesPosition.x = Math.abs(crop.x) / scale;
        mHudLivesPosition.y = Math.abs(gameHeight - mCurrentLivesTexture.getHeight() * scale - HUD_PADDING * scale) / scale;

        mHudHealthPosition.x = Math.abs(crop.x) / scale;
        mHudHealthPosition.y = Math.abs(gameHeight - mCurrentLivesTexture.getHeight() * scale - mCurrentHealthTexture.getHeight() * scale - HUD_PADDING * scale) / scale;

        mHudCrystalsPosition.x = Math.abs(gameWidth - mHudCrystalsCount.getWidth() * scale + crop.x) / scale;
        mHudCrystalsPosition.y = Math.abs(gameHeight - mHudCrystalsCount.getHeight() * scale - HUD_PADDING * scale) / scale;

        mHudCurrentPrimaryItemPosition.x = centerScreenX - mHudCurrentPrimaryItemTexture.getWidth() - 1;
        mHudCurrentPrimaryItemPosition.y = Math.abs(gameHeight - mHudCurrentPrimaryItemTexture.getHeight() * scale - HUD_PADDING * scale) / scale;

        mHudCurrentSecondaryItemPosition.x = centerScreenX + 1;
        mHudCurrentSecondaryItemPosition.y = Math.abs(gameHeight - mHudCurrentSecondaryItemTexture.getHeight() * scale - HUD_PADDING * scale) / scale;

        mOrthographicCamera.update();
    }

    @Override
    public void setHudHealthHearts(int hearts) {
        mHearts = hearts;
        GLog.d(TAG, "setHudHealthHearts: " + mHearts);
        if (mHearts == 2) {
            mCurrentHealthTexture = mHudHeartsTwo;
        }
        else if (mHearts == 3) {
            mCurrentHealthTexture = mHudHeartsThree;
        }
        else if (mHearts == 4) {
            mCurrentHealthTexture = mHudHeartsFour;
        }
        else if (mHearts == 5) {
            mCurrentHealthTexture = mHudHeartsFive;
        }
        else if (mHearts == 6) {
            mCurrentHealthTexture = mHudHeartsSix;
        }
        else if (mHearts == 7) {
            mCurrentHealthTexture = mHudHeartsSeven;
        }
        else if (mHearts == 8) {
            mCurrentHealthTexture = mHudHeartsEight;
        }
        else if (mHearts == 9) {
            mCurrentHealthTexture = mHudHeartsNine;
        }
        else if (mHearts == 10) {
            mCurrentHealthTexture = mHudHeartsTen;
        }
        resetHudShapes();
        setHudHealth(mHearts * PlayerModel.HEART_HEALTH_AMOUNT);
    }

    //TODO this is called twice for some reason when the level loads
    @Override
    public void setHudHealth(int health) {
        GLog.d(TAG, "setHudHealth");

        float fullHealth = mHearts * PlayerModel.HEART_HEALTH_AMOUNT;

        if (health > 50) {
            float percentFullBottom = (health - 50) / fullHealth;
            float newWidthBottom = percentFullBottom * mInitialWidth;
            mRedShapeWidthTop = mCurrentHealthTexture.getWidth() - 34;
            mRedShapeWidthBottom = getHealthBottom(health, newWidthBottom);
        }
        else {
            float percentFull = health / fullHealth;
            float newWidth = percentFull * mInitialWidth;
            mRedShapeWidthTop = getHealthTop(health, newWidth);
            mRedShapeWidthBottom = 0;
        }
    }

    private float getHealthTop(int health, float newWidth) {
        if ((mHearts >= 5) && health > 29) {
            newWidth = newWidth + 3;
        }
        else if (mHearts == 4 && health > 19) {
            newWidth = newWidth + 2;
        }
        else if (mHearts == 3 && health > 9) {
            newWidth = newWidth + 1;
        }
        return newWidth;
    }

    private float getHealthBottom(int health, float newWidth) {
        if (mHearts == 10 && health > 58) {
            newWidth = newWidth + 3;
        }
        else if (mHearts == 8 && health > 38) {
            newWidth = newWidth + 2;
        }
        else if (mHearts == 6 && health > 18) {
            newWidth = newWidth + 1;
        }
        return newWidth;
    }

    @Override
    public void resetHudShapes() {
        GLog.d(TAG, "resetHudShapes");
        mInitialWidth = mHearts * 18;
        mRedShapeWidthTop = mInitialWidth;
        mRedShapeHeight = 20;
    }

    @Override
    public void setHudLives(int lives) {
        mLives = lives;
        GLog.d(TAG, "setHudLives: " + mLives);
        if(mLives == 5) {
            mCurrentLivesTexture = mHudLivesFive;
        }
        else if (mLives == 4) {
            mCurrentLivesTexture = mHudLivesFour;
        }
        else if (mLives == 3) {
            mCurrentLivesTexture = mHudLivesThree;
        }
        else if (mLives == 2) {
            mCurrentLivesTexture = mHudLivesTwo;
        }
        else if (mLives == 1) {
            mCurrentLivesTexture = mHudLivesOne;
        }
    }

    @Override
    public void setCrystalCount(int crystalCount) {
        mCrystals = crystalCount;
        GLog.d(TAG, "setCrystalCount: " + mCrystals);
        mBlueCrystalStringDisplay = Integer.toString(mCrystals);
    }

    @Override
    public void dispose() {
        GLog.d(TAG, "dispose");
    }

}
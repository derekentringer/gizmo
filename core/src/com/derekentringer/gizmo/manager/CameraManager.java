package com.derekentringer.gizmo.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.derekentringer.gizmo.settings.Constants;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.map.MapParser;

public class CameraManager {

    private OrthographicCamera mMainCamera;
    private OrthographicCamera mMidBackgroundCamera;
    private OrthographicCamera mBackgroundCamera;

    private OrthographicCamera mBox2dDebugCamera;
    private Box2DDebugRenderer mBox2dDebugRenderer;

    private float mEffectiveViewportWidth;
    private float mEffectiveViewportHeight;

    public void createGameCameras() {
        createMainCamera();
        createMidBackgroundCamera();
        createBackgroundCamera();
        if(Constants.IS_DEBUG_BOX2D) {
            createDebugRendererCamera();
        }
    }

    public void createMainCamera() {
        mMainCamera = new OrthographicCamera();
        mMainCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mMainCamera.update();
    }

    public OrthographicCamera getMainCamera() {
        return mMainCamera;
    }

    public void createMidBackgroundCamera() {
        mMidBackgroundCamera = new OrthographicCamera();
        mMidBackgroundCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mMidBackgroundCamera.zoom = 1.3f;
        mMidBackgroundCamera.update();
    }

    public OrthographicCamera getMidBackgroundCamera() {
        return mMidBackgroundCamera;
    }

    public void createBackgroundCamera() {
        mBackgroundCamera = new OrthographicCamera();
        mBackgroundCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        mBackgroundCamera.zoom = 1.7f;
        mBackgroundCamera.update();
    }

    public OrthographicCamera getBackgroundCamera() {
        return mBackgroundCamera;
    }

    public void createDebugRendererCamera() {
        mBox2dDebugRenderer = new Box2DDebugRenderer();
        mBox2dDebugCamera = new OrthographicCamera();
        mBox2dDebugCamera.setToOrtho(false, WorldUtils.ppmCalc(Constants.GAME_WIDTH), WorldUtils.ppmCalc(Constants.GAME_HEIGHT));
        mBox2dDebugCamera.update();
    }

    public Box2DDebugRenderer getBox2dDebugRenderer() {
        return mBox2dDebugRenderer;
    }

    public OrthographicCamera getDebugRendererCamera() {
        return mBox2dDebugCamera;
    }

    public void updateCameraPlayerMovement(float playerX, float playerY, MapParser mapParser) {
        MapProperties prop = mapParser.getTiledMap().getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        mEffectiveViewportWidth = mMainCamera.viewportWidth * mMainCamera.zoom;
        mEffectiveViewportHeight = mMainCamera.viewportHeight * mMainCamera.zoom;

        float minWidth = mEffectiveViewportWidth / 2f;
        float minHeight = mEffectiveViewportHeight / 2f;
        float maxWidth = (mapWidth * mapParser.getTileSize()) - (mEffectiveViewportWidth / 2f);
        float maxHeight = (mapHeight * mapParser.getTileSize()) - (mEffectiveViewportHeight / 2f);

        mMainCamera.position.x = Math.round(MathUtils.clamp(mMainCamera.position.x + (playerX * Constants.PPM - mMainCamera.position.x) * 0.1f, minWidth, maxWidth));
        mMainCamera.position.y = Math.round(MathUtils.clamp(mMainCamera.position.y + (playerY * Constants.PPM - mMainCamera.position.y) * 0.1f, minHeight, maxHeight));

        mMainCamera.update();

        mMidBackgroundCamera.position.x = Math.round(MathUtils.clamp(mMidBackgroundCamera.position.x + (playerX * Constants.PPM - mMidBackgroundCamera.position.x) * 0.1f, minWidth, maxWidth));
        mMidBackgroundCamera.position.y = Math.round(MathUtils.clamp(mMidBackgroundCamera.position.y + (playerY * Constants.PPM - mMidBackgroundCamera.position.y) * 0.1f, minHeight, maxHeight));

        mMidBackgroundCamera.update();

        mBackgroundCamera.position.x = Math.round(MathUtils.clamp(mBackgroundCamera.position.x + (playerX * Constants.PPM - mBackgroundCamera.position.x) * 0.1f, minWidth, maxWidth));
        mBackgroundCamera.position.y = Math.round(MathUtils.clamp(mBackgroundCamera.position.y + (playerY * Constants.PPM - mBackgroundCamera.position.y) * 0.1f, minHeight, maxHeight));

        mBackgroundCamera.update();
    }

}
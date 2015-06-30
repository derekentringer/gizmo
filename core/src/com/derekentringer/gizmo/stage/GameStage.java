package com.derekentringer.gizmo.stage;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.Gizmo;
import com.derekentringer.gizmo.actor.data.DoorType;
import com.derekentringer.gizmo.actor.data.structure.DoorUserData;
import com.derekentringer.gizmo.actor.data.structure.GroundUserData;
import com.derekentringer.gizmo.actor.data.structure.WallUserData;
import com.derekentringer.gizmo.actor.player.IPlayerDelegate;
import com.derekentringer.gizmo.actor.player.PlayerActor;
import com.derekentringer.gizmo.actor.structure.DoorActor;
import com.derekentringer.gizmo.actor.structure.GroundActor;
import com.derekentringer.gizmo.actor.structure.WallActor;
import com.derekentringer.gizmo.level.Level;
import com.derekentringer.gizmo.util.FixtureUtils;
import com.derekentringer.gizmo.util.PlayerUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.constant.Constants;
import com.derekentringer.gizmo.util.input.UserInput;

public class GameStage extends Stage implements ContactListener, IPlayerDelegate {

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private OrthographicCamera tiledCamera;

    private World world;
    private Level level;

    private GroundActor groundActor;
    private WallActor wallActor;
    private DoorActor doorActor;
    private PlayerActor playerActor;

    private SpriteBatch spriteBatch;

    private float tileSize;

    private float effectiveViewportWidth;
    private float effectiveViewportHeight;
    
    public GameStage() {
        setupWorld();
        loadLevel();
        tileMapBodies();
        setupDebugRendererCamera();
        setupTiledCamera();
        startBackgroundMusic();
    }

    private void setupWorld() {
        spriteBatch = new SpriteBatch();
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        createPlayer();
    }

    private void setupDebugRendererCamera() {
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WorldUtils.ppmCalc(Constants.GAME_WIDTH), WorldUtils.ppmCalc(Constants.GAME_HEIGHT));
        camera.update();
    }

    private void setupTiledCamera() {
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        tiledCamera.update();
    }

    private void loadLevel() {
        level = new Level(Constants.LEVEL_SEVEN);
    }

    private void startBackgroundMusic() {
        Music backgroundMusic = Gizmo.assetManager.get("res/music/background.ogg", Music.class);
        if(!Constants.DEBUGGING) {
            backgroundMusic.play();
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.2f);
        }
    }

    private void tileMapBodies() {
        //TODO this can be enhanced for larger levels by creating a Manager -
        //http://siondream.com/blog/games/populate-your-box2d-world-using-the-libgdx-maps-api/

        TiledMapTileLayer layerGround = (TiledMapTileLayer) level.getTiledMap().getLayers().get("ground");
        tileSize = layerGround.getTileWidth();

        for(int row = 0; row < layerGround.getHeight(); row++) {
            for(int col = 0; col < layerGround.getWidth(); col++) {
                //check for empty cells
                TiledMapTileLayer.Cell cell = layerGround.getCell(col, row);
                if(cell == null) continue;
                if(cell.getTile() == null) continue;

                //if not empty, create our body for the cell
                groundActor = new GroundActor(WorldUtils.createStaticBody(new GroundUserData(), world, tileSize, row, col, false));
                addActor(groundActor);
            }
        }

        TiledMapTileLayer layerWall = (TiledMapTileLayer) level.getTiledMap().getLayers().get("wall");
        tileSize = layerWall.getTileWidth();

        for(int row = 0; row < layerWall.getHeight(); row++) {
            for(int col = 0; col < layerWall.getWidth(); col++) {
                //check for empty cells
                TiledMapTileLayer.Cell cell = layerWall.getCell(col, row);
                if(cell == null) continue;
                if(cell.getTile() == null) continue;

                //if not empty, create our body for the cell
                wallActor = new WallActor(WorldUtils.createStaticBody(new WallUserData(), world, tileSize, row, col, false));
                addActor(wallActor);
            }
        }

        TiledMapTileLayer layerDoorPrevious = (TiledMapTileLayer) level.getTiledMap().getLayers().get("doorprevious");
        tileSize = layerDoorPrevious.getTileWidth();

        for(int row = 0; row < layerDoorPrevious.getHeight(); row++) {
            for(int col = 0; col < layerDoorPrevious.getWidth(); col++) {
                //check for empty cells
                TiledMapTileLayer.Cell cell = layerDoorPrevious.getCell(col, row);
                if(cell == null) continue;
                if(cell.getTile() == null) continue;

                //if not empty, create our body for the cell
                doorActor = new DoorActor(WorldUtils.createStaticBody(new DoorUserData(), world, tileSize, row, col, true), DoorType.PREVIOUS);
                addActor(doorActor);
            }
        }

        TiledMapTileLayer layerDoorNext = (TiledMapTileLayer) level.getTiledMap().getLayers().get("doornext");
        tileSize = layerGround.getTileWidth();

        for(int row = 0; row < layerDoorNext.getHeight(); row++) {
            for(int col = 0; col < layerDoorNext.getWidth(); col++) {
                //check for empty cells
                TiledMapTileLayer.Cell cell = layerDoorNext.getCell(col, row);
                if(cell == null) continue;
                if(cell.getTile() == null) continue;

                //if not empty, create our body for the cell
                doorActor = new DoorActor(WorldUtils.createStaticBody(new DoorUserData(), world, tileSize, row, col, true), DoorType.NEXT);
                addActor(doorActor);
            }
        }
    }

    private void createPlayer() {
        playerActor = new PlayerActor(PlayerUtils.createPlayer(world));
        addActor(playerActor);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a =  contact.getFixtureA();
        Fixture b =  contact.getFixtureB();

        if(FixtureUtils.fixtureIsPlayerHitArea(a)) {
            playerActor.setIsOnGround(true);
        }
        else if(FixtureUtils.fixtureIsPlayerHitArea(b)) {
            playerActor.setIsOnGround(true);
        }

        if(FixtureUtils.fixtureIsDoor(a)) {
            playerActor.setIsAtDoor(true);
        }
        else if(FixtureUtils.fixtureIsDoor(b)) {
            playerActor.setIsAtDoor(true);
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    @Override
    public void draw() {
        super.draw();

        //tiled maps render camera
        level.getTiledMapRenderer().setView(tiledCamera);
        level.getTiledMapRenderer().render();

        //world debugRenderer camera
        //renderer.render(world, camera.combined);

        spriteBatch.setProjectionMatrix(tiledCamera.combined);
        playerActor.render(spriteBatch);

        updateCameraPlayerMovement(playerActor.getPosition().x, playerActor.getPosition().y);

        handlePlayerPosition(playerActor.getPosition().y);
    }

    private void updateCameraPlayerMovement(float playerX, float playerY) {
        MapProperties prop = level.getTiledMap().getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        effectiveViewportWidth = tiledCamera.viewportWidth * tiledCamera.zoom;
        effectiveViewportHeight = tiledCamera.viewportHeight * tiledCamera.zoom;

        float minWidth = effectiveViewportWidth / 2f;
        float minHeight = effectiveViewportHeight / 2f;
        float maxWidth = (mapWidth * tileSize) - (effectiveViewportWidth / 2f);
        float maxHeight = (mapHeight * tileSize) - (effectiveViewportHeight / 2f);

        tiledCamera.position.x = Math.round(MathUtils.clamp(playerX * Constants.PPM, minWidth, maxWidth));
        tiledCamera.position.y = Math.round(MathUtils.clamp(playerY * Constants.PPM, minHeight, maxHeight));

        tiledCamera.update();
    }

    private void handlePlayerPosition(float playerY) {
        if(playerY * Constants.PPM < 0) {
            playerIsOffMap(true);
        }
    }

    private void handleInput() {
        if(UserInput.isDown(UserInput.JUMP_BUTTON)) {
            if(playerActor.getIsOnGround()) {
                playerActor.setIsOnGround(false);
                playerActor.jump();
            }
        }

        if(UserInput.isDown(UserInput.LEFT_BUTTON)) {
            playerActor.moveLeft();
        }

        if(UserInput.isDown(UserInput.RIGHT_BUTTON)) {
            playerActor.moveRight();
        }

        if(!UserInput.isDown(UserInput.LEFT_BUTTON) && !UserInput.isDown(UserInput.RIGHT_BUTTON)) {
            playerActor.stoppedMoving();
        }

        if(!UserInput.isDown(UserInput.JUMP_BUTTON)) {
            playerActor.stopJumping();
        }

        if(UserInput.isDown(UserInput.ENTER_DOOR)) {
            if(playerActor.getIsAtDoor()) {
                //TODO load the correct level
                if(doorActor.getDoorType() == DoorType.PREVIOUS) {
                    //
                }
                else if(doorActor.getDoorType() == DoorType.NEXT) {
                    createPlayer();
                }
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        UserInput.update();
        handleInput();

        playerActor.update(delta);

        Constants.ACCUMULATOR += delta;
        while (Constants.ACCUMULATOR >= delta) {
            world.step(Constants.TIME_STEP, 6, 2);
            Constants.ACCUMULATOR -= Constants.TIME_STEP;
        }
    }

    @Override
    public void playerIsOffMap(boolean offMap) {
        createPlayer();
    }

}
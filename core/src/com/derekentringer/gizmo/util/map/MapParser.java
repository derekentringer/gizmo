package com.derekentringer.gizmo.util.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.components.actor.boss.PhantomBossActor;
import com.derekentringer.gizmo.components.actor.enemy.PhantomActor;
import com.derekentringer.gizmo.components.actor.object.HeartActor;
import com.derekentringer.gizmo.components.actor.object.KeyActor;
import com.derekentringer.gizmo.components.actor.object.LifeActor;
import com.derekentringer.gizmo.components.actor.player.PlayerActor;
import com.derekentringer.gizmo.components.actor.structure.GroundActor;
import com.derekentringer.gizmo.components.actor.structure.WallActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBlackActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBloodActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorBronzeActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorGoldActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorOffActor;
import com.derekentringer.gizmo.components.actor.structure.door.DoorOtherActor;
import com.derekentringer.gizmo.components.stage.GameStage;
import com.derekentringer.gizmo.model.enemy.PhantomLargeModel;
import com.derekentringer.gizmo.model.enemy.PhantomModel;
import com.derekentringer.gizmo.model.level.LevelModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.object.LifeModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.model.structure.DoorModel;
import com.derekentringer.gizmo.model.structure.DoorOffModel;
import com.derekentringer.gizmo.model.structure.DoorType;
import com.derekentringer.gizmo.model.structure.GroundModel;
import com.derekentringer.gizmo.model.structure.WallModel;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.EnemyUtils;
import com.derekentringer.gizmo.util.ObjectUtils;
import com.derekentringer.gizmo.util.PlayerUtils;
import com.derekentringer.gizmo.util.map.interfaces.IMapParser;

import java.util.ArrayList;

public class MapParser extends Stage {

    private static final String TAG = MapParser.class.getSimpleName();

    private final ArrayList<BaseActor> mActorsArray = new ArrayList<BaseActor>();
    private final ArrayList<BaseActor> mTempActorsArray = new ArrayList<BaseActor>();

    private ArrayList<IMapParser> listeners = new ArrayList<IMapParser>();

    private GameStage mGameStage;
    private TiledMap mTiledMap;
    private TiledMap mTiledMapMidBackground;
    private TiledMap mTiledMapBackground;

    private TiledMapRenderer mTiledMapRenderer;
    private TiledMapRenderer mTiledMapMidBackgroundRenderer;
    private TiledMapRenderer mTiledMapBackgroundRenderer;

    private float mTileSize;

    private LevelModel mLoadedLevelModel;

    public MapParser(GameStage gameStage, LevelModel loadedLevelModel, String tileMapName, String tileMapMidBackground, String tileMapBackground) {
        mGameStage = gameStage;

        mLoadedLevelModel = loadedLevelModel;

        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMagFilter = Texture.TextureFilter.Nearest;
        params.textureMinFilter = Texture.TextureFilter.Nearest;

        mTiledMap = new TmxMapLoader().load(tileMapName, params);
        mTiledMapRenderer = new OrthogonalTiledMapRenderer(mTiledMap);

        mTiledMapMidBackground = new TmxMapLoader().load(tileMapMidBackground, params);
        mTiledMapMidBackgroundRenderer = new OrthogonalTiledMapRenderer(mTiledMapMidBackground);

        mTiledMapBackground = new TmxMapLoader().load(tileMapBackground, params);
        mTiledMapBackgroundRenderer = new OrthogonalTiledMapRenderer(mTiledMapBackground);
    }

    public void addListener(IMapParser listener) {
        listeners.add(listener);
    }

    public void addToActorsArray(BaseActor actor) {
        mActorsArray.add(actor);
    }

    public ArrayList<BaseActor> getActorsArray() {
        return mActorsArray;
    }

    public void addToTempActorsArray(BaseActor actor) {
        mTempActorsArray.add(actor);
    }

    public ArrayList<BaseActor> getTempActorsArray() {
        return mTempActorsArray;
    }

    public void createTileMapLayers(World world) {
        for (int curLayer = 0; curLayer < getTiledMap().getLayers().getCount(); curLayer++) {
            if (getTiledMap().getLayers().get(curLayer) instanceof TiledMapTileLayer) {

                TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) getTiledMap().getLayers().get(curLayer);

                if (tiledMapTileLayer == null) {
                    return;
                }

                String curLayerName = tiledMapTileLayer.getName();
                mTileSize = tiledMapTileLayer.getTileWidth();

                for (int row = 0; row < tiledMapTileLayer.getHeight(); row++) {
                    for (int col = 0; col < tiledMapTileLayer.getWidth(); col++) {

                        //check for empty cells
                        TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell(col, row);
                        if (cell == null) {
                            continue;
                        }
                        if (cell.getTile() == null) {
                            continue;
                        }

                        if (curLayerName.equalsIgnoreCase(GroundModel.TILE_GROUND)) {
                            GroundActor groundActor = new GroundActor(BodyUtils.createStaticBody(new GroundModel(), world, mTileSize, row, col, false));
                            addActor(groundActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(WallModel.TILE_WALL)) {
                            WallActor wallActor = new WallActor(BodyUtils.createStaticBody(new WallModel(), world, mTileSize, row, col, false));
                            addActor(wallActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.PREVIOUS)) {
                            DoorActor doorActor = new DoorActor(BodyUtils.createStaticBody(new DoorModel(DoorType.PREVIOUS), world, mTileSize, row, col, true));
                            addActor(doorActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.NEXT)) {
                            DoorActor doorActor = new DoorActor(BodyUtils.createStaticBody(new DoorModel(DoorType.NEXT), world, mTileSize, row, col, true));
                            addActor(doorActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.DOOR_OFF)) {
                            DoorOffActor doorOffActor = new DoorOffActor(BodyUtils.createStaticBody(new DoorOffModel(), world, mTileSize, row, col, true));
                            addActor(doorOffActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.LOCKED_GOLD)) {
                            createLockedGoldDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get("levelnumber").toString()),
                                    tiledMapTileLayer.getProperties().get("destination").toString(), checkIfDoorLocked(DoorType.LOCKED_GOLD), row, col);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.LOCKED_BRONZE)) {
                            createLockedBronzeDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get("levelnumber").toString()),
                                    tiledMapTileLayer.getProperties().get("destination").toString(), checkIfDoorLocked(DoorType.LOCKED_BRONZE), row, col);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.LOCKED_BLOOD)) {
                            createLockedBloodDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get("levelnumber").toString()),
                                    tiledMapTileLayer.getProperties().get("destination").toString(), checkIfDoorLocked(DoorType.LOCKED_BLOOD), row, col);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.LOCKED_BLACK)) {
                            createLockedBlackDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get("levelnumber").toString()),
                                    tiledMapTileLayer.getProperties().get("destination").toString(), checkIfDoorLocked(DoorType.LOCKED_BLACK), row, col);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.OTHER)) {
                            createOtherDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get("levelnumber").toString()),
                                    tiledMapTileLayer.getProperties().get("destination").toString(), false, row, col);
                        }
                    }
                }
            }
        }
    }

    public void createTileMapObjects(World world, String whichDoor) {
        for (int curLayer = 0; curLayer < getTiledMap().getLayers().getCount(); curLayer++) {
            MapLayer mapLayer = getTiledMap().getLayers().get(curLayer);

            if (mapLayer == null) {
                return;
            }

            for (MapObject mapObject : mapLayer.getObjects()) {
                if (mapLayer.getName().equalsIgnoreCase(PhantomModel.PHANTOM)) {
                    PhantomActor phantomActor = new PhantomActor(EnemyUtils.createPhantom(new PhantomModel(), world, getMapObjectCoords(mapObject)));
                    phantomActor.setName(PhantomModel.PHANTOM);
                    addActor(phantomActor);
                    addToActorsArray(phantomActor);
                }
                else if (mapLayer.getName().equalsIgnoreCase(PhantomLargeModel.PHANTOM_LARGE)) {
                    PhantomBossActor phantomBoss = new PhantomBossActor(world, mGameStage, EnemyUtils.createLargePhantom(new PhantomLargeModel(), world, getMapObjectCoords(mapObject)));
                    phantomBoss.setName(PhantomLargeModel.PHANTOM_LARGE);
                    addActor(phantomBoss);
                    addToActorsArray(phantomBoss);
                    phantomBoss.addListener(mGameStage);
                }
                else if (mapLayer.getName().equalsIgnoreCase(PlayerModel.PLAYER_DESTINATIONS)) {
                    if (whichDoor.equalsIgnoreCase(mapObject.getProperties().get("name").toString())) {
                        createPlayerActor(world, getMapObjectCoords(mapObject));
                    }
                }
                else if (mapLayer.getName().equalsIgnoreCase(KeyModel.KEY)) {
                    String keyType = (String) mapObject.getProperties().get("keyType");
                    if (!loopThruPickedUpKeysArray(mLoadedLevelModel.getPickedUpKeys(), keyType)) {
                        KeyActor keyActor = new KeyActor(ObjectUtils.createKey(new KeyModel(keyType), world, getMapObjectCoords(mapObject)), keyType);
                        keyActor.setName(KeyModel.KEY);
                        addActor(keyActor);
                        addToActorsArray(keyActor);
                    }
                }
                else if (mapLayer.getName().equalsIgnoreCase(HeartModel.HEART)) {
                    // do not load a heart if it was picked up already
                    // only supporting one heart per level atm
                    if (mLoadedLevelModel != null && mLoadedLevelModel.getPickedUpHearts().size() == 0) {
                        HeartActor heartActor = new HeartActor(ObjectUtils.createHeart(new HeartModel(), world, getMapObjectCoords(mapObject)));
                        heartActor.setName(HeartModel.HEART);
                        addActor(heartActor);
                        addToActorsArray(heartActor);
                    }
                }
                else if (mapLayer.getName().equalsIgnoreCase(LifeModel.LIFE)) {
                    // do not load a heart if it was picked up already
                    // only supporting one heart per level atm
                    if (mLoadedLevelModel != null && mLoadedLevelModel.getPickedUpLives().size() == 0) {
                        LifeActor lifeActor = new LifeActor(ObjectUtils.createLife(new LifeModel(), world, getMapObjectCoords(mapObject)));
                        lifeActor.setName(LifeModel.LIFE);
                        addActor(lifeActor);
                        addToActorsArray(lifeActor);
                    }
                }
            }
        }
    }

    private void createPlayerActor(World world, Vector2 coordinates) {
        PlayerActor playerActor = new PlayerActor(PlayerUtils.createPlayer(world, coordinates));
        playerActor.setName(PlayerModel.PLAYER);
        addActor(playerActor);
        addToActorsArray(playerActor);
        for(IMapParser listener : listeners){
            listener.setPlayerActor(playerActor);
        }
    }

    private void createLockedGoldDoorActor(World world, int levelNumber, String doorTypeDest, boolean isLocked, int row, int col) {
        DoorGoldActor doorGoldActor = new DoorGoldActor(BodyUtils.createStaticBody(new DoorModel(DoorType.LOCKED_GOLD, levelNumber, doorTypeDest, isLocked), world, mTileSize, row, col, true), isLocked);
        doorGoldActor.setName(DoorType.LOCKED_GOLD);
        addActor(doorGoldActor);
        addToActorsArray(doorGoldActor);
        for(IMapParser listener : listeners){
            listener.setLockedGoldDoor(doorGoldActor);
        }
    }

    private void createLockedBronzeDoorActor(World world, int levelNumber, String doorTypeDest, boolean isLocked, int row, int col) {
        DoorBronzeActor doorBronzeActor = new DoorBronzeActor(BodyUtils.createStaticBody(new DoorModel(DoorType.LOCKED_BRONZE, levelNumber, doorTypeDest, isLocked), world, mTileSize, row, col, true), isLocked);
        doorBronzeActor.setName(DoorType.LOCKED_BRONZE);
        addActor(doorBronzeActor);
        addToActorsArray(doorBronzeActor);
        for(IMapParser listener : listeners){
            listener.setLockedBronzeDoor(doorBronzeActor);
        }
    }

    private void createLockedBloodDoorActor(World world, int levelNumber, String doorTypeDest, boolean isLocked, int row, int col) {
        DoorBloodActor doorBloodActor = new DoorBloodActor(BodyUtils.createStaticBody(new DoorModel(DoorType.LOCKED_BLOOD, levelNumber, doorTypeDest, isLocked), world, mTileSize, row, col, true), isLocked);
        doorBloodActor.setName(DoorType.LOCKED_BLOOD);
        addActor(doorBloodActor);
        addToActorsArray(doorBloodActor);
        for(IMapParser listener : listeners){
            listener.setLockedBloodDoor(doorBloodActor);
        }
    }

    private void createLockedBlackDoorActor(World world, int levelNumber, String doorTypeDest, boolean isLocked, int row, int col) {
        DoorBlackActor doorBlackActor = new DoorBlackActor(BodyUtils.createStaticBody(new DoorModel(DoorType.LOCKED_BLACK, levelNumber, doorTypeDest, isLocked), world, mTileSize, row, col, true), isLocked);
        doorBlackActor.setName(DoorType.LOCKED_BLACK);
        addActor(doorBlackActor);
        addToActorsArray(doorBlackActor);
        for(IMapParser listener : listeners){
            listener.setLockedBlackDoor(doorBlackActor);
        }
    }

    private void createOtherDoorActor(World world, int levelNumber, String doorTypeDest, boolean isLocked, int row, int col) {
        DoorOtherActor doorOtherActor = new DoorOtherActor(BodyUtils.createStaticBody(new DoorModel(DoorType.OTHER, levelNumber, doorTypeDest, isLocked), world, mTileSize, row, col, true));
        addActor(doorOtherActor);
    }

    private Vector2 getMapObjectCoords(MapObject mapObject) {
        Vector2 mapObjectCoordinates = new Vector2();
        Float xPosD = (Float) mapObject.getProperties().get("x");
        Float yPosD = (Float) mapObject.getProperties().get("y");
        mapObjectCoordinates.x = xPosD.intValue();
        mapObjectCoordinates.y = yPosD.intValue();
        return mapObjectCoordinates;
    }

    private static boolean loopThruPickedUpKeysArray(ArrayList<KeyModel> array, String targetValue) {
        for (KeyModel lookingForKey : array) {
            if (lookingForKey.getKeyType().equals(targetValue)) {
                return true;
            }
        }
        return false;
    }

    private static boolean loopThruOpenedDoorsArray(ArrayList<DoorModel> array, String targetValue) {
        for (DoorModel lookingForDoor : array) {
            if (lookingForDoor.getDoorType().equals(targetValue)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfDoorLocked(String doorType) {
        return !loopThruOpenedDoorsArray(mLoadedLevelModel.getOpenedDoors(), doorType);
    }

    public void destroyTiledMap() {
        mTiledMap.dispose();
    }

    public TiledMap getTiledMap() {
        return mTiledMap;
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return mTiledMapRenderer;
    }

    public TiledMapRenderer getTiledMapMidBackgroundRenderer() {
        return mTiledMapMidBackgroundRenderer;
    }

    public TiledMapRenderer getTiledMapBackgroundRenderer() {
        return mTiledMapBackgroundRenderer;
    }

    public float getTileSize() {
        return mTileSize;
    }

}
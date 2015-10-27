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
import com.derekentringer.gizmo.component.actor.BaseActor;
import com.derekentringer.gizmo.component.actor.boss.phantom.PhantomBossActor;
import com.derekentringer.gizmo.component.actor.enemy.PhantomActor;
import com.derekentringer.gizmo.component.actor.object.BoomerangActor;
import com.derekentringer.gizmo.component.actor.object.HeartActor;
import com.derekentringer.gizmo.component.actor.object.KeyActor;
import com.derekentringer.gizmo.component.actor.object.LifeActor;
import com.derekentringer.gizmo.component.actor.player.PlayerActor;
import com.derekentringer.gizmo.component.actor.structure.GroundActor;
import com.derekentringer.gizmo.component.actor.structure.LavaActor;
import com.derekentringer.gizmo.component.actor.structure.WallActor;
import com.derekentringer.gizmo.component.actor.structure.destroyable.DestroyableBlockClayActor;
import com.derekentringer.gizmo.component.actor.structure.destroyable.DestroyableBlockDirtActor;
import com.derekentringer.gizmo.component.actor.structure.destroyable.DestroyableBlockFallActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorBlackActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorBloodActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorBronzeActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorGoldActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorOffActor;
import com.derekentringer.gizmo.component.actor.structure.door.DoorOtherActor;
import com.derekentringer.gizmo.component.stage.GameStage;
import com.derekentringer.gizmo.model.BaseModel;
import com.derekentringer.gizmo.model.enemy.BaseEnemyModel;
import com.derekentringer.gizmo.model.enemy.PhantomLargeModel;
import com.derekentringer.gizmo.model.enemy.PhantomModel;
import com.derekentringer.gizmo.model.item.BaseItemModel;
import com.derekentringer.gizmo.model.level.LevelModel;
import com.derekentringer.gizmo.model.object.BoomerangModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
import com.derekentringer.gizmo.model.object.LifeModel;
import com.derekentringer.gizmo.model.player.PlayerModel;
import com.derekentringer.gizmo.model.structure.GroundModel;
import com.derekentringer.gizmo.model.structure.LavaModel;
import com.derekentringer.gizmo.model.structure.WallModel;
import com.derekentringer.gizmo.model.structure.destroyable.BaseDestroyableModel;
import com.derekentringer.gizmo.model.structure.destroyable.DestroyableBlockClayModel;
import com.derekentringer.gizmo.model.structure.destroyable.DestroyableBlockDirtModel;
import com.derekentringer.gizmo.model.structure.destroyable.DestroyableBlockFallModel;
import com.derekentringer.gizmo.model.structure.door.DoorModel;
import com.derekentringer.gizmo.model.structure.door.DoorOffModel;
import com.derekentringer.gizmo.model.structure.door.DoorType;
import com.derekentringer.gizmo.util.BodyUtils;
import com.derekentringer.gizmo.util.EnemyUtils;
import com.derekentringer.gizmo.util.ObjectUtils;
import com.derekentringer.gizmo.util.PlayerUtils;
import com.derekentringer.gizmo.util.WorldUtils;
import com.derekentringer.gizmo.util.map.interfaces.IMapParser;

import java.util.ArrayList;

public class MapParser extends Stage {

    private static final String TAG = MapParser.class.getSimpleName();

    private final ArrayList<BaseActor> mActorsArray = new ArrayList<BaseActor>();
    private final ArrayList<Vector2> mDroppedItemPositionArray = new ArrayList<Vector2>();
    private final ArrayList<Vector2> mBossDroppedItemPositionArray = new ArrayList<Vector2>();
    private final ArrayList<BaseActor> mTempActorsArray = new ArrayList<BaseActor>();

    private ArrayList<IMapParser> listeners = new ArrayList<IMapParser>();

    private static final String LEVEL_NUMBER = "level_number";
    private static final String DESTINATION = "destination";
    private static final String DESTINATION_NAME = "destination_name";
    private static final String KEY_TYPE = "key_type";
    private static final String BOSS_TYPE = "boss_type";

    private static final String ITEM_TYPE = "item_type";

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

    public ArrayList<Vector2> getDroppedItemPositionArray() {
        return mDroppedItemPositionArray;
    }

    public void addToDroppedItemPositionArray(Vector2 position) {
        mDroppedItemPositionArray.add(position);
    }

    public void resetDroppedItemPositionArray() {
        mDroppedItemPositionArray.clear();
    }

    public ArrayList<Vector2> getBossDroppedItemPositionArray() {
        return mBossDroppedItemPositionArray;
    }

    public void addToBossDroppedItemPositionArray(Vector2 position) {
        mBossDroppedItemPositionArray.add(position);
    }

    public void resetBossDroppedItemPositionArray() {
        mBossDroppedItemPositionArray.clear();
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

                        if (curLayerName.equalsIgnoreCase(BaseModel.TILE_IGNORE)) {
                            cell.setTile(null);
                        }

                        if (curLayerName.equalsIgnoreCase(GroundModel.TILE_GROUND)) {
                            GroundActor groundActor = new GroundActor(BodyUtils.createStaticWorldBody(new GroundModel(), world, mTileSize, row, col, false));
                            addActor(groundActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(WallModel.TILE_WALL)) {
                            WallActor wallActor = new WallActor(BodyUtils.createStaticWorldBody(new WallModel(), world, mTileSize, row, col, false));
                            addActor(wallActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DestroyableBlockDirtModel.DESTROYABLE_BLOCK_DIRT)) {
                            Vector2 blockPosition = new Vector2(row, col);
                            if (!loopThruDestroyedBlocksArray(mLoadedLevelModel.getDestroyedBlockList(), blockPosition)) {
                                DestroyableBlockDirtActor destroyableBlockDirtActor = new DestroyableBlockDirtActor(BodyUtils.createStaticWorldBody(new DestroyableBlockDirtModel(WorldUtils.randomBoolean(), row, col), world, mTileSize, row, col, false));
                                destroyableBlockDirtActor.setName(DestroyableBlockDirtModel.DESTROYABLE_BLOCK_DIRT);
                                addActor(destroyableBlockDirtActor);
                                addToActorsArray(destroyableBlockDirtActor);
                            }
                        }
                        else if (curLayerName.equalsIgnoreCase(DestroyableBlockClayModel.DESTROYABLE_BLOCK_CLAY)) {
                            Vector2 blockPosition = new Vector2(row, col);
                            if (!loopThruDestroyedBlocksArray(mLoadedLevelModel.getDestroyedBlockList(), blockPosition)) {
                                DestroyableBlockClayActor destroyableBlockClayActor = new DestroyableBlockClayActor(BodyUtils.createStaticWorldBody(new DestroyableBlockClayModel(WorldUtils.randomBoolean(), row, col), world, mTileSize, row, col, false));
                                destroyableBlockClayActor.setName(DestroyableBlockClayModel.DESTROYABLE_BLOCK_CLAY);
                                addActor(destroyableBlockClayActor);
                                addToActorsArray(destroyableBlockClayActor);
                            }
                        }
                        else if (curLayerName.equalsIgnoreCase(DestroyableBlockFallModel.DESTROYABLE_BLOCK_FALL)) {
                            Vector2 blockPosition = new Vector2(row, col);
                            if (!loopThruDestroyedBlocksArray(mLoadedLevelModel.getDestroyedBlockList(), blockPosition)) {
                                DestroyableBlockFallActor destroyableBlockFallActor = new DestroyableBlockFallActor(BodyUtils.createDynamicWorldBody(new DestroyableBlockFallModel(row, col), world, mTileSize, row, col, false));
                                destroyableBlockFallActor.setName(DestroyableBlockFallModel.DESTROYABLE_BLOCK_FALL);
                                addActor(destroyableBlockFallActor);
                                addToActorsArray(destroyableBlockFallActor);
                                destroyableBlockFallActor.addListener(mGameStage);
                            }
                        }
                        else if (curLayerName.equalsIgnoreCase(LavaModel.LAVA)) {
                            LavaActor lavaActor = new LavaActor(BodyUtils.createStaticWorldBody(new LavaModel(), world, mTileSize, row, col, true));
                            lavaActor.setName(LavaModel.LAVA);
                            addToActorsArray(lavaActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.DOOR_PREVIOUS)) {
                            DoorActor doorActor = new DoorActor(BodyUtils.createStaticWorldBody(new DoorModel(DoorType.DOOR_PREVIOUS), world, mTileSize, row, col, true));
                            addActor(doorActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.DOOR_NEXT)) {
                            DoorActor doorActor = new DoorActor(BodyUtils.createStaticWorldBody(new DoorModel(DoorType.DOOR_NEXT), world, mTileSize, row, col, true));
                            addActor(doorActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.DOOR_OFF)) {
                            DoorOffActor doorOffActor = new DoorOffActor(BodyUtils.createStaticWorldBody(new DoorOffModel(), world, mTileSize, row, col, true));
                            addActor(doorOffActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.DOOR_LOCKED_GOLD)) {
                            createLockedGoldDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get(LEVEL_NUMBER).toString()),
                                    tiledMapTileLayer.getProperties().get(DESTINATION).toString(), checkIfDoorLocked(DoorType.DOOR_LOCKED_GOLD), row, col);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.DOOR_LOCKED_BRONZE)) {
                            createLockedBronzeDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get(LEVEL_NUMBER).toString()),
                                    tiledMapTileLayer.getProperties().get(DESTINATION).toString(), checkIfDoorLocked(DoorType.DOOR_LOCKED_BRONZE), row, col);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.DOOR_LOCKED_BLOOD)) {
                            createLockedBloodDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get(LEVEL_NUMBER).toString()),
                                    tiledMapTileLayer.getProperties().get(DESTINATION).toString(), checkIfDoorLocked(DoorType.DOOR_LOCKED_BLOOD), row, col);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.DOOR_LOCKED_BLACK)) {
                            createLockedBlackDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get(LEVEL_NUMBER).toString()),
                                    tiledMapTileLayer.getProperties().get(DESTINATION).toString(), checkIfDoorLocked(DoorType.DOOR_LOCKED_BLACK), row, col);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.DOOR_OTHER)) {
                            createOtherDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get(LEVEL_NUMBER).toString()),
                                    tiledMapTileLayer.getProperties().get(DESTINATION).toString(), false, row, col);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.DOOR_OTHER)) {
                            createOtherDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get(LEVEL_NUMBER).toString()),
                                    tiledMapTileLayer.getProperties().get(DESTINATION).toString(), false, row, col);
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
                    PhantomActor phantomActor = new PhantomActor(EnemyUtils.createPhantom(new PhantomModel(true), world, getMapObjectCoords(mapObject)));
                    phantomActor.setName(PhantomModel.PHANTOM);
                    addActor(phantomActor);
                    addToActorsArray(phantomActor);
                }
                else if (mapLayer.getName().equalsIgnoreCase(PhantomLargeModel.PHANTOM_LARGE)) {
                    String bossType = (String) mapObject.getProperties().get(BOSS_TYPE);
                    if (!loopThruDestroyedBossArray(mLoadedLevelModel.getDestroyedBossList(), bossType)) {
                        PhantomBossActor phantomBoss = new PhantomBossActor(world, mGameStage, EnemyUtils.createLargePhantom(new PhantomLargeModel(true, bossType), world, getMapObjectCoords(mapObject)));
                        phantomBoss.setName(PhantomLargeModel.PHANTOM_LARGE);
                        addActor(phantomBoss);
                        addToActorsArray(phantomBoss);
                        phantomBoss.addListener(mGameStage);
                    }
                }
                else if (mapLayer.getName().equalsIgnoreCase(PlayerModel.PLAYER_DESTINATIONS)) {
                    if (whichDoor.equalsIgnoreCase(mapObject.getProperties().get(DESTINATION_NAME).toString())) {
                        createPlayerActor(world, getMapObjectCoords(mapObject));
                    }
                }
                else if (mapLayer.getName().equalsIgnoreCase(KeyModel.KEY)) {
                    String keyType = (String) mapObject.getProperties().get(KEY_TYPE);
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
                    // do not load a life if it was picked up already
                    // only supporting one life per level atm
                    if (mLoadedLevelModel != null && mLoadedLevelModel.getPickedUpLives().size() == 0) {
                        LifeActor lifeActor = new LifeActor(ObjectUtils.createLife(new LifeModel(), world, getMapObjectCoords(mapObject)));
                        lifeActor.setName(LifeModel.LIFE);
                        addActor(lifeActor);
                        addToActorsArray(lifeActor);
                    }
                }
                else if (mapLayer.getName().equalsIgnoreCase(BaseItemModel.PLAYER_ITEM)) {
                    String itemType = (String) mapObject.getProperties().get(ITEM_TYPE);
                    if (!loopThruPickedUpItemsArray(mLoadedLevelModel.getPickedUpItems(), itemType)) {

                        // boomerangs
                        if (itemType.equalsIgnoreCase(BoomerangModel.BOOMERANG_WOOD)
                                || itemType.equalsIgnoreCase(BoomerangModel.BOOMERANG_EMERALD)
                                || itemType.equalsIgnoreCase(BoomerangModel.BOOMERANG_AMETHYST)
                                || itemType.equalsIgnoreCase(BoomerangModel.BOOMERANG_BLOODSTONE) ) {
                            BoomerangActor boomerangActor = new BoomerangActor(ObjectUtils.createBoomerang(new BoomerangModel(itemType), world, getMapObjectCoords(mapObject)), itemType);
                            boomerangActor.setName(BoomerangModel.BOOMERANG);
                            addActor(boomerangActor);
                            addToActorsArray(boomerangActor);
                        }

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
        for (IMapParser listener : listeners) {
            listener.setPlayerActor(playerActor);
        }
    }

    private void createLockedGoldDoorActor(World world, int levelNumber, String doorTypeDest, boolean isLocked, int row, int col) {
        DoorGoldActor doorGoldActor = new DoorGoldActor(BodyUtils.createStaticWorldBody(new DoorModel(DoorType.DOOR_LOCKED_GOLD, levelNumber, doorTypeDest, isLocked), world, mTileSize, row, col, true), isLocked);
        doorGoldActor.setName(DoorType.DOOR_LOCKED_GOLD);
        addActor(doorGoldActor);
        addToActorsArray(doorGoldActor);
        for (IMapParser listener : listeners) {
            listener.setLockedGoldDoor(doorGoldActor);
        }
    }

    private void createLockedBronzeDoorActor(World world, int levelNumber, String doorTypeDest, boolean isLocked, int row, int col) {
        DoorBronzeActor doorBronzeActor = new DoorBronzeActor(BodyUtils.createStaticWorldBody(new DoorModel(DoorType.DOOR_LOCKED_BRONZE, levelNumber, doorTypeDest, isLocked), world, mTileSize, row, col, true), isLocked);
        doorBronzeActor.setName(DoorType.DOOR_LOCKED_BRONZE);
        addActor(doorBronzeActor);
        addToActorsArray(doorBronzeActor);
        for (IMapParser listener : listeners) {
            listener.setLockedBronzeDoor(doorBronzeActor);
        }
    }

    private void createLockedBloodDoorActor(World world, int levelNumber, String doorTypeDest, boolean isLocked, int row, int col) {
        DoorBloodActor doorBloodActor = new DoorBloodActor(BodyUtils.createStaticWorldBody(new DoorModel(DoorType.DOOR_LOCKED_BLOOD, levelNumber, doorTypeDest, isLocked), world, mTileSize, row, col, true), isLocked);
        doorBloodActor.setName(DoorType.DOOR_LOCKED_BLOOD);
        addActor(doorBloodActor);
        addToActorsArray(doorBloodActor);
        for (IMapParser listener : listeners) {
            listener.setLockedBloodDoor(doorBloodActor);
        }
    }

    private void createLockedBlackDoorActor(World world, int levelNumber, String doorTypeDest, boolean isLocked, int row, int col) {
        DoorBlackActor doorBlackActor = new DoorBlackActor(BodyUtils.createStaticWorldBody(new DoorModel(DoorType.DOOR_LOCKED_BLACK, levelNumber, doorTypeDest, isLocked), world, mTileSize, row, col, true), isLocked);
        doorBlackActor.setName(DoorType.DOOR_LOCKED_BLACK);
        addActor(doorBlackActor);
        addToActorsArray(doorBlackActor);
        for (IMapParser listener : listeners) {
            listener.setLockedBlackDoor(doorBlackActor);
        }
    }

    private void createOtherDoorActor(World world, int levelNumber, String doorTypeDest, boolean isLocked, int row, int col) {
        DoorOtherActor doorOtherActor = new DoorOtherActor(BodyUtils.createStaticWorldBody(new DoorModel(DoorType.DOOR_OTHER, levelNumber, doorTypeDest, isLocked), world, mTileSize, row, col, true));
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
            if (lookingForKey.getKeyType().equalsIgnoreCase(targetValue)) {
                return true;
            }
        }
        return false;
    }

    private static boolean loopThruOpenedDoorsArray(ArrayList<DoorModel> array, String targetValue) {
        for (DoorModel lookingForDoor : array) {
            if (lookingForDoor.getDoorType().equalsIgnoreCase(targetValue)) {
                return true;
            }
        }
        return false;
    }

    private static boolean loopThruPickedUpItemsArray(ArrayList<BaseItemModel> array, String targetValue) {
        for (BaseItemModel lookingForItem : array) {
            if (lookingForItem.getItemType().equalsIgnoreCase(targetValue)) {
                return true;
            }
        }
        return false;
    }

    private static boolean loopThruDestroyedBlocksArray(ArrayList<BaseDestroyableModel> array, Vector2 targetValue) {
        for (BaseDestroyableModel lookingForItem : array) {
            if (lookingForItem != null
                    && lookingForItem.getPosition().equals(targetValue)) {
                return true;
            }
        }
        return false;
    }

    private static boolean loopThruDestroyedBossArray(ArrayList<BaseEnemyModel> array, String targetValue) {
        for (BaseEnemyModel lookingForBoss : array) {
            if (lookingForBoss.getBossType().equalsIgnoreCase(targetValue)) {
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
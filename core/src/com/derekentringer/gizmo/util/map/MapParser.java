package com.derekentringer.gizmo.util.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.components.actor.BaseActor;
import com.derekentringer.gizmo.components.actor.enemy.PhantomActor;
import com.derekentringer.gizmo.components.actor.enemy.PhantomLargeActor;
import com.derekentringer.gizmo.components.actor.object.HeartActor;
import com.derekentringer.gizmo.components.actor.object.KeyActor;
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
import com.derekentringer.gizmo.model.enemy.PhantomLargeModel;
import com.derekentringer.gizmo.model.enemy.PhantomModel;
import com.derekentringer.gizmo.model.level.LevelModel;
import com.derekentringer.gizmo.model.object.HeartModel;
import com.derekentringer.gizmo.model.object.KeyModel;
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
import com.derekentringer.gizmo.util.map.interfaces.IMapParserDelegate;

import java.util.ArrayList;

public class MapParser extends Stage {

    public IMapParserDelegate delegate = null;

    public final ArrayList<BaseActor> actorsArray = new ArrayList<BaseActor>();

    private TiledMap tiledMap;
    private TiledMap tiledMapMidBackground;
    private TiledMap tiledMapBackground;

    private TiledMapRenderer tiledMapRenderer;
    private TiledMapRenderer tiledMapMidBackgroundRenderer;
    private TiledMapRenderer tiledMapBackgroundRenderer;

    private float tileSize;

    private LevelModel sLoadedLevelModel;

    public MapParser(LevelModel loadedLevelModel, String tileMapName, String tileMapMidBackground, String tileMapBackground) {
        sLoadedLevelModel = loadedLevelModel;

        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMagFilter = Texture.TextureFilter.Nearest;
        params.textureMinFilter = Texture.TextureFilter.Nearest;

        tiledMap = new TmxMapLoader().load(tileMapName, params);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        tiledMapMidBackground = new TmxMapLoader().load(tileMapMidBackground, params);
        tiledMapMidBackgroundRenderer = new OrthogonalTiledMapRenderer(tiledMapMidBackground);

        tiledMapBackground = new TmxMapLoader().load(tileMapBackground, params);
        tiledMapBackgroundRenderer = new OrthogonalTiledMapRenderer(tiledMapBackground);
    }

    public void createTileMapLayers(World world) {
        for (int curLayer = 0; curLayer < getTiledMap().getLayers().getCount(); curLayer++) {
            if (getTiledMap().getLayers().get(curLayer) instanceof TiledMapTileLayer) {

                TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) getTiledMap().getLayers().get(curLayer);

                if (tiledMapTileLayer == null) {
                    return;
                }

                String curLayerName = tiledMapTileLayer.getName();
                tileSize = tiledMapTileLayer.getTileWidth();

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
                            GroundActor groundActor = new GroundActor(BodyUtils.createStaticBody(new GroundModel(), world, tileSize, row, col, false));
                            addActor(groundActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(WallModel.TILE_WALL)) {
                            WallActor wallActor = new WallActor(BodyUtils.createStaticBody(new WallModel(), world, tileSize, row, col, false));
                            addActor(wallActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.PREVIOUS)) {
                            DoorActor doorActor = new DoorActor(BodyUtils.createStaticBody(new DoorModel(DoorType.PREVIOUS), world, tileSize, row, col, true));
                            addActor(doorActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.NEXT)) {
                            DoorActor doorActor = new DoorActor(BodyUtils.createStaticBody(new DoorModel(DoorType.NEXT), world, tileSize, row, col, true));
                            addActor(doorActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.DOOR_OFF)) {
                            DoorOffActor doorOffActor = new DoorOffActor(BodyUtils.createStaticBody(new DoorOffModel(), world, tileSize, row, col, true));
                            addActor(doorOffActor);
                        }
                        /*else if (curLayerName.equalsIgnoreCase(DoorType.LOCKED_GOLD)) {
                            createLockedGoldDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get("levelnumber").toString()),
                                    tiledMapTileLayer.getProperties().get("destination").toString(), checkIfDoorLocked(DoorType.LOCKED_GOLD), row, col);
                        }*/
                        else if (curLayerName.equalsIgnoreCase(DoorType.LOCKED_BRONZE)) {
                            createLockedBronzeDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get("levelnumber").toString()),
                                    tiledMapTileLayer.getProperties().get("destination").toString(), row, col);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.LOCKED_BLOOD)) {
                            createLockedBloodDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get("levelnumber").toString()),
                                    tiledMapTileLayer.getProperties().get("destination").toString(), row, col);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.LOCKED_BLACK)) {
                            createLockedBlackDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get("levelnumber").toString()),
                                    tiledMapTileLayer.getProperties().get("destination").toString(), row, col);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.OTHER)) {
                            createOtherDoorActor(world, Integer.parseInt(tiledMapTileLayer.getProperties().get("levelnumber").toString()),
                                    tiledMapTileLayer.getProperties().get("destination").toString(), row, col);
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

                if (mapLayer.getName().equalsIgnoreCase(DoorType.LOCKED_GOLD)) {
                    Float xPosD = (Float) mapObject.getProperties().get("x");
                    Float yPosD = (Float) mapObject.getProperties().get("y");
                    int xPos = xPosD.intValue();
                    int yPos = yPosD.intValue();

                    createLockedGoldDoorActor(world,
                            Integer.parseInt(mapObject.getProperties().get("levelnumber").toString()),
                            mapObject.getProperties().get("destination").toString(),
                            checkIfDoorLocked(DoorType.LOCKED_GOLD),
                            xPos,
                            yPos);
                }

                else if (mapLayer.getName().equalsIgnoreCase(PhantomModel.PHANTOM)) {
                    Float xPosD = (Float) mapObject.getProperties().get("x");
                    Float yPosD = (Float) mapObject.getProperties().get("y");
                    int xPos = xPosD.intValue();
                    int yPos = yPosD.intValue();
                    PhantomActor phantomActor = new PhantomActor(EnemyUtils.createPhantom(new PhantomModel(), world, xPos, yPos));
                    phantomActor.setName(PhantomModel.PHANTOM);
                    addActor(phantomActor);
                    actorsArray.add(phantomActor);
                }
                else if (mapLayer.getName().equalsIgnoreCase(PhantomLargeModel.PHANTOM_LARGE)) {
                    Float xPosD = (Float) mapObject.getProperties().get("x");
                    Float yPosD = (Float) mapObject.getProperties().get("y");
                    int xPos = xPosD.intValue();
                    int yPos = yPosD.intValue();
                    PhantomLargeActor phantomLargeActor = new PhantomLargeActor(EnemyUtils.createLargePhantom(new PhantomLargeModel(), world, xPos, yPos));
                    phantomLargeActor.setName(PhantomLargeModel.PHANTOM_LARGE);
                    addActor(phantomLargeActor);
                    actorsArray.add(phantomLargeActor);
                }
                else if (mapLayer.getName().equalsIgnoreCase(PlayerModel.PLAYER_DESTINATIONS)) {
                    if (whichDoor.equalsIgnoreCase(mapObject.getProperties().get("name").toString())) {
                        Float xPosD = (Float) mapObject.getProperties().get("x");
                        Float yPosD = (Float) mapObject.getProperties().get("y");
                        int xPos = xPosD.intValue();
                        int yPos = yPosD.intValue();
                        createPlayerActor(world, xPos, yPos);
                    }
                }
                else if (mapLayer.getName().equalsIgnoreCase(KeyModel.KEY)) {
                    Float xPosD = (Float) mapObject.getProperties().get("x");
                    Float yPosD = (Float) mapObject.getProperties().get("y");
                    String keyType = (String) mapObject.getProperties().get("keyType");
                    int xPos = xPosD.intValue();
                    int yPos = yPosD.intValue();

                    if (!loopThruPickedUpKeysArray(sLoadedLevelModel.getPickedUpKeys(), keyType)) {
                        KeyActor keyActor = new KeyActor(ObjectUtils.createKey(new KeyModel(keyType), world, xPos, yPos), keyType);
                        keyActor.setName(KeyModel.KEY);
                        addActor(keyActor);
                        actorsArray.add(keyActor);
                    }

                }
                else if (mapLayer.getName().equalsIgnoreCase(HeartModel.HEART)) {
                    Float xPosD = (Float) mapObject.getProperties().get("x");
                    Float yPosD = (Float) mapObject.getProperties().get("y");
                    int xPos = xPosD.intValue();
                    int yPos = yPosD.intValue();

                    // do not load a heart if it was picked up already
                    // only supporting one heart per level atm
                    if (sLoadedLevelModel != null && sLoadedLevelModel.getPickedUpHearts().size() == 0) {
                        HeartActor heartActor = new HeartActor(ObjectUtils.createHeart(new HeartModel(), world, xPos, yPos));
                        heartActor.setName(HeartModel.HEART);
                        addActor(heartActor);
                        actorsArray.add(heartActor);
                    }

                }
            }
        }
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
        return !loopThruOpenedDoorsArray(sLoadedLevelModel.getOpenedDoors(), doorType);
    }

    private void createPlayerActor(World world, int xPos, int yPos) {
        PlayerActor playerActor = new PlayerActor(PlayerUtils.createPlayer(world, xPos, yPos));
        playerActor.setName(PlayerModel.PLAYER);
        addActor(playerActor);
        actorsArray.add(playerActor);
        delegate.setPlayerActor(playerActor);
    }

    private void createLockedGoldDoorActor(World world, int levelNumber, String doorTypeDest, boolean isLocked, int xpos, int ypos) {
        DoorGoldActor doorGoldActor = new DoorGoldActor(ObjectUtils.createDoor(new DoorModel(DoorType.LOCKED_GOLD, levelNumber, doorTypeDest), world, xpos, ypos), isLocked);
        doorGoldActor.setName(DoorType.LOCKED_GOLD);
        addActor(doorGoldActor);
        actorsArray.add(doorGoldActor);
        delegate.setLockedGoldDoor(doorGoldActor);
    }

    private void createLockedBronzeDoorActor(World world, int levelNumber, String doorTypeDest, int row, int col) {
        DoorBronzeActor doorBronzeActor = new DoorBronzeActor(BodyUtils.createStaticBody(new DoorModel(DoorType.LOCKED_BRONZE, levelNumber, doorTypeDest), world, tileSize, row, col, true));
        addActor(doorBronzeActor);
        delegate.setLockedBronzeDoor(doorBronzeActor);
    }

    private void createLockedBloodDoorActor(World world, int levelNumber, String doorTypeDest, int row, int col) {
        DoorBloodActor doorBloodActor = new DoorBloodActor(BodyUtils.createStaticBody(new DoorModel(DoorType.LOCKED_BLOOD, levelNumber, doorTypeDest), world, tileSize, row, col, true));
        addActor(doorBloodActor);
        delegate.setLockedBloodDoor(doorBloodActor);
    }

    private void createLockedBlackDoorActor(World world, int levelNumber, String doorTypeDest, int row, int col) {
        DoorBlackActor doorBlackActor = new DoorBlackActor(BodyUtils.createStaticBody(new DoorModel(DoorType.LOCKED_BLACK, levelNumber, doorTypeDest), world, tileSize, row, col, true));
        addActor(doorBlackActor);
        delegate.setLockedBlackDoor(doorBlackActor);
    }
    private void createOtherDoorActor(World world, int levelNumber, String doorTypeDest, int row, int col) {
        DoorOtherActor doorOtherActor = new DoorOtherActor(BodyUtils.createStaticBody(new DoorModel(DoorType.OTHER, levelNumber, doorTypeDest), world, tileSize, row, col, true));
        addActor(doorOtherActor);
    }

    public void destroyTiledMap() {
        tiledMap.dispose();
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public TiledMapRenderer getTiledMapMidBackgroundRenderer() {
        return tiledMapMidBackgroundRenderer;
    }

    public TiledMapRenderer getTiledMapBackgroundRenderer() {
        return tiledMapBackgroundRenderer;
    }

    public float getTileSize() {
        return tileSize;
    }

}
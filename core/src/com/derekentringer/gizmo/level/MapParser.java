package com.derekentringer.gizmo.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.actor.data.DoorType;
import com.derekentringer.gizmo.actor.data.enemy.PhantomData;
import com.derekentringer.gizmo.actor.data.structure.DoorUserData;
import com.derekentringer.gizmo.actor.data.structure.GroundUserData;
import com.derekentringer.gizmo.actor.data.structure.WallUserData;
import com.derekentringer.gizmo.actor.enemy.PhantomActor;
import com.derekentringer.gizmo.actor.structure.DoorActor;
import com.derekentringer.gizmo.actor.structure.GroundActor;
import com.derekentringer.gizmo.actor.structure.WallActor;
import com.derekentringer.gizmo.util.EnemyUtils;
import com.derekentringer.gizmo.util.WorldUtils;

import java.util.ArrayList;

public class MapParser extends Stage {

    private TiledMap tiledMap;
    private TiledMap tiledMapMidBackground;
    private TiledMap tiledMapBackground;

    private TiledMapRenderer tiledMapRenderer;
    private TiledMapRenderer tiledMapMidBackgroundRenderer;
    private TiledMapRenderer tiledMapBackgroundRenderer;

    private float tileSize;

    public final ArrayList<Actor> actorsArray = new ArrayList<Actor>();

    public MapParser(String tileMapName, String tileMapMidBackground, String tileMapBackground) {
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
        for(int curLayer = 0; curLayer < getTiledMap().getLayers().getCount(); curLayer++) {
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

                        if (curLayerName.equalsIgnoreCase(GroundUserData.TILE_GROUND)) {
                            GroundActor groundActor = new GroundActor(WorldUtils.createStaticBody(new GroundUserData(), world, tileSize, row, col, false));
                            addActor(groundActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(WallUserData.TILE_WALL)) {
                            WallActor wallActor = new WallActor(WorldUtils.createStaticBody(new WallUserData(), world, tileSize, row, col, false));
                            addActor(wallActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.PREVIOUS)) {
                            DoorActor doorActor = new DoorActor(WorldUtils.createStaticBody(new DoorUserData(DoorType.PREVIOUS), world, tileSize, row, col, true));
                            addActor(doorActor);
                        }
                        else if (curLayerName.equalsIgnoreCase(DoorType.NEXT)) {
                            DoorActor doorActor = new DoorActor(WorldUtils.createStaticBody(new DoorUserData(DoorType.NEXT), world, tileSize, row, col, true));
                            addActor(doorActor);
                        }
                    }
                }
            }
        }
    }

    public void createMapLayers(World world) {
        for(int curLayer = 0; curLayer < getTiledMap().getLayers().getCount(); curLayer++) {
            MapLayer mapLayer = getTiledMap().getLayers().get(curLayer);

            if (mapLayer == null) {
                return;
            }

            for(MapObject mapObject: mapLayer.getObjects()) {
                if(mapLayer.getName().equalsIgnoreCase(PhantomData.PHANTOM)) {
                    Float xPosD = (Float) mapObject.getProperties().get("x");
                    Float yPosD = (Float) mapObject.getProperties().get("y");
                    int xPos = xPosD.intValue();
                    int yPos = yPosD.intValue();
                    PhantomActor phantomActor = new PhantomActor(EnemyUtils.createPhantom(new PhantomData(), world, xPos, yPos));
                    addActor(phantomActor);
                    actorsArray.add(phantomActor);
                }
            }
        }
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
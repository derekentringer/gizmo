package com.derekentringer.gizmo.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.derekentringer.gizmo.actor.data.DoorType;
import com.derekentringer.gizmo.actor.data.structure.DoorUserData;
import com.derekentringer.gizmo.actor.data.structure.GroundUserData;
import com.derekentringer.gizmo.actor.data.structure.WallUserData;
import com.derekentringer.gizmo.actor.structure.DoorActor;
import com.derekentringer.gizmo.actor.structure.GroundActor;
import com.derekentringer.gizmo.actor.structure.WallActor;
import com.derekentringer.gizmo.util.WorldUtils;

public class Level extends Stage {

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    private float tileSize;

    public Level(String tilemapName) {
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMagFilter = Texture.TextureFilter.Nearest;
        params.textureMinFilter = Texture.TextureFilter.Nearest;
        tiledMap = new TmxMapLoader().load(tilemapName, params);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void createTileMapLayer(World world, String layerName) {
        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) getTiledMap().getLayers().get(layerName);

        if(tiledMapTileLayer == null) {
            return;
        }

        tileSize = tiledMapTileLayer.getTileWidth();

        for(int row = 0; row < tiledMapTileLayer.getHeight(); row++) {
            for(int col = 0; col < tiledMapTileLayer.getWidth(); col++) {

                //check for empty cells
                TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell(col, row);
                if(cell == null) {
                    continue;
                }
                if(cell.getTile() == null) {
                    continue;
                }

                if(getTiledMap().getLayers().get(layerName).getName().equalsIgnoreCase(GroundUserData.TILE_GROUND)) {
                    GroundActor groundActor = new GroundActor(WorldUtils.createStaticBody(new GroundUserData(), world, tileSize, row, col, false));
                    addActor(groundActor);
                }
                else if(getTiledMap().getLayers().get(layerName).getName().equalsIgnoreCase(WallUserData.TILE_WALL)) {
                    WallActor wallActor = new WallActor(WorldUtils.createStaticBody(new WallUserData(), world, tileSize, row, col, false));
                    addActor(wallActor);
                }
                else if(getTiledMap().getLayers().get(layerName).getName().equalsIgnoreCase(DoorType.PREVIOUS)) {
                    DoorActor doorActor = new DoorActor(WorldUtils.createStaticBody(new DoorUserData(DoorType.PREVIOUS), world, tileSize, row, col, true));
                    addActor(doorActor);
                }
                else if(getTiledMap().getLayers().get(layerName).getName().equalsIgnoreCase(DoorType.NEXT)) {
                    DoorActor doorActor = new DoorActor(WorldUtils.createStaticBody(new DoorUserData(DoorType.NEXT), world, tileSize, row, col, true));
                    addActor(doorActor);
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

    public float getTileSize() {
        return tileSize;
    }

}
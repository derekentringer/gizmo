package com.derekentringer.gizmo.manager;

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

public class TileMapManager extends Stage {

    private TiledMap tiledMap;
    private TiledMap tiledMapMidBackground;
    private TiledMap tiledMapBackground;

    private TiledMapRenderer tiledMapRenderer;
    private TiledMapRenderer tiledMapMidBackgroundRenderer;
    private TiledMapRenderer tiledMapBackgroundRenderer;

    private float tileSize;

    public TileMapManager(String tileMapName, String tileMapBackground, String tileMapMidBackground) {
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMagFilter = Texture.TextureFilter.Nearest;
        params.textureMinFilter = Texture.TextureFilter.Nearest;

        tiledMap = new TmxMapLoader().load(tileMapName, params);
        tiledMapMidBackground = new TmxMapLoader().load(tileMapMidBackground, params);
        tiledMapBackground = new TmxMapLoader().load(tileMapBackground, params);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        tiledMapMidBackgroundRenderer = new OrthogonalTiledMapRenderer(tiledMapMidBackground);
        tiledMapBackgroundRenderer = new OrthogonalTiledMapRenderer(tiledMapBackground);
    }

    public void createTileMapLayers(World world) {
        for(int curLayer = 0; curLayer < getTiledMap().getLayers().getCount(); curLayer++) {
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
package com.derekentringer.gizmo.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Level {

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;

    public Level(String tilemapName) {
        //TODO need to find out more about the loader params
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMagFilter = Texture.TextureFilter.Nearest;
        params.textureMinFilter = Texture.TextureFilter.Nearest;
        tiledMap = new TmxMapLoader().load(tilemapName, params);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

}
package com.suiton2d.assets;

import com.badlogic.gdx.maps.Map;

public class TileSheet<T extends Map> implements Asset<T> {

    public enum TileSheetType {
        TILED,
    }

    private String path;
    private String filename;
    private boolean loaded = false;
    private T tileMap;
    private int width;
    private int height;
    protected TileSheetType type;

    public TileSheet(String path, T tileMap, TileSheetType type) {
        this.path = path;
        this.filename = path.substring(path.lastIndexOf("/"));
        this.tileMap = tileMap;
        this.type = type;
        this.width = tileMap.getProperties().get("width", Integer.class);
        this.height = tileMap.getProperties().get("height", Integer.class);
    }

    @SuppressWarnings("unused")
    public TileSheetType getType() {
        return type;
    }

    @Override
    public T getData() {
        return tileMap;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    @Override
    public void dispose() {
        if (tileMap != null) {
            tileMap.dispose();
        }
    }

    public int getBoundingWidth() {
        return width;
    }

    public int getBoundingHeight() {
        return height;
    }
}

package com.suiton2d.components.gfx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.TiledTileSheet;
import com.suiton2d.scene.Scene;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com
 */
public class TiledMapRenderer extends Renderer {

    private AssetManager assetManager;
    private Scene scene;
    private String filename;
    private OrthogonalTiledMapRenderer renderer;

    public TiledMapRenderer(String name, String filename, Scene scene, AssetManager assetManager) {
        super(name);
        this.filename = filename;
        this.scene = scene;
        this.assetManager = assetManager;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public TiledTileSheet getTiledTileSheet() {
        return assetManager.getAsset(filename, TiledTileSheet.class);
    }

    @Override
    public void render(Batch batch, float dt) {
        if (getTiledTileSheet() != null) {
            if (renderer == null)
                renderer = new OrthogonalTiledMapRenderer(getTiledTileSheet().getData());
            OrthographicCamera camera = (OrthographicCamera) scene.getCamera();
            camera.translate((getBoundingWidth()/2), (getBoundingHeight()/2));
            camera.update();
            renderer.setView(camera);
            renderer.render();
            camera.translate(-(getBoundingWidth()/2), -(getBoundingHeight()/2));
            camera.update();
        }
    }

    @Override
    public int getBoundingWidth() {
        return getTiledTileSheet() != null ? getTiledTileSheet().getBoundingWidth() : 0;
    }

    @Override
    public int getBoundingHeight() {
        return getTiledTileSheet() != null ? getTiledTileSheet().getBoundingHeight() : 0;
    }

    @Override
    public void finish() {
        if (renderer != null)
            renderer.dispose();
    }
}

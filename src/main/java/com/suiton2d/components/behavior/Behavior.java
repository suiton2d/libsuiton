package com.suiton2d.components.behavior;

import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.Script;
import com.suiton2d.components.Component;
import com.suiton2d.components.physics.CollisionListener;
import com.suiton2d.scene.GameObject;

/**
 * Behavior is a {@link Component} for executing
 * {@link Script} assets, providing custom functionality to
 * {@link GameObject}s via javascript.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class Behavior implements Component, CollisionListener {
    private AssetManager assetManager;
    private String filename;
    private String name;
    private GameObject gameObject;
    private boolean enabled = true;

    public Behavior(String name, String filename, AssetManager assetManager) {
        this.name = name;
        this.filename = filename;
        this.assetManager = assetManager;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Script getScript() {
        return assetManager.getAsset(filename, Script.class);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public GameObject getGameObject() {
        return gameObject;
    }

    @Override
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void start() {
        if (gameObject != null) {
            getScript().start(gameObject);
        }
    }

    @Override
    public void update(float dt) {
        if (gameObject != null) {
            getScript().update(gameObject, dt);
        }
    }

    @Override
    public void finish() {
        getScript().finish();
    }

    @Override
    public void beginCollision(GameObject go1, GameObject go2) {
        if (enabled) {
            getScript().beginCollision(go1, go2);
        }
    }

    @Override
    public void endCollision(GameObject go1, GameObject go2) {
        if (enabled) {
            getScript().endCollision(go1, go2);
        }
    }
}

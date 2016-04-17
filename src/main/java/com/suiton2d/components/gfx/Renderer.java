package com.suiton2d.components.gfx;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.suiton2d.components.Component;
import com.suiton2d.scene.GameObject;

import java.util.Optional;

public abstract class Renderer implements Component {

    private String name;
    private GameObject gameObject;
    private boolean enabled = true;

    public abstract int getBoundingWidth();

    public abstract int getBoundingHeight();

    public Renderer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Optional<GameObject> getGameObject() {
        return Optional.ofNullable(gameObject);
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

    /**
     * Abstract method used for rendering.
     * @param batch The SpriteBatch instance to use for rendering.
     * @param dt The time since the last frame update.
     */
    public abstract void render(Batch batch, float dt);
}

package com.suiton2d.components;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Renderer extends Component {

    public abstract int getBoundingWidth();

    public abstract int getBoundingHeight();

    public Renderer(String name) {
        super(name);
    }

    /**
     * Abstract method used for rendering.
     * @param batch The SpriteBatch instance to use for rendering.
     * @param dt The time since the last frame update.
     */
    public abstract void render(Batch batch, float dt);
}

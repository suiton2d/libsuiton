package com.suiton2d.assets;

import com.suiton2d.scene.GameObject;

/**
 * Script is an {@link Asset} representing
 * a javascript file. Scripts are used by Behavior components to
 * provide functionality to GameObjects.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public interface Script<T> extends Asset<T> {

    /**
     * Call start function.
     * @param gameObject The {@link GameObject} that owns the script.
     */
    void start(GameObject gameObject);

    /**
     * Call update function.
     * @param gameObject The {@link GameObject} that owns the script.
     * @param dt The time since last update.
     */
    void update(GameObject gameObject, float dt);

    /**
     * Call finish function.
     */
    void finish();

    /**
     * Call beginCollision function.
     * @param go1 The {@link GameObject} that initiated the collision.
     * @param go2 The {@link GameObject} that was collided with.
     */
    void beginCollision(GameObject go1, GameObject go2);

    /**
     * Call endCollision function.
     * @param go1 The {@link GameObject} that initiated the collision.
     * @param go2 The {@link GameObject} that was collided with.
     */
    void endCollision(GameObject go1, GameObject go2);
}

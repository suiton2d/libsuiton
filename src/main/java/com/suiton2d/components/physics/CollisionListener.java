package com.suiton2d.components.physics;

import com.suiton2d.scene.GameObject;

/**
 * CollisionListener is an interface for responding to collision events.
 */
public interface CollisionListener {

    /**
     * Called when a collision event begins.
     * @param go1 the {@link GameObject} that initiated the collision.
     * @param go2 the {@link GameObject} that {@code go1} collided with.
     */
    void beginCollision(GameObject go1, GameObject go2);

    /**
     * Called when a collision event ends.
     * @param go1 the {@link GameObject} that initiated the collision.
     * @param go2 the {@link GameObject} that {@code go1} collided with.
     */
    void endCollision(GameObject go1, GameObject go2);
}

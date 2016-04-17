/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) 2014 Jon Bonazza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.suiton2d.components;

import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.Scene;

import java.util.Optional;

/**
 * Component is an interface for nebula2d's components.
 * Components are an integral part of N2D's component-bsaed entity
 * system. components define the functionality for {@link GameObject}.
 *
 * @author Jon Bonazza
 */
public interface Component {

    String getName();

    Optional<GameObject> getGameObject();

    void setGameObject(GameObject gameObject);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    /**
     * Called exactly once at the start of the {@link Scene}.
     */
    void start();

    /**
     * Called once per frame.
     * @param dt The time since the last frame update.
     */
    void update(float dt);

    /**
     * Called exactly once immediately before a {@link Scene}
     * is changed or ended otherwise.
     */
    void finish();

    void beginCollision(GameObject go1, GameObject go2);

    void endCollision(GameObject go1, GameObject go2);
}

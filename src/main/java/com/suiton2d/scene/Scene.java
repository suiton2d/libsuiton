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

package com.suiton2d.scene;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.suiton2d.util.CollisionListener;

/**
 * Scene is a class representing a single "level" in the game.
 *
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class Scene {

    private String name;
    private Stage stage;
    private World physicalWorld;
    private boolean allowSleeping;

    public Scene(String name, Vector2 gravity, boolean sleepPhysics) {
        physicalWorld = new World(gravity, sleepPhysics);
        physicalWorld.setContactListener(new CollisionListener(this));
        stage = new Stage(new ScreenViewport());
        this.name = name;
        this.allowSleeping = sleepPhysics;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Stage getStage() {
        return stage;
    }

    public Camera getCamera() {
        return stage.getCamera();
    }

    public World getPhysicalWorld() {
        return physicalWorld;
    }

    public Vector2 getGravity() {
        return physicalWorld.getGravity();
    }

    public void setGravity(int x, int y) {
        physicalWorld.setGravity(new Vector2(x, y));
    }

    public boolean isAllowSleeping() {
        return allowSleeping;
    }

    public void addLayer(Layer layer) {
        stage.addActor(layer);
        layer.setScene(this);
    }

    /**
     * Retrieves the {@link Layer} in the Scene with the given name.
     * @param name The name of the Layer to retrieve.
     * @return the target Layer.
     */
    public Layer getLayer(String name) {
        for (Actor a : stage.getActors()) {
            if (a.getName().equals(name))
                return (Layer)a;
        }

        return null;
    }

    public Array<Actor> getChildren() {
        return stage.getActors();
    }

    public void start() {
        for (Actor a : stage.getActors())
            ((Layer)a).start();
    }

    public void update(float dt, boolean act) {
        Camera cam = stage.getCamera();

        if (cam == null)
            return;

        cam.update();
        if (act)
            stage.act(dt);

        stage.draw();
    }

    public void update(float dt) {
        update(dt, true);
    }

    public void finish() {
        for (Actor a : stage.getActors())
            ((Layer)a).finish();

        if (stage != null)
            stage.dispose();

        if (physicalWorld != null)
            physicalWorld.dispose();
    }

    public void beginCollision(GameObject go1, GameObject go2) {
        for (Actor a : stage.getActors())
            ((Layer)a).beginCollision(go1, go2);
    }

    public void endCollision(GameObject go1, GameObject go2) {
        for (Actor a : stage.getActors())
            ((Layer)a).endCollision(go1, go2);
    }

    public void cleanup() {
        if (physicalWorld != null) {
            physicalWorld.dispose();
            physicalWorld = null;
        }

        if (stage != null) {
            stage.dispose();
            stage = null;
        }
    }
}

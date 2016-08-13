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

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.suiton2d.assets.AssetManager;

/**
 * SceneManager is a class used to manage the game's various {@link Scene}s.
 *
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class SceneManager {

    private Scene currentScene;
    private SceneData sceneData;
    private AssetManager assetManager;

    public SceneManager(AssetManager assetManager) {
        this(assetManager, new SceneData());
    }

    public SceneManager(AssetManager assetManager, SceneData sceneData) {
        this.assetManager = assetManager;
        init(sceneData);
    }

    public void init(SceneData sceneData) {
        this.sceneData = sceneData;
        setCurrentScene(sceneData.getStartScene());
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Retrieves the singleton instance of SceneManager. SceneManager should
     * only ever have once instance at any given time.
     * @return Singleton SceneManager instance.
     */
    public Scene getCurrentScene() {
        return currentScene;
    }


    /**
     * Retrieves the {@link Scene} with the given name.
     * @param name The name of the Scene to retrieve.
     * @return The target Scene.
     */
    public Scene getScene(String name) {
        return sceneData.get(name);
    }

    /**
     * Adds a Scene to the scene manager.
     * @param scene The Scene to add.
     */
    public void addScene(Scene scene) {
        sceneData.put(scene.getName(), scene);
    }

    /**
     * Changes the current scene, unloading the currently loaded
     * assets and loading in the assets for the new scene.
     * @param name The name of the Scene to load.
     */
    public void setCurrentScene(String name) {

        if (sceneData.containsKey(name)) {
            if (currentScene != null) {
                currentScene.finish();
                assetManager.unloadAssets(currentScene.getName());
            }
            currentScene = getScene(name);
            assetManager.loadAssets(name);
            currentScene.start();
        }
    }

    /**
     * Starts the current scene. This allows the scene to do some last minute initialization.
     */
    public void start() {
        currentScene.start();
    }

    /**
     * Updates the current scene. This will step all of the scene's actors if desired before render one frame.
     * @param dt The time delta since the last update iteration.
     * @param act Whether or not to have the scene's actors act. If this is false, the scene will only be rendered
     *            and no acting will be performed.
     */
    public void update(float dt, boolean act) {
        if (currentScene != null)
            currentScene.update(dt, act);
    }

    /**
     * Updates the current scene. This will step all of the scene's actors before render one frame.
     * It is the same as calling {@code SceneManager.getInstance().update(dt, true);}.
     * @param dt The time delta since the last update iteration.
     */
    public void update(float dt) {
        update(dt, true);
    }

    /**
     * Steps the physical world for the current scene and updates all of the dynamic bodied game objects positions
     * and rotations.
     */
    public void fixedUpdate() {
        if (currentScene == null)
            return;

        World physicalWorld = currentScene.getPhysicalWorld();
        physicalWorld.step(1/45f, 6, 2);

        Array<Body> bodies = new Array<>();
        physicalWorld.getBodies(bodies);

        for (Body body : bodies) {
            if (body.getType() != BodyDef.BodyType.StaticBody) {
                GameObject go = (GameObject) body.getUserData();
                go.setPosition(body.getPosition().x, body.getPosition().y);
                go.setRotation((float) (body.getAngle() * 180.0f / Math.PI));
            }
        }
    }

    public Array<Scene> getSceneList() {
        return sceneData.values().toArray();
    }

    public int getSceneCount() {
        return sceneData.size;
    }

    /**
     * Cleans up all registered scenes. After cleanup, all scenes will still be registered, but they will be
     * effectively "dead."
     */
    public void cleanup() {
        for (Scene scene : sceneData.values()) {
            scene.cleanup();
        }
    }

    /**
     * Performs a cleanup before unregistering all registered scenes.
     */
    public void clear() {
        cleanup();
        sceneData.clear();
    }
}

package com.suiton2d.scene;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * SceneData is a read-only collection of all of a game's scene's and their associated names.
 */
public class SceneData {
    private ObjectMap<String, Scene> sceneMap;
    private String startScene;

    public SceneData(ObjectMap<String, Scene> sceneMap, String startScene) {
        this.sceneMap = sceneMap;
        this.startScene = startScene;
    }

    public ObjectMap<String, Scene> getSceneMap() {
        return sceneMap;
    }

    public String getStartScene() {
        return startScene;
    }
}

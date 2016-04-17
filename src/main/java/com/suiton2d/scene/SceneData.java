package com.suiton2d.scene;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * SceneData is a collection of all of a game's scene's and their associated names.
 */
public class SceneData extends ObjectMap<String, Scene> {
    private String startScene;

    public SceneData(String startScene) {
        this.startScene = startScene;
    }

    public String getStartScene() {
        return startScene;
    }
}

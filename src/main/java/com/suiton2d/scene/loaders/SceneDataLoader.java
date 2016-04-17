package com.suiton2d.scene.loaders;

import com.badlogic.gdx.files.FileHandle;
import com.suiton2d.scene.SceneData;

import java.io.IOException;

public interface SceneDataLoader {
    /**
     * Loads scene data from {@code file}.
     * @param file The file to load scene data from.
     * @return The loaded {@link SceneData}.
     */
    SceneData loadSceneData(FileHandle file) throws IOException;
}

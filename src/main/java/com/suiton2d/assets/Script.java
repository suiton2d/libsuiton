package com.suiton2d.assets;

/**
 * Script is an {@link Asset} representing
 * a javascript file. Scripts are used by Behavior components to
 * provide functionality to GameObjects.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class Script extends Asset<String> {

    public Script(String path, String content) {
        super(path, content);
    }
}

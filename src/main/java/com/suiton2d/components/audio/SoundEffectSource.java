package com.suiton2d.components.audio;

import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.SoundEffect;
import com.suiton2d.components.Component;
import com.suiton2d.scene.GameObject;

/**
 * SoundEffectSource is a {@link Component} used for the
 * playback of sound effects.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class SoundEffectSource implements Component {

    private AssetManager assetManager;
    private String filename;
    private String name;
    private GameObject gameObject;
    private boolean enabled = true;

    public SoundEffectSource(String name, String filename, AssetManager assetManager) {
        this.name = name;
        this.filename = filename;
        this.assetManager = assetManager;
    }

    public SoundEffect getSoundEffect() {
        return assetManager.getAsset(filename, SoundEffect.class);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public GameObject getGameObject() {
        return gameObject;
    }

    @Override
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Plays the {@link SoundEffect}.
     * @return The sound ID associated with the playback instance
     */
    public long play() {
        return getSoundEffect().play();
    }

    /**
     * Plays the SoundEffect in a loop.
     * @return The sound ID associated with the playback instance
     */
    @SuppressWarnings("unused")
    public long loop() {
        return getSoundEffect().loop();
    }

    /**
     * Stops a playback instance of the SoundEffect.
     * @param soundId The sound ID associated with the playback instance to stop.
     */
    @SuppressWarnings("unused")
    public void stop(long soundId) {
        getSoundEffect().stop(soundId);
    }

    /**
     * Pauses a playback instance of the SoundEffect.
     * @param soundId The sound ID associated with the playback instance to pause.
     */
    @SuppressWarnings("unused")
    public void pause(long soundId) {
        getSoundEffect().pause(soundId);
    }

    /**
     * Resumes a playback instance of the SoundEffect.
     * @param soundId The sound ID associated with the playback instance to resume.
     */
    @SuppressWarnings("unused")
    public void resume(long soundId) {
        getSoundEffect().resume(soundId);
    }
}

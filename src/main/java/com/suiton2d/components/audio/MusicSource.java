package com.suiton2d.components.audio;

import com.badlogic.gdx.audio.Music;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.MusicTrack;
import com.suiton2d.components.Component;
import com.suiton2d.scene.GameObject;

import java.util.Optional;

/**
 * MusicSource is a {@link Component} used for the playback of
 * music files.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class MusicSource implements Component {

    private String filename;
    private String name;
    private GameObject gameObject;
    private boolean enabled = true;

    public MusicSource(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public MusicTrack getMusicTrack() {
        return AssetManager.getAsset(filename, MusicTrack.class);
    }
    /**
     * Plays the {@link MusicTrack}.
     */
    @SuppressWarnings("unused")
    public void play() {
        getMusicTrack().play();
    }

    /**
     * Stops the MusicTrack.
     */
    @SuppressWarnings("unused")
    public void stop() {
        getMusicTrack().stop();
    }

    /**
     * Pauses the MusicTrack.
     */
    @SuppressWarnings("unused")
    public void pause() {
        getMusicTrack().pause();
    }

    @SuppressWarnings("unused")
    public boolean isLooping() {
        return getMusicTrack().isLooping();
    }

    @SuppressWarnings("unused")
    public void setLooping(boolean looping) {
        getMusicTrack().setLooping(looping);
    }

    @SuppressWarnings("unused")
    public float getVolume() {
        return getMusicTrack().getData().getVolume();
    }

    @SuppressWarnings("unused")
    public void setVolume(float volume) {
        getMusicTrack().getData().setVolume(volume);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Optional<GameObject> getGameObject() {
        return Optional.ofNullable(gameObject);
    }

    @Override
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void finish() {

    }

    @Override
    public void beginCollision(GameObject go1, GameObject go2) {

    }

    @Override
    public void endCollision(GameObject go1, GameObject go2) {

    }

    /**
     * Sets a callback that is fired whenever MusicTrack playback completes.
     * @param listener The callback that will be fired on playback completion.
     */
    @SuppressWarnings("unused")
    public void setOnCompletionListener(Music.OnCompletionListener listener) {
        getMusicTrack().getData().setOnCompletionListener(listener);
    }
}

package com.suiton2d.assets;

import com.badlogic.gdx.audio.Sound;

/**
 * SoundEffect is an {@link Asset} for the
 * playback of sound effects. SoundEffects are sampled.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class SoundEffect implements Asset<Sound> {

    private String path;
    private String filename;
    private boolean loaded = false;
    private Sound sound;

    public SoundEffect(String path, Sound sound) {
        this.path = path;
        this.filename = path.substring(path.lastIndexOf("/")+1);
        this.sound = sound;
    }

    /**
     * Plays the SoundEffect.
     * @return The sound ID associated with the current playback instance.
     */
    public long play() {
        return sound.play();
    }

    /**
     * Plays the SoundEffect at a given volume.
     * @param volume The volume at which to play the SoundEffect.
     * @return The sound ID associated with th current playback instance.
     */
    @SuppressWarnings("unused")
    public long play(float volume) {
        return sound.play(volume);
    }

    /**
     * Plays the SoundEffect in a loop.
     * @return The sound ID associated with th current playback instance.
     */
    public long loop() {
        return sound.loop();
    }

    /**
     * Plays the SoundEffect in a loop at the given volume.
     * @param volume The volume at which to play the SoundEffect.
     * @return The sound ID associated with th current playback instance.
     */
    @SuppressWarnings("unused")
    public long loop(float volume) {
        return sound.loop(volume);
    }

    /**
     * Stops playback of a given SoundEffect playback instance.
     * @param soundId The sound ID of the playback instance to stop.
     */
    public void stop(long soundId) {
        sound.stop(soundId);
    }

    /**
     * Pauses playback of a given SoundEffect playback instance.
     * @param soundId The sound ID of the playback instance to pause.
     */
    public void pause(long soundId) {
        sound.pause(soundId);
    }

    /**
     * Resumes playback of a given SoundEffect playback instance.
     *
     * @param soundId The sound ID of the playback instance to resume.
     */
    public void resume(long soundId) {
        sound.resume(soundId);
    }

    @Override
    public Sound getData() {
        return sound;
    }

    @Override
    public String getFilename() {
        return filename;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    @Override
    public void dispose() {
        if (sound != null) {
            sound.dispose();
        }
    }
}

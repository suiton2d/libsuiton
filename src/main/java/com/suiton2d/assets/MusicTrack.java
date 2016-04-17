package com.suiton2d.assets;

import com.badlogic.gdx.audio.Music;

/**
 * MusicTrack is an {@link Asset} for the
 * playback of music files. MusicTracks are streamed.
 *
 * @author      Jon Bonazza <jonbonazza@gmail.com>
 */
public class MusicTrack implements Asset<Music> {

    private String path;
    private String filename;
    private boolean loaded = false;
    private Music track;

    public MusicTrack(String path, Music track) {
        this.path = path;
        this.filename = path.substring(path.lastIndexOf("/")+1);
        this.track = track;
    }

    /**
     * Plays the MusicSource.
     */
    public void play() {
        track.play();
    }

    /**
     * Stops playback of the MusicSource.
     */
    public void stop() {
        track.stop();
    }

    /**
     * Pauses playback of the MusicSource.
     */
    public void pause() {
        track.pause();
    }

    public boolean isLooping() {
        return track.isLooping();
    }

    public void setLooping(boolean looping) {
        track.setLooping(looping);
    }

    @Override
    public Music getData() {
        return track;
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
        if (track != null) {
            track.dispose();
        }
    }
}

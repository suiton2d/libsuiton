package com.suiton2d.assets;

import com.badlogic.gdx.Gdx;
import com.suiton2d.scene.GameObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * JavascriptScript is a {@link Script} implementation for javascript files.
 */
public class JavascriptScript implements Script<ScriptEngine> {
    private static final String TAG = JavascriptScript.class.getSimpleName();
    private String path;
    private String filename;
    private boolean loaded;
    private ScriptEngine scriptEngine;
    private Invocable invocable;

    public JavascriptScript(String path, ScriptEngine scriptEngine) {
        this.path = path;
        this.filename = path.substring(path.lastIndexOf("/")+1);
        this.scriptEngine = scriptEngine;
        this.invocable = (Invocable) scriptEngine;
    }

    @Override
    public ScriptEngine getData() {
        return scriptEngine;
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
        scriptEngine = null;
    }

    @Override
    public void start(GameObject gameObject) {
        try {
            invocable.invokeFunction("start", gameObject);
        } catch (NoSuchMethodException ignored) {

        } catch (ScriptException e) {
            Gdx.app.log(TAG, "Failed to execute start() function.", e);
        }
    }

    @Override
    public void update(GameObject gameObject, float dt) {
        try {
            invocable.invokeFunction("update", gameObject, dt);
        } catch (NoSuchMethodException ignored) {

        } catch (ScriptException e) {
            Gdx.app.log(TAG, "Failed to execute update() function.", e);
        }
    }

    @Override
    public void finish() {
        try {
            invocable.invokeFunction("finish");
        } catch (NoSuchMethodException ignored) {

        } catch (ScriptException e) {
            Gdx.app.log(TAG, "Failed to execute finish() function.", e);
        }
    }

    @Override
    public void beginCollision(GameObject go1, GameObject go2) {
        try {
            invocable.invokeFunction("beginCollision", go1, go2);
        } catch (NoSuchMethodException ignored) {

        } catch (ScriptException e) {
            Gdx.app.log(TAG, "Failed to execute beginCollision() function.", e);
        }
    }

    @Override
    public void endCollision(GameObject go1, GameObject go2) {
        try {
            invocable.invokeFunction("endCollision", go1, go2);
        } catch (NoSuchMethodException ignored) {

        } catch (ScriptException e) {
            Gdx.app.log(TAG, "Failed to execute endCollision() function.", e);
        }
    }
}

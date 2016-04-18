package com.suiton2d.assets.loaders;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;
import com.suiton2d.assets.JavascriptScript;
import com.suiton2d.assets.Script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptLoader extends SynchronousAssetLoader<Script, ScriptLoader.ScriptParameter>{

    private static final String TAG = ScriptLoader.class.getSimpleName();

    public ScriptLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Script load(AssetManager assetManager, String fileName, FileHandle file, ScriptParameter parameter) {
        Script script = null;
        if ("js".equalsIgnoreCase(file.extension())) {
            try {
                script = loadJavascript(file);
            } catch (ScriptException e) {
                Gdx.app.log(TAG, "Failed to load javascript file.", e);
            }
        }

        // TODO: Figure out what needs to be done here in case bad input is provided...
        return script;
    }

    private JavascriptScript loadJavascript(FileHandle file) throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
        initJavascriptObjects(engine);
        engine.eval(file.reader());
        return new JavascriptScript(file.path(), engine);
    }

    private void initJavascriptObjects(ScriptEngine scriptEngine) throws ScriptException {
        scriptEngine.put(Actions.class.getSimpleName(), scriptEngine.eval(Actions.class.getName()));
        scriptEngine.put(MoveByAction.class.getSimpleName(), scriptEngine.eval(MoveByAction.class.getName()));
        scriptEngine.put(MoveToAction.class.getSimpleName(), scriptEngine.eval(MoveToAction.class.getName()));
        scriptEngine.put(Vector.class.getSimpleName(), scriptEngine.eval(Vector.class.getName()));

        scriptEngine.put("Input", Gdx.input);
        scriptEngine.put("Net", Gdx.net);
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, ScriptParameter parameter) {
        return null;
    }

    public static class ScriptParameter extends AssetLoaderParameters<Script> {

    }
}

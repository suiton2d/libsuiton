package com.suiton2d.assets.loaders;


import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.suiton2d.assets.JavascriptScript;
import com.suiton2d.assets.Script;
import com.suiton2d.util.ByteUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class ScriptLoader extends SynchronousAssetLoader<Script, ScriptLoader.ScriptParameter>{

    public ScriptLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Script load(AssetManager assetManager, String fileName, FileHandle file, ScriptParameter parameter) {
        String contents = ByteUtils.decodeBase64String(new String(file.readBytes()));
        if ("js".equalsIgnoreCase(file.extension())) {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            try {
                ScriptableObject globalScope = JavascriptScript.getGlobalScope();
                Scriptable scope = context.newObject(globalScope);
                scope.setPrototype(globalScope);
                scope.setParentScope(null);
                context.evaluateString(scope, contents, fileName, 1, null);
                Object startFunction = scope.get("start", scope);
                Object updateFunction = scope.get("update", scope);
                Object finishFunction = scope.get("finish", scope);
                Object beginCollisionFunction = scope.get("beginCollision", scope);
                Object endCollisionFunction = scope.get("endCollision", scope);
                return new JavascriptScript(file.path(),
                        scope,
                        startFunction != Scriptable.NOT_FOUND ? (Function) startFunction : null,
                        updateFunction != Scriptable.NOT_FOUND ? (Function) updateFunction : null,
                        finishFunction != Scriptable.NOT_FOUND ? (Function) finishFunction : null,
                        beginCollisionFunction != Scriptable.NOT_FOUND ? (Function) beginCollisionFunction : null,
                        endCollisionFunction != Scriptable.NOT_FOUND ? (Function) endCollisionFunction : null);
            } finally {
                Context.exit();
            }
        }

        // TODO: Figure out what needs to be done here in case bad input is provided...
        return null;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, ScriptParameter parameter) {
        return null;
    }

    public static class ScriptParameter extends AssetLoaderParameters<Script> {

    }
}

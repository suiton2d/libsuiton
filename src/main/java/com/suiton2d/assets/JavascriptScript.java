package com.suiton2d.assets;

import com.suiton2d.scene.GameObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * JavascriptScript is a {@link Script} implementation for javascript files.
 */
public class JavascriptScript implements Script<Scriptable> {

    private static ScriptableObject globalScope;

    static {
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        try {
            globalScope = context.initStandardObjects();
        } finally {
            Context.exit();
        }
    }


    private String path;
    private String filename;
    private boolean loaded;
    private Scriptable scope;

    private Function startFunction;
    private Function updateFunction;
    private Function finishFunction;
    private Function beginCollisionFunction;
    private Function endCollisionFunction;

    public JavascriptScript(String path,
                            Scriptable scope,
                            Function startFunction,
                            Function updateFunction,
                            Function finishFunction,
                            Function beginCollisionFunction,
                            Function endCollisionFunction) {
        this.path = path;
        this.filename = path.substring(path.lastIndexOf("/"));
        this.scope = scope;
        this.startFunction = startFunction;
        this.updateFunction = updateFunction;
        this.finishFunction = finishFunction;
        this.beginCollisionFunction = beginCollisionFunction;
        this.endCollisionFunction = endCollisionFunction;
    }

    public static ScriptableObject getGlobalScope() {
        return globalScope;
    }

    @Override
    public Scriptable getData() {
        return scope;
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
        scope = null;
    }

    @Override
    public void start(GameObject gameObject) {
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        try {
            if (startFunction != Scriptable.NOT_FOUND) {
                startFunction.call(context, scope, scope, new Object[]{gameObject});
            }
        } finally {
            Context.exit();
        }
    }

    @Override
    public void update(GameObject gameObject, float dt) {
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        try {
            if (updateFunction != Scriptable.NOT_FOUND) {
                updateFunction.call(context, scope, scope, new Object[]{gameObject, dt});
            }
        } finally {
            Context.exit();
        }
    }

    @Override
    public void finish() {
        startFunction = null;
        updateFunction = null;
        finishFunction = null;
        beginCollisionFunction = null;
        endCollisionFunction = null;
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        try {
            if (finishFunction != Scriptable.NOT_FOUND) {
                finishFunction.call(context, scope, scope, null);
            }
        } finally {
            Context.exit();
        }
    }

    @Override
    public void beginCollision(GameObject go1, GameObject go2) {
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        try {
            if (beginCollisionFunction != Scriptable.NOT_FOUND) {
                beginCollisionFunction.call(context, scope, scope, new Object[]{go1, go2});
            }
        } finally {
            Context.exit();
        }
    }

    @Override
    public void endCollision(GameObject go1, GameObject go2) {
        Context context = Context.enter();
        context.setOptimizationLevel(-1);
        try {
            if (endCollisionFunction != Scriptable.NOT_FOUND) {
                endCollisionFunction.call(context, scope, scope, new Object[]{go1, go2});
            }
        } finally {
            Context.exit();
        }
    }
}

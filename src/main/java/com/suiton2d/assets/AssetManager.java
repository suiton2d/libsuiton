/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) 2014 Jon Bonazza
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.suiton2d.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.utils.ObjectMap;
import com.suiton2d.assets.loaders.MusicTrackLoader;
import com.suiton2d.assets.loaders.ScriptLoader;
import com.suiton2d.assets.loaders.SoundEffectLoader;
import com.suiton2d.assets.loaders.SpriteLoader;
import com.suiton2d.assets.loaders.TiledTileSheetLoader;
import com.suiton2d.scene.Scene;

import java.util.ArrayList;
import java.util.List;

/**
 * AssetManager is a class used to manage a game's assets
 *
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class AssetManager {

    private ObjectMap<String, List<AssetDescriptor>> assetMap = new ObjectMap<>();

    private com.badlogic.gdx.assets.AssetManager manager;

    public AssetManager(FileHandleResolver fileHandleResolver) {
        manager = new com.badlogic.gdx.assets.AssetManager(fileHandleResolver);
        initLoaders(fileHandleResolver);
    }

    private void initLoaders(FileHandleResolver resolver) {
        manager.setLoader(MusicTrack.class, new MusicTrackLoader(resolver));
        manager.setLoader(Script.class, new ScriptLoader(resolver));
        manager.setLoader(SoundEffect.class, new SoundEffectLoader(resolver));
        manager.setLoader(Sprite.class, new SpriteLoader(resolver));
        manager.setLoader(TiledTileSheet.class, new TiledTileSheetLoader(resolver));
    }

    public <T> T getAsset(String filename, Class<T> type) {
        return manager.isLoaded(filename) ? manager.get(filename, type) : null;
    }

    /**
     * Loads the assets for the {@link Scene} with the given name into memory.
     * @param sceneName the name of the Scene whose assets should be loaded.
     */
    public void loadAssets(String sceneName) {
        List<AssetDescriptor> assets = assetMap.get(sceneName);
        if (assets != null) {
            for (AssetDescriptor asset : assets)
                manager.load(asset);
        }

        manager.finishLoading();
    }

    /**
     * Unloads the assets for the Scene with the given name from memory.
     * @param sceneName the name of the Scene whose assets should be unloaded.
     */
    public void unloadAssets(String sceneName) {
        List<AssetDescriptor> assets = assetMap.get(sceneName);
        if (assets != null) {
            for (AssetDescriptor asset : assets)
                manager.unload(asset.fileName);
        }
    }

    public void registerAsset(String sceneName, AssetDescriptor ad) {
        List<AssetDescriptor> ads = assetMap.get(sceneName);
        if (ads == null) {
            ads = new ArrayList<>();
            assetMap.put(sceneName, ads);
        }
        ads.add(ad);
    }

    public void cleanup() {
        for (List<AssetDescriptor> assetList : assetMap.values()) {
            assetList.stream()
                    .filter(asset -> manager.isLoaded(asset.fileName))
                    .forEach(asset -> manager.unload(asset.fileName));
            assetList.clear();
        }
        assetMap.clear();
    }
}

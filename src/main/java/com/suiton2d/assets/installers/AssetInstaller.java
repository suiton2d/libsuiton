package com.suiton2d.assets.installers;

import com.suiton2d.assets.AssetManager;

/**
 * AssetInstaller is an interface used to install assets to an {@link AssetManager}.
 */
public interface AssetInstaller {

    /**
     * Installs assets to {@code assetManager}.
     * @param assetManager The {@link AssetManager} to install assets to.
     * @throws Exception if installation failed.
     */
    void install(AssetManager assetManager) throws Exception;
}

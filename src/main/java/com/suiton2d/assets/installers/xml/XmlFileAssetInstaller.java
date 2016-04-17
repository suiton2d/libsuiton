package com.suiton2d.assets.installers.xml;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.suiton2d.assets.AssetManager;
import com.suiton2d.assets.MusicTrack;
import com.suiton2d.assets.Script;
import com.suiton2d.assets.SoundEffect;
import com.suiton2d.assets.Sprite;
import com.suiton2d.assets.TiledTileSheet;
import com.suiton2d.assets.installers.AssetInstaller;

public class XmlFileAssetInstaller implements AssetInstaller {

    private FileHandle xmlFile;

    public XmlFileAssetInstaller(FileHandle xmlFile) {
        this.xmlFile = xmlFile;
    }

    @Override
    public void install(AssetManager assetManager) throws Exception {
        XmlReader reader = new XmlReader();
        XmlReader.Element root = reader.parse(xmlFile);

        Array<XmlReader.Element> assets = root.getChildrenByName("asset");
        for (XmlReader.Element assetElement : assets) {
            String path = assetElement.getAttribute("path");
            String type = assetElement.getAttribute("assetType");
            String sceneName = assetElement.getAttribute("sceneName");

            if (type.equalsIgnoreCase("SPRITE")) {
                assetManager.registerAsset(sceneName, new AssetDescriptor<>(path, Sprite.class));
            } else if (type.equalsIgnoreCase("MUSIC")) {
                assetManager.registerAsset(sceneName, new AssetDescriptor<>(path, MusicTrack.class));
            } else if (type.equalsIgnoreCase("SFX")) {
                assetManager.registerAsset(sceneName, new AssetDescriptor<>(path, SoundEffect.class));
            } else if (type.equalsIgnoreCase("SCRIPT")) {
                assetManager.registerAsset(sceneName, new AssetDescriptor<>(path, Script.class));
            } else if (type.equalsIgnoreCase("TILED_TILE_SHEET")) {
                assetManager.registerAsset(sceneName, new AssetDescriptor<>(path, TiledTileSheet.class));
            }
        }
    }
}

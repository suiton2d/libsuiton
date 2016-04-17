package com.suiton2d.scene.loaders.xml;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.suiton2d.components.Component;
import com.suiton2d.components.audio.MusicSource;
import com.suiton2d.components.audio.SoundEffectSource;
import com.suiton2d.components.behavior.Behavior;
import com.suiton2d.components.gfx.SpriteRenderer;
import com.suiton2d.components.gfx.TiledMapRenderer;
import com.suiton2d.components.physics.BoundingBox;
import com.suiton2d.components.physics.Circle;
import com.suiton2d.components.physics.Collider;
import com.suiton2d.components.physics.CollisionShape;
import com.suiton2d.components.physics.PhysicsMaterial;
import com.suiton2d.components.physics.RigidBody;
import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.Layer;
import com.suiton2d.scene.Scene;
import com.suiton2d.scene.SceneData;
import com.suiton2d.scene.loaders.SceneDataLoader;

import java.io.IOException;

/**
 * XmlSceneDataLoader is a {@link SceneDataLoader} implementation that loads scene data from an XML file.
 */
public class XmlSceneDataLoader implements SceneDataLoader {
    @Override
    @SuppressWarnings("unchecked")
    public SceneData loadSceneData(FileHandle file) throws IOException {
        XmlReader reader = new XmlReader();
        XmlReader.Element root = reader.parse(file);
        Array<XmlReader.Element> sceneElements = root.getChildrenByName("scene");
        String startScene = root.getAttribute("startScene");
        ObjectMap<String, Scene> sceneMap = new ObjectMap<>();
        for (XmlReader.Element sceneElement : sceneElements) {
            String sceneName = sceneElement.getAttribute("name");
            XmlReader.Element gravityElement = sceneElement.getChildByName("gravity");
            float gX = gravityElement != null ? gravityElement.getFloatAttribute("x", 0.0f) : 0.0f;
            float gY = gravityElement != null ? gravityElement.getFloatAttribute("y", 0.0f) : 0.0f;
            boolean sleepPhysics = sceneElement.getBooleanAttribute("sleepPhysics", false);
            Scene scene = new Scene(sceneName, new Vector2(gX, gY), sleepPhysics);

            Array<XmlReader.Element> layerElements = sceneElement.getChildrenByName("layer");
            for (int i = 0; i < layerElements.size; ++i) {
                XmlReader.Element layerElement = layerElements.get(i);
                String layerName = layerElement.getAttribute("name");
                Layer layer = new Layer(layerName, i);
                Array<XmlReader.Element> gameObjectElements = layerElement.getChildrenByName("gameObject");
                for (XmlReader.Element gameObjectElement : gameObjectElements) {
                    String gameObjectName = gameObjectElement.getAttribute("name");
                    float rotation = gameObjectElement.getFloatAttribute("rot");
                    XmlReader.Element position = gameObjectElement.getChildByName("position");
                    float posX = position.getFloatAttribute("x");
                    float posY = position.getFloatAttribute("y");
                    XmlReader.Element scale = gameObjectElement.getChildByName("scale");
                    float scaleX = scale.getFloatAttribute("x");
                    float scaleY = scale.getFloatAttribute("y");

                    GameObject gameObject = new GameObject(gameObjectName);
                    gameObject.setPosition(posX, posY);
                    gameObject.setScale(scaleX, scaleY);
                    gameObject.setRotation(rotation);

                    Array<XmlReader.Element> componentElements = gameObjectElement.getChildrenByName("component");
                    for (XmlReader.Element componentElement : componentElements) {
                        String componentName = componentElement.getAttribute("name");
                        String componentType = componentElement.getAttribute("componentType");
                        boolean enabled = componentElement.getBooleanAttribute("enabled");
                        Component component = null;

                        if (componentType.equalsIgnoreCase("RENDER")) {
                            String rendererType = componentElement.getAttribute("rendererType");
                            if (rendererType.equalsIgnoreCase("SPRITE_RENDERER")) {
                                String spritePath = componentElement.getAttribute("sprite");
                                component = new SpriteRenderer(componentName, spritePath);
                            } else if (rendererType.equalsIgnoreCase("TILE_MAP_RENDERER")) {
                                String tileSheetPath = componentElement.getAttribute("tileSheet");
                                component = new TiledMapRenderer(componentName, tileSheetPath);
                            }
                        } else if (componentType.equalsIgnoreCase("MUSIC")) {
                            String trackPath = componentElement.getAttribute("track");
                            component = new MusicSource(componentName, trackPath);
                        } else if (componentType.equalsIgnoreCase("SFX")) {
                            String sfxPath = componentElement.getAttribute("sfx");
                            component = new SoundEffectSource(componentName, sfxPath);
                        } else if (componentType.equalsIgnoreCase("BEHAVE")) {
                            String scriptPath = componentElement.getAttribute("script");
                            component = new Behavior(componentName, scriptPath);
                        } else if (componentType.equalsIgnoreCase("RIGID_BODY")) {
                            boolean isKinematic = componentElement.getBooleanAttribute("isKinematic");
                            boolean fixedRotation = componentElement.getBooleanAttribute("fixedRotation");
                            boolean isBullet = componentElement.getBooleanAttribute("isBullet");
                            float mass = componentElement.getFloatAttribute("mass");
                            XmlReader.Element shapeElement = componentElement.getChildByName("collisionShape");
                            String shapeType = shapeElement.getAttribute("shapeType");

                            XmlReader.Element materialElement = shapeElement.getChild(0);
                            float density = materialElement.getFloatAttribute("density");
                            float friction = materialElement.getFloatAttribute("friction");
                            float restitution = materialElement.getFloatAttribute("restitution");

                            PhysicsMaterial material = new PhysicsMaterial(density, friction, restitution);

                            CollisionShape shape = null;
                            if (shapeType.equalsIgnoreCase("BOX")) {
                                float w = shapeElement.getFloatAttribute("w");
                                float h = shapeElement.getFloatAttribute("h");
                                shape = new BoundingBox(material, w, h);
                            } else if (shapeType.equalsIgnoreCase("CIRCLE")) {
                                float r = shapeElement.getFloatAttribute("r");
                                shape = new Circle(material, r);
                            }

                            if (shape != null) {
                                component = new RigidBody(componentName, shape, isKinematic, mass,
                                        fixedRotation, isBullet);
                            }
                        } else if (componentType.equalsIgnoreCase("COLLIDER")) {
                            boolean isSensor = componentElement.getBooleanAttribute("isSensor");
                            XmlReader.Element shapeElement = componentElement.getChildByName("collisionShape");
                            String shapeType = shapeElement.getAttribute("shapeType");

                            XmlReader.Element materialElement = shapeElement.getChild(0);
                            float density = materialElement.getFloatAttribute("density");
                            float friction = materialElement.getFloatAttribute("friction");
                            float restitution = materialElement.getFloatAttribute("restitution");

                            PhysicsMaterial material = new PhysicsMaterial(density, friction, restitution);

                            CollisionShape shape = null;
                            if (shapeType.equalsIgnoreCase("BOX")) {
                                float w = shapeElement.getFloatAttribute("w");
                                float h = shapeElement.getFloatAttribute("h");
                                shape = new BoundingBox(material, w, h);
                            } else if (shapeType.equalsIgnoreCase("CIRCLE")) {
                                float r = shapeElement.getFloatAttribute("r");
                                shape = new Circle(material, r);
                            }

                            if (shape != null) {
                                component = new Collider(componentName, shape, isSensor);
                            }
                        }

                        if (component != null) {
                            component.setEnabled(enabled);
                            gameObject.addComponent(component);
                        }
                    }

                    layer.addActor(gameObject);
                }

                scene.addLayer(layer);
            }

            sceneMap.put(sceneName, scene);
        }

        return new SceneData(sceneMap, startScene);
    }
}

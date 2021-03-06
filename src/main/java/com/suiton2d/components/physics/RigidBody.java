/*
 * Nebula2D is a cross-platform, 2D game engine for PC, Mac, & Linux
 * Copyright (c) $date.year Jon Bonazza
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

package com.suiton2d.components.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.suiton2d.components.Component;
import com.suiton2d.scene.GameObject;
import com.suiton2d.scene.Transform;

/**
 * @author Jon Bonazza <jonbonazza@gmail.com>
 */
public class RigidBody<T extends CollisionShape> implements Component {

    private World world;

    private boolean isKinematic;
    private boolean fixedRotation;
    private boolean isBullet;
    private float mass;
    private Body physicsBody;
    private T collisionShape;

    private String name;
    private GameObject gameObject;
    private boolean enabled = true;

    public RigidBody(String name, T collisionShape, boolean isKinematic,
                     float mass, boolean fixedRotation, boolean isBullet,
                     World world) {
        this.name = name;
        this.isKinematic = isKinematic;
        this.fixedRotation = fixedRotation;
        this.isBullet = isBullet;
        this.collisionShape = collisionShape;
        this.mass = mass;
        this.world = world;
    }

    public boolean isKinematic() {
        return isKinematic;
    }

    public void setIsKinematic(boolean isKinematic) {
        this.isKinematic = isKinematic;
    }

    public boolean isFixedRotation() {
        return fixedRotation;
    }

    public void setFixedRotation(boolean fixedRotation) {
        this.fixedRotation = fixedRotation;
    }

    public boolean isBullet() {
        return isBullet;
    }

    public void setIsBullet(boolean isBullet) {
        this.isBullet = isBullet;
    }

    public float getMass() {
        return mass;
    }

    public T getCollisionShape() {
        return collisionShape;
    }

    public void setCollisionShape(T collisionShape) {
        this.collisionShape = collisionShape;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public GameObject getGameObject() {
        return gameObject;
    }

    @Override
    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    @SuppressWarnings("unused")
    public void applyForce(Vector2 force, Vector2 point, boolean wake) {
        physicsBody.applyForce(force, point, wake);
    }

    @SuppressWarnings("unused")
    public void applyLinearImpulse(Vector2 impulse, Vector2 point, boolean wake) {
        physicsBody.applyLinearImpulse(impulse, point, wake);
    }

    @SuppressWarnings("unused")
    public void applyAngularImpulse(float impulse, boolean wake) {
        physicsBody.applyAngularImpulse(impulse, wake);
    }

    @SuppressWarnings("unused")
    public void applyTorque(float torque, boolean wake) {
        physicsBody.applyTorque(torque, wake);
    }

    public void setMass(float mass) {
        this.mass = mass;
        physicsBody.getMassData().mass = mass;
    }

    @Override
    public void start() {
        if (gameObject != null) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = isKinematic ? BodyDef.BodyType.KinematicBody : BodyDef.BodyType.DynamicBody;
            Transform transform = new Transform(gameObject);
            Vector2 pos = transform.getPosition();
            bodyDef.position.set(pos);
            bodyDef.angle = transform.getRotation();
            bodyDef.fixedRotation = fixedRotation;
            bodyDef.bullet = isBullet;

            physicsBody = world.createBody(bodyDef);
            physicsBody.setUserData(getGameObject());
            physicsBody.getMassData().mass = mass;

            if (collisionShape != null)
                collisionShape.affixTo(physicsBody, false).setUserData(gameObject);
        }
    }

    @Override
    public void finish() {
        world.destroyBody(physicsBody);
        physicsBody = null;
    }
}

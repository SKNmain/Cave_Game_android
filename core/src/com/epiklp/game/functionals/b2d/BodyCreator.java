package com.epiklp.game.functionals.b2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.epiklp.game.Cave;

import sun.security.provider.SHA;

/**
 * Created by Asmei on 2018-02-12.
 */

public class BodyCreator {
    private static FixtureDef fixDef = new FixtureDef();

    //It's only for building map from Tiled
    public static Body createStaticBodyForMapBuild(Shape shape, Object userData) {
        Body pBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.fixedRotation = true;
        pBody = TheBox.world.createBody(def);
        Filter f = new Filter();
        f.categoryBits = TheBox.CATEGORY_BODY;
        f.maskBits = TheBox.MASK_BODY;
        pBody.createFixture(shape, 1).setFilterData(f);

        pBody.setUserData(userData);
        return pBody;
    }

    //it need a position in PIXEL not in meter from box2d!
    //so if you want to create a Body with position from body.getPosition, you must multiply it by (* Cave.PPM / Cave.SCALE)
    public static Body createBody(float x, float y, boolean isStatic, boolean fixedRotation) {
        Body pBody;
        BodyDef def = new BodyDef();
        if (isStatic) def.type = BodyDef.BodyType.StaticBody;
        else def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x / Cave.PPM * Cave.SCALE, y / Cave.PPM * Cave.SCALE);
        def.fixedRotation = fixedRotation;
        pBody = TheBox.world.createBody(def);
        return pBody;
    }

    public static Body createBody(float x, float y, boolean isStatic) {
        return createBody(x, y, isStatic, true);
    }

    public static void createBoxShape(Body body, float width, float height, float density, float friction, Vector2 shift, Object userData) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / Cave.PPM, height / Cave.PPM, shift, 0);
        fixDef.shape = shape;
        fixDef.density = density;
        fixDef.friction = friction;
        fixDef.isSensor = false;
        fixDef.filter.categoryBits = TheBox.CATEGORY_BODY;
        fixDef.filter.maskBits = TheBox.MASK_BODY;
        body.createFixture(fixDef).setUserData(userData);
        shape.dispose();
    }

    public static void createBoxShape(Body body, float width, float height, float density, float friction, Object userData) {
        createBoxShape(body, width, height, density, friction, new Vector2(0,0),  userData);
    }
    public static void createBoxShapeFromVer(Body body, float density, float friction, boolean isSensor, Vector2 ... verticles) {
        PolygonShape shape = new PolygonShape();
        shape.set(verticles);
        fixDef.shape = shape;
        fixDef.density = density;
        fixDef.friction = friction;
        fixDef.isSensor = isSensor;
        body.createFixture(fixDef);
        shape.dispose();
    }
    //UserData is null
    public static void createBoxShape(Body body, float width, float height, float density, float friction) {
        createBoxShape(body, width, height, density, friction, null);
    }

    //Friction and density is 1f
    //UserData is null
    public static void createBoxShape(Body body, float width, float height) {
        createBoxShape(body, width, height, 1, 1, null);
    }

    public static void createBoxSensor(Body body, float width, float height, float density, float friction, Vector2 shiftFromCenter, Object userData) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / Cave.PPM, height / Cave.PPM, new Vector2(shiftFromCenter.x / Cave.PPM, shiftFromCenter.y / Cave.PPM), 0);
        fixDef.shape = shape;
        fixDef.density = density;
        fixDef.friction = friction;
        fixDef.isSensor = true;
        fixDef.filter.categoryBits = TheBox.CATEGORY_SENSOR;
        fixDef.filter.maskBits = TheBox.MASK_SENSOR;
        body.createFixture(fixDef).setUserData(userData);
        shape.dispose();
    }

    //Shift is 0, 0
    public static void createBoxSensor(Body body, float width, float height, float density, float friction, Object userData) {
        createBoxSensor(body, width, height, density, friction, new Vector2(0, 0), userData);
    }

    //Friction and density is 0f
    public static void createBoxSensor(Body body, float width, float height, Vector2 shiftFromCenter, Object userData) {
        createBoxSensor(body, width, height, 0, 0, shiftFromCenter, userData);
    }

    //Friction and density is 0f
    //Shift is 0, 0
    //UserData is null
    public static void createBoxSensor(Body body, float width, float height) {
        createBoxSensor(body, width, height, 0, 0, new Vector2(0, 0), null);
    }
}

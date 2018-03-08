package com.epiklp.game.functionals.b2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.Cave;
import com.epiklp.game.actors.GameObject;

import java.util.Iterator;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Created by Asmei on 2017-11-28.
 */

public class TheBox {

    /*
        public static final short CATEGORY_PLAYER = 0x0001;
        public static final short CATEGORY_ENEMY = 0x0002;
        public static final short CATEGORY_MAP = 0x0004;
        public static final short CATEGORY_ITEM = 0x0008;
        public static final short CATEGORY_BULLET = 0x0032;
        public static final short CATEGORY_WALL = 0x0128;

        public static final short MASK_PLAYER = 1;
        public static final short MASK_WALL = 1;
        public static final short MASK_ENEMY = CATEGORY_MAP | CATEGORY_SENSOR | CATEGORY_PLAYER | CATEGORY_BULLET;
        public static final short MASK_SENSOR = CATEGORY_PLAYER;
        public static final short MASK_BULLET = CATEGORY_MAP | CATEGORY_BULLET | CATEGORY_PLAYER | CATEGORY_ENEMY;
    */
    public static final short CATEGORY_SENSOR = 0x0001;
    public static final short CATEGORY_BODY = 0x004;
    public static final short CATEGORY_LIGHT = 0x008;
    public static final short MASK_SENSOR = CATEGORY_BODY | CATEGORY_SENSOR;
    public static final short MASK_BODY = CATEGORY_BODY | CATEGORY_LIGHT | CATEGORY_SENSOR;
    public static final short MASK_LIGHT = CATEGORY_BODY | CATEGORY_LIGHT;

    public static World world;
    public static RayHandler rayHandler;


    private static Array<GameObject> deleteArrayGameObjects = new Array<GameObject>();
    private static Array<Joint> deleteArrayJoints = new Array<Joint>();

    public static void initWorld() {
        world = new World(new Vector2(0, -30f), true);
        initRayHandler();
    }

    public static void destroyWorld() {
        if (rayHandler != null) {
            rayHandler.dispose();
        }
        if (world != null) {
            world.dispose();
        }

    }

    public static void initRayHandler() {
        rayHandler = new RayHandler(TheBox.world);
        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);
        //rayHandler.setAmbientLight(0.7f);
        rayHandler.setAmbientLight(new Color(0.05f, 0.05f, 0.05f, 0.7f));
        rayHandler.setBlurNum(3);
        rayHandler.setBlur(true);
        rayHandler.diffuseBlendFunc.set(GL20.GL_DST_COLOR, GL20.GL_SRC_COLOR);
        rayHandler.setShadows(true);
        //Light.setGlobalContactFilter( (short)0, (short) 2, TheBox.MASK_LIGHT);

        Light.setGlobalContactFilter(CATEGORY_LIGHT, (short) 0, TheBox.MASK_LIGHT);
    }

    public static PointLight createPointLight(Body body, int rays, Color color, boolean xRay, int distance, int x, int y) {
        PointLight pointLight = new PointLight(rayHandler, rays, color, distance, x, y);
        pointLight.attachToBody(body);
        pointLight.setXray(true); // cienie
        pointLight.setIgnoreAttachedBody(false);
        pointLight.setSoftnessLength(2);
        return pointLight;

    }

    //chamska metoda, do zmiany, jak wymyślę lepszą....
    //Jednak sama deklaracja raczej zostanie
    public static void cleanWorld() {
        world = new World(new Vector2(0, -30f), true);
        initRayHandler();
    }

    //It should be check world.isLocket before you use it
    public static void destroyBody(Body body) {
        if (!world.isLocked())
            world.destroyBody(body);
    }

    public static void addToDeleteArray(GameObject gameObject) {
        deleteArrayGameObjects.add(gameObject);
    }

    public static Iterator<GameObject> getDeleteArrayIter() {
        return deleteArrayGameObjects.iterator();
    }

    public static void addToDeleteArrayJoints(Joint joint) {
        deleteArrayJoints.add(joint);
    }

    public static Iterator<Joint> getDeleteArrayIterJoins() {
        return deleteArrayJoints.iterator();
    }


    public static void sweepDeadBodies() {
        if (!world.isLocked()) {
            Iterator<Joint> j = TheBox.getDeleteArrayIterJoins();
            while (j.hasNext()) {
                world.destroyJoint(j.next());
                j.remove();
            }
            Iterator<GameObject> i = TheBox.getDeleteArrayIter();
            while (i.hasNext()) {
                i.next().destroy();
                i.remove();
            }
        }
    }
}

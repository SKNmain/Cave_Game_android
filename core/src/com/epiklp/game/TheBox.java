package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Asmei on 2017-11-28.
 */

public class TheBox {
    public static World world;

    private TheBox() {
    }

    public static void initWorld() {
        world = new World(new Vector2(0, -10f), true);
    }

    public static Body createBox(int x, int y, float width, float height, boolean isStatic) {
        Body pBody;
        BodyDef def = new BodyDef();
        if (isStatic) def.type = BodyDef.BodyType.StaticBody;
        else def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x / Cave.PPM, y / Cave.PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / Cave.PPM, height / Cave.PPM);

        pBody.createFixture(shape, 1);//.setUserData("player");
        shape.dispose();
        return pBody;
    }


    public static Body createwithJson() {
        BodyEditorLoader loader = new BodyEditorLoader(
                Gdx.files.internal("jon/punkty.json"));

        // 1. Create a BodyDef, as usual.
        BodyDef bd = new BodyDef();
        bd.position.set(0, 0);
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.fixedRotation = true;

        // 2. Create a FixtureDef, as usual.
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;
        Body body = world.createBody(bd);
        loader.attachFixture(body, "Name", fd, 1.7f);
        return body;
    }
    public static void createBoxSensor(Body body, float width, float height, Vector2 shiftFromCenter){

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/Cave.PPM,height/Cave.PPM, new Vector2(shiftFromCenter.x/Cave.PPM,shiftFromCenter.y/Cave.PPM), 0);
        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = 0;
        fixDef.friction = 0;
        fixDef.isSensor = true;
        body.createFixture(fixDef);
        shape.dispose();
    }
    public static void destroyWorld() {
        if (world != null) {
            world.dispose();
        }
    }
}

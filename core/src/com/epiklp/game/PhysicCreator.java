package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Asmei on 2017-11-28.
 */

public class PhysicCreator {

    public static Body createBox(World world, int x, int y, float width, float height, boolean isStatic) {
        Body pBody;
        BodyDef def = new BodyDef();
        if(isStatic) def.type = BodyDef.BodyType.StaticBody;
        else def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x/Cave.PPM, y/Cave.PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/Cave.PPM,height/Cave.PPM);

        pBody.createFixture(shape,1).setUserData("player");
        shape.dispose();
        return pBody;
    }


    public static Body createwithJson(World world)
    {
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

}

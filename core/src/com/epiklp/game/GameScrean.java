package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by epiklp on 27.11.17.
 */

class GameScrean implements Screen, Interface {
    final Cave cave;

    private OrthographicCamera camera;
    private Controller controller;
//	private TextureGame textureGame;

    //physic world 2D
    private Box2DDebugRenderer b2dr;
    private World world;
    private Body player, mapgame;
    private float horizontalForce = 0;

    //texture && sprite && font && map
    private SpriteBatch batch;
    private Sprite playerText;
    private UI ui;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;

    //hero
    private Hero hero;
    Array<Body> bodies;

    public GameScrean(Cave cave) {
        this.cave = cave;

        camera = new OrthographicCamera(width/PPM/SCALE, height/PPM/SCALE);
        hero = new Hero();

        //	textureGame = new TextureGame();
        controller = new Controller();
        ui = new UI();

        world = new World(new Vector2(0,-10),false);

        b2dr = new Box2DDebugRenderer();
        //rayHandler = new RayHandler(world);



        player = CreateBox(400, 300,28f , 48, false);
        batch = new SpriteBatch();
        playerText = new Sprite(new Texture("character/1.png"));

        map = new TmxMapLoader().load("Map/map.tmx");
        tmr = new OrthogonalTiledMapRenderer(map, 0.062f);

        bodies = TiledObject.parseTiledObjectLayer(world, map.getLayers().get("collision").getObjects());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(Gdx.graphics.getDeltaTime());
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                hero.setGround(true);
            }

            @Override
            public void endContact(Contact contact) {
                hero.setGround(false);
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        //textureGame.draw();
        tmr.render();
        batch.begin();
        batch.draw(playerText,player.getPosition().x-0.8f, player.getPosition().y - 1.5f, 108/SCALE/PPM, 192/SCALE/PPM);
        batch.end();
        b2dr.render(world, camera.combined.scl(PPM));
        controller.draw();
        ui.draw(hero.getLive(), hero.getMagic(), player.getPosition().x, player.getPosition().y);
    }
    public void update(float delta)
    {
        world.step(1/60f, 6, 2);
        inputUpdate();
        cameraUpdate();
        tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);
    }

    private void cameraUpdate() {
        Vector3 poition = camera.position;
        poition.x = player.getPosition().x;
        poition.y = player.getPosition().y;//height/PPM/4;
        camera.position.set(poition);
        camera.update();
    }

    private Body CreateBox(int x, int y, float width, float height, boolean isStatic) {
        Body pBody;
        BodyDef def = new BodyDef();
        if(isStatic) def.type = BodyDef.BodyType.StaticBody;
        else def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x/PPM, y/PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/PPM,height/PPM);

        pBody.createFixture(shape,1).setUserData("player");
        shape.dispose();
        return pBody;
    }


    private void inputUpdate() {

        if(Gdx.input.isTouched()) {
            if (controller.isLeftPressed()) {
                playerText.setFlip(true, false);
                if (horizontalForce > -(hero.getspeedWalk()))
                    horizontalForce -= 0.4f;
            } else if (controller.isRightPressed()) {
                playerText.setFlip(false, false);
                if (horizontalForce < (hero.getspeedWalk()))
                    horizontalForce += 0.4f;
            }
        }
        else
        {
            if(horizontalForce > 0.1)
            {
                horizontalForce -= 0.2f;
            }
            else
            if(horizontalForce < -0.1)
            {
                horizontalForce += 0.2f;
            }
            else
            {
                horizontalForce = 0;
            }
        }

        player.setLinearVelocity(horizontalForce, player.getLinearVelocity().y);

        if (controller.isUpPressed() && hero.getGround())
        {
            player.setLinearVelocity(0, 7);
        }


        if(controller.isDownPressed())
            hero.setLive(-3);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        batch.dispose();
        ui.dispose();
        //textureGame.dispose();
        controller.dispose();
        tmr.dispose();
        map.dispose();
    }

    public Body createwithJson()
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

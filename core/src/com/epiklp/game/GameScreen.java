package com.epiklp.game;

import com.badlogic.gdx.Application;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.epiklp.game.actors.Hero;

/**
 * Created by epiklp on 27.11.17.
 *
 * Stage dla controllera
 */

class GameScreen implements Screen{
    final Cave cave;

    private Stage stage;

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
    private Array<Body> bodies;

//    private RayHandler rayHandler;

    public GameScreen(Cave cave) {
        this.cave = cave;
        stage = new Stage(new ExtendViewport(Cave.WIDTH,Cave.HEIGHT));
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera(Cave.WIDTH/Cave.PPM/Cave.SCALE,
                                        Cave.HEIGHT/Cave.PPM/Cave.SCALE);

        //	textureGame = new TextureGame();
        controller = new Controller();
        ui = new UI();

        world = new World(new Vector2(0,-10),true);

        b2dr = new Box2DDebugRenderer();
        //rayHandler = new RayHandler(world);


        hero = new Hero(new Sprite(new Texture("character/1.png")));
        hero.setBody(createBox(400, 300,28f , 48, false));
        stage.addActor(hero);

        batch = new SpriteBatch();


        map = new TmxMapLoader().load("Map/map.tmx");
        tmr = new OrthogonalTiledMapRenderer(map, 0.062f);

        bodies = TiledObject.parseTiledObjectLayer(world, map.getLayers().get("collision").getObjects());

        //rayHandler = new RayHandler(world);

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

        checkEndGame();
        //textureGame.draw();
        tmr.render();
        b2dr.render(world, camera.combined/*.scl(Cave.PPM)*/);
        controller.draw();
        ui.draw(hero.getLife(), hero.getMagic(), hero.getBody().getPosition().x, hero.getBody().getPosition().y);


        stage.act();
        stage.draw();
    }

    private void checkEndGame() {
        if(hero.isDead()) {
            cave.setScreen(new EndScreen(cave));
        }
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
        Vector3 position = camera.position;
        position.x = hero.getBody().getPosition().x;
        position.y = hero.getBody().getPosition().y;//HEIGHT/PPM/4;
        camera.position.set(position);
        camera.update();
    }

    private Body createBox(int x, int y, float width, float height, boolean isStatic) {
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

    private void inputUpdate() {

        if(Gdx.input.isTouched()) {
            if (controller.isLeftPressed()) {
                hero.getSprite().setFlip(true, false);
                if (horizontalForce > -(hero.getSpeedWalk()))
                    horizontalForce -= 0.4f;
            } else if (controller.isRightPressed()) {
                hero.getSprite().setFlip(false, false);
                if (horizontalForce < (hero.getSpeedWalk()))
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

        hero.getBody().setLinearVelocity(horizontalForce, hero.getBody().getLinearVelocity().y);

        if (controller.isUpPressed() && hero.getGround())
        {
            hero.getBody().setLinearVelocity(0, 7);
        }


        if(controller.isDownPressed())
            hero.setLife(-3);
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
//        rayHandler.dispose();
        world.dispose();
        b2dr.dispose();
        batch.dispose();
        ui.dispose();
        //textureGame.dispose();
        controller.dispose();
        tmr.dispose();
        map.dispose();
    }

}

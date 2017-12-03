package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epiklp.game.actors.Enemy;
import com.epiklp.game.actors.FlameDemon;
import com.epiklp.game.actors.GameActor;
import com.epiklp.game.actors.Hero;

import java.util.Iterator;

/**
 * Created by epiklp on 27.11.17.
 * <p>
 * Stage dla controllera
 */

class GameScreen implements Screen {
    final Cave cave;

    private Stage stage;

    private OrthographicCamera camera;
    private Controller controller;
//	private TextureGame textureGame;

    //physic world 2D
    private Box2DDebugRenderer b2dr;

    private float horizontalForce = 0;

    //texture && sprite && font && map
    private UI ui;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Viewport viewport;
    //hero
    private Hero hero;
    private GameActor enemy;
    private Array<Enemy> enemies;
    private Array<Body> bodies;

    private MyContactListener myContactListener;

//    private RayHandler rayHandler;

    public GameScreen(Cave cave) {
        this.cave = cave;
        TheBox.initWorld();
        camera = new OrthographicCamera(Cave.WIDTH, Cave.HEIGHT);
        viewport = new ExtendViewport(Cave.WIDTH / Cave.SCALE, Cave.HEIGHT / Cave.SCALE, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        myContactListener = new MyContactListener();
        controller = new Controller();
        ui = new UI();

        b2dr = new Box2DDebugRenderer();
        //rayHandler = new RayHandler(world);


        enemy = new FlameDemon();
        stage.addActor(enemy);
        hero = new Hero();
        stage.addActor(hero);

        map = new TmxMapLoader().load("Map/map.tmx");
        tmr = new OrthogonalTiledMapRenderer(map, 2f);

        bodies = TiledObject.parseTiledObjectLayer(TheBox.world, map.getLayers().get("collision").getObjects());

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
        TheBox.world.setContactListener(myContactListener);

        checkEndGame();
        //textureGame.draw();
        tmr.render();
        b2dr.render(TheBox.world, camera.combined.scl(Cave.PPM));

        stage.act();
        stage.draw();

        controller.draw();
        ui.draw(hero.getLife(), hero.getMagic(), hero.getBody().getPosition().x, hero.getBody().getPosition().y);

    }

    private void checkEndGame() {
        if (hero.isDead()) {
            cave.setScreen(new EndScreen(cave));
        }
    }

    public void update(float delta) {
        sweepDeadBodies();
        TheBox.world.step(1 / 60f, 6, 2);
        inputUpdate();
        cameraUpdate();
        tmr.setView(camera);
        stage.getViewport().setCamera(camera);
    }

    private void cameraUpdate() {
        Vector3 position = camera.position;
        position.x = hero.getBody().getPosition().x * Cave.PPM;
        position.y = hero.getBody().getPosition().y * Cave.PPM;//HEIGHT/PPM/4;
        camera.position.set(position);
        camera.update();
    }


    private void inputUpdate() {

        if (Gdx.input.isTouched()) {
            if (controller.isLeftPressed()) {
                hero.getSprite().setFlip(true, false);
                if (horizontalForce > -(hero.getSpeedWalk()))
                    horizontalForce -= 0.4f;
            } else if (controller.isRightPressed()) {
                hero.getSprite().setFlip(false, false);
                if (horizontalForce < (hero.getSpeedWalk()))
                    horizontalForce += 0.4f;
            }
        } else {
            if (horizontalForce > 0.1) {
                horizontalForce -= 0.2f;
            } else if (horizontalForce < -0.1) {
                horizontalForce += 0.2f;
            } else {
                horizontalForce = 0;
            }
        }
        hero.setSpeedX(horizontalForce);
        if (controller.isUpPressed() && hero.getBody().getLinearVelocity().y == 0) {
            hero.setSpeedY(7f);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);

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
        TheBox.destroyWorld();
        b2dr.dispose();
        ui.dispose();
        controller.dispose();
        tmr.dispose();
        map.dispose();
    }

    public void sweepDeadBodies() {
        if (!TheBox.world.isLocked()) {
            Iterator<Enemy> i = myContactListener.getDeadsTableIter();
            while (i.hasNext()) {
                i.next().destroy();
                i.remove();
            }
        }
    }
}

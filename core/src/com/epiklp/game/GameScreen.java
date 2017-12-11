package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epiklp.game.actors.enemies.Enemy;
import com.epiklp.game.actors.enemies.FlameDemon;
import com.epiklp.game.actors.GameObject;
import com.epiklp.game.actors.Hero;

import java.util.Iterator;

import box2dLight.PointLight;
import box2dLight.RayHandler;

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
    private GameObject enemy;
    private Array<Enemy> enemies;
    private Array<Body> mapBodies;

    private MyContactListener myContactListener;

    private RayHandler rayHandler;
    private PointLight pointLight;

    public GameScreen(Cave cave) {
        this.cave = cave;
        TheBox.initWorld();
        camera = new OrthographicCamera(Cave.WIDTH, Cave.HEIGHT);
        viewport = new ExtendViewport(Cave.WIDTH / 1.5f , Cave.HEIGHT / 1.5f, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        myContactListener = new MyContactListener();
        controller = new Controller();
        ui = new UI();

        b2dr = new Box2DDebugRenderer();

        enemy = new FlameDemon();
        stage.addActor(enemy);
        hero = new Hero();
        stage.addActor(hero);
        map = new TmxMapLoader().load("Map/map.tmx");
        tmr = new OrthogonalTiledMapRenderer(map, 2f);

        mapBodies = TiledObject.parseTiledObjectLayer(TheBox.world, map.getLayers().get("collision").getObjects());
        //initRayHandler();

    }

    private void initRayHandler() {
        RayHandler.setGammaCorrection(false);
        RayHandler.useDiffuseLight(false);
        rayHandler = new RayHandler(TheBox.world);
        rayHandler.setAmbientLight(0.1f, 0.1f, 0.1f, 0.2f);
        rayHandler.setCulling(true);
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(1);
        rayHandler.setShadows(true);

        pointLight = new PointLight(rayHandler, 360, new Color(1f, 1f, 1f, 1f), 18, -2,-2);
        pointLight.attachToBody(hero.getBody());
        pointLight.setXray(false);
    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        TheBox.world.step(1 / 60f, 6, 2);

       // rayHandler.update();
       // pointLight.update();
        inputUpdate();
        cameraUpdate();
//        rayHandler.setCombinedMatrix(camera.combined.scl(Cave.PPM));
        tmr.setView(camera);
        stage.getViewport().setCamera(camera);
        sweepDeadBodies();
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
        tmr.render();
        b2dr.render(TheBox.world, camera.combined.scl(Cave.PPM));

        stage.act();
        stage.draw();
        //rayHandler.render();
        controller.draw();
        ui.draw(hero.getLife(), hero.getMagic(), hero.getBody().getPosition().x, hero.getBody().getPosition().y);

    }

    private void checkEndGame() {
        if (hero.isDead()) {
            cave.setScreen(new EndScreen(cave));
        }
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

        if (controller.isAttackPressed()) {
            hero.shoot();
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
        TheBox.destroyWorld();
        b2dr.dispose();
        ui.dispose();
        controller.dispose();
        tmr.dispose();
        map.dispose();
        //rayHandler.dispose();
    }

    public void sweepDeadBodies() {
        if (!TheBox.world.isLocked()) {
            Iterator<GameObject> i = TheBox.getDeleteArrayIter();
            while (i.hasNext()) {
                i.next().destroy();
                i.remove();
            }
        }
    }
}

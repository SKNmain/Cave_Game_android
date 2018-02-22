package com.epiklp.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
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
import com.epiklp.game.Cave;
import com.epiklp.game.actors.GameObject;
import com.epiklp.game.functionals.Controller;
import com.epiklp.game.functionals.GameContactListener;
import com.epiklp.game.functionals.MapBuilder;
import com.epiklp.game.functionals.b2d.TheBox;
import com.epiklp.game.functionals.UI;
import com.epiklp.game.actors.characters.Enemy;
import com.epiklp.game.actors.characters.Hero;

/**
 * Created by epiklp on 27.11.17.
 * Created by epiklp on 27.11.17.
 */

public class GameScreen implements Screen {
    final Cave cave;

    private Stage stage;

    private OrthographicCamera camera;
    private Controller controller;

    private Box2DDebugRenderer b2dr;

    private float horizontalForce = 0;

    //texture && sprite && font && map
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Viewport viewport;
    //hero
    private Hero hero;

    private Array<Enemy> enemies;
    private Array<GameObject> items;
    private Array<Body> mapBodies;

    private GameContactListener gameContactListener;


    public GameScreen(Cave cave) {
        this.cave = cave;
        camera = new OrthographicCamera(Cave.WIDTH, Cave.HEIGHT);
        viewport = new ExtendViewport(Cave.WIDTH / 1.2f, Cave.HEIGHT / 1.2f, camera);
        stage = new Stage(viewport);
        gameContactListener = new GameContactListener();
        controller = new Controller(false);
        Gdx.input.setInputProcessor(new InputMultiplexer());


        b2dr = new Box2DDebugRenderer();
        map = new TmxMapLoader().load("Map/tmp.tmx"); //mapa  odpowiednimi wymiarami mieniona 32x32

        tmr = new OrthogonalTiledMapRenderer(map, 2f);
        mapBodies = MapBuilder.parseTiledObjectLayer(map.getLayers().get("collision").getObjects());
        hero = MapBuilder.parseHeroFromObjectLayer(map.getLayers().get("characters").getObjects());
        items = MapBuilder.parseItemsAndObjectFromObjectLayer(map.getLayers().get("items").getObjects());
        stage.addActor(hero);
        enemies = MapBuilder.parseEnemiesFromObjectLayer(map.getLayers().get("characters").getObjects());
        for (Enemy ac : enemies) {
            stage.addActor(ac);
        }
        for (GameObject i : items) {
            stage.addActor(i);
        }


        InputMultiplexer inputMultiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        inputMultiplexer.addProcessor(Cave.MenuPause);
        inputMultiplexer.addProcessor(controller);
    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        TheBox.world.step(1 / 60f, 6, 2);
        TheBox.sweepDeadBodies();

        TheBox.rayHandler.update();
        inputUpdate();
        cameraUpdate();
        tmr.setView(camera);
        stage.getViewport().setCamera(camera);
        TheBox.rayHandler.setCombinedMatrix(camera.combined.scl(Cave.PPM));
        cave.ui.update(hero.maxLife, hero.actLife, hero.maxMana, hero.actMana);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Cave.state.equals(Cave.STATE.GAME)) {
            update(Gdx.graphics.getDeltaTime());
            TheBox.world.setContactListener(gameContactListener);
            checkEndGame();
            tmr.render();
            stage.act();
            stage.draw();
            TheBox.rayHandler.render();
            controller.draw();
            cave.ui.draw();


        } else  if (Cave.state.equals(Cave.STATE.RESTART)) {
            TheBox.cleanWorld();
            dispose();
            cave.setScreen(new Menu(cave));
        }
        else if (Cave.state.equals(Cave.STATE.OPTION))
        {
            tmr.render();
            stage.draw();
            TheBox.rayHandler.render();
            Cave.MenuPause.draw();
        }
        if(Cave.renderBox2D)
            b2dr.render(TheBox.world, camera.combined.scl(Cave.PPM));
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
            if (controller.isLeftPressed())
                hero.wantToMoveLeft();
            else if (controller.isRightPressed())
                hero.wantToMoveRight();
        } else
            hero.wantToIdle();


        if (controller.isUpPressed() && hero.canClimbing()) {
            hero.wantToClimb();
        } else if (controller.isUpPressed()) {
            hero.wantToJump();
        }

        if (controller.isAttackPressed()) {
            //hero.shoot();
            hero.meleeAttack();
        }

        if (controller.isHomePresed()) {
            Cave.state = Cave.STATE.OPTION;
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
        controller.dispose();
        tmr.dispose();
        map.dispose();
    }


}

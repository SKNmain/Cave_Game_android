package com.epiklp.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
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
import com.epiklp.game.Cave;
import com.epiklp.game.Functional.Controller;
import com.epiklp.game.Functional.GameContactListener;
import com.epiklp.game.Functional.MapBuilder;
import com.epiklp.game.Functional.TheBox;
import com.epiklp.game.Functional.UI;
import com.epiklp.game.actors.GameObject;
import com.epiklp.game.actors.characters.Enemy;
import com.epiklp.game.actors.characters.FlameDemon;
import com.epiklp.game.actors.characters.Hero;

import java.util.Iterator;

/**
 * Created by epiklp on 27.11.17.
 * Created by epiklp on 27.11.17.
 */

public class GameScreen implements Screen {
    final Cave cave;

    private boolean PAUSE;
    private Stage stage;

    private OrthographicCamera camera;
    private Controller controller;

    private Box2DDebugRenderer b2dr;

    private float horizontalForce = 0;

    //texture && sprite && font && map
    private UI ui;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Viewport viewport;
    //hero
    private Hero hero;

    private Array<Enemy> enemies;
    private Array<Body> mapBodies;

    private GameContactListener gameContactListener;
    private PauseMenu MenuPause;

    public GameScreen(Cave cave) {
        PAUSE = false;
        this.cave = cave;
        camera = new OrthographicCamera(Cave.WIDTH, Cave.HEIGHT);
        viewport = new ExtendViewport(Cave.WIDTH / 1.2f, Cave.HEIGHT / 1.2f, camera);
        stage = new Stage(viewport);
        gameContactListener = new GameContactListener();
        controller = new Controller(false);
        Gdx.input.setInputProcessor(new InputMultiplexer());
        ui = new UI();

        b2dr = new Box2DDebugRenderer();
        map = new TmxMapLoader().load("Map/tmp.tmx"); //mapa  odpowiednimi wymiarami mieniona 32x32

        tmr = new OrthogonalTiledMapRenderer(map, 2f);
        mapBodies = MapBuilder.parseTiledObjectLayer(TheBox.world, map.getLayers().get("collision").getObjects());
        hero = MapBuilder.parseHeroFromObjectLayer(map.getLayers().get("characters").getObjects());
        stage.addActor(hero);
        enemies = MapBuilder.parseEnemiesFromObjectLayer(map.getLayers().get("characters").getObjects());
        for(Enemy ac : enemies){
            stage.addActor(ac);
        }
        MenuPause = new PauseMenu();

        InputMultiplexer inputMultiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        inputMultiplexer.addProcessor(MenuPause);
        inputMultiplexer.addProcessor(controller);
    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        TheBox.world.step(1 / 60f, 6, 2);
        sweepDeadBodies();

        TheBox.rayHandler.update();
        inputUpdate();
        cameraUpdate();
        tmr.setView(camera);
        stage.getViewport().setCamera(camera);
        TheBox.rayHandler.setCombinedMatrix(camera.combined.scl(Cave.PPM));
        ui.update(hero.getLife(), hero.getMagic());

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!PAUSE) {
            update(Gdx.graphics.getDeltaTime());
            TheBox.world.setContactListener(gameContactListener);
            checkEndGame();
            tmr.render();
            stage.act();
            stage.draw();
            TheBox.rayHandler.render();
            controller.draw();
            ui.draw();
            b2dr.render(TheBox.world, camera.combined.scl(Cave.PPM));

        } else {
            MenuPause.draw();
            updateMenu(Gdx.graphics.getDeltaTime());
        }
    }

    public void updateMenu(float delta) {
        if (MenuPause.presssResume) {
            resume();
        }
        if (MenuPause.pressRestart) {
            TheBox.cleanWorld();
            cave.setScreen(new Menu(cave));
            //  dispose();
        }
        if (MenuPause.pressExit) {
            dispose();
            Gdx.app.exit();
        }
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
                hero.setState(Hero.STATE.RUNNING);
                hero.setTurn(false);
            } else if (controller.isRightPressed()) {
                hero.setState(Hero.STATE.RUNNING);
                hero.setTurn(true);
            }
        } else {
            hero.setState(Hero.STATE.STANDING);
        }
        if (controller.isUpPressed() && hero.canClimbing() ) {
            hero.setState(Hero.STATE.CLIMBING);
        }
        else if (controller.isUpPressed()) {
            hero.setState(Hero.STATE.JUMPING);
        }


        if (controller.isAttackPressed()) {
            hero.shoot();
        }

        if (controller.isHomePresed()) {
            pause();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {
        PAUSE = true;
    }

    @Override
    public void resume() {
        PAUSE = false;
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
        MenuPause.dispose();
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

package com.epiklp.game.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epiklp.game.Cave;
import com.epiklp.game.Functional.Assets;
import com.epiklp.game.Functional.Controller;
import com.epiklp.game.Functional.MyContactListener;
import com.epiklp.game.Functional.TheBox;
import com.epiklp.game.actors.characters.Hero;


/**
 * Created by epiklp on 23.12.17.
 */

public class Menu implements Screen {
    private enum STATE{
        OPTION, GAME, CREDIT, SHOP
    };


    final Cave cave;
    private Camera camera;
    private Viewport viewport;
    private Stage stage;
//    private MyContactListener myContactListener;
    private Controller controller;
    private Hero hero;
    private float horizontalForce = 0;
    private Box2DDebugRenderer b2dr;
    private Image image;
    private Image shop;
    private Image credit;
    private Image cave1;
    private Image cave2;
    private boolean enterShop, enterCredit, enterCave;
    private Body creditBody, shopBody, caveBody;
    private Music soundTrack;
    private CreditsScreen creditsScreen;

    private STATE state;

    private PauseMenu MenuPause;

    public Menu(Cave cave)
    {
        state = STATE.GAME;

        this.cave = cave;

        enterCredit = false;
        enterShop = false;

        //music
        soundTrack = Assets.manager.get(Assets.menuMusic);
        soundTrack.setLooping(true);
        soundTrack.play();

        //Download Assets
        image = new Image(Assets.manager.get(Assets.menuLayer));
        image.setSize(Cave.WIDTH, Cave.HEIGHT);
        shop = new Image(Assets.manager.get(Assets.shopLayer));
        shop.setSize(200,200);
        shop.setPosition(500,55);
        credit = new Image(Assets.manager.get(Assets.creditLayer));
        credit.setSize(150,150);
        credit.setPosition(180, 55);
        cave1 = new Image(Assets.manager.get(Assets.cave1Layer));
        cave1.setSize(400,Cave.HEIGHT);
        cave1.setPosition(Cave.WIDTH-500, 55);
        cave2 = new Image(Assets.manager.get(Assets.cave2Layer));
        cave2.setSize(400,Cave.HEIGHT);
        cave2.setPosition(Cave.WIDTH-500, 55);

        //Create stage
        camera = new OrthographicCamera(Cave.WIDTH, Cave.HEIGHT);
        viewport = new ExtendViewport(Cave.WIDTH / 1.5f , Cave.HEIGHT / 1.2f, camera);
        stage = new Stage(viewport);
        stage.addActor(image);
        stage.addActor(shop);
        stage.addActor(credit);
        stage.addActor(cave1);
        TheBox.initWorld(); //Crete WorldBox
        hero = new Hero();  //Hero
        stage.addActor(hero);
        stage.addActor(cave2);


        b2dr = new Box2DDebugRenderer();
        TheBox.createBox(0,50,Cave.WIDTH,10,true, (short)0, (short)0);
        creditBody = TheBox.createBox(257, 250, 10,10, true, (short)0, (short)0);
        creditBody.setUserData("credit");
        TheBox.createBoxSensor(creditBody, 64,86, new Vector2(0,-98));
        shopBody = TheBox.createBox(600, 250, 10,10, true, (short)0, (short)0);
        shopBody.setUserData("shop");
        TheBox.createBoxSensor(shopBody, 86,86, new Vector2(0,-98));
        caveBody = TheBox.createBox(1075, 250,10,10, true, (short)0, (short)0);
        TheBox.createBoxSensor(caveBody, 86,86, new Vector2(0,-98));
        caveBody.setUserData("cave");

        //Multi Events
        MenuPause = new PauseMenu();
        controller = new Controller(false);
        Gdx.input.setInputProcessor(new InputMultiplexer());
        InputMultiplexer inputMultiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        inputMultiplexer.addProcessor(MenuPause);
        inputMultiplexer.addProcessor(controller);

        TheBox.world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if(a.getUserData() instanceof Hero && b.getUserData() == "credit")
                {
                    controller.enterOn(new Vector2(240, 250));
                    enterCredit = true;
                    return;
                }

                if(a.getUserData() instanceof Hero && b.getUserData() == "shop")
                {
                    controller.enterOn(new Vector2(665, Cave.HEIGHT/3 + 20));
                    enterShop = true;
                    return;
                }

                if(a.getUserData() instanceof Hero && b.getUserData() == "cave")
                {
                    controller.enterOn(new Vector2(1140, Cave.HEIGHT/3 + 20));
                    enterCave = true;
                    return;
                }
            }

            @Override
            public void endContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if(a.getUserData() instanceof Hero && b.getUserData() == "credit")
                {
                    controller.enterOff();
                    enterCredit = false;
                    return;
                }

                if(a.getUserData() instanceof Hero && b.getUserData() == "shop")
                {
                    controller.enterOff();
                    enterShop = false;
                    return;
                }

                if(a.getUserData() instanceof Hero && b.getUserData() == "cave")
                {
                    controller.enterOff();
                    enterCave = false;
                    return;
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(state.equals(STATE.OPTION)) {
            MenuPause.draw();
            updateMenu(Gdx.graphics.getDeltaTime());
        }
        else if(state.equals(STATE.GAME)) {
            b2dr.render(TheBox.world, camera.combined.scl(Cave.PPM));
            stage.act();
            stage.draw();
            controller.draw();
            update(Gdx.graphics.getDeltaTime());
            //TheBox.world.setContactListener(myContactListener);
        }
        else if(state.equals(STATE.CREDIT)){

        }
    }

    private void update(float delta)
    {
        TheBox.world.step(1 / 60f, 6, 2);
        inputUpdate();  //Controler
    }

    private void inputUpdate() {
        if (Gdx.input.isTouched()) {
            if (controller.isLeftPressed()) {
                hero.getSprite().setFlip(true, false);
                hero.setTurn(false);
                if (horizontalForce > -(hero.getSpeedWalk())) horizontalForce -= 0.4f;
            } else if (controller.isRightPressed()) {
                hero.getSprite().setFlip(false, false);
                hero.setTurn(true);
                if (horizontalForce < (hero.getSpeedWalk())) horizontalForce += 0.4f;
            }
        } else {
            if (horizontalForce > 0.1) {horizontalForce -= 0.2f;}
            else if (horizontalForce < -0.1) {horizontalForce += 0.2f;}
            else {horizontalForce = 0;}
        }
        hero.setSpeedX(horizontalForce);
        if (controller.isUpPressed() && hero.getBody().getLinearVelocity().y == 0) {hero.setSpeedY(7f);}

        if(controller.isEnterPresed())
        {
            if(enterShop) System.out.println("weeee shoping time!!!!!!");
            else if(enterCredit) System.out.println("credit or somethink");
            else if(enterCave) System.out.println("map");
        }

        if(controller.isHomePresed()) { pause();}
    }

    public void updateMenu(float delta)
    {
        if(MenuPause.presssResume) {resume();}
        if(MenuPause.pressRestart)
        {
            dispose();
            cave.setScreen(new GameScreen(cave));
        }
        if(MenuPause.pressExit)
        {
            dispose();
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {state = STATE.OPTION;}

    @Override
    public void resume() {state = STATE.GAME;}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        controller.dispose();
        hero.destroy();
        MenuPause.dispose();
    }
}

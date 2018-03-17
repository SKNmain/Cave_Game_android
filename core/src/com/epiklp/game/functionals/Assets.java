package com.epiklp.game.functionals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by epiklp on 29.11.17.
 */

public class Assets {

    //Asset
    public static final AssetManager MANAGER = new AssetManager();

    public static final AssetDescriptor<TextureAtlas> textureAtlas = new AssetDescriptor<TextureAtlas>("textures/enemies.atlas", TextureAtlas.class);

    //player
    public static final AssetDescriptor<Texture> logo = new AssetDescriptor<Texture>("logo.png", Texture.class);
    /*******************************************/

    public static final AssetDescriptor<ParticleEffect> hitParticleEff = new AssetDescriptor<ParticleEffect>("particles/hitEffect.p", ParticleEffect.class);

    //Buttons
    /*******************************************/
    public static final AssetDescriptor<Texture> pauseWindow = new AssetDescriptor<Texture>("PauseMenu/window.png", Texture.class);
    public static final AssetDescriptor<Texture> restartButton = new AssetDescriptor<Texture>("PauseMenu/button_restart.png", Texture.class);
    public static final AssetDescriptor<Texture> resumeButton = new AssetDescriptor<Texture>("PauseMenu/button_resume.png", Texture.class);
    public static final AssetDescriptor<Texture> backButton = new AssetDescriptor<Texture>("MenuButtons/button_back.png", Texture.class);
    public static final AssetDescriptor<Texture> playButton = new AssetDescriptor<Texture>("MenuButtons/button_play.png", Texture.class);
    public static final AssetDescriptor<Texture> quitButton = new AssetDescriptor<Texture>("MenuButtons/button_quit.png", Texture.class);
    public static final AssetDescriptor<Texture> continueButton = new AssetDescriptor<Texture>("MenuButtons/button_continue.png", Texture.class);
    public static final AssetDescriptor<Texture> creditButton = new AssetDescriptor<Texture>("MenuButtons/button_credit.png", Texture.class);
    public static final AssetDescriptor<Texture> enterButton = new AssetDescriptor<Texture>("button/E.png", Texture.class);
    /*******************************************/

    //ControlerButtons
    /*******************************************/
    public static final AssetDescriptor<Texture> sellectButton = new AssetDescriptor<Texture>("ControlerButtons/sellect.png", Texture.class);
    public static final AssetDescriptor<Texture> leftButton = new AssetDescriptor<Texture>("ControlerButtons/Left.png", Texture.class);
    public static final AssetDescriptor<Texture> rightButton = new AssetDescriptor<Texture>("ControlerButtons/Right.png", Texture.class);
    public static final AssetDescriptor<Texture> mageAttackButton = new AssetDescriptor<Texture>("ControlerButtons/mageAttack.png", Texture.class);
    public static final AssetDescriptor<Texture> swordAttackButton = new AssetDescriptor<Texture>("ControlerButtons/swordAttack.png", Texture.class);
    public static final AssetDescriptor<Texture> upButton = new AssetDescriptor<Texture>("ControlerButtons/Up.png", Texture.class);
    public static final AssetDescriptor<Texture> homeButton = new AssetDescriptor<Texture>("ControlerButtons/home2.png", Texture.class);
    /*******************************************/

    //Pause
    /*******************************************/
    public static final AssetDescriptor<Texture> exit = new AssetDescriptor<Texture>("PauseMenuButtons/Exit.png", Texture.class);
    public static final AssetDescriptor<Texture> on = new AssetDescriptor<Texture>("PauseMenuButtons/ON.png", Texture.class);
    public static final AssetDescriptor<Texture> off = new AssetDescriptor<Texture>("PauseMenuButtons/OFF.png", Texture.class);
    public static final AssetDescriptor<Texture> resume = new AssetDescriptor<Texture>("PauseMenuButtons/Resume.png", Texture.class);
    public static final AssetDescriptor<Texture> restart = new AssetDescriptor<Texture>("PauseMenuButtons/Restart.png", Texture.class);
    public static final AssetDescriptor<Texture> render = new AssetDescriptor<Texture>("PauseMenuButtons/Box2D.png", Texture.class);
    public static final AssetDescriptor<Texture> BACGROUND_PAUSE = new AssetDescriptor<Texture>("PauseMenuButtons/background_pause.png", Texture.class);
    /*******************************************/


    //menu
    /*******************************************/
    public static final AssetDescriptor<Texture> menuLayer = new AssetDescriptor<Texture>("menu/menu.png", Texture.class);
    public static final AssetDescriptor<Texture> creditLayer = new AssetDescriptor<Texture>("menu/credit.png", Texture.class);
    /*******************************************/


    //UI
    /*******************************************/
    public static final AssetDescriptor<Texture> STATUS_HP = new AssetDescriptor<Texture>("UI/statusbar/hp_bar.png", Texture.class);
    public static final AssetDescriptor<Texture> STATUS_MANA = new AssetDescriptor<Texture>("UI/statusbar/mp_bar.png", Texture.class);
    public static final AssetDescriptor<Texture> STATUS_AURA = new AssetDescriptor<Texture>("UI/statusbar/aura_bar.png", Texture.class);
    public static final AssetDescriptor<Texture> EMPTY_BAR = new AssetDescriptor<Texture>("UI/statusbar/empty_bar.png", Texture.class);
    public static final AssetDescriptor<Texture> HEART = new AssetDescriptor<Texture>("UI/statusbar/Heart.png", Texture.class);
    public static final AssetDescriptor<Texture> MANA = new AssetDescriptor<Texture>("UI/statusbar/Mana.png", Texture.class);
    public static final AssetDescriptor<Texture> AURA = new AssetDescriptor<Texture>("UI/statusbar/Aura.png", Texture.class);

    public static final AssetDescriptor<Texture> uiSword = new AssetDescriptor<Texture>("UI/sword.png", Texture.class);
    public static final AssetDescriptor<Texture> uiStaff = new AssetDescriptor<Texture>("UI/staff.png", Texture.class);
    public static final AssetDescriptor<Texture> uiBag = new AssetDescriptor<Texture>("UI/bag.png", Texture.class);
    /*******************************************/

    //SFX
    public static final AssetDescriptor<Sound> leftFootStep = new AssetDescriptor<Sound>("SFX/lFootStone.ogg", Sound.class);
    public static final AssetDescriptor<Sound> rightFootStep = new AssetDescriptor<Sound>("SFX/rFootStone.ogg", Sound.class);
    public static final AssetDescriptor<Sound> jumpingSound = new AssetDescriptor<Sound>("SFX/jump.ogg", Sound.class);
    public static final AssetDescriptor<Sound> swordSound = new AssetDescriptor<Sound>("SFX/sword.ogg", Sound.class);
    public static final AssetDescriptor<Sound> castingFlameSpell = new AssetDescriptor<Sound>("SFX/flame.ogg", Sound.class);
    public static final AssetDescriptor<Music> menuMusic = new AssetDescriptor<Music>("SFX/menu.wav", Music.class);
    public static final AssetDescriptor<Music> gameMusic = new AssetDescriptor<Music>("SFX/cave_theme.ogg", Music.class);


    public static BitmapFont smallFont; //Font
    public static BitmapFont bigFont; //Font

    public static final AssetDescriptor<Texture>[] layer = new AssetDescriptor[9]; // GameOver

    public static void load() {

        MANAGER.load(logo);

        MANAGER.load(textureAtlas);
       // MANAGER.load(hitParticleEff);
        //SFX
        MANAGER.load(leftFootStep);
        MANAGER.load(rightFootStep);
        MANAGER.load(jumpingSound);
        MANAGER.load(swordSound);
        MANAGER.load(castingFlameSpell);
        MANAGER.load(menuMusic);
        MANAGER.load(gameMusic);

        //ControlerButtons
        MANAGER.load(leftButton);
        MANAGER.load(rightButton);
        MANAGER.load(upButton);
        MANAGER.load(mageAttackButton);
        MANAGER.load(swordAttackButton);
        MANAGER.load(homeButton);

        //Pause
        MANAGER.load(exit);
        MANAGER.load(on);
        MANAGER.load(off);
        MANAGER.load(resume);
        MANAGER.load(render);
        MANAGER.load(restart);
        MANAGER.load(BACGROUND_PAUSE);

        //Buttons
        MANAGER.load(restartButton);
        MANAGER.load(resumeButton);
        MANAGER.load(sellectButton);
        MANAGER.load(pauseWindow);
        MANAGER.load(backButton);
        MANAGER.load(quitButton);
        MANAGER.load(playButton);
        MANAGER.load(continueButton);
        MANAGER.load(creditButton);
        MANAGER.load(enterButton);

        //Menu
        MANAGER.load(menuLayer);
        MANAGER.load(creditLayer);

        //UI
        MANAGER.load(STATUS_HP);
        MANAGER.load(STATUS_MANA);
        MANAGER.load(STATUS_AURA);
        MANAGER.load(EMPTY_BAR);
        MANAGER.load(HEART);
        MANAGER.load(AURA);
        MANAGER.load(MANA);
        MANAGER.load(uiStaff);
        MANAGER.load(uiSword);
        MANAGER.load(uiBag);


        String tmp = "";
        for (int i = 0; i < 9; i++) {
            tmp = "layer/" + (i + 1) + ".png";
            layer[i] = new AssetDescriptor<Texture>(tmp, Texture.class);
            MANAGER.load(layer[i]);
        }


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/font.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        BitmapFont character = generator.generateFont(parameter);
        //BitmapFont character2 = generator.generateFont(parameter);
        smallFont = character;
        parameter.size = 80;
        BitmapFont character2 = generator.generateFont(parameter);
        bigFont = character2;
        parameter = null;
        generator.dispose();

    }

    public static void dispose() {
        MANAGER.dispose();

    }


}

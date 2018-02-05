package com.epiklp.game.Functional;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.epiklp.game.actors.characters.Enemy;
import com.epiklp.game.actors.characters.Hero;
import com.epiklp.game.actors.weapons.Bullet;

/**
 * Created by Asmei on 2017-12-02.
 */

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        boolean aIsSen = a.isSensor();
        boolean bIsSen = b.isSensor();

        //Enemy and hero touch
        if (!aIsSen && !bIsSen && a.getBody().getUserData() instanceof Enemy && b.getBody().getUserData() instanceof Hero) {
            Hero hero = (Hero) b.getBody().getUserData();
            Enemy enemy = (Enemy) a.getBody().getUserData();
            hero.setLife(-enemy.getStrengh());
            enemy.setLife(-hero.getStrengh());
            return;
        } else if (!aIsSen && !bIsSen && b.getBody().getUserData() instanceof Enemy && a.getBody().getUserData() instanceof Hero) {
            Hero hero = (Hero) a.getBody().getUserData();
            Enemy enemy = (Enemy) b.getBody().getUserData();
            hero.setLife(-enemy.getStrengh());
            enemy.setLife(-hero.getStrengh());
            return;
        }
        //Enemy sensor and hero touch (enemy is in following mode)
        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), Enemy.WARD_SENSOR) && b.getBody().getUserData() instanceof Hero) {
            Hero hero = (Hero) b.getBody().getUserData();
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setFollowing(true).setHeroPos(hero.getBody().getPosition());
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), Enemy.WARD_SENSOR) && a.getBody().getUserData() instanceof Hero) {
            Hero hero = (Hero) a.getBody().getUserData();
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setFollowing(true).setHeroPos(hero.getBody().getPosition());
            return;
        }

        //Shooting
        if (!bIsSen && a.getBody().getUserData() instanceof Bullet && b.getBody().getUserData() instanceof Enemy) {
            Bullet bullet = (Bullet) a.getBody().getUserData();
            if (bullet.getGameCharacter() instanceof Hero) {
                Enemy enemy = (Enemy) b.getBody().getUserData();
                enemy.setLife(-bullet.getHitPoint());
                enemy.setAttacked(true).setHeroPos(bullet.getGameCharacter().getBody().getPosition());
                bullet.setToDelete();
            }
            return;
        } else if (!aIsSen && b.getBody().getUserData() instanceof Bullet && a.getBody().getUserData() instanceof Enemy) {
            Bullet bullet = (Bullet) b.getBody().getUserData();
            if (bullet.getGameCharacter() instanceof Hero) {
                Enemy enemy = (Enemy) a.getBody().getUserData();
                enemy.setLife(-bullet.getHitPoint());
                enemy.setAttacked(true).setHeroPos(bullet.getGameCharacter().getBody().getPosition());
                bullet.setToDelete();
            }
            return;
        }
        //Missile gets wall
        if (a.getBody().getUserData() instanceof Bullet && Utils.equalsWithNulls(b.getBody().getUserData(), "MapBuilder")) {
            Bullet bullet = (Bullet) a.getBody().getUserData();
            bullet.setToDelete();
            return;
        } else if (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") && b.getBody().getUserData() instanceof Bullet) {
            Bullet bullet = (Bullet) b.getBody().getUserData();
            bullet.setToDelete();
            return;
        }

        //Can I jump?
        if (aIsSen && !bIsSen && Utils.equalsWithNulls(a.getUserData(), Hero.JUMP_SENSOR) && Utils.equalsWithNulls(b.getBody().getUserData(), "MapBuilder")) {
            Hero hero = (Hero) a.getBody().getUserData();
            hero.onGround();
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") && Utils.equalsWithNulls(b.getUserData(), Hero.JUMP_SENSOR)) {
            Hero hero = (Hero) b.getBody().getUserData();
            hero.onGround();
            return;
        }

    }

    @Override
    public void endContact(Contact contact) {

        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        boolean bIsSen = b.isSensor();
        boolean aIsSen = a.isSensor();
        //Enemy warding sensor and hero
        if (aIsSen && !bIsSen && Utils.equalsWithNulls(a.getUserData(), Enemy.WARD_SENSOR) && b.getBody().getUserData() instanceof Hero) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setFollowing(false);
            return;
        } else if (bIsSen && !aIsSen && Utils.equalsWithNulls(b.getUserData(), Enemy.WARD_SENSOR) && a.getBody().getUserData() instanceof Hero) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setFollowing(false);
            return;
        }

        //I can't jump?
        if (aIsSen && !bIsSen && Utils.equalsWithNulls(a.getUserData(), Hero.JUMP_SENSOR) && Utils.equalsWithNulls(b.getBody().getUserData(), "MapBuilder")) {
            Hero hero = (Hero) a.getBody().getUserData();
            hero.outGround();
            return;
        } else if (bIsSen && !aIsSen && Utils.equalsWithNulls(b.getBody().getUserData(), "MapBuilder") && Utils.equalsWithNulls(b.getUserData(), Hero.JUMP_SENSOR)) {
            Hero hero = (Hero) b.getBody().getUserData();
            hero.outGround();
            return;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}


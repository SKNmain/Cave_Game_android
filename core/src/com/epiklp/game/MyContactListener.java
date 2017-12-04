package com.epiklp.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.actors.GameObject;
import com.epiklp.game.actors.enemies.Enemy;
import com.epiklp.game.actors.Hero;

import java.util.Iterator;

/**
 * Created by Asmei on 2017-12-02.
 */

public class MyContactListener implements ContactListener {
    private Array<GameObject> deads = new Array<GameObject>();

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        boolean aIsSensor = contact.getFixtureA().isSensor();
        boolean bIsSensor = contact.getFixtureB().isSensor();

        if(a.getUserData() instanceof Enemy && b.getUserData() instanceof Hero && !aIsSensor) {
            Hero hero = (Hero) b.getUserData();
            Enemy enemy = (Enemy) a.getUserData();
            hero.setLife(-enemy.getStrengh());
            enemy.setLife(-hero.getStrengh());
            if (enemy.isDead()) deads.add(enemy);

        }
        if(b.getUserData() instanceof Enemy && a.getUserData() instanceof Hero && !bIsSensor) {
            Hero hero = (Hero) a.getUserData();
            Enemy enemy = (Enemy) b.getUserData();
            hero.setLife(-enemy.getStrengh());
            enemy.setLife(-hero.getStrengh());
            if (enemy.isDead()) deads.add(enemy);
        }
        //sensor
        if(a.getUserData() instanceof Enemy && b.getUserData() instanceof Hero && aIsSensor) {
            Hero hero = (Hero) b.getUserData();
            Enemy enemy = (Enemy) a.getUserData();
            enemy.setFollowing(true).setHeroPos(hero.getBody().getPosition());
        }
        if(b.getUserData() instanceof Enemy && a.getUserData() instanceof Hero && bIsSensor) {
            Hero hero = (Hero) a.getUserData();
            Enemy enemy = (Enemy) b.getUserData();
            enemy.setFollowing(true).setHeroPos(hero.getBody().getPosition());
        }
    }

    @Override
    public void endContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        boolean aIsSensor = contact.getFixtureA().isSensor();
        boolean bIsSensor = contact.getFixtureB().isSensor();
        //sensor
        if(a.getUserData() instanceof Enemy && b.getUserData() instanceof Hero && aIsSensor) {
            Enemy enemy = (Enemy) a.getUserData();
            enemy.setFollowing(false);
        }
        if(b.getUserData() instanceof Enemy && a.getUserData() instanceof Hero && bIsSensor) {
            Enemy enemy = (Enemy) b.getUserData();
            enemy.setFollowing(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
    public Iterator<GameObject> getDeadsTableIter(){
        return deads.iterator();
    }
}


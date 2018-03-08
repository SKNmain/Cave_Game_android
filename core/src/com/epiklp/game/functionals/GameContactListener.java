package com.epiklp.game.functionals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.epiklp.game.actors.characters.Enemy;
import com.epiklp.game.actors.characters.GameCharacter;
import com.epiklp.game.actors.characters.Hero;
import com.epiklp.game.actors.items.Bottle;
import com.epiklp.game.actors.traps.Trap;
import com.epiklp.game.actors.weapons.FireBall;
import com.epiklp.game.actors.traps.Spikes;
import com.epiklp.game.actors.weapons.Weapon;

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

        //Insta Traps
        if (a.getBody().getUserData() instanceof GameCharacter && b.getBody().getUserData() instanceof Trap) {
            ((GameCharacter) a.getBody().getUserData()).getDamage(((Trap) b.getBody().getUserData()).getHitPoint());
            return;
        } else if (b.getBody().getUserData() instanceof GameCharacter && a.getBody().getUserData() instanceof Trap) {
            ((GameCharacter) b.getBody().getUserData()).getDamage(((Trap) a.getBody().getUserData()).getHitPoint());
            return;
        }
        //Enemy and hero touch
        if (!aIsSen && !bIsSen && a.getBody().getUserData() instanceof Enemy && b.getBody().getUserData() instanceof Hero) {
            Hero hero = (Hero) b.getBody().getUserData();
            Enemy enemy = (Enemy) a.getBody().getUserData();
            hero.getDamage(enemy.getStrengh());
            return;
        } else if (!aIsSen && !bIsSen && b.getBody().getUserData() instanceof Enemy && a.getBody().getUserData() instanceof Hero) {
            Hero hero = (Hero) a.getBody().getUserData();
            Enemy enemy = (Enemy) b.getBody().getUserData();
            hero.getDamage(enemy.getStrengh());
            return;
        }
        //Enemy sensor and hero touch (enemy is in following mode)
        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.PATROL_SENSOR) && b.getBody().getUserData() instanceof Hero) {
            Hero hero = (Hero) b.getBody().getUserData();
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setFollowing(true).setHeroLastPos(hero.getBody().getPosition());
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.PATROL_SENSOR) && a.getBody().getUserData() instanceof Hero) {
            Hero hero = (Hero) a.getBody().getUserData();
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setFollowing(true).setHeroLastPos(hero.getBody().getPosition());
            return;
        }

        //Attack from hero to enemy
        if (!bIsSen && a.getBody().getUserData() instanceof Weapon && b.getBody().getUserData() instanceof GameCharacter) {
            Weapon weapon = (Weapon) a.getBody().getUserData();
            if (weapon.getGameCharacter() instanceof Hero && b.getBody().getUserData() instanceof Enemy) {
                Enemy enemy = (Enemy) b.getBody().getUserData();
                enemy.getDamage(weapon.getHitPoint());
                enemy.setAttacked(true).setHeroLastPos(weapon.getGameCharacter().getBody().getPosition());
                enemy.getBody().applyLinearImpulse(new Vector2((enemy.getTurn() ? -weapon.getHitPoint() * 10 : weapon.getHitPoint() * 10), 0),
                        enemy.getBody().getWorldCenter(), true);
                weapon.setToDelete();
            } else if (weapon.getGameCharacter() instanceof Enemy && b.getBody().getUserData() instanceof Hero) {
                Hero hero = (Hero) b.getBody().getUserData();
                hero.getDamage(weapon.getHitPoint());
                weapon.setToDelete();
            }
            return;
        } else if (!aIsSen && b.getBody().getUserData() instanceof Weapon && a.getBody().getUserData() instanceof GameCharacter) {
            Weapon weapon = (Weapon) b.getBody().getUserData();
            if (weapon.getGameCharacter() instanceof Hero && a.getBody().getUserData() instanceof Enemy) {
                Enemy enemy = (Enemy) a.getBody().getUserData();
                enemy.getDamage(weapon.getHitPoint());
                enemy.setAttacked(true).setHeroLastPos(weapon.getGameCharacter().getBody().getPosition());
                enemy.getBody().applyLinearImpulse(new Vector2((enemy.getTurn() ? -weapon.getHitPoint() * 10 : weapon.getHitPoint() * 10), 0),
                        enemy.getBody().getWorldCenter(), true);
                weapon.setToDelete();
            } else if (weapon.getGameCharacter() instanceof Enemy && a.getBody().getUserData() instanceof Hero) {
                Hero hero = (Hero) a.getBody().getUserData();
                hero.getDamage(weapon.getHitPoint());
                weapon.setToDelete();
            }
            return;
        }
        //Missile gets wall
        //If inside is only temporarry
        if (a.getBody().getUserData() instanceof Weapon && Utils.equalsWithNulls(b.getBody().getUserData(), "MapBuilder")) {
            Weapon weapon = (Weapon) a.getBody().getUserData();
            if (weapon instanceof FireBall) {
                weapon.setToDelete();
                weapon.getBody().getFixtureList().first().getFilterData().maskBits = (short) 0;
            }
            return;
        } else if (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") && b.getBody().getUserData() instanceof Weapon) {
            Weapon weapon = (Weapon) b.getBody().getUserData();
            if (weapon instanceof FireBall) {
                weapon.setToDelete();
                weapon.getBody().getFixtureList().first().getFilterData().maskBits = (short) 0;
            }
            return;
        }

        //Pot
        if (!bIsSen && a.getBody().getUserData() instanceof Bottle && b.getBody().getUserData() instanceof Hero) {
            Bottle bottle = (Bottle) a.getBody().getUserData();
            bottle.use((Hero) b.getBody().getUserData());
            bottle.setToDelete();
            return;
        } else if (!aIsSen && b.getBody().getUserData() instanceof Bottle && a.getBody().getUserData() instanceof Hero) {
            Bottle bottle = (Bottle) b.getBody().getUserData();
            bottle.use((Hero) a.getBody().getUserData());
            bottle.setToDelete();
            return;
        }

        //Can I JUMP?
        if (aIsSen && !bIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.JUMP_SENSOR) && Utils.equalsWithNulls(b.getBody().getUserData(), "MapBuilder")) {
            Hero hero = (Hero) a.getBody().getUserData();
            hero.onGround();
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.JUMP_SENSOR)) {
            Hero hero = (Hero) b.getBody().getUserData();
            hero.onGround();
            return;
        }

        //Can I CLIMB?
        if (aIsSen && !bIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.CLIMB_SENSOR) && Utils.equalsWithNulls(b.getBody().getUserData(), "CLIMBING_WALL")) {
            Hero hero = (Hero) a.getBody().getUserData();
            hero.setCanClimb(true);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL") && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.CLIMB_SENSOR)) {
            Hero hero = (Hero) b.getBody().getUserData();
            hero.setCanClimb(true);
            return;
        }


        // ================ SENSORS FOR ENEMY AI ===============================
        //Enemy attack, if touch the sensor
        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.LEFT_ATTACK_SENSOR) && b.getBody().getUserData() instanceof Hero) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setSensorUp(true, GameCharacter.SENSORS.LEFT_ATTACK_SENSOR);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.LEFT_ATTACK_SENSOR) && a.getBody().getUserData() instanceof Hero) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setSensorUp(true, GameCharacter.SENSORS.LEFT_ATTACK_SENSOR);
            return;
        }
        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.RIGHT_ATTACK_SENSOR) && b.getBody().getUserData() instanceof Hero) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setSensorUp(true, GameCharacter.SENSORS.RIGHT_ATTACK_SENSOR);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.RIGHT_ATTACK_SENSOR) && a.getBody().getUserData() instanceof Hero) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setSensorUp(true, GameCharacter.SENSORS.RIGHT_ATTACK_SENSOR);
            return;
        }

        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.LEFT_DOWN_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setSensorUp(true, GameCharacter.SENSORS.LEFT_DOWN_SENSOR);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.LEFT_DOWN_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setSensorUp(true, GameCharacter.SENSORS.LEFT_DOWN_SENSOR);
            return;
        }
        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.RIGHT_DOWN_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setSensorUp(true, GameCharacter.SENSORS.RIGHT_DOWN_SENSOR);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.RIGHT_DOWN_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setSensorUp(true, GameCharacter.SENSORS.RIGHT_DOWN_SENSOR);
            return;
        }


        //UP
        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.LEFT_UP_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setSensorUp(true, GameCharacter.SENSORS.LEFT_UP_SENSOR);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.LEFT_UP_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setSensorUp(true, GameCharacter.SENSORS.LEFT_UP_SENSOR);
            return;
        }
        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.RIGHT_UP_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setSensorUp(true, GameCharacter.SENSORS.RIGHT_UP_SENSOR);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.RIGHT_UP_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setSensorUp(true, GameCharacter.SENSORS.RIGHT_UP_SENSOR);
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
        if (aIsSen && !bIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.PATROL_SENSOR) && b.getBody().getUserData() instanceof Hero) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setFollowing(false);
            return;
        } else if (bIsSen && !aIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.PATROL_SENSOR) && a.getBody().getUserData() instanceof Hero) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setFollowing(false);
            return;
        }

        //I can't jump?
        if (aIsSen && !bIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.JUMP_SENSOR) && Utils.equalsWithNulls(b.getBody().getUserData(), "MapBuilder")) {
            Hero hero = (Hero) a.getBody().getUserData();
            hero.outGround();
            return;
        } else if (bIsSen && !aIsSen && Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.JUMP_SENSOR)) {
            Hero hero = (Hero) b.getBody().getUserData();
            hero.outGround();
            return;
        }
        //I can't climb
        if (aIsSen && !bIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.CLIMB_SENSOR) && Utils.equalsWithNulls(b.getBody().getUserData(), "CLIMBING_WALL")) {
            Hero hero = (Hero) a.getBody().getUserData();
            hero.setCanClimb(false);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL") && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.CLIMB_SENSOR)) {
            Hero hero = (Hero) b.getBody().getUserData();
            hero.setCanClimb(false);
            return;
        }
        // ================ SENSORS FOR ENEMY AI ===============================
        //Enemy sensor and hero touch (enemy is in following mode)
        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.LEFT_ATTACK_SENSOR) && b.getBody().getUserData() instanceof Hero) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setSensorUp(false, GameCharacter.SENSORS.LEFT_ATTACK_SENSOR);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.LEFT_ATTACK_SENSOR) && a.getBody().getUserData() instanceof Hero) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setSensorUp(false, GameCharacter.SENSORS.LEFT_ATTACK_SENSOR);
            return;
        }
        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.RIGHT_ATTACK_SENSOR) && a.getBody().getUserData() instanceof Hero) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setSensorUp(false, GameCharacter.SENSORS.RIGHT_ATTACK_SENSOR);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.RIGHT_ATTACK_SENSOR) && b.getBody().getUserData() instanceof Hero) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setSensorUp(false, GameCharacter.SENSORS.RIGHT_ATTACK_SENSOR);
            return;
        }

        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.LEFT_DOWN_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setSensorUp(false, GameCharacter.SENSORS.LEFT_DOWN_SENSOR);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.LEFT_DOWN_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setSensorUp(false, GameCharacter.SENSORS.LEFT_DOWN_SENSOR);
            return;
        }
        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.RIGHT_DOWN_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setSensorUp(false, GameCharacter.SENSORS.RIGHT_DOWN_SENSOR);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.RIGHT_DOWN_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setSensorUp(false, GameCharacter.SENSORS.RIGHT_DOWN_SENSOR);
            return;
        }

        //UP
        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.LEFT_UP_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setSensorUp(false, GameCharacter.SENSORS.LEFT_UP_SENSOR);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.LEFT_UP_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setSensorUp(false, GameCharacter.SENSORS.LEFT_UP_SENSOR);
            return;
        }
        if (!bIsSen && aIsSen && Utils.equalsWithNulls(a.getUserData(), GameCharacter.SENSORS.RIGHT_UP_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) a.getBody().getUserData();
            enemy.setSensorUp(false, GameCharacter.SENSORS.RIGHT_UP_SENSOR);
            return;
        } else if (!aIsSen && bIsSen && Utils.equalsWithNulls(b.getUserData(), GameCharacter.SENSORS.RIGHT_UP_SENSOR)
                &&
                (Utils.equalsWithNulls(a.getBody().getUserData(), "MapBuilder") || Utils.equalsWithNulls(a.getBody().getUserData(), "CLIMBING_WALL"))) {
            Enemy enemy = (Enemy) b.getBody().getUserData();
            enemy.setSensorUp(false, GameCharacter.SENSORS.RIGHT_UP_SENSOR);
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


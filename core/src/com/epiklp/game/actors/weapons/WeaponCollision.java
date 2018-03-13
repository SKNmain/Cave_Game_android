package com.epiklp.game.actors.weapons;

/**
 * Created by Asmei on 2018-03-13.
 */

public class WeaponCollision {
    public static void collisionMissiles(Weapon a, Weapon b){
        if( a.getHitPoint() > b.getHitPoint()){
            a.setHitPoint(-b.getHitPoint());
            b.setToDelete();
        }
        else if( b.getHitPoint() > a.getHitPoint()){
            b.setHitPoint(-a.getHitPoint());
            a.setToDelete();
        }


    }
}

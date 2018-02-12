package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by epiklp on 19.11.17.
 * <p>
 * <p>
 * <p>
 * Wszystkie wartości dla attackRange oraz patrolRange podawac w jednostkach Box2d!
 */


public abstract class Enemy extends GameCharacter {

    public enum AI_STATE {
        PATROLING, ATTACKED, FOLLOWING,
    }

    public static final String PATROL_SENSOR = "PATROL_SEN";

    protected float chanceOfDrop;
    protected float attackRange;
    protected float patrolRange;
    protected Vector2 leftPatrolPoints;
    protected Vector2 rightPatrolPoints;

    protected boolean following;
    protected boolean attacked;

    protected Vector2 heroLastPos;
    float elapsed_time = 0;


    public Enemy(Sprite sprite, float sizeX, float sizeY) {
        super(sprite, sizeX, sizeY);
        leftPatrolPoints = new Vector2();
        rightPatrolPoints = new Vector2();
        state = STATE.RUNNING;
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        moving();
    }

    //return this, only for shorter record in GameContactListener
    public Enemy setFollowing(boolean following) {
        this.following = following;
        return this;
    }

    public Enemy setAttacked(boolean attacked) {
        this.attacked = attacked;
        return this;
    }

    public void setHeroLastPos(Vector2 pos) {
        this.heroLastPos = pos;
    }

    public float getChanceOfDrop() {
        return chanceOfDrop;
    }


    //call it AFTER create body
    protected void setPatrolPoints() {
        Vector2 actualPosX = body.getPosition();

        leftPatrolPoints.x = actualPosX.x - patrolRange;
        leftPatrolPoints.y = actualPosX.y;
        rightPatrolPoints.x = actualPosX.x + patrolRange;
        rightPatrolPoints.y = actualPosX.y;
    }


    protected void patroling() {
        if (state == STATE.RUNNING) {
            if (turn) {
                setSpeedX(runSpeed);
                float distance = body.getPosition().x - rightPatrolPoints.x;
                if (distance >= 0) {
                    turn = false;
                }
            } else {
                setSpeedX(-runSpeed);
                float distance = body.getPosition().x - leftPatrolPoints.x;
                if (distance <= 0) {
                    turn = true;
                }
            }
        }
    }

    protected void moving() {
        if (following || attacked) {
            followHero();
            setPatrolPoints();
        } else {
            patroling();
        }
    }

    protected void followHero() {
        if (heroLastPos.x > body.getPosition().x - 3f && heroLastPos.x < body.getPosition().x + 3f) {
            body.setLinearVelocity(0, 0);
        } else if (heroLastPos.x > body.getPosition().x - 3f) {
            body.setLinearVelocity(runSpeed, 0);
        } else if (heroLastPos.x < body.getPosition().x + 3f) {
            body.setLinearVelocity(-runSpeed, 0);
        }
    }

}

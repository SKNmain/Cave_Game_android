package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.epiklp.game.functionals.b2d.BodyCreator;

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

    protected boolean flying; //if we want a flying creature, we need set it to true in constructor

    protected float chanceOfDrop;
    protected float attackRange;
    protected float patrolRange;
    protected Vector2 leftPatrolPoints;
    protected Vector2 rightPatrolPoints;

    protected boolean leftDownSensor;
    protected boolean rightDownSensor;
    protected boolean leftUpSensor;
    protected boolean rightUpSensor;


    protected boolean following;
    protected boolean attacked;


    protected Vector2 heroLastPos;


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
                if (distance >= 0 || rightDownSensor) {
                    turn = false;
                }

                if (flying) {
                    if (rightUpSensor) rightPatrolPoints.x = body.getPosition().x;
                } else {
                    if (rightUpSensor) {
                        rightPatrolPoints.x = body.getPosition().x;
                    }
                }

            } else {
                setSpeedX(-runSpeed);
                float distance = body.getPosition().x - leftPatrolPoints.x;

                if (distance <= 0 || leftDownSensor) {
                    turn = true;
                }
                if (flying) {
                    if (leftUpSensor) leftPatrolPoints.x = body.getPosition().x;
                } else {
                    if (leftUpSensor){
                        leftPatrolPoints.x = body.getPosition().x;
                    }
                }

            }
        }
    }

    protected void moving() {
        // hero get away, set to patroling
        if (attacked && body.getPosition().dst(heroLastPos) > 20f)
            attacked = false;

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

    protected void setSensorAround(Vector2 posLD, Vector2 posRD, Vector2 posLU, Vector2 posRU) {
        BodyCreator.createBoxSensor(body, 5f, 5f, posLD, SENSORS.LEFT_DOWN_SENSOR);
        BodyCreator.createBoxSensor(body, 5f, 5f, posLU, SENSORS.LEFT_UP_SENSOR);
        BodyCreator.createBoxSensor(body, 5f, 5f, posRD, SENSORS.RIGHT_DOWN_SENSOR);
        BodyCreator.createBoxSensor(body, 5f, 5f, posRU, SENSORS.RIGHT_UP_SENSOR);
    }

    public void setSensorUp(boolean active, SENSORS sensor) {
        if (sensor == SENSORS.LEFT_DOWN_SENSOR) leftDownSensor = active;
        else if (sensor == SENSORS.LEFT_UP_SENSOR) leftUpSensor = active;
        else if (sensor == SENSORS.RIGHT_DOWN_SENSOR) rightDownSensor = active;
        else if (sensor == SENSORS.RIGHT_UP_SENSOR) rightUpSensor = active;
    }

}

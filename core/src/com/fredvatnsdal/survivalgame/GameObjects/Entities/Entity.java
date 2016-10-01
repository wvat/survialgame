package com.fredvatnsdal.survivalgame.GameObjects.Entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.fredvatnsdal.survivalgame.GameHelpers.AssetLoader;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Fred on 22/08/2015.
 */
public class Entity {

    protected float x,y,width,height,centerX,centerY ,angle;
    protected double direction;
    protected int meleeDamage;
    protected boolean attacking, stopX, stopY, alive, hit, targetInRange, readyToAttack;
    protected Rectangle bounds, hitBox, attackBounds;
    protected Vector2 position, velocity, acceleration;
    protected GameWorld gameWorld;
    protected CharacterDirection currentDirection;
    protected Animation walk_front,walk_back,walk_left,walk_right,
                        idle_front,idle_back,idle_left,idle_right,
                        attack_front,attack_back,attack_left,attack_right;
    public enum CharacterDirection{FRONT,BACK,RIGHT,LEFT}

    public Entity(float x, float y, float width, float height,GameWorld gameWorld,float scale){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gameWorld = gameWorld;

        position = new Vector2(x,y);
        velocity = new Vector2(0,0);
        acceleration = new Vector2(0,0);

        angle = velocity.angle(); //angle in degrees

        bounds = new Rectangle(position.x - width/3,position.y - (height) ,width* scale/3,height*scale/3);
        hitBox = new Rectangle(position.x - width/3,position.y - (height) ,width* scale/3,height*scale);
        attackBounds = new Rectangle(position.x - width,position.y - (height) ,width* scale/2,height*scale/2);

        attacking = false;
        stopX = false;
        stopY = false;
        alive = true;
        hit = false;
        targetInRange = false;
        readyToAttack = true;

    }
    public void update(float delta){
        if(alive) {
            velocity.add(acceleration.cpy().scl(delta));
            position.add(velocity.cpy().scl(delta));
        }
        bounds.setPosition(position.x - width / 6, position.y - (height)); //update bounds position.
        hitBox.setPosition(position.x - width / 6, position.y - (height)); //update hitbox position.
        attackBounds.setPosition(position.x - width / 2.5f, position.y - height); //update attackBounds position.
        direction = Math.atan2(velocity.y,velocity.x);
        angle = velocity.angle();
        if(position.x <= 0){
            position.x = 0;
        }
        if(position.x + width >= gameWorld.getTileMapWidth()){
            position.x = gameWorld.getTileMapWidth() - width;
        }
        if(position.y - height <= 0){
            position.y = 0 + height;
        }
        if(position.y + height >= gameWorld.getTileMapHeight()){
            position.y = gameWorld.getTileMapHeight() - (height);
        }
    }

    public void evaluateAnimation(){}
    public void draw(float runTime, SpriteBatch batch){}
    public void restart(){}
    public int basicAttack(){return 0;}
    public void hurt(int damage){}

    //==========GETTERS AND SETTERS===========
    public Rectangle getBounds() {
        return bounds;
    }
    public Rectangle getHitBox() {
        return hitBox;
    }
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    public Vector2 getPosition() {
        return position;
    }
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    public Vector2 getVelocity() {
        return velocity;
    }
    public Vector2 getAcceleration() {
        return acceleration;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    public float getCenterX() {
        return centerX;
    }
    public float getCenterY() {
        return centerY;
    }
    public void setX(float x){
        position.x = x;
    }
    public void setY(float y){
        position.y = y;
    }
    public void setStopX(boolean stopX) {
        this.stopX = stopX;
    }
    public void setStopY(boolean stopY) {
        this.stopY = stopY;
    }
    public boolean isAlive() {
        return alive;
    }
    public Rectangle getAttackBounds() {
        return attackBounds;
    }
    public boolean isTargetInRange() {
        return targetInRange;
    }
    public void setTargetInRange(boolean targetInRange) {
        this.targetInRange = targetInRange;
    }
    public float getAngle() {
        return angle;
    }
    public int getMeleeDamage() {
        return meleeDamage;
    }
    public void setReadyToAttack(boolean readyToAttack) {
        this.readyToAttack = readyToAttack;
    }
    public boolean isReadyToAttack() {
        return readyToAttack;
    }
}

package com.fredvatnsdal.survivalgame.GameObjects.Projectiles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

import java.util.Vector;

import javax.naming.NameNotFoundException;

/**
 * Created by Fred on 20/08/2015.
 */
public class Projectile {
    private float x,y,width,height,directionX,directionY;
    protected Vector2 velocity;
    protected Vector2 position;
    protected Vector2 acceleration;
    protected int damage;
    protected float initialSpeed = 600;
    protected float rotation;
    protected double launchAngle;
    protected boolean hit = false;
    protected boolean visible = true;
    protected GameWorld gameWorld;
    protected Rectangle boundingRectangle;

    public Projectile(float x, float y, float width, float height,float directionX,float directionY,GameWorld gameWorld) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.directionX = directionX;
        this.directionY = directionY;

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);

        launchAngle = (Math.atan2(directionY, directionX));

        Gdx.app.log("Projectiles","Direction X: " + directionX);
        Gdx.app.log("Projectiles","Direction Y: " + directionY);
        Gdx.app.log("Projectiles", "Projectile launched at angle: " + launchAngle * (180 / Math.PI));

        if(!Double.isNaN(launchAngle)) {
            velocity.x = (float) (initialSpeed * Math.cos(launchAngle));
            velocity.y = (float) (initialSpeed * Math.sin(launchAngle));

            Gdx.app.log("Projectiles","Velocity X: " + velocity.x);
            Gdx.app.log("Projectiles","Velocity Y: " + velocity.y);


        }
        else{
            velocity.x = 0;
            velocity.y = initialSpeed;
        }
        boundingRectangle = new Rectangle(position.x,position.y,width,height);

    }


    public void draw(float runTime, SpriteBatch batch){

    }

    public void update(float delta){
        velocity.add(acceleration.cpy().scl(delta));
        position.add(velocity.cpy().scl(delta));

        /**
         * Under certain conditions, get
         * rid of the projectile by setting to
         * invisible for disposal.
         */

        if(hit){
            visible = false;
        }
        if(position.x >= 1000f){
            visible = false;
        }
        if(position.x <= -500f){
            visible = false;
        }
        if(position.y >= 1000f){
            visible = false;
        }

        boundingRectangle.setPosition(position.x,position.y);
    }
    public void setHit(boolean hit) {
        this.hit = hit;
    }
    public float getX(){
        return position.x;
    }
    public float getY(){
        return position.y;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setIsVisible(boolean isVisible) {
        this.visible = isVisible;
    }
    public Rectangle getBoundingRectangle() {
        return boundingRectangle;
    }
    public int getDamage() {
        return damage;
    }
    public boolean isHit() {
        return hit;
    }
}

package com.fredvatnsdal.survivalgame.GameObjects.Entities.Consumable;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Entity;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

import javafx.scene.shape.Circle;

/**
 * Created by Fred on 9/22/2015.
 */
public class Consumable extends Entity {
    protected float aX,aY;
    protected final int RADIUS = 40;
    protected final int UPTIME = 15000;
    protected final int SPEED = 40;
    protected boolean consumed, heroInRadius; //Has the consumable been consumed?
    protected Vector2 targetPosition;
    protected Circle pickUpRadius;
    protected Animation currentAnimation;

    public Consumable(float x, float y, float width, float height, GameWorld gameWorld, float scale) {
        super(x, y, width, height, gameWorld, scale);
        pickUpRadius = new Circle(x,y,RADIUS);
        bounds = new Rectangle(position.x,position.y,width,height);

        targetPosition = new Vector2();
        consumed  = false;
        heroInRadius = false;
    }
    @Override
    public void update(float delta) {
        if(!consumed){
            velocity.add(acceleration.cpy().scl(delta));
            position.add(acceleration.cpy().scl(delta));
        }
        bounds.setPosition(position.x, position.y); //update bounds position.

        targetPosition.x = gameWorld.getHero().getX();
        targetPosition.y = gameWorld.getHero().getY();

        aX = targetPosition.x - position.x;
        aY = targetPosition.y - position.y;

        direction = Math.atan2(aY, aX);

        if(heroInRadius){
            seek(targetPosition.x,targetPosition.y);
        }

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

    public void seek(float targetX, float targetY) {
        aX = targetX - position.x;
        aY = targetY - position.y;
        direction = Math.atan2(aY, aX);
        velocity.x = (float) (SPEED * Math.cos(direction));
        velocity.y = (float) (SPEED * Math.sin(direction));
    }
}

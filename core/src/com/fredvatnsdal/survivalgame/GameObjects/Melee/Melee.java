package com.fredvatnsdal.survivalgame.GameObjects.Melee;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

import javafx.scene.shape.Line;

/**
 * Created by Fred on 9/15/2015.
 */
public class Melee {
    protected float x,y,width,height;
    protected float directionX, directionY;
    protected float reach;
    protected float pointX,pointY;
    protected GameWorld gameWorld;
    protected Vector2 point; //The furthest reach of the attack (anything between this point and the center of the entity will result in a confirmed hit).7
    protected Vector2 velocity;



    public Melee(float x, float y, float width, float height, float directionX, float directionY, GameWorld gameWorld){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.directionX = directionX;
        this.directionY = directionY;
        this.gameWorld = gameWorld;

        reach = 100;

        pointX = 100 * directionX;
        pointY = 100 * directionY;

        point = new Vector2(x,y); //set the point at the origin of the entity
        velocity = new Vector2(25 * directionX, 25 * directionY);

    }

    public void update(float delta){


        pointX = 100 * directionX;
        pointY = 100 * directionY;

        point.add(velocity.cpy().scl(delta));


    }






}

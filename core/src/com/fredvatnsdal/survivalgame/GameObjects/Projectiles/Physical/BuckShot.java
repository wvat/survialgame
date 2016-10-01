package com.fredvatnsdal.survivalgame.GameObjects.Projectiles.Physical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fredvatnsdal.survivalgame.GameObjects.Projectiles.Projectile;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

/**
 * Created by Fred on 21/08/2015.
 */
public class BuckShot extends Projectile {

    private float x,y,width,height;
    private float directionX,directionY;
    private float newDxR,newDyR,newDxL,newDyL;
    private double primaryLaunchAngle;
    private double rightLaunchAngle;
    private double leftLaunchAngle;
    private GameWorld gameWorld;
    private BuckShot rightBuck = null;
    private BuckShot leftBuck = null;
    private int count;

    public BuckShot(float x, float y, float width, float height, float directionX, float directionY, GameWorld gameWorld,int count) {
        super(x, y, width, height, directionX, directionY, gameWorld);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.directionX = directionX;
        this.directionY = directionY;
        this.gameWorld = gameWorld;
        this.count = count;

        damage = 1;

        primaryLaunchAngle = launchAngle; //The launch angle based on the direction the player has aimed.
        Gdx.app.log("BUCK","\nprimary launch angle: " + primaryLaunchAngle + " launch angle: " + launchAngle);

        if(count != 0){
            createSideBuck(primaryLaunchAngle);
        }

        count --;

    }
    public void createSideBuck(double primaryLaunchAngle){
        rightLaunchAngle = primaryLaunchAngle - 0.24;
        leftLaunchAngle = primaryLaunchAngle + 0.24;
        Gdx.app.log("BUCK","right launch angle: " + rightLaunchAngle);
        Gdx.app.log("BUCK","left launch angle: " + leftLaunchAngle);

        newDxR = (float)Math.cos(rightLaunchAngle);
        newDyR = (float)Math.sin(rightLaunchAngle);

        newDxL = (float)Math.cos(leftLaunchAngle);
        newDyL = (float)Math.sin(leftLaunchAngle);

        Gdx.app.log("BUCK","new directionX right : " + newDxR);
        Gdx.app.log("BUCK","new directionY right : " + newDyR);
        Gdx.app.log("BUCK","new directionX left : " + newDxL);
        Gdx.app.log("BUCK","new directionY left : " + newDyL);

       rightBuck = new BuckShot(x,y,width,height,newDxR,newDyR,gameWorld,0); //Create a new Buck shot 20deg above original;
       leftBuck = new BuckShot(x,y,width,height,newDxL,newDyL,gameWorld,0); //Create a new Buck shot 20deg below original;
    }
    @Override
    public void draw(float runTime, SpriteBatch batch) {
        super.draw(runTime, batch);
    }
    @Override
    public void update(float delta) {
        super.update(delta);
    }
    public BuckShot getRightBuck() {
        return rightBuck;
    }
    public BuckShot getLeftBuck() {
        return leftBuck;
    }

}

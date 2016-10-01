package com.fredvatnsdal.survivalgame.GameObjects.Entities.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.fredvatnsdal.survivalgame.GameHelpers.AssetLoader;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Entity;
import com.fredvatnsdal.survivalgame.GameObjects.Projectiles.Physical.BuckShot;
import com.fredvatnsdal.survivalgame.GameObjects.Projectiles.Projectile;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Fred on 19/08/2015.
 */
public class Hero extends Entity {
    private final float SCALE = 3;
    private final int SPEED = 200;
    private final int MAX_LIFE = 10;

    private boolean attacking, readyToFire_gun, alive;
    private long startTime, endTime;
    private int life;
    private float x,y,width,height,directionX,directionY;


    private GameWorld gameWorld;
    private Animation currentAnimation;
    private List<BuckShot> buckShots;
    private List<Projectile> projectiles;
    private CharacterDirection currentDirection = CharacterDirection.FRONT;
    public enum CharacterDirection{FRONT,BACK,LEFT,RIGHT}

    public Hero(float x, float y, float width, float height,GameWorld gameWorld,float scale){
        super(x,y,width,height,gameWorld,scale);
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.gameWorld = gameWorld;

        buckShots = new ArrayList<BuckShot>();
        projectiles = new ArrayList<Projectile>();


        attacking = false;
        readyToFire_gun = true;
        alive = true;
        life = MAX_LIFE;

        startTime = TimeUtils.millis();

        initAnimations();
    }

    public void initAnimations(){
        walk_front = AssetLoader.wizard_front_walk;
        walk_back = AssetLoader.wizard_back_walk;
        walk_left = AssetLoader.wizard_left_walk;
        walk_right = AssetLoader.wizard_right_walk;

        idle_front = AssetLoader.wizard_front_idle;
        idle_back = AssetLoader.wizard_back_idle;
        idle_left = AssetLoader.wizard_left_idle;
        idle_right = AssetLoader.wizard_right_idle;

        attack_front = AssetLoader.wizard_front_shoot;
        attack_back = AssetLoader.wizard_back_shoot;
        attack_left = AssetLoader.wizard_left_shoot;
        attack_right = AssetLoader.wizard_right_shoot;

    }

    public void fireWeapon(){
        if(alive) {
            if (readyToFire_gun) {
                if (velocity.x == 0 && velocity.y == 0) {
                    switch (currentDirection) {
                        case FRONT:
                            directionX = 0;
                            directionY = -1;
                            break;
                        case RIGHT:
                            directionX = 1;
                            directionY = 0;
                            break;
                        case LEFT:
                            directionX = -1;
                            directionY = 0;
                            break;
                        case BACK:
                            directionX = 0;
                            directionY = 1;
                            break;
                    }
                } else {
                    directionX = gameWorld.getTouchpad().getKnobPercentX();
                    directionY = gameWorld.getTouchpad().getKnobPercentY();
                }

                BuckShot buckShot = new BuckShot(position.x + width / 2, position.y + height / 2 - 10, 5, 5, directionX, directionY, gameWorld, 1);

                projectiles.add(buckShot);
                projectiles.add(buckShot.getLeftBuck());
                projectiles.add(buckShot.getRightBuck());

                attacking = true;
                readyToFire_gun = false;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        readyToFire_gun = true;
                    }
                }, 1000);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        attacking = false;
                    }
                }, 400);
            }
        }
    }

    @Override
    public void hurt(int damage) {
        super.hurt(damage);

        Gdx.app.log("Time","StartTime: " + startTime);
        Gdx.app.log("Time","EndTime: " + endTime);
        Gdx.app.log("Time","DeltaTime: " + (endTime - startTime));

        if(endTime - startTime >= 1000) { //If delta time is >= 1 second
            startTime = endTime;          //set the new start time

            if (alive) {
                life -= damage;
            }
            if (life < 0) {
                life = 0;
            }
        }
        Gdx.app.log("Life","Damaged | Damage : " + damage);
    }

    @Override
    public void update(float delta){
        Gdx.app.log("Life","Life : " + life);
        endTime = TimeUtils.millis(); //update value of endtime to always be the current time.

        if(life <= 0){
            alive = false;
        }

        if(alive) {
            velocity.x = gameWorld.getTouchpad().getKnobPercentX() * SPEED;
            velocity.y = gameWorld.getTouchpad().getKnobPercentY() * SPEED;
        }
        else{
            velocity.x = 0;
            velocity.y = 0;
        }
        super.update(delta);

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

        evaluateAnimation();
    }

    @Override
    public void evaluateAnimation(){
        if(attacking) {
            if (velocity.x > 0 && velocity.x >= Math.abs(velocity.y)) {
                currentAnimation = attack_right;
                currentDirection = CharacterDirection.RIGHT;
            } else if (velocity.x < 0 && Math.abs(velocity.x) >= Math.abs(velocity.y)) {
                currentAnimation = attack_left;
                currentDirection = CharacterDirection.LEFT;
            } else if (velocity.y < 0 && Math.abs(velocity.y) > Math.abs(velocity.x)) {
                currentAnimation = attack_front;
                currentDirection = CharacterDirection.FRONT;
            } else if (velocity.y > 0 && Math.abs(velocity.y) > Math.abs(velocity.x)) {
                currentAnimation = attack_back;
                currentDirection = CharacterDirection.BACK;
            } else if (velocity.y == 0 && velocity.x == 0) {
                System.out.println("Current Direction: " + currentDirection);
                switch (currentDirection) {
                    case FRONT:
                        currentAnimation = attack_front;
                        break;
                    case BACK:
                        currentAnimation = attack_back;
                        break;
                    case LEFT:
                        currentAnimation = attack_left;
                        break;
                    case RIGHT:
                        currentAnimation = attack_right;
                        break;
                }
            }
        }
            if (!attacking) {
                if (velocity.x > 0 && velocity.x >= Math.abs(velocity.y)) {
                    currentAnimation = walk_right;
                    currentDirection = CharacterDirection.RIGHT;
                } else if (velocity.x < 0 && Math.abs(velocity.x) >= Math.abs(velocity.y)) {
                    currentAnimation = walk_left;
                    currentDirection = CharacterDirection.LEFT;
                } else if (velocity.y < 0 && Math.abs(velocity.y) > Math.abs(velocity.x)) {
                    currentAnimation = walk_front;
                    currentDirection = CharacterDirection.FRONT;
                } else if (velocity.y > 0 && Math.abs(velocity.y) > Math.abs(velocity.x)) {
                    currentAnimation = walk_back;
                    currentDirection = CharacterDirection.BACK;
                } else if (velocity.y == 0 && velocity.x == 0) {
                    System.out.println("Current Direction: " + currentDirection);
                    switch (currentDirection) {
                        case FRONT:
                            currentAnimation = idle_front;
                            break;
                        case BACK:
                            currentAnimation = idle_back;
                            break;
                        case LEFT:
                            currentAnimation = idle_left;
                            break;
                        case RIGHT:
                            currentAnimation = idle_right;
                            break;
                    }
                }
            }
        }

    @Override
    public void draw(float runTime, SpriteBatch batch){
        if(currentAnimation != null) {
            batch.draw(currentAnimation.getKeyFrame(runTime), position.x, position.y, width/2f, height/2f,width,height, SCALE, SCALE,0);
        }
    }
    @Override
    public void restart(){
        super.restart();
        position.x = x;
        position.y = y;
        life = MAX_LIFE;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 0;
        readyToFire_gun = true;
        hit = false;
        alive = true;
        buckShots.clear();
    }

    //=============GETTERS AND SETTERS=================
    public Animation getCurrentAnimation() {
        return currentAnimation;}
    public List<BuckShot> getBuckShots() {
        return buckShots;
    }
    public List<Projectile> getProjectiles() {
        return projectiles;
    }
    public float getX(){
        return position.x;
    }
    public float getY(){
        return position.y;
    }
    public float getVy(){
        return velocity.y;
    }
    public float getVx(){
        return velocity.x;
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    public boolean isAlive() {
        return alive;
    }
    public int getLife() {
        return life;
    }
}

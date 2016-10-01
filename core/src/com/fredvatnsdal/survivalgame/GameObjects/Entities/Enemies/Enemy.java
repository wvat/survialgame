package com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fredvatnsdal.survivalgame.GameHelpers.AssetLoader;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Consumable.Consumable;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Entity;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Fred on 19/08/2015.
 */
public class Enemy extends Entity {
    //****CONSTANTS******
    protected final float scale = 3f;           //How much to scale the character
    //****VARIABLES******
    protected int speed, collisionDamage, life;
    protected float x, y, width, height;         //Positioning and dimensions
    protected float aX, aY;
    protected double direction, strength;
    protected Rectangle boundingRectangle;       //The bounding rectangle of the enemy
    protected Animation currentAnimation;        //The current animation to be played.
    protected Map <Consumable, Float> lootTable; //This map of key-value pairs represents the loot table of the enemy, specifying a drop chance for each included consumable.
    protected GameWorld gameWorld;               //The gameworld object.
    protected Vector2 targetPosition;            //Vector representing the location of the target(player).

    public Enemy(float x, float y, float width, float height, GameWorld gameWorld, float scale) {
        super(x, y, width, height, gameWorld, scale);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gameWorld = gameWorld;

        targetPosition = new Vector2(0, 0);

        life = 5;               //Default life value.
        speed = 50;             //Default speed value.
        collisionDamage = 2;    //Default collision damage value.
        meleeDamage = 2;        //Default melee attack damage value.

        initAnimations();
    }

    public void initAnimations() {
     /*   walk_front = AssetLoader.skeleton_front_walk;
        walk_back = AssetLoader.skeleton_back_walk;
        walk_right = AssetLoader.skeleton_right_walk;
        walk_left = AssetLoader.skeleton_left_walk;

        idle_front = AssetLoader.skeleton_front_walk;
        idle_back = AssetLoader.skeleton_back_walk;
        idle_right = AssetLoader.skeleton_right_walk;
        idle_left = AssetLoader.skeleton_left_walk;

        attack_front = AssetLoader.skeleton_front_attack;
        attack_back = AssetLoader.skeleton_back_attack;
        attack_left = AssetLoader.skeleton_left_attack;
        attack_right = AssetLoader.skeleton_right_attack;*/
    }


    public void update(float delta) {
        if (life <= 0) {
            alive = false;
        }
        targetPosition.x = gameWorld.getHero().getX();
        targetPosition.y = gameWorld.getHero().getY();

        aX = targetPosition.x - position.x;
        aY = targetPosition.y - position.y;

        direction = Math.atan2(aY, aX);

        if(alive) {
            velocity.x = (float) (speed * Math.cos(direction));
            velocity.y = (float) (speed * Math.sin(direction));
        } else if(!alive || attacking){
            velocity.x = 0f;
            velocity.y = 0f;
        }

        Gdx.app.log("Enemy", "target position x: " + targetPosition.x);
        Gdx.app.log("Enemy", "target position y: " + targetPosition.y);

        if (gameWorld.getHero().isAlive()) {
            seek(targetPosition.x, targetPosition.y); //seek out the target
        } else {
            velocity.x  = 0;
            velocity.y  = 0;
        }
        super.update(delta);
        evaluateAnimation();
    }

    @Override
    public int basicAttack(){
        attacking = true;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                attacking = false;
            }
        },400);
        return meleeDamage;
    }

    public void evaluateAnimation() {
        if (attacking) {
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

    public void seek(float targetX, float targetY) {
        aX = targetX - position.x;
        aY = targetY - position.y;

        direction = Math.atan2(aY, aX);

        velocity.x = (float) (speed * Math.cos(direction));
        velocity.y = (float) (speed * Math.sin(direction));

        Gdx.app.log("Enemy", "aX: " + aX);
        Gdx.app.log("Enemy", "aY: " + aY);

        Gdx.app.log("Enemy", "direction: " + direction);

        Gdx.app.log("Enemy", "velocity x: " + velocity.x);
        Gdx.app.log("Enemy", "velocity y: " + velocity.y);
    }

    @Override
    public void hurt(int damage) {
        super.hurt(damage);
        life -= damage;
    }

    @Override
    public void draw(float runTime, SpriteBatch batch) {
        if (currentAnimation != null) {
            batch.draw(currentAnimation.getKeyFrame(runTime), position.x, position.y, width / 2f, height / 2f, width, height, scale, scale, 0f);
            Gdx.app.log("Enemy", "Animation is not null");
        } else {
            Gdx.app.log("Enemy", "Animation is null");
        }
    }


    //=========GETTERS AND SETTERS=======
    public Rectangle getBoundingRectangle() {
        return boundingRectangle;
    }
    public int getCollisionDamage() {
        return collisionDamage;
    }
    public boolean isAlive() {
        return alive;
    }
    public double getStrength() {
        return strength;
    }
    public void setStrength(double strength) {this.strength = strength;}
}

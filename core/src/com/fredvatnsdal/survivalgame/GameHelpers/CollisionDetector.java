package com.fredvatnsdal.survivalgame.GameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Entity;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Player.Hero;
import com.fredvatnsdal.survivalgame.GameObjects.Projectiles.Projectile;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Fred on 22/08/2015.
 */
public class CollisionDetector {

    private float delta_X;
    private float delta_Y;
    private GameWorld gameWorld;
    private float angle;
    private boolean timerRunning = false;

    public CollisionDetector(GameWorld gameWorld){

        this.gameWorld = gameWorld;
    }

    public void checkCollision(List<Entity> entities){
        for(final Entity entity : entities) {
            if (entity.getClass() == Hero.class) { //The current entity is the hero
                for (final Entity entity1 : entities) {
                    angle = new Vector2(entity.getPosition().x - entity1.getPosition().x, entity.getPosition().y - entity1.getPosition().y).angle();
                    if (entity != entity1 && EnemyDatabase.entityToEnemy.get(entity1) != null) { //make sure we are dealing with the hero and an enemy
                        if(entity1.getAttackBounds().overlaps(entity.getHitBox())){ //Hero is in attack range
                            if(entity1.getAngle() - angle < 30 && entity1.getAngle() - angle > -30){ //target in 60 deg cone in front of entity
                                if(entity1.isReadyToAttack() && entity1.isAlive()) {
                                    entity1.setReadyToAttack(false);
                                    entity.hurt(entity1.basicAttack());
                                }
                                else{
                                    if(!timerRunning){//No timer is currently running.
                                        timerRunning = true;//timer is now running.
                                        new Timer().schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                entity1.setReadyToAttack(true); //entity is ready to attack again.
                                                timerRunning = false;           //the timer is done, and can be run again.
                                            }
                                        },1000);
                                    }
                                }
                            }
                        }
                        else{
                            entity1.setTargetInRange(false);
                        }
                        if (entity.isAlive() && entity1.isAlive()) {
                            if (entity.getBounds().overlaps(entity1.getBounds())) {
                                adjustEntities(entity, entity1);
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        entity.hurt(1);
                                    }
                                }, 1000);
                            }
                        }
                    }
                }
            }
            else if(entity.getClass() != Hero.class) { //Handle collisions among enemies
                for (Entity entity1 : entities) {
                    if(entity1.getClass() != Hero.class) {
                        if (entity != entity1) {
                            if (entity.isAlive() && entity1.isAlive()) {
                                if(entity.getBounds().overlaps(entity1.getBounds())) {
                                    adjustEntities(entity, entity1);
                                }
                            }
                        }
                    }
                }
            }
            for(Projectile projectile : gameWorld.getHero().getProjectiles()){
                if(entity.getClass() != Hero.class){
                    if(entity.isAlive()){
                        if (projectile.getBoundingRectangle().overlaps(entity.getHitBox())) {
                            if (!projectile.isHit()) {
                                entity.hurt(projectile.getDamage());
                                Gdx.app.log("Damage", "Projectile Damage: " + projectile.getDamage());
                                projectile.setHit(true);
                            }
                        }
                    }
                }
            }
        }
    }

    /*public void checkCollision(Hero hero, List<Enemy> enemies){


        for(Enemy enemy : enemies){
            if(enemy.isAlive()) {
                if (hero.getBounds().overlaps(enemy.getBounds())) {
                    adjustEntities(hero, enemy);
                } else if (!hero.getBounds().overlaps(enemy.getBounds())) {
                    hero.setStopX(false);
                    hero.setStopY(false);
                    enemy.setStopX(false);
                    enemy.setStopY(false);
                }
                for (Enemy secondEnemy : enemies) {
                    if (secondEnemy != enemy && secondEnemy.isAlive()) {
                        if (secondEnemy.getBounds().overlaps(enemy.getBounds())) {
                            adjustEntities(enemy, secondEnemy);
                        }
                    }
                }
            }
            for(Projectile projectile : hero.getProjectiles()){
                if(hero.isAlive() && enemy.isAlive()) {
                    if (projectile.getBoundingRectangle().overlaps(enemy.getHitBox())) {
                        if (!projectile.isHit()) {
                            enemy.dealDamage(projectile.getDamage());
                            Gdx.app.log("Damage", "Projectile Damage: " + projectile.getDamage());
                            projectile.setHit(true);
                        }
                    }
                }
            }
        }
    }*/

    public void checkAttackRange(){

    }

    public void adjustEntities(Entity firstEntity, Entity secondEntity){

        Gdx.app.log("Collision","FE Bounds width: " + firstEntity.getBounds().getWidth());
        Gdx.app.log("Collision","FE Bounds X: " + firstEntity.getBounds().getX());
        Gdx.app.log("Collision","SE Bounds width: " + secondEntity.getBounds().getWidth());
        Gdx.app.log("Collision","SE Bounds X: " + secondEntity.getBounds().getX());

        if(firstEntity.getBounds().getX() < secondEntity.getBounds().getX()){
            delta_X = (firstEntity.getBounds().getX() + firstEntity.getBounds().getWidth()) - secondEntity.getBounds().getX();
        }
        if(secondEntity.getBounds().getX() <= firstEntity.getBounds().getX()){
            delta_X = firstEntity.getBounds().getX() - (secondEntity.getBounds().getX() + secondEntity.getBounds().getWidth());
        }
        Gdx.app.log("Collision", "Delta X: " + delta_X );

        if(firstEntity.getBounds().getY() < secondEntity.getBounds().getY()){
            delta_Y = (firstEntity.getBounds().getY() + firstEntity.getBounds().getHeight()) - secondEntity.getBounds().getY();
        }
        else if(secondEntity.getBounds().getY() <= firstEntity.getBounds().getY()){
            delta_Y = firstEntity.getBounds().getY() - (secondEntity.getBounds().getY() + secondEntity.getBounds().getHeight());
        }

        Gdx.app.log("Collision", "Delta Y: " + delta_Y);

        secondEntity.setX(secondEntity.getPosition().x + delta_X);

    }
}


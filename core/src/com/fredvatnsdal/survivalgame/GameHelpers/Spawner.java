package com.fredvatnsdal.survivalgame.GameHelpers;

import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Enemy;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Melee.Skeleton;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Fred on 9/13/2015.
 */
public class Spawner {
    private List<Enemy> enemies;
    private GameWorld gameWorld;
    private Enemy enemy;
    private Skeleton skeleton;
    private float scale;
    private int tileMapWidth,tileMapHeight;
    private Random random;
    private int maximumEnemies;
    private int waveStrength;

    public Spawner(GameWorld gameWorld,float scale, int tileMapWidth, int tileMapHeight){
        this.gameWorld = gameWorld;
        this.tileMapHeight = tileMapHeight;
        this.tileMapWidth = tileMapWidth;
        this.scale = scale;

        enemies = new ArrayList<Enemy>();

        random = new Random();
    }

    /**
     * Method used to generate and spawn enemies for each wave.
     * The method takes into account the wave number to generate the
     * appropriate amount of enemies.
     * @return
     */
    public List<Enemy> spawnEnemies(int waveNumber){
        waveStrength = waveNumber;
        if(waveStrength >= 10){
            waveStrength = 10;
        }

        do{
            if((waveStrength - EnemyDatabase.enemyDB.get("skeleton")) >= 0){
                skeleton = new Skeleton(random.nextFloat() * (float)tileMapWidth, random.nextFloat()*(float)tileMapHeight, 32f, 32f, gameWorld, scale);
                gameWorld.getEntities().add(skeleton);
                enemies.add(skeleton);
                EnemyDatabase.entityToEnemy.put(skeleton,skeleton);
            }
            waveStrength --;

        }while(waveStrength >= 0);

        return enemies;
    }
}

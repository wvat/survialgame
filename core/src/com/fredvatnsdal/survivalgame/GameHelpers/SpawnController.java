package com.fredvatnsdal.survivalgame.GameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Enemy;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Melee.Skeleton;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Melee.Slime;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Melee.Zombie;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Ranged.DarkWizard;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Ranged.SkeletonArcher;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Entity;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;
import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Fred on 2016-08-28.
 * Handles spawning of enemies and consumables. Enemy spawns are calculated based on the wave.
 *
 */
public class SpawnController {
    private final int RANGE_MIN = 1;
    private final int RANGE_MAX = 3;
    private final double DIFF_INCREMENT = 0.1;  //Increment strength by 10% every 10 waves.
    private int tileMapWidth, tileMapHeight;
    private float scale;
    private Random random;
    private GameWorld gameWorld;
    //Constructor
    public SpawnController(GameWorld gameWorld, int tileMapWidth, int tileMapHeight, float scale){
        this.tileMapWidth = tileMapWidth;
        this.tileMapHeight = tileMapHeight;
        this.gameWorld = gameWorld;
        random = new Random();
    }
    //Spawns the wave of enemies
    public java.util.List<Enemy> spawnWave(int waveNumber){
        int difficultyMultipler, enemyMultiplier;
        int[] splitWaveNumber = new int[2];
        splitWaveNumber = decomposeWaveNumber(waveNumber);  //obtain the split portions of the wave number.
        difficultyMultipler = splitWaveNumber[0];
        enemyMultiplier = splitWaveNumber[1];
        buildWave(difficultyMultipler, enemyMultiplier);
        return buildWave(difficultyMultipler, enemyMultiplier);
    }
    //Builds the wave of enemies
    public java.util.List<Enemy> buildWave(int difficultyMultiplier, int enemyMultiplier){
        int totalEnemies,randomRange,enemyCount;
        double totalWeight, zombieWeight, slimeWeight, skeletonWeight, skeletonArcherWeight, darkWizardWeight, newDamage;
        java.util.List<Enemy> enemyList = new ArrayList<Enemy>();
        HashMap<String,EnemyAttributes> enemySpawnWeight = EnemyDatabase.enemyAttributesHashMap;

        slimeWeight = enemySpawnWeight.get("enemy_slime").spawnWeight;
        zombieWeight =  enemySpawnWeight.get("enemy_zombie").spawnWeight;
        skeletonWeight =  enemySpawnWeight.get("enemy_skeleton").spawnWeight;
        skeletonArcherWeight =  enemySpawnWeight.get("enemy_skeleton_archer").spawnWeight;
        darkWizardWeight =  enemySpawnWeight.get("enemy_dark_wizard").spawnWeight;


        randomRange = (int)((Math.random() * RANGE_MAX) + RANGE_MIN);  //calculate random range for enemy spawn numbers.

        //Waves 1-9 (typical enemy waves).
        if((enemyMultiplier >= 1 && enemyMultiplier < 10) && (randomRange >= RANGE_MIN && randomRange <= RANGE_MAX)) {
            totalEnemies = enemyMultiplier * randomRange;
        }
        //Boss Wave
        else if(enemyMultiplier == 10){
            totalEnemies = 1;
        }
        //Error checking:
        else{
            Gdx.app.error("Error: Out of range", "Error: totalEnemies variable is invalid.");
            System.exit(0);
            totalEnemies = 1;
        }

        //For waves 1-3, spawn slimes and zombies.
        if(enemyMultiplier >= 1 && enemyMultiplier <= 3){
            totalWeight = slimeWeight + zombieWeight;
            enemyCount = (int)(slimeWeight * totalEnemies);
            for(int i = 0; i < enemyCount; i++){
                Slime newSlime = new Slime(random.nextFloat() * (float)tileMapWidth, random.nextFloat()*(float)tileMapHeight, 32f, 32f, gameWorld, scale);
                enemyList = updateLists(newSlime, enemyList, difficultyMultiplier);
            }
            enemyCount = (int)(zombieWeight * totalEnemies);
            for(int i = 0; i < enemyCount; i++){
                Zombie newZombie = new Zombie(random.nextFloat() * (float)tileMapWidth, random.nextFloat()*(float)tileMapHeight, 32f, 32f, gameWorld, scale);
                enemyList = updateLists(newZombie, enemyList, difficultyMultiplier);
            }
        }
        //For waves 4-6, spawn skeletons, slimes, and zombies.
        else if(enemyMultiplier >= 4 && enemyMultiplier <= 6){
            totalWeight = slimeWeight + zombieWeight + skeletonWeight;
            slimeWeight = slimeWeight/totalWeight;
            zombieWeight = zombieWeight/totalWeight;
            skeletonWeight = skeletonWeight/totalWeight;
            enemyCount = (int)(slimeWeight * totalEnemies);
            for(int i = 0; i < enemyCount; i++){
                Slime newSlime = new Slime(random.nextFloat() * (float)tileMapWidth, random.nextFloat()*(float)tileMapHeight, 32f, 32f, gameWorld, scale);
                enemyList = updateLists(newSlime, enemyList, difficultyMultiplier);
            }
            enemyCount = (int)(zombieWeight * totalEnemies);
            for(int i = 0; i < enemyCount; i++){
                Zombie newZombie = new Zombie(random.nextFloat() * (float)tileMapWidth, random.nextFloat()*(float)tileMapHeight, 32f, 32f, gameWorld, scale);
                enemyList = updateLists(newZombie, enemyList, difficultyMultiplier);
            }
            enemyCount = (int)(skeletonWeight * totalEnemies);
            for(int i = 0; i < enemyCount; i++){
                Zombie newZombie = new Zombie(random.nextFloat() * (float)tileMapWidth, random.nextFloat()*(float)tileMapHeight, 32f, 32f, gameWorld, scale);
                enemyList = updateLists(newZombie, enemyList, difficultyMultiplier);
            }
        }
        //For waves 7-9, spawn skeletons, skeleton archers, and dark wizards.
        else if(enemyMultiplier >= 7 && enemyMultiplier <= 9){}
        //For wave 10, spawn a random boss.
        else if(enemyMultiplier == 10){}
        else{}

        return enemyList;
    }

    //Breaks apart the wave number, and returns the difficulty multiplier and the enemy multiplier.
    public int[] decomposeWaveNumber(int waveNumber){
        int[] splitWaveNumber = new int[2];
        double temp_x, temp_y;      //temp_x is before dp, and temp_y is after dp.
        temp_x = waveNumber / 10;   //divide wave number by ten, such that it is of the form (...x.y).
        temp_y = temp_x % 1;        //remainder of division by 1 gives the decimal portion (y) of the number.
        temp_x = temp_x - temp_y;   //subtract (y) from (x.y) to receive (x).
        temp_y = (temp_y * 10) + 1;       //multiply (y) by 10 to receive integer value. (+1 for 1-10)

        //Put portions into the array:
        splitWaveNumber[0] = (int)temp_x;   //cast as integer to round (x).
        splitWaveNumber[1] = (int)temp_y;   //cast as integer to round (y).

        return splitWaveNumber;
    }

    public java.util.List<Enemy> updateLists(Enemy enemy, java.util.List<Enemy> enemyList, double difficultyMultiplier){
        enemy.setStrength(enemy.getStrength() + difficultyMultiplier * DIFF_INCREMENT);
        gameWorld.getEntities().add(enemy);
        enemyList.add(enemy);
        EnemyDatabase.entityToEnemy.put(enemy, enemy);
        return enemyList;
    }

    public java.util.List generateEnemies(java.util.List<Enemy> enemyList, int totalEnemies, int difficultyMultiplier, int slimes, int zombies, int skeletons, int skeletonArchers, int darkWizards){
        slimes = 0;
        zombies = 0;
        skeletons = 0;
        skeletonArchers = 0;
        darkWizards = 0;
        double totalWeight, slimeWeight, zombieWeight, skeletonWeight, skeletonArcherWeight, darkWizardWeight;
        int enemyCount = 0;
        HashMap<String,EnemyAttributes> enemySpawnWeight = EnemyDatabase.enemyAttributesHashMap;
        slimeWeight = enemySpawnWeight.get("enemy_slime").spawnWeight;
        zombieWeight =  enemySpawnWeight.get("enemy_zombie").spawnWeight;
        skeletonWeight =  enemySpawnWeight.get("enemy_skeleton").spawnWeight;
        skeletonArcherWeight =  enemySpawnWeight.get("enemy_skeleton_archer").spawnWeight;
        darkWizardWeight =  enemySpawnWeight.get("enemy_dark_wizard").spawnWeight;

        totalWeight = (slimeWeight * slimes) + (zombieWeight * zombies) + (skeletonWeight * skeletons) + (skeletonArcherWeight * skeletonArchers) + (darkWizardWeight * darkWizards);

        slimeWeight = slimeWeight/totalWeight;
        zombieWeight = zombieWeight/totalWeight;
        skeletonWeight = skeletonWeight/totalWeight;
        skeletonArcherWeight = skeletonArcherWeight/totalWeight;
        darkWizardWeight = darkWizardWeight/totalWeight;

        if(slimes == 1){
            enemyCount = (int)(slimeWeight * totalEnemies);
            for(int i = 0; i < enemyCount; i++){
                Slime newSlime = new Slime(random.nextFloat() * (float)tileMapWidth, random.nextFloat()*(float)tileMapHeight, 32f, 32f, gameWorld, scale);
                enemyList = updateLists(newSlime, enemyList, difficultyMultiplier);
            }
        }
        if(zombies == 1){
            enemyCount = (int)(slimeWeight * totalEnemies);
            for(int i = 0; i < enemyCount; i++){
                Zombie newZombie = new Zombie(random.nextFloat() * (float)tileMapWidth, random.nextFloat()*(float)tileMapHeight, 32f, 32f, gameWorld, scale);
                enemyList = updateLists(newZombie, enemyList, difficultyMultiplier);
            }
        }
        if(skeletons == 1){
            enemyCount = (int)(skeletonWeight * totalEnemies);
            for(int i = 0; i < enemyCount; i++){
                Skeleton newSkeleton = new Skeleton(random.nextFloat() * (float)tileMapWidth, random.nextFloat()*(float)tileMapHeight, 32f, 32f, gameWorld, scale);
                enemyList = updateLists(newSkeleton, enemyList, difficultyMultiplier);
            }
        }
        if(skeletonArchers == 1){
            enemyCount = (int)(skeletonArcherWeight * totalEnemies);
            for(int i = 0; i < enemyCount; i++){
                SkeletonArcher newSkeletonArcher = new SkeletonArcher(random.nextFloat() * (float)tileMapWidth, random.nextFloat()*(float)tileMapHeight, 32f, 32f, gameWorld, scale);
                enemyList = updateLists(newSkeletonArcher, enemyList, difficultyMultiplier);
            }
        }
        if(darkWizards == 1){
            enemyCount = (int)(darkWizardWeight * totalEnemies);
            for(int i = 0; i < enemyCount; i++) {
                DarkWizard newDarkWizard = new DarkWizard(random.nextFloat() * (float) tileMapWidth, random.nextFloat() * (float) tileMapHeight, 32f, 32f, gameWorld, scale);
                enemyList = updateLists(newDarkWizard, enemyList, difficultyMultiplier);
            }
        }

        return enemyList;
    }
}

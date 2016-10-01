package com.fredvatnsdal.survivalgame.GameHelpers;

import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Enemy;

/**
 * Created by Fred on 2016-08-28.
 */
public class EnemyAttributes {
    public double spawnWeight;
    public int strength;
    public EnemyAttributes(double spawnWeight, int strength){
        this.spawnWeight = spawnWeight;
        this.strength = 1;
    }

}

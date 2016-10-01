package com.fredvatnsdal.survivalgame.GameHelpers;

import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Enemy;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Melee.Skeleton;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Melee.Slime;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Melee.Zombie;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Ranged.DarkWizard;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Ranged.SkeletonArcher;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Entity;

import java.util.HashMap;

/**
 * Created by Fred on 9/22/2015.
 */
public class EnemyDatabase {
    public static HashMap<Entity,Enemy> entityToEnemy;
    public static HashMap<String, EnemyAttributes> enemyAttributesHashMap;
    public static void createDatabase(){
        entityToEnemy = new HashMap<Entity, Enemy>();
        enemyAttributesHashMap = new HashMap<String, EnemyAttributes>();

        enemyAttributesHashMap.put("enemy_slime", new EnemyAttributes(0.5,1));
        enemyAttributesHashMap.put("enemy_zombie", new EnemyAttributes(0.5,2));
        enemyAttributesHashMap.put("enemy_skeleton", new EnemyAttributes(0.25,3));
        enemyAttributesHashMap.put("enemy_skeleton_archer", new EnemyAttributes(0.25,1));
        enemyAttributesHashMap.put("enemy_dark_wizard", new EnemyAttributes(0.1,2));

    }
}

package com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Melee;

import com.fredvatnsdal.survivalgame.GameHelpers.AssetLoader;
import com.fredvatnsdal.survivalgame.GameHelpers.EnemyDatabase;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Enemy;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

/**
 * Created by Fred on 9/22/2015.
 */
public class Skeleton extends Enemy {
    public Skeleton(float x, float y, float width, float height, GameWorld gameWorld, float scale) {
        super(x, y, width, height, gameWorld, scale);
        strength = EnemyDatabase.enemyAttributesHashMap.get("enemy_skeleton").strength; //This is a basic enemy. get the strength from the database.
    }
    @Override
    public void initAnimations() {
        walk_front = AssetLoader.skeleton_front_walk;
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
        attack_right = AssetLoader.skeleton_right_attack;
    }
}

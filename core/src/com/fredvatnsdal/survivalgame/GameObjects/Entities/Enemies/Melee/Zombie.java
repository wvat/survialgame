package com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Melee;

import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Enemy;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

/**
 * Created by Fred on 2016-08-28.
 */
public class Zombie extends Enemy {
    public Zombie(float x, float y, float width, float height, GameWorld gameWorld, float scale) {
        super(x, y, width, height, gameWorld, scale);
    }
}

package com.fredvatnsdal.survivalgame.GameObjects.Projectiles.Magical;

import com.fredvatnsdal.survivalgame.GameObjects.Projectiles.Projectile;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

/**
 * Created by Fred on 2016-08-28.
 */
public class Spell extends Projectile {
    public Spell(float x, float y, float width, float height, float directionX, float directionY, GameWorld gameWorld) {
        super(x, y, width, height, directionX, directionY, gameWorld);
    }
}

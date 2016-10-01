package com.fredvatnsdal.survivalgame.GameObjects.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Fred on 2016-09-07.
 */
public class Tile {
    protected TextureRegion tileTexture;      //Texture of the tile.
    protected Rectangle tileBounds;
    protected String tileCode;
    protected boolean canWalk;

    public Tile(Rectangle tileBounds, boolean canWalk, String tileCode ){
        this.tileCode = tileCode;
        this.tileBounds = tileBounds;
        this.canWalk = canWalk;

        initTextures(tileCode);
    }

    public void initTextures(String tileCode){


    }

    public Rectangle getTileBounds() {
        return tileBounds;
    }
    public void setTileBounds(Rectangle tileBounds) {
        this.tileBounds = tileBounds;
    }
    public String getTileCode() {
        return tileCode;
    }
    public void setTileCode(String tileCode) {
        this.tileCode = tileCode;
    }
    public boolean isCanWalk() {
        return canWalk;
    }
    public void setCanWalk(boolean canWalk) {
        this.canWalk = canWalk;
    }
}

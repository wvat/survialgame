package com.fredvatnsdal.survivalgame.Game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.fredvatnsdal.survivalgame.GameHelpers.AssetLoader;
import com.fredvatnsdal.survivalgame.GameHelpers.EnemyDatabase;
import com.fredvatnsdal.survivalgame.Screens.GameScreen;

/**
 * Created by Fred on 19/08/2015.
 */
public class MainGame extends Game implements ApplicationListener{
    @Override
    public void create() {
        AssetLoader.load();         //load the assets
        EnemyDatabase.createDatabase();   //create enemy data base
        setScreen(new GameScreen(this));

    }
    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}

package com.fredvatnsdal.survivalgame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.fredvatnsdal.survivalgame.Game.MainGame;
import com.fredvatnsdal.survivalgame.GameHelpers.AssetLoader;
import com.fredvatnsdal.survivalgame.GameHelpers.InputHandler;
import com.fredvatnsdal.survivalgame.GameWorld.GameRenderer;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

/**
 * Created by Fred on 19/08/2015.
 */
public class GameScreen implements Screen{
    private MainGame mainGame;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private InputMultiplexer inputMultiplexer;

    private GameWorld gameWorld;
    private GameRenderer gameRenderer;
    private float runTime = 0;
    private float screenWidth,screenHeight;
    private float gameWidth,gameHeight;
    private float scaleFactorX,scaleFactorY;

    public GameScreen(MainGame mainGame){
        this.mainGame = mainGame;
        batch = new SpriteBatch();



        screenWidth = Gdx.graphics.getWidth();
        screenHeight  = Gdx.graphics.getHeight();

        gameWidth = 640;
        gameHeight = screenHeight/ (screenWidth/gameWidth);

        scaleFactorX = gameWidth/screenWidth;
        scaleFactorY = gameHeight/screenHeight;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,gameWidth,gameHeight);

        gameWorld = new GameWorld(gameWidth,gameHeight);

        gameRenderer = new GameRenderer(gameWorld);
        inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(new InputHandler(mainGame,gameWorld,scaleFactorX,scaleFactorY));
        inputMultiplexer.addProcessor(gameWorld.getStage());
        Gdx.input.setInputProcessor(inputMultiplexer);

    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        runTime += delta;

        gameWorld.update(delta);
        gameRenderer.render(runTime);
    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {

    }
    @Override
    public void dispose() {
    }
}

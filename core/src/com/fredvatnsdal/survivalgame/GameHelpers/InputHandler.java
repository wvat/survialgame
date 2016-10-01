package com.fredvatnsdal.survivalgame.GameHelpers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.fredvatnsdal.survivalgame.Game.MainGame;
import com.fredvatnsdal.survivalgame.GameWorld.GameWorld;

/**
 * Created by Fred on 19/08/2015.
 */
public class InputHandler  implements InputProcessor {
    private GameWorld gameWorld;

    public InputHandler(MainGame mainGame,GameWorld gameWorld, float scaleFactorX, float scaleFactorY) {
        this.gameWorld = gameWorld;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(gameWorld.currentState == GameWorld.GameState.READY){
            gameWorld.currentState = GameWorld.GameState.RUNNING;
        }
        if(gameWorld.currentState == GameWorld.GameState.GAMEOVER){
            gameWorld.currentState = GameWorld.GameState.READY;
            gameWorld.restartGame();
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

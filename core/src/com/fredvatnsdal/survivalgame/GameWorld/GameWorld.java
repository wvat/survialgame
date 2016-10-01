package com.fredvatnsdal.survivalgame.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fredvatnsdal.survivalgame.GameHelpers.AssetLoader;
import com.fredvatnsdal.survivalgame.GameHelpers.CollisionDetector;
import com.fredvatnsdal.survivalgame.GameHelpers.EnemyDatabase;
import com.fredvatnsdal.survivalgame.GameHelpers.Spawner;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Enemies.Enemy;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Entity;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Player.Hero;
import com.fredvatnsdal.survivalgame.GameObjects.Projectiles.Projectile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Fred on 19/08/2015.
 */
public class GameWorld{
    //=====Constants====
    private static final float heroWidth = 32f;
    private static final float heroHeight = 32f;
    private static final float scale = 3f;

    private Hero hero;
    private Enemy enemy;
    private TiledMap tiledMap;
    private MapProperties mapProperties;
    private int tileMapWidth,tileMapHeight,mapWidth,mapHeight,tileWidth,tileHeight;
    private Stage stage;
    private Touchpad touchpad;
    private Button fireGun,fireSpell,pauseButton;
    private com.badlogic.gdx.scenes.scene2d.ui.Label lifePoints, stateMessage, enemiesRemain, waveNumber;
    private float gameWidth,gameHeight;
    private List<Entity> entities;
    private List<Enemy> enemies;
    private Random random;
    private CollisionDetector collisionDetector;
    private Spawner spawner;
    private int WAVES = 0;
    private int ENEMY_COUNT = 0;
    private boolean quickUpdate = false;


    public enum GameState{
        PAUSE,READY,RUNNING,GAMEOVER
    }
    public GameState currentState = GameState.READY;

    public GameWorld(float gameWidth, float gameHeight){
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        collisionDetector = new CollisionDetector(this);

        random = new Random();

        enemies = new ArrayList<Enemy>();       //Used to contain all of the enemies specifically.
        entities = new ArrayList<Entity>();     //Contains all of the entities for the game (eg hero + enemies).

        touchpad = new Touchpad(10, AssetLoader.touchpadStyle);
        touchpad.setBounds(15,15,200,200);

        tiledMap = new TmxMapLoader().load("data/dungeon_tiles_demo.tmx");
        mapProperties = tiledMap.getProperties();

        mapWidth = mapProperties.get("width", Integer.class);
        mapHeight = mapProperties.get("height", Integer.class);
        tileWidth = mapProperties.get("tilewidth", Integer.class);
        tileHeight = mapProperties.get("tileheight", Integer.class);

        tileMapWidth = tileWidth * mapWidth;
        tileMapHeight = tileHeight * mapHeight;

        spawner = new Spawner(this,scale,tileMapWidth,tileMapHeight);

        hero = new Hero( tileMapWidth/2f - heroWidth/2f, tileMapHeight/2f - heroHeight/2f,heroWidth,heroHeight,this,scale);

       //enemies = spawner.spawnEnemies(WAVES);

        //Compile a list of entities:
        entities.add(hero);


        //**********UI SETUP************
        lifePoints = new com.badlogic.gdx.scenes.scene2d.ui.Label("Life Points : " + hero.getLife(),AssetLoader.buttonSkin);
        stateMessage = new Label("Current State",AssetLoader.buttonSkin);
        enemiesRemain = new Label("Enemies Remaining: " + ENEMY_COUNT, AssetLoader.buttonSkin);
        waveNumber = new Label("Wave Number: " + waveNumber,AssetLoader.buttonSkin);

        fireGun = new TextButton("G",AssetLoader.buttonSkin,"default");
        fireSpell = new TextButton("S",AssetLoader.buttonSkin,"default");
        pauseButton = new TextButton("| |",AssetLoader.buttonSkin,"default");


        //*****LABELS******
        lifePoints.setSize(300f,150f);
        lifePoints.setPosition(Gdx.graphics.getWidth() - 150f, Gdx.graphics.getHeight() - 100f);
        lifePoints.setColor(Color.RED);

        stateMessage.setSize(900f, 450f);
        stateMessage.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        stateMessage.setColor(Color.WHITE);

        enemiesRemain.setSize(900f, 450f);
        enemiesRemain.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 30);
        enemiesRemain.setColor(Color.YELLOW);

        waveNumber.setSize(900f, 450f);
        waveNumber.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 60);
        waveNumber.setColor(Color.YELLOW);


        //****BUTTONS*****
        fireGun.setWidth(100f);
        fireGun.setHeight(100f);
        fireGun.setPosition(Gdx.graphics.getWidth() - 115f, 15f);

        fireSpell.setWidth(100f);
        fireSpell.setHeight(100f);
        fireSpell.setPosition(Gdx.graphics.getWidth() - 230f, 15f);

        pauseButton.setWidth(100f);
        pauseButton.setHeight(100f);
        pauseButton.setPosition(15f, Gdx.graphics.getHeight() - 115f);

        fireGun.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                if(currentState == GameState.RUNNING) {
                    hero.fireWeapon();
                }
            }
        });
        fireSpell.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(currentState == GameState.RUNNING) {

                }
            }
        });
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               if(currentState == GameState.PAUSE){
                   currentState = GameState.RUNNING;
               }
               else{
                   currentState = GameState.PAUSE;

               }

            }
        });

        //******STAGE SETUP*******
        stage = new Stage();
        stage.addActor(touchpad);
        stage.addActor(fireGun);
        stage.addActor(fireSpell);
        stage.addActor(pauseButton);
        stage.addActor(lifePoints);
        stage.addActor(stateMessage);
        stage.addActor(enemiesRemain);
        stage.addActor(waveNumber);
    }
    public void update(float delta){
        switch (currentState){
            case RUNNING:
            case GAMEOVER:
                updateRunning(delta);
                break;
            case PAUSE:
            case READY:
                updatePaused(delta);
                break;
        }
    }
    public void updateRunning(float delta){
        stage.act();
        ENEMY_COUNT = enemies.size();
        for(Entity entity : entities){
            entity.update(delta);
        }
        for(Projectile projectile : hero.getProjectiles()){
            projectile.update(delta);
        }

        collisionDetector.checkCollision(entities);

        if(enemies.size() == 0 || enemies == null){ //All enemies are dead/no enemies spawned yet.
            WAVES ++;
            enemies.clear();
            for(Iterator<Entity> entityIterator = entities.iterator(); entityIterator.hasNext();){
                Entity entity = entityIterator.next();
                if(EnemyDatabase.entityToEnemy.get(entity) != null) {//There is a value for this entity, must be an enemy.
                    Gdx.app.log("ETE","ETE working");
                    entityIterator.remove();
                }
            }
            enemies = spawner.spawnEnemies(WAVES);
        }

        lifePoints.setText("Life Points : " + hero.getLife());
        enemiesRemain.setText("Enemies Remain: " + ENEMY_COUNT);
        waveNumber.setText("Wave: " + WAVES);

        if(!hero.isAlive()){
            currentState = GameState.GAMEOVER;
        }
        if(currentState == GameState.GAMEOVER){
            stateMessage.setText("   GAME OVER   \nTAP TO TRY AGAIN");
        }
        if(currentState == GameState.RUNNING){
            stateMessage.setText("Running");
        }

        //****Remove any dead enemies from the enemies array list******
        for(Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();){
            Enemy enemy = iterator.next();
            if(!enemy.isAlive()){
                iterator.remove();
            }
        }
        //****Remove any projectiles that have exceeded a certain range****
        for(Iterator<Projectile> iterator = hero.getProjectiles().iterator(); iterator.hasNext();){
            Projectile projectile = iterator.next();
            if(!projectile.isVisible()){
                iterator.remove();
            }
        }

        quickUpdate = false;

    }
    public void updatePaused(float delta){
        if(currentState == GameState.READY){
            stateMessage.setText("READY? TAP TO START!");
        }
        if(currentState == GameState.PAUSE){
            stateMessage.setText("PAUSED");
        }
        if(!quickUpdate){
            hero.update(delta);
            quickUpdate = true;
        }

    }

    public void restartGame(){
        WAVES = 0;
        for (Entity entity : entities){
            entity.restart();
        }

        //***If the game is restarted, clean out enemies from the arrays.
        for(Iterator<Entity> entityIterator = entities.iterator(); entityIterator.hasNext();){
            Entity entity = entityIterator.next();
            if(EnemyDatabase.entityToEnemy.get(entity) != null) {//There is a value for this entity, must be an enemy.
                Gdx.app.log("ETE","ETE working");
                entityIterator.remove();
            }
        }
        enemies.clear();
    }


    //=============GETTERS AND SETTERS==============
    public float getGameWidth() {
        return gameWidth;
    }
    public float getGameHeight() {
        return gameHeight;
    }
    public Stage getStage() {
        return stage;
    }
    public Touchpad getTouchpad() {
        return touchpad;
    }
    public Hero getHero() {
        return hero;
    }
    public TiledMap getTiledMap() {
        return tiledMap;
    }
    public int getTileMapWidth() {
        return tileMapWidth;
    }
    public int getTileMapHeight() {
        return tileMapHeight;
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public List<Entity> getEntities() {
        return entities;
    }
}

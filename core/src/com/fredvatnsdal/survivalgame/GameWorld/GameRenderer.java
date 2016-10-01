package com.fredvatnsdal.survivalgame.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.fredvatnsdal.survivalgame.GameHelpers.AssetLoader;
import com.fredvatnsdal.survivalgame.GameHelpers.EnemyDatabase;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Entity;
import com.fredvatnsdal.survivalgame.GameObjects.Entities.Player.Hero;
import com.fredvatnsdal.survivalgame.GameObjects.Projectiles.Physical.BuckShot;
import com.fredvatnsdal.survivalgame.GameObjects.Projectiles.Projectile;

/**
 * Created by Fred on 19/08/2015.
 */
public class GameRenderer {
    private ShapeRenderer shapeRenderer;
    private GameWorld gameWorld;
    private Hero hero;
    private com.badlogic.gdx.graphics.g2d.Animation heroAnimation;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private Stage stage;
    private SpriteBatch batch;
    private OrthographicCamera camera;


    public GameRenderer(GameWorld gameWorld){
        this.gameWorld = gameWorld;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,gameWorld.getGameWidth(),gameWorld.getGameHeight());
        camera.zoom -= 0.2;

        tiledMap = gameWorld.getTiledMap();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);


        initObjects();
        initAssets();
    }
    public void initObjects(){
        hero = gameWorld.getHero();
        stage = gameWorld.getStage();
    }
    public void initAssets(){
        heroAnimation = hero.getCurrentAnimation();
    }

    public void render(float runTime) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        camera.position.set(hero.getX(), hero.getY(), 0);
        shapeRenderer.setProjectionMatrix(camera.combined);
        camera.update();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Projectile projectile : hero.getProjectiles()) {
            if (projectile.getClass() == BuckShot.class) {
                BuckShot buckShot = (BuckShot) projectile;
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.rect(buckShot.getX(), buckShot.getY(), buckShot.getWidth(), buckShot.getHeight());
            }
        }
        for(Entity entity : gameWorld.getEntities()){
            if(entity.isAlive()) {
                if(EnemyDatabase.entityToEnemy.get(entity) != null){
                    if(entity.isTargetInRange()){
                        shapeRenderer.setColor(Color.YELLOW);
                    }
                    else{
                        shapeRenderer.setColor(Color.BLUE);
                    }
                    shapeRenderer.rect( entity.getAttackBounds().x,
                            entity.getAttackBounds().y,
                            entity.getAttackBounds().width,
                            entity.getAttackBounds().height);
                }
                shapeRenderer.setColor(Color.RED);
            }
            else{
                shapeRenderer.setColor(Color.BLACK);
            }
            shapeRenderer.rect(entity.getHitBox().getX(),
                    entity.getHitBox().getY(),
                    entity.getHitBox().getWidth(),
                    entity.getHitBox().getHeight());
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(entity.getBounds().getX(),
                    entity.getBounds().getY(),
                    entity.getBounds().getWidth(),
                    entity.getBounds().getHeight());
        }
        /*
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(hero.getHitBox().getX(),
                                hero.getHitBox().getY(),
                                hero.getHitBox().getWidth(),
                                hero.getHitBox().getHeight());
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(hero.getBounds().getX(),
                    hero.getBounds().getY(),
                    hero.getBounds().getWidth(),
                    hero.getBounds().getHeight());
                   */



            shapeRenderer.end();

            batch.setProjectionMatrix(camera.combined);

            batch.begin();
            AssetLoader.blockFont.draw(batch, "Current State: " + gameWorld.currentState, 0, 0);
            /*for(Enemy enemy : gameWorld.getEnemies()){
                enemy.draw(runTime,batch);
            }

            hero.draw(runTime, batch);//Draw the hero*/
            for(Entity entity : gameWorld.getEntities()){
                entity.draw(runTime,batch);
            }
            batch.end();

            stage.act();
            stage.draw();


        }

}

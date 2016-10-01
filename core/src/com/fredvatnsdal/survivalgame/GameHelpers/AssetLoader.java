package com.fredvatnsdal.survivalgame.GameHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fred on 19/08/2015.
 */
public class AssetLoader {
    //Constants
    private static int FRAME_COLS;

    private static int FRAME_ROWS;
    private static int FRAME_SIZEW = 32;
    private static int FRAME_SIZEH = 32;

    //For sprite flipping
    private static boolean flipY = false;

    //===========FONTS====================
    public static BitmapFont blockFont; //A temporary font.

    //===========TOUCHPAD RELATED============
    public static Touchpad.TouchpadStyle touchpadStyle;
    public static Skin touchpadSkin, buttonSkin;
    public static Drawable touchBackground;
    public static Drawable touchKnob;
    public static Texture blockTexture;

    //==========SPRITE SHEETS=============
    public static Texture wizardSpriteSheet;
    public static Texture skeletonSpriteSheet;
    public static Texture skeletonTestSpriteSheet;


    /**
     * Wizard Character Related Variables:
     */

    //==========Wizard Textures==========
    public static TextureRegion wizard_front_walk1,wizard_front_walk2,wizard_front_walk3,
                                wizard_right_walk1,wizard_right_walk2,wizard_right_walk3,
                                wizard_left_walk1,wizard_left_walk2,wizard_left_walk3,
                                wizard_back_walk1,wizard_back_walk2,wizard_back_walk3,
                                wizard_front_idle1,wizard_front_idle2,wizard_front_idle3,
                                wizard_right_idle1,wizard_right_idle2,wizard_right_idle3,
                                wizard_left_idle1,wizard_left_idle2,wizard_left_idle3,
                                wizard_back_idle1,wizard_back_idle2,wizard_back_idle3,
                                wizard_front_shoot1,wizard_front_shoot2,wizard_front_shoot3,
                                wizard_right_shoot1,wizard_right_shoot2,wizard_right_shoot3,
                                wizard_left_shoot1,wizard_left_shoot2,wizard_left_shoot3,
                                wizard_back_shoot1,wizard_back_shoot2,wizard_back_shoot3;

    //=========Wizard Animations=========
    public static Animation wizard_front_walk,wizard_right_walk,wizard_left_walk,wizard_back_walk,
                            wizard_front_idle,wizard_right_idle,wizard_left_idle,wizard_back_idle,
                            wizard_front_shoot,wizard_right_shoot,wizard_left_shoot,wizard_back_shoot;

    /**
     * Skeleton Related Variables:
     */

    //===============Skeleton Textures=================
    public static TextureRegion[][] tmp;

    //==============Skeleton Animations==================
    public static Animation skeleton_front_walk,skeleton_right_walk,skeleton_left_walk,skeleton_back_walk,
                            skeleton_front_idle,skeleton_right_idle,skeleton_left_idle,skeleton_back_idle,
                            skeleton_front_attack,skeleton_right_attack,skeleton_left_attack,skeleton_back_attack;

    public static void load(){
        loadTextures();
        loadAudio();
        loadFonts();
    }
    public static void loadTextures(){
        //=====LOAD SPRITE SHEETS======
        //Wizard:
        wizardSpriteSheet = new Texture(Gdx.files.internal("data/textures/Wizard_new.png"));
        //Skeleton:
        skeletonSpriteSheet = new Texture(Gdx.files.internal("data/textures/skeleton_sample.png"));
        skeletonTestSpriteSheet = new Texture(Gdx.files.internal("data/textures/Skeleton_Test.png"));

        //=====CREATE THE TOUCHPAD======
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground",new Texture(Gdx.files.internal("data/touchBackground.png")));
        touchpadSkin.add("touchKnob", new Texture("data/touchKnob.png"));
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;

        //======UI SKINS========
        buttonSkin = new Skin(Gdx.files.internal("data/uiskin.json"));



        buildWizard(); //Create all that is wizard related (ie animations, etc)
        buildSkeleton();


    }
    public static void loadAudio(){

    }
    public static void loadFonts(){
        blockFont = new BitmapFont(Gdx.files.internal("data/fonts/block_42s.fnt"));
        blockFont.getData().setScale(0.5f,0.5f);
    }

    /**
     *  Helper methods for characters:
     */
    public static void buildWizard(){
        //======FORWARD=====:
        wizard_front_walk1 = new TextureRegion(wizardSpriteSheet,0,0,32,32);
        wizard_front_walk1.flip(false,flipY);
        wizard_front_walk2 = new TextureRegion(wizardSpriteSheet,32,0,32,32);
        wizard_front_walk2.flip(false,flipY);
        wizard_front_walk3 = new TextureRegion(wizardSpriteSheet,64,0,32,32);
        wizard_front_walk3.flip(false,flipY);

        wizard_front_idle1 = new TextureRegion(wizardSpriteSheet,0,128,32,32);
        wizard_front_idle2 = new TextureRegion(wizardSpriteSheet,32,128,32,32);
        wizard_front_idle3 = new TextureRegion(wizardSpriteSheet,64,128,32,32);

        wizard_front_shoot1 = new TextureRegion(wizardSpriteSheet,96,0,32,32);
        wizard_front_shoot2 = new TextureRegion(wizardSpriteSheet,128,0,32,32);
        wizard_front_shoot3 = new TextureRegion(wizardSpriteSheet,160,0,32,32);

        //Animation WF:
        TextureRegion[] front_walk_slides = {wizard_front_walk1,wizard_front_walk2,wizard_front_walk3};
        wizard_front_walk = new Animation(0.2f,front_walk_slides);
        wizard_front_walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //Animation IF:
        TextureRegion[] front_idle_slides = {wizard_front_idle1,wizard_front_idle2,wizard_front_idle3};
        wizard_front_idle = new Animation(0.2f,front_idle_slides);
        wizard_front_idle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //Animation SF:
        TextureRegion[] front_shoot_slides = {wizard_front_shoot1,wizard_front_shoot2,wizard_front_shoot3};
        wizard_front_shoot = new Animation(0.2f,front_shoot_slides);
        wizard_front_shoot.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //======RIGHT======:
        wizard_right_walk1 = new TextureRegion(wizardSpriteSheet,0,32,32,32);
        wizard_right_walk1.flip(false,flipY);
        wizard_right_walk2 = new TextureRegion(wizardSpriteSheet,32,32,32,32);
        wizard_right_walk2.flip(false,flipY);
        wizard_right_walk3 = new TextureRegion(wizardSpriteSheet,64,32,32,32);
        wizard_right_walk3.flip(false, flipY);

        wizard_right_idle1 = new TextureRegion(wizardSpriteSheet,0,160,32,32);
        wizard_right_idle2 = new TextureRegion(wizardSpriteSheet,32,160,32,32);
        wizard_right_idle3 = new TextureRegion(wizardSpriteSheet,64,160,32,32);

        wizard_right_shoot1 = new TextureRegion(wizardSpriteSheet,96,32,32,32);
        wizard_right_shoot2 = new TextureRegion(wizardSpriteSheet,128,32,32,32);
        wizard_right_shoot3 = new TextureRegion(wizardSpriteSheet,160,32,32,32);

        //Animation WR:
        TextureRegion[] right_walk_slides = {wizard_right_walk1,wizard_right_walk2,wizard_right_walk3};
        wizard_right_walk = new Animation(0.2f,right_walk_slides);
        wizard_right_walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //Animation IR:
        TextureRegion[] right_idle_slides = {wizard_right_idle1,wizard_right_idle2,wizard_right_idle3};
        wizard_right_idle = new Animation(0.2f,right_idle_slides);
        wizard_right_idle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //Animation SR:
        TextureRegion[] right_shoot_slides = {wizard_right_shoot1,wizard_right_shoot2,wizard_right_shoot3};
        wizard_right_shoot = new Animation(0.2f,right_shoot_slides);
        wizard_right_shoot.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);


        //======LEFT======:
        wizard_left_walk1 = new TextureRegion(wizardSpriteSheet,0,64,32,32);
        wizard_left_walk1.flip(false,flipY);
        wizard_left_walk2 = new TextureRegion(wizardSpriteSheet,32,64,32,32);
        wizard_left_walk2.flip(false,flipY);
        wizard_left_walk3 = new TextureRegion(wizardSpriteSheet,64,64,32,32);

        wizard_left_idle1 = new TextureRegion(wizardSpriteSheet,0,192,32,32);
        wizard_left_idle2 = new TextureRegion(wizardSpriteSheet,32,192,32,32);
        wizard_left_idle3 = new TextureRegion(wizardSpriteSheet,64,192,32,32);

        wizard_left_shoot1 = new TextureRegion(wizardSpriteSheet,96,64,32,32);
        wizard_left_shoot2 = new TextureRegion(wizardSpriteSheet,128,64,32,32);
        wizard_left_shoot3 = new TextureRegion(wizardSpriteSheet,160,64,32,32);

        //Animation WL:
        TextureRegion[] left_walk_slides = {wizard_left_walk1,wizard_left_walk2,wizard_left_walk3};
        wizard_left_walk = new Animation(0.2f,left_walk_slides);
        wizard_left_walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //Animation IL:
        TextureRegion[] left_idle_slides = {wizard_left_idle1,wizard_left_idle2,wizard_left_idle3};
        wizard_left_idle = new Animation(0.2f,left_idle_slides);
        wizard_left_idle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //Animation SL:
        TextureRegion[] left_shoot_slides = {wizard_left_shoot1,wizard_left_shoot2,wizard_left_shoot3};
        wizard_left_shoot = new Animation(0.2f,left_shoot_slides);
        wizard_left_shoot.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //======BACK======:
        wizard_back_walk1 = new TextureRegion(wizardSpriteSheet,0,96,32,32);
        wizard_back_walk1.flip(false,flipY);
        wizard_back_walk2 = new TextureRegion(wizardSpriteSheet,32,96,32,32);
        wizard_back_walk2.flip(false,flipY);
        wizard_back_walk3 = new TextureRegion(wizardSpriteSheet,64,96,32,32);
        wizard_back_walk3.flip(false, flipY);

        wizard_back_idle1 = new TextureRegion(wizardSpriteSheet,0,224,32,32);
        wizard_back_idle2 = new TextureRegion(wizardSpriteSheet,32,224,32,32);
        wizard_back_idle3 = new TextureRegion(wizardSpriteSheet,64,224,32,32);

        wizard_back_shoot1 = new TextureRegion(wizardSpriteSheet,96,96,32,32);
        wizard_back_shoot2 = new TextureRegion(wizardSpriteSheet,128,96,32,32);
        wizard_back_shoot3 = new TextureRegion(wizardSpriteSheet,160,96,32,32);

        //Animation WB:
        TextureRegion[] back_walk_slides = {wizard_back_walk1,wizard_back_walk2,wizard_back_walk3};
        wizard_back_walk = new Animation(0.2f,back_walk_slides);
        wizard_back_walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //Animation IB:
        TextureRegion[] back_idle_slides = {wizard_back_idle1,wizard_back_idle2,wizard_back_idle3};
        wizard_back_idle = new Animation(0.2f,back_idle_slides);
        wizard_back_idle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        //Animation SB:
        TextureRegion[] back_shoot_slides = {wizard_back_shoot1,wizard_back_shoot2,wizard_back_shoot3};
        wizard_back_shoot = new Animation(0.2f,back_shoot_slides);
        wizard_back_shoot.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }


    public static void buildSkeleton(){

        FRAME_COLS = skeletonSpriteSheet.getWidth()/FRAME_SIZEW;
        FRAME_ROWS = skeletonSpriteSheet.getHeight()/FRAME_SIZEH;


        tmp = TextureRegion.split(skeletonSpriteSheet,
                                  skeletonSpriteSheet.getWidth()/FRAME_COLS,
                                  skeletonSpriteSheet.getHeight()/FRAME_ROWS);

        //
        TextureRegion[] front_walk_slides = new TextureRegion[2];
        TextureRegion[] front_attack_slides = new TextureRegion[2];
        TextureRegion[] right_walk_slides = new TextureRegion[4];
        TextureRegion[] right_attack_slides = new TextureRegion[4];
        TextureRegion[] left_walk_slides = new TextureRegion[4];
        TextureRegion[] left_attack_slides = new TextureRegion[4];
        TextureRegion[] back_walk_slides = new TextureRegion[2];
        TextureRegion[] back_attack_slides = new TextureRegion[2];

        int index = 0;
        int i = 0;


        for(int j = 0; j < 2; j++){
            front_walk_slides[index++] = tmp[i][j];
        }
        index = 0;
        for(int j = 2; j < 4; j++){
            front_attack_slides[index++] = tmp[i][j];
        }
        i++;
        index = 0;
        for(int j = 0; j < FRAME_COLS; j++){
            right_walk_slides[index++] = tmp[i][j];
        }
        i++;
        index = 0;
        for(int j = 0; j < FRAME_COLS; j++){
            right_attack_slides[index++] = tmp[i][j];
        }
        i++;
        index = 0;
        for(int j = 0; j < FRAME_COLS; j++){
            left_attack_slides[index++] = tmp[i][j];
        }
        i++;
        index = 0;
        for(int j = 0; j < FRAME_COLS; j++){
            left_walk_slides[index++] = tmp[i][j];
        }
        i++;
        index = 0;
        for(int j = 0; j < 2; j++){
            back_walk_slides[index++] = tmp[i][j];
        }
        index = 0;
        for(int j = 2; j < 4; j++){
            back_attack_slides[index++] = tmp[i][j];
        }



        skeleton_front_walk = new Animation(0.2f,front_walk_slides);
        skeleton_front_walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skeleton_front_attack = new Animation(0.2f,front_attack_slides);
        skeleton_front_attack.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skeleton_right_walk = new Animation(0.2f,right_walk_slides);
        skeleton_right_walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skeleton_right_attack = new Animation(0.2f,right_attack_slides);
        skeleton_right_attack.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skeleton_left_walk = new Animation(0.2f,left_walk_slides);
        skeleton_left_walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skeleton_left_attack = new Animation(0.2f,left_attack_slides);
        skeleton_left_attack.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skeleton_back_walk = new Animation(0.2f,back_walk_slides);
        skeleton_back_walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skeleton_back_attack = new Animation(0.2f,back_attack_slides);
        skeleton_back_attack.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

    }

    public static void dispose(){
        //Textures:
        wizardSpriteSheet.dispose();
        skeletonSpriteSheet.dispose();
        touchpadSkin.dispose();
        buttonSkin.dispose();
        //Audio:
        //Fonts:
    }


}

package com.bomberman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import javafx.scene.input.Mnemonic;
import sun.rmi.runtime.Log;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class GameScreen implements Screen {

    final BomberMan game;
    private Stage stage;

    private GameScreen layer;


    OrthographicCamera camera;
    Texture ManImg;
    Texture bg;
    Texture BombImg;
    Texture BoomImg;
    Texture EnemyImg;
    Texture BblockImg;
    Texture UblockImg;
    Rectangle Man;


    Array<Rectangle> bombs;
    Array<Rectangle> booms;

    LinkedList<Rectangle> Bblocks;
    LinkedList<Rectangle> enemys;


    long bombstime;
    long boomstime;

    int i=0;
    int j=0;
    int boomX;
    int boomY;
    int[][] map = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},

    };




    public GameScreen(final BomberMan gam) {
        this.game = gam;

        ManImg = new Texture("hero.png");
        BombImg = new Texture("bomb.png");
        BoomImg = new Texture("boom.png");
        bg = new Texture("bg.png");
        EnemyImg = new Texture("enemy.png");
        BblockImg = new Texture("block.png");


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 1024);
        stage = new Stage(new StretchViewport(1280, 1024));
        Gdx.input.setInputProcessor(stage);

        Man = new Rectangle();
        Man.x = 64;
        Man.y = 64;
        Man.width = 64;
        Man.height = 64;

        bombs = new Array<Rectangle>();
        booms = new Array<Rectangle>();

        Bblocks = new LinkedList<Rectangle>();
        enemys = new LinkedList<Rectangle>();


    }

    private void spawnbomb() {

        if (i<1){
            Rectangle bomb = new Rectangle();
            bomb.x = Man.x;
            bomb.y = Man.y;
            bomb.width = 64;
            bomb.height = 64;
            bombs.add(bomb);

            bombstime = TimeUtils.nanoTime();

            boomX = bomb.x;
            boomY = bomb.y;

        }

    }

    private void spawnboom(int x ,int y){
        Rectangle boom = new Rectangle();
        boom.x = boomX+x;
        boom.y = boomY+y;
        boom.width = 64;
        boom.height = 64;
        booms.add(boom);

        boomstime = TimeUtils.nanoTime();

    }

    private void spawnenemy(int x,int y){
        Rectangle enemy = new Rectangle();
        enemy.x = x;
        enemy.y = y;
        enemy.width = 64;
        enemy.height = 64;
        enemys.add(enemy);
    }

    private void spawnBblock(int x,int y){
        Rectangle Bblock = new Rectangle();
        Bblock.x = x;
        Bblock.y = y;
        Bblock.width = 64;
        Bblock.height = 64;
        Bblocks.add(Bblock);
    }






    @Override
    public void show() {

        spawnenemy(200,200);
        spawnenemy(500,500);

        for (int i=0;i<16;i++) {
            for (int j = 0; j < 20; j++) {
                if (this.map[i][j] == 1){
                    spawnBblock(64 * j, (1024-64 - (i * 64)));
                }else if (this.map[i][j] == 0){

                }

            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);


        game.batch.begin();

        game.batch.draw(bg,0,0);
        game.batch.draw(ManImg,Man.x, Man.y,Man.width,Man.height);
        for(Rectangle bomb: bombs) {
            game.batch.draw(BombImg, bomb.x, bomb.y,bomb.width,bomb.height);
        }
        for(Rectangle boom: booms) {
            game.batch.draw(BoomImg, boom.x, boom.y,boom.width,boom.height);
        }

        for(Rectangle enemy: enemys) {
            game.batch.draw(EnemyImg, enemy.x, enemy.y,enemy.width,enemy.height);
        }

        for (Rectangle Bblock : Bblocks){
            game.batch.draw(BblockImg,Bblock.x,Bblock.y,Bblock.width,Bblock.height);
        }

        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {

            Man.y += 500 * Gdx.graphics.getDeltaTime();


        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            Man.x -= 500 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            Man.y -= 500 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            Man.x += 500 * Gdx.graphics.getDeltaTime();
        }


        if(Man.x < 0) Man.x = 0;
        if(Man.x+64 > 1280) Man.x = 1280-64;

        if(Man.y < 0) Man.y = 0;
        if(Man.y+64 > 1024) Man.y = 1024-64;

        for(Rectangle enemy: enemys) {
            if(((Man.y >= enemy.y)&&(Man.y <= enemy.y+64)) || ((Man.y+64 >= enemy.y)&&(Man.y+64 <= enemy.y+64))){

                if ((Man.x+64 >= enemy.x)&&(Man.x<= enemy.x+64)){
                    game.setScreen(new GameoverScreen(game));
                }
            }

            if (j < 1){
                if (enemy.x+64 > 1200){
                    j=1;
                }
                enemy.x += 200 *Gdx.graphics.getDeltaTime();
            }

            if (j == 1){
                if (enemy.x <0){
                    j=0;
                }
                enemy.x -= 200*Gdx.graphics.getDeltaTime();
            }

            for (Rectangle boom: booms){
                if(((enemy.y >= boom.y)&&(enemy.y <= boom.y+64)) || ((enemy.y+64 >= boom.y)&&(enemy.y+64 <= boom.y+64))){

                    if ((enemy.x+64 >= boom.x)&&(enemy.x<= boom.x+64)){
                        enemys.remove(enemy);
                    }
                }

            }

        }

        for (Rectangle boom:booms){

            if(((Man.y >= boom.y)&&(Man.y <= boom.y+64)) || ((Man.y+64 >= boom.y)&&(Man.y+64 <= boom.y+64))){

                if ((Man.x+64 >= boom.x)&&(Man.x<= boom.x+64)){
                    game.setScreen(new GameoverScreen(game));
                }
            }

        }

        for (Rectangle Bblock:Bblocks){


        }


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){

            spawnbomb();
            i++;


        }



        Iterator<Rectangle> iter = bombs.iterator();
        while (iter.hasNext()) {
            Rectangle bomb = iter.next();
            if(TimeUtils.nanoTime() - bombstime > 2000000000) {
                i = 0;
                spawnboom(64,0);
                spawnboom(-64,0);
                spawnboom(0,64);
                spawnboom(0,-64);
                spawnboom(0,0);



                iter.remove();

            }

        }

        Iterator<Rectangle> iter2 = booms.iterator();
        while (iter2.hasNext()){
            Rectangle boom = iter2.next();
            if (TimeUtils.nanoTime() - boomstime  > 500000000){
                iter2.remove();
            }
        }






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

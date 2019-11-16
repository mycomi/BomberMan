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
import sun.rmi.runtime.Log;


import java.awt.*;
import java.util.Iterator;

public class GameScreen implements Screen {

    final BomberMan game;
    private Stage stage;

    OrthographicCamera camera;
    Texture ManImg;
    Texture bg;
    Texture BombImg;
    Texture BoomImg;
    Texture EnemyImg;
    Rectangle Man;
    Rectangle Enemy;


    Array<Rectangle> bombs;
    Array<Rectangle> booms;
    long bombstime;
    long boomstime;

    int i=0;
    int j=0;
    int boomX;
    int boomY;

    public GameScreen(final BomberMan gam) {
        this.game = gam;

        ManImg = new Texture("hero.png");
        BombImg = new Texture("bomb.png");
        BoomImg = new Texture("boom.png");
        bg = new Texture("bg.png");
        EnemyImg = new Texture("enemy.png");


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 1024);
        stage = new Stage(new StretchViewport(1280, 1024));
        Gdx.input.setInputProcessor(stage);

        Man = new Rectangle();
        Man.x = 0;
        Man.y = 0;
        Man.width = 64;
        Man.height = 64;

        bombs = new Array<Rectangle>();
        booms = new Array<Rectangle>();

        Enemy = new Rectangle();
        Enemy.x = 200;
        Enemy.y = 200;


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


    @Override
    public void show() {

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

        game.batch.draw(EnemyImg,Enemy.x,Enemy.y);


        game.batch.end();

        Gdx.app.log("LOG","Boom Time: " + boomstime +"Bomb Time: "+ bombstime +"TimeUnit: "+TimeUtils.nanoTime());


        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            Man.y += 500 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            Man.x -= 500 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            Man.y -= 500 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            Man.x += 500 * Gdx.graphics.getDeltaTime();
        }

        if(Man.x < 0) Man.x = 0;
        if(Man.x+64 > 800) Man.x = 800-64;

        if(Man.y < 0) Man.y = 0;
        if(Man.y+64 > 480) Man.y = 480-64;

        if(((Man.y >= Enemy.y)&&(Man.y <= Enemy.y+64)) || ((Man.y+64 >= Enemy.y)&&(Man.y+64 <= Enemy.y+64))){

            if ((Man.x+64 >= Enemy.x)&&(Man.x<=Enemy.x+64)){
                game.setScreen(new GameoverScreen(game));

            }

        }

        if (j < 1){
            if (Enemy.x+64 > 800){
                j=1;
            }
            Enemy.x += 200 *Gdx.graphics.getDeltaTime();
        }

        if (j == 1){
            if (Enemy.x <0){
                j=0;
            }
            Enemy.x -= 200*Gdx.graphics.getDeltaTime();
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


                iter.remove();


            }

        }

        Iterator<Rectangle> iter2 = booms.iterator();
        while (iter2.hasNext()){
            Rectangle boom = iter2.next();
            if (TimeUtils.nanoTime() - boomstime  > 1000000000){
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

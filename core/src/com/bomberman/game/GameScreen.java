package com.bomberman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


import java.awt.*;
import java.util.Iterator;

public class GameScreen implements Screen {

    final BomberMan game;

    OrthographicCamera camera;
    Texture DotImg;
    Texture bg;
    Texture BombImg;
    Texture EnemyImg;
    Rectangle Dot;
    Rectangle Enemy;


    Array<Rectangle> bombs;
    long bombstime;

    int i=0;
    int j=0;

    public GameScreen(final BomberMan gam) {
        this.game = gam;

        DotImg = new Texture("point.png");
        BombImg = new Texture("bomb.png");
        bg = new Texture("bg.png");
        EnemyImg = new Texture("cat.png");


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);

        Dot = new Rectangle();
        Dot.x = 0;
        Dot.y = 0;
        Dot.width = 8;
        Dot.height = 8;

        bombs = new Array<Rectangle>();

        Enemy = new Rectangle();
        Enemy.x = 200;
        Enemy.y = 200;


    }

    private void spawnbomb() {

        if (i<1){
            Rectangle bomb = new Rectangle();
            bomb.x = Dot.x;
            bomb.y = Dot.y;
            bomb.width = 8;
            bomb.height = 8;
            bombs.add(bomb);

            bombstime = TimeUtils.nanoTime();
        }

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

        game.batch.draw(DotImg,Dot.x,Dot.y);
        for(Rectangle bomb: bombs) {
            game.batch.draw(BombImg, bomb.x, bomb.y);
        }

        game.batch.draw(EnemyImg,Enemy.x,Enemy.y);



        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            Dot.y += 500 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            Dot.x -= 500 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            Dot.y -= 500 * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            Dot.x += 500 * Gdx.graphics.getDeltaTime();
        }

        if(Dot.x < 0) Dot.x = 0;
        if(Dot.x > 1700) Dot.x = 1700;

        if(Dot.y < 0) Dot.y = 0;
        if(Dot.y > 900) Dot.y = 900;

        if (Dot.x == Enemy.x) Dot.x = 0;

        if (j < 1){
            if (Enemy.x > 1000){
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
            if(TimeUtils.nanoTime() - bombstime > 1000000000) {
                i = 0;
                iter.remove();

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

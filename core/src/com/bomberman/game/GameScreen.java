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
import com.badlogic.gdx.math.Rectangle;
import com.bomberman.game.object.*;
import com.sun.tools.javac.comp.Check;


import java.util.Iterator;
import java.util.LinkedList;

public class GameScreen implements Screen {

    final BomberMan game;
    private Stage stage;

    private GameScreen layer;

    private Ublock ublock;
    private Bblock bblock;
    private Player player;
    private Enemy enemy;
    private Bomb bomb;
    private Boom boom;

    SpriteBatch batch;
    OrthographicCamera camera;
    Texture bg;


    LinkedList<Enemy> enemys;
    LinkedList<Ublock> ublockslist;
    LinkedList<Bblock> bblockslist;
    LinkedList<Bomb> bombs;
    LinkedList<Boom> booms;

    long bombstime;
    long boomstime;

    float i=0;
    float j=0;

    int k = 0;
    int l = 0;

    int bombN = 1;

    float playerX = 64;
    float playerY = 64;

    float bombX =99999 , bombY =99999;

    float boomX,boomY;


    int[][] map = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,2,0,0,0,2,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},

    };




    public GameScreen(final BomberMan gam) {
        this.game = gam;

        bg = new Texture("bg.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 1024);
        stage = new Stage(new StretchViewport(1280, 1024));
        Gdx.input.setInputProcessor(stage);


    }


    /**
     * สร้างระเบิด
     */


    private void checkCollisions(Rectangle player, Rectangle ublock){

        if (player.overlaps(ublock)){
                if(player.getX() + 64 > ublock.x && player.getX() < ublock.x){
                    //collision with right side of bucket
                    playerX = ublock.x - 64;
                    playerY = player.getY();
                }

                if(player.getX() < ublock.x + 64 && player.getX() > ublock.x){
                    //collision with left side of bucket
                    playerX = ublock.x + 64 ;
                    playerY = player.getY();
                }


                if(player.getY() + 64 > ublock.y && player.getY() < ublock.y){
                    //collision with top side of bucket
                    playerY = ublock.y - 64;
                    playerX = player.getX();
                }

                if(player.getY() < ublock.y + 64 && player.getY() > ublock.y){
                    //collision with bottom side of bucket
                    playerY = ublock.y + 64;
                    playerX = player.getX();
                }
        }
    }



    @Override
    public void show() {
        ublockslist = new LinkedList<>();
        bblockslist = new LinkedList<>();
        batch = new SpriteBatch();

        enemys = new LinkedList<>();

        enemys.add(new Enemy(200,500));
        enemys.add(new Enemy(500,400));

        while(k<16) {
            while(l<20) {
                if (this.map[k][l] == 1){
                    ublockslist.add(new Ublock(64*l,1024-64-(k*64)));
                    l++;

                }else if (this.map[k][l] == 0){
                    l++;
                }else if (this.map[k][l] == 2){
                    bblockslist.add(new Bblock(64*l,1024-64-(k*64)));
                    l++;
                }

            }
            k++;
            l=0;
        }


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        player = new Player(playerX,playerY);


        bombs = new LinkedList<>();
        booms = new LinkedList<>();

        bombs.add(new Bomb(bombX,bombY));

        batch.begin();

        batch.draw(bg,0,0);


        for (Ublock ublock : ublockslist) {
            ublock.Draw(batch);

            checkCollisions(player.rectangle(),ublock.rectangle());

        }

        for (Bblock bblock : bblockslist) {
            bblock.Draw(batch);

        }

        for (Enemy enemy : enemys){
            enemy.Draw(batch);

        }

        for (Bomb bomb : bombs){
            bomb.Draw(batch);

        }

        for (Boom boom : booms){
            boom.Draw(batch);
        }

        player.Draw(batch);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){

            if (bombN == 1){
                bombstime = TimeUtils.nanoTime();
                bombX = playerX;
                bombY = playerY;
                bombN = 0;
            }

        }



        if (TimeUtils.nanoTime() - bombstime > 2000000000){
            bombN = 1;

            booms.add(new Boom(bombX,bombY+64));
            booms.add(new Boom(bombX,bombY-64));
            booms.add(new Boom(bombX+64,bombY));
            booms.add(new Boom(bombX-64,bombY));
        }






        System.out.println("Bombtime :" + bombstime + "Time :" + TimeUtils.nanoTime());




        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {

        //    if (moveableW == true)
            playerY += 500 * Gdx.graphics.getDeltaTime();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

         //   if (moveableA == true)
            playerX -= 500 * Gdx.graphics.getDeltaTime();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

         //   if (moveableS == true)
            playerY -= 500 * Gdx.graphics.getDeltaTime();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

         //   if (moveableD == true)
                playerX += 500 * Gdx.graphics.getDeltaTime();


        }




        if(player.getX() < 0) playerX = 0;
        if(player.getX()+64 > 1280) playerX = 1280-64;

        if(player.getY() < 0) playerY = 0;
        if(player.getY()+64 > 1024) playerY = 1024-64;



        for(Enemy enemy: enemys) {
            if(((player.getY() >= enemy.getY())&&(player.getY() <= enemy.getY()+64)) || ((player.getY()+64 >= enemy.getY())&&(player.getY()+64 <= enemy.getY()+64))){

                if ((player.getX()+64 >= enemy.getX())&&(player.getX()<= enemy.getX()+64)){
                    game.setScreen(new GameoverScreen(game));
                }
            }


            for (Boom boom: booms){
                if(((enemy.getY() >= boom.getY())&&(enemy.getY() <= boom.getY()+64)) || ((enemy.getY()+64 >= boom.getY())&&(enemy.getY()+64 <= boom.getY()+64))){

                    if ((enemy.getY()+64 >= boom.getX())&&(enemy.getY()<= boom.getX()+64)){
                        enemys.remove(enemy);
                    }
                }

            }


        }

       for (Boom boom:booms){

            if(((player.getY() >= boom.getY())&&(player.getY() <= boom.getY()+64)) || ((player.getY()+64 >= boom.getY())&&(player.getY()+64 <= boom.getY()+64))){

                if ((player.getX()+64 >= boom.getX())&&(player.getX()<= boom.getX()+64)){
                    game.setScreen(new GameoverScreen(game));
                }
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





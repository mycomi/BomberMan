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
import com.bomberman.game.object.Bomb;
import com.bomberman.game.object.Enemy;
import com.bomberman.game.object.Player;
import com.bomberman.game.object.Ublock;
import com.sun.tools.javac.comp.Check;


import java.util.Iterator;
import java.util.LinkedList;

public class GameScreen implements Screen {

    final BomberMan game;
    private Stage stage;

    private GameScreen layer;

    private Ublock ublock;
    private Player player;
    private Enemy enemy;
    private Bomb bomb;

    SpriteBatch batch;
    OrthographicCamera camera;
    Texture bg;
    Texture BoomImg;

    Texture BblockImg;


    Array<Rectangle> booms;

    LinkedList<Rectangle> Bblocks;

    LinkedList<Enemy> enemys;
    Array<Ublock> ublockslist;
    Array<Bomb> bombs;

    long bombstime;
    long boomstime;

    float i=0;
    float j=0;

    int k = 0;
    int l = 0;

    float playerX = 300;
    float playerY = 600;

    float boomX;
    float boomY;


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

        BoomImg = new Texture("boom.png");
        bg = new Texture("bg.png");
        BblockImg = new Texture("block.png");


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 1024);
        stage = new Stage(new StretchViewport(1280, 1024));
        Gdx.input.setInputProcessor(stage);



        booms = new Array<Rectangle>();

        Bblocks = new LinkedList<Rectangle>();



    }


    /**
     * สร้างระเบิด
     */


    private void spawnboom(int x ,int y){
        Rectangle boom = new Rectangle();
        boom.x = boomX+x;
        boom.y = boomY+y;
        boom.width = 64;
        boom.height = 64;
        booms.add(boom);


    }




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
        ublockslist = new Array<Ublock>();
        batch = new SpriteBatch();


        while(k<16) {
            while(l<20) {
                if (this.map[k][l] == 1){
                    ublockslist.add(new Ublock(64*l,1024-64-(k*64)));
                    l++;

                }else if (this.map[k][l] == 0){
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

        enemys = new LinkedList<>();

        enemys.add(new Enemy(200,500));
        enemys.add(new Enemy(500,400));

        bombs = new Array<Bomb>();


        batch.begin();

        batch.draw(bg,0,0);


        for (Ublock ublock : ublockslist) {
            ublock.Draw(batch);

            checkCollisions(player.rectangle(),ublock.rectangle());

        }

        for (Enemy enemy : enemys){
            enemy.Draw(batch);
        }


        player.Draw(batch);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            bombs.add(new Bomb(playerX,playerY));
            bombstime = TimeUtils.nanoTime();

        }

        for (Bomb bomb : bombs){
            bomb.Draw(batch);
        }


        for(Rectangle boom: booms) {
            batch.draw(BoomImg, boom.x, boom.y,boom.width,boom.height);
        }



        for (Rectangle Bblock : Bblocks){
            batch.draw(BblockImg,Bblock.x,Bblock.y,Bblock.width,Bblock.height);
        }

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





            for (Rectangle boom: booms){
                if(((enemy.getY() >= boom.y)&&(enemy.getY() <= boom.y+64)) || ((enemy.getY()+64 >= boom.y)&&(enemy.getY()+64 <= boom.y+64))){

                    if ((enemy.getY()+64 >= boom.x)&&(enemy.getY()<= boom.x+64)){
                        enemys.remove(enemy);
                    }
                }

            }

        }

       for (Rectangle boom:booms){

            if(((player.getY() >= boom.y)&&(player.getY() <= boom.y+64)) || ((player.getY()+64 >= boom.y)&&(player.getY()+64 <= boom.y+64))){

                if ((player.getX()+64 >= boom.x)&&(player.getX()<= boom.x+64)){
                    game.setScreen(new GameoverScreen(game));
                }
            }

        }





        Iterator<Bomb> iter = bombs.iterator();
        while (iter.hasNext()) {
            bomb = iter.next();
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

        /*
        for(Rectangle Bblock : Bblocks){
            Rectangle manrec = new Rectangle(Man.x,Man.y,Man.width,Man.height);
            if(manrec.overlaps(spawnBblock(Bblock.x,Bblock.y))){
                System.out.println("Hit!!!!!!!!!");
            }
        }


         */




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





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
    Array<Bomb> bombs;
    LinkedList<Boom> booms;

    long bombstime;
    long boomstime;

    float i=0;
    float j=0;

    int k = 0;
    int l = 0;

    int bombN = 1;
    float time = 0f;
    float playerX = 65;
    float playerY = 1024-65;

    float tempX=9999,tempY=9999;

    float bombX =99999 , bombY =99999;

    float boomX,boomY;

    boolean moveableW = true,moveableA = true,moveableD = true,moveableS=true;


    int[][] map = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,2,0,0,2,0,2,2,0,2,2,2,2,0,0,0,1},
            {1,0,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,2,1},
            {1,0,2,2,2,2,0,2,0,2,2,2,0,0,0,2,2,2,2,1},
            {1,0,1,2,1,2,1,2,1,2,1,0,1,0,1,0,1,2,0,1},
            {1,0,2,2,2,2,0,0,0,0,0,0,2,2,2,0,0,0,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,2,1,2,1,2,1,2,2,1},
            {1,0,0,2,2,0,0,0,0,0,0,0,2,2,2,0,0,2,0,1},
            {1,0,1,2,1,0,1,2,1,0,1,0,1,0,1,0,1,2,0,1},
            {1,0,0,2,2,0,2,2,2,0,2,0,0,0,2,2,2,0,0,1},
            {1,0,1,2,1,0,1,0,1,0,1,2,1,0,1,2,1,0,0,1},
            {1,0,2,2,2,0,0,0,0,2,2,2,2,2,0,0,0,0,0,1},
            {1,0,1,2,1,0,1,2,1,0,1,2,1,0,1,0,1,0,0,1},
            {1,0,0,0,0,0,2,2,2,0,2,2,2,0,0,0,0,0,0,1},
            {1,0,1,0,1,0,1,2,1,0,1,2,1,0,1,0,1,0,0,1},
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


    private void checkCollision (Rectangle player, Rectangle block){


        if (player.overlaps(block)){
                if(player.getX() + 64 > block.x && player.getX() < block.x){
                    //collision with right side of bucket
                    playerX = block.x - 64;
                    playerY = player.getY();
         //           moveableD = false;
                }

                if(player.getX() < block.x + 64 && player.getX() > block.x){
                    //collision with left side of bucket
                    playerX = block.x + 64 ;
                    playerY = player.getY();
      //              moveableA = false;
                }


                if(player.getY() + 64 > block.y && player.getY() < block.y){
                    //collision with top side of bucket
                    playerY = block.y - 64;
                    playerX = player.getX();
                 //   moveableW = false;
                }

                if(player.getY() < block.y + 64 && player.getY() > block.y){
                    //collision with bottom side of bucket
                    playerY = block.y + 64;
                    playerX = player.getX();
                 //   moveableS = false;
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
        time = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        player = new Player(playerX,playerY);
        for (Enemy enemy : enemys){
            enemy.Move(delta);
        }

        bombs = new Array<Bomb>();
        booms = new LinkedList<Boom>();

        batch.begin();

        batch.draw(bg,0,0);


        for (Ublock ublock : ublockslist) {
            ublock.Draw(batch);
            checkCollision(player.rectangle(),ublock.rectangle());

        }

        for (Bblock bblock : bblockslist) {
            bblock.Draw(batch);
            checkCollision(player.rectangle(),bblock.rectangle());

        }

        for (Enemy enemy : enemys){
            enemy.Draw(batch);
        }

        player.Draw(batch);

        bombs.add(new Bomb(bombX,bombY));

        booms.add(new Boom(boomX,boomY+64));
        booms.add(new Boom(boomX,boomY-64));
        booms.add(new Boom(boomX+64,boomY));
        booms.add(new Boom(boomX-64,boomY));
        booms.add(new Boom(boomX,boomY));


        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if (bombN == 1){
                bombX = playerX;
                bombY = playerY;
                bombstime = TimeUtils.nanoTime();
                boomstime = TimeUtils.nanoTime()+2000000000;
                bombN = 0;
                tempX = bombX;
                tempY = bombY;
            }
        }

        if (TimeUtils.nanoTime() - bombstime >2000000000 ){
            boomX = tempX;
            boomY = tempY;
            bombX = 99999;
            bombY = 99999;

            bombN = 1;

        }

        if (TimeUtils.nanoTime() - boomstime >2000000000){
            boomX = 99999;
            boomY = 99999;
        }


        System.out.println("Time :" + TimeUtils.nanoTime() + " Boom : " + boomstime +" Bomb : "+bombstime);

        for (Bomb bomb : bombs){
            bomb.Draw(batch);
        }

        for (Boom boom : booms){
            boom.Draw(batch);


        }

        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {

        //    if (moveableW == true)
            playerY += 250 * Gdx.graphics.getDeltaTime();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

         //   if (moveableA == true)
            playerX -= 250 * Gdx.graphics.getDeltaTime();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

         //   if (moveableS == true)
            playerY -= 250 * Gdx.graphics.getDeltaTime();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

         //   if (moveableD == true)
                playerX += 250 * Gdx.graphics.getDeltaTime();

        }

        if(player.getX() < 64) playerX = 64;
        if(player.getX()+64 > 1280-64) playerX = 1280-128;

        if(player.getY() < 64) playerY = 64;
        if(player.getY()+64 > 1024-64) playerY = 1024-128;



        for(Enemy enemy: enemys) {
            if(((player.getY() >= enemy.getY())&&(player.getY() <= enemy.getY()+64)) || ((player.getY()+64 >= enemy.getY())&&(player.getY()+64 <= enemy.getY()+64))){

                if ((player.getX()+64 >= enemy.getX())&&(player.getX()<= enemy.getX()+64)){
                    game.setScreen(new GameoverScreen(game));
                }
            }

            for (Boom boom:booms){

                if(((enemy.getY() >= boom.getY())&&(enemy.getY() <= boom.getY()+64)) || ((enemy.getY()+64 >= boom.getY())&&(enemy.getY()+64 <= boom.getY()+64))){

                    if ((enemy.getX()+64 >= boom.getX())&&(enemy.getX()<= boom.getX()+64)){
                        enemys.remove(enemy);
                    }
                }

                if (enemys.isEmpty()){
                    game.setScreen(new WinScreen(game));

                }

            }


        }

       for (Boom boom:booms){

            if(((player.getY() >= boom.getY())&&(player.getY() <= boom.getY()+64)) || ((player.getY()+64 >= boom.getY())&&(player.getY()+64 <= boom.getY()+64))){

                if ((player.getX()+64 >= boom.getX())&&(player.getX()<= boom.getX()+64)){
                    game.setScreen(new GameScreen(game));
                }
            }

            try{
                for (Bblock bblock : bblockslist){
                    if(((bblock.getY() >= boom.getY())&&(bblock.getY() <= boom.getY()+64)) || ((bblock.getY()+64 >= boom.getY())&&(bblock.getY()+64 <= boom.getY()+64))){

                        if ((bblock.getX()+64 >= boom.getX())&&(bblock.getX()<= boom.getX()+64)){
                            bblockslist.remove(bblock);
                        }
                    }
                }
            }catch (Exception e){

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

        batch.dispose();
        stage.dispose();
        game.dispose();
    }
}





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

    SpriteBatch batch;
    OrthographicCamera camera;
    Texture bg;
    Texture BombImg;
    Texture BoomImg;
    Texture EnemyImg;
    Texture BblockImg;


    Array<Rectangle> bombs;
    Array<Rectangle> booms;

    LinkedList<Rectangle> Bblocks;
    LinkedList<Rectangle> enemys;
    Array<Ublock> ublockslist;

    long bombstime;
    long boomstime;

    float i=0;
    float j=0;

    int k = 0;
    int l = 0;

    float x = 300;
    float y = 600;

    float boomX;
    float boomY;

    boolean moveableW =true ,moveableA = true,moveableS = true ,moveableD = true;

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

        BombImg = new Texture("bomb.png");
        BoomImg = new Texture("boom.png");
        bg = new Texture("bg.png");
        EnemyImg = new Texture("enemy.png");
        BblockImg = new Texture("block.png");


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 1024);
        stage = new Stage(new StretchViewport(1280, 1024));
        Gdx.input.setInputProcessor(stage);



        bombs = new Array<Rectangle>();
        booms = new Array<Rectangle>();

        Bblocks = new LinkedList<Rectangle>();
        enemys = new LinkedList<Rectangle>();


    }


    /**
     * สร้างระเบิด
     */
    private void spawnbomb() {

        if (i<1){
            Rectangle bomb = new Rectangle();
            bomb.x = player.getX();
            bomb.y = player.getY();
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


    private void checkCollisions(Rectangle player, Rectangle ublock){

        if (player.overlaps(ublock)){
            if(player.getX() + 64 > ublock.getX() && player.getX() < ublock.getX()){
                //collision with right side of bucket

                moveableD = false;
                moveableA = true;

            }

            if(player.getX() < ublock.x + 64 && player.getX() > ublock.x){
                //collision with left side of bucket

                moveableA = false;

            }

            if(player.getY() + 64 > ublock.y && player.getY() < ublock.y){
                //collision with top side of bucket

                moveableW = false;
            }

            if(player.getY() < ublock.y + 64 && player.getY() > ublock.y){
                //collision with bottom side of bucket

                moveableS = false;

            }

        }

    }


/* private void checkCollisions(Rectangle player, Rectangle ublock){

        if (player.overlaps(ublock)){
            if(player.getX() + 64 > ublock.x && player.getX() < ublock.x){
                //collision with right side of bucket
                moveableD = false;

            }
            if(player.getX() < ublock.x + 64 && player.getX() > ublock.x){
                //collision with left side of bucket
                moveableA = false;


            }

            if(player.getY() + 64 > ublock.y && player.getY() < ublock.y){
                //collision with top side of bucket
                moveableW = false;

            }
            if(player.getY() < ublock.y + 64 && player.getY() > ublock.y){
                //collision with bottom side of bucket
                moveableS = false;


            }

        }
    }

 */






    @Override
    public void show() {
        ublockslist = new Array<Ublock>();
        batch = new SpriteBatch();
        spawnenemy(200,200);
        spawnenemy(500,500);



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

        player = new Player(x,y);




        batch.begin();

        batch.draw(bg,0,0);


      /*  Rectangle Manrec = new Rectangle(player.getX(),player.getY(),player.getWidth(),player.getHeight());

        for (Ublock ublock : ublockslist) {
            ublock.Draw(batch);
            if (Manrec.overlaps(ublock.rectangle())){
                if(player.getX() + 64 > ublock.getX() && player.getX() < ublock.getX()){
                    //collision with right side of bucket
                }
                if(player.getX() < ublock.getX() + 64 && player.getX() > ublock.getX()){
                    //collision with left side of bucket
                }
                if(player.getY() + 64 > ublock.getY() && player.getY() < ublock.getY()){
                    //collision with top side of bucket
                }
                if(player.getY() < ublock.getY() + 64 && player.getY() > ublock.getY()){
                    //collision with bottom side of bucket
                }

            }
        }

        */

        for (Ublock ublock : ublockslist) {
            ublock.Draw(batch);

            checkCollisions(player.rectangle(),ublock.rectangle());

        }



        player.Draw(batch);


        for(Rectangle bomb: bombs) {
            batch.draw(BombImg, bomb.x, bomb.y,bomb.width,bomb.height);
        }
        for(Rectangle boom: booms) {
            batch.draw(BoomImg, boom.x, boom.y,boom.width,boom.height);
        }

        for(Rectangle enemy: enemys) {
            batch.draw(EnemyImg, enemy.x, enemy.y,enemy.width,enemy.height);
        }

        for (Rectangle Bblock : Bblocks){
            batch.draw(BblockImg,Bblock.x,Bblock.y,Bblock.width,Bblock.height);
        }

        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {

            if (moveableW == true)
            y += 500 * Gdx.graphics.getDeltaTime();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            if (moveableA == true)
            x -= 500 * Gdx.graphics.getDeltaTime();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

            if (moveableS == true)
            y -= 500 * Gdx.graphics.getDeltaTime();

        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

            if (moveableD == true){
                x += 500 * Gdx.graphics.getDeltaTime();
            }


        }




        if(player.getX() < 0) x = 0;
        if(player.getX()+64 > 1280) x = 1280-64;

        if(player.getY() < 0) y = 0;
        if(player.getY()+64 > 1024) y = 1024-64;



        for(Rectangle enemy: enemys) {
            if(((player.getY() >= enemy.y)&&(player.getY() <= enemy.y+64)) || ((player.getY()+64 >= enemy.y)&&(player.getY()+64 <= enemy.y+64))){

                if ((player.getX()+64 >= enemy.x)&&(player.getX()<= enemy.x+64)){
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

            if(((player.getY() >= boom.y)&&(player.getY() <= boom.y+64)) || ((player.getY()+64 >= boom.y)&&(player.getY()+64 <= boom.y+64))){

                if ((player.getX()+64 >= boom.x)&&(player.getX()<= boom.x+64)){
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





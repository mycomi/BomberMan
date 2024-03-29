package com.bomberman.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenuScreen implements Screen {

    final BomberMan game;

    OrthographicCamera camera;

    Music NERV;
    private Stage stage;
    private Skin skin;
    SpriteBatch batch;
    Texture bg;


    public MainMenuScreen(final BomberMan gam) {
        game = gam;
        /**
         * background
         */
        bg = new Texture("bg.jpg");

        /**
         * background music
         */
        NERV = Gdx.audio.newMusic(Gdx.files.internal("NERV.mp3"));
        NERV.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 1024);

        stage = new Stage(new StretchViewport(1280,1024));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        /**
         * start button
         */
        TextButton buttonStart = new TextButton("START",skin);
        buttonStart.setWidth(200);
        buttonStart.setHeight(50);
        buttonStart.setPosition(1280/2-200/2,500);
        stage.addActor(buttonStart);

        /**
         * click start button
         */
        buttonStart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                super.clicked(event,x,y);
                NERV.dispose();
                game.setScreen(new GameScreen(game));
               // game.setScreen(new GameScreen());
            }
        });

        /**
         * exit button
         */
        TextButton buttonExit = new TextButton("Exit",skin);
        buttonExit.setWidth(200);
        buttonExit.setHeight(50);
        buttonExit.setPosition(1280/2-200/2,250);
        stage.addActor(buttonExit);

        /**
         * click exit button
         */
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                super.clicked(event,x,y);
                Gdx.app.exit();
                // game.setScreen(new GameScreen());
            }
        });


    }

    @Override
    public void show() {

        /**
         * play background music
         */
        NERV.play();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        batch = new SpriteBatch();

        batch.begin();
        batch.draw(bg,0,0);
        batch.end();
        stage.draw();



        /*
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to BomberMan!!! ", 300, 300);
        game.font.draw(game.batch, "Press Enter keys to begin!", 300, 199);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }

         */

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);

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

package com.bomberman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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

public class GameoverScreen implements Screen {

    final BomberMan game;

    OrthographicCamera camera;

    private Stage stage;
    private Skin skin;
    SpriteBatch batch;
    Texture bg;

    public GameoverScreen(final BomberMan gam) {
        game = gam;

        bg = new Texture("gameover.jpg");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 1024);

        stage = new Stage(new StretchViewport(1280,1024));
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton buttonStart = new TextButton("Restart",skin);
        buttonStart.setWidth(200);
        buttonStart.setHeight(50);
        buttonStart.setPosition(1280/2-200/2,300);
        stage.addActor(buttonStart);

        buttonStart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event,float x,float y){
                super.clicked(event,x,y);
                game.setScreen(new GameScreen(game));
            }
        });


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());


        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        batch = new SpriteBatch();

        batch.begin();
        batch.draw(bg,0,0);

        batch.end();
        stage.draw();

   /*     if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
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

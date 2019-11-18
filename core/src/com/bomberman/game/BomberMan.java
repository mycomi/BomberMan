package com.bomberman.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class BomberMan extends Game {
	SpriteBatch batch;
	BitmapFont font;

/*	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

 */

	/**
	 * create and set variable
	 */

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}


	@Override
	public void dispose () {
		batch.dispose();

		font.dispose();

	}



}

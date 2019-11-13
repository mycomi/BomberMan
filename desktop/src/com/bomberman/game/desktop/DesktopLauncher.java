package com.bomberman.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bomberman.game.BomberMan;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title ="BombMap";
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new BomberMan(), config);


	}
}

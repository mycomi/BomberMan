package com.bomberman.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bomberman.game.BomberMan;

import java.util.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title ="BombMap";
		config.width = 1280;
		config.height = 1024;
		new LwjglApplication(new BomberMan(), config);


    }
}

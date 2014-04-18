package com.accurve.flappybirdclone;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Flappy Bird Clone";
		cfg.depth = 0; 
		cfg.width = 480 / 4 * 3;
		cfg.height = 800 / 4 * 3;

		Game game = new Game();
		
		new LwjglApplication(game.applicationListener, cfg);
	}
}

package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.CallModuls.ShowDialog;
import com.mygdx.game.Screens.FirstScreen;
import com.mygdx.game.Screens.SecondScreen;
import com.mygdx.game.Screens.ThirdScreen;

public class Main extends Game {
	public static boolean isRunning = false;
	public static ShowDialog dialog;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new FirstScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}

package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.CallModuls.ShowDialog;
import com.mygdx.game.Main;

import javax.swing.JOptionPane;

public class DesktopLauncher implements ShowDialog {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width  = (int) (Main.WIDTH * 1.2);
		config.height = (int) (Main.HEIGHT * 1.2);
		config.samples = 4;

		Main.dialog = new DesktopLauncher();

		new LwjglApplication(new Main(), config);
	}

	@Override
	public void showDialog(String message, String buttonText) {
		JOptionPane.showMessageDialog(null, message);
		Main.isRunning = false;
	}
}

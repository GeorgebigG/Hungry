package com.mygdx.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.CallModuls.ShowDialog;
import com.mygdx.game.Main;

public class AndroidLauncher extends AndroidApplication implements ShowDialog {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		Main.dialog = this;

		initialize(new Main(), config);
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setMessage("Are you sure want to exit?")
				.setNegativeButton("No", null).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				finish();
			}
		}).create().show();
	}

	@Override
	public void showDialog(final String message, final String buttonText) {
		this.handler.post(new Runnable() {
			@Override
			public void run() {
				new AlertDialog.Builder(AndroidLauncher.this).setMessage(message)
						.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								Main.isRunning = false;
							}
						}).setCancelable(false).create().show();
			}
		});
	}
}

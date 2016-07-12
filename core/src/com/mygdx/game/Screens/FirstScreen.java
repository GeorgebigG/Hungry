package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Enemies.Enemy;
import com.mygdx.game.Enemies.MicroGreenCell;
import com.mygdx.game.Enemies.NanoRedCell;
import com.mygdx.game.Main;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by gio on 7/8/16.
 */
public class FirstScreen extends Screen {

    @Override
    public void dispose() {
        super.dispose();
        for (Enemy cell : smallEnemies) cell.dispose();
        for (Enemy cell : largeEnemies) cell.dispose();
    }

    public FirstScreen(Main game) {
        super(game);
    }

    public void update(float dt) {
        handleInput();

        for (Enemy cell : smallEnemies) cell.update(player, allEnemies);
        for (Enemy cell : largeEnemies) cell.update(player, allEnemies);

        if (TimeUtils.nanoTime() - lastTimeCreatedSmallEnemy >= smallEnemySpawnTime)
            addSmallEnemy(new NanoRedCell(createCordinats(smallCellRadius), smallCellRadius));
        if (TimeUtils.nanoTime() - lastTimeCreatedLargeEnemy >= largeEnemySpawnTime)
            addLargeEnemy(new MicroGreenCell(createCordinats(largeCellRadius), largeCellRadius));

        if (smallEnemyIntersects() || largeEnemyIntersect()) {
            if (player.width > playerRadiusAtTheEnd) game.setScreen(new SecondScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        if (!Main.isRunning)
            update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(gamecam.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        drawBackground();
        drawPlayer();

        drawSmallEnemies();
        drawLargeEnemies();

        renderer.end();

        stage.act();
        stage.draw();
    }

    public void pause() {}
    public void hide() {}
    public void resume() {}
    public void show() {}
}

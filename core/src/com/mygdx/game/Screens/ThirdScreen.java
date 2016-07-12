package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Enemies.Enemy;
import com.mygdx.game.Enemies.PinkCell;
import com.mygdx.game.Enemies.SmallOrangeCell;
import com.mygdx.game.Main;

/**
 * Created by gio on 7/11/16.
 */

public class ThirdScreen extends Screen {

    public ThirdScreen(Main game) {
        super(game);
        playerSpeed += 2;
    }

    @Override
    public void update(float dt) {
        handleInput();
        if (TimeUtils.nanoTime() - lastTimeCreatedSmallEnemy >= smallEnemySpawnTime)
            addSmallEnemy(new PinkCell(createCordinats(smallCellRadius), smallCellRadius));
        if (TimeUtils.nanoTime() - lastTimeCreatedLargeEnemy >= largeEnemySpawnTime)
            addLargeEnemy(new SmallOrangeCell(createCordinats(largeCellRadius), largeCellRadius));

        for (Enemy cell : smallEnemies) cell.update(player, allEnemies);
        for (Enemy cell : largeEnemies) cell.update(player, allEnemies);

        if (smallEnemyIntersects() || largeEnemyIntersect()) {
            if (player.width > playerRadiusAtTheEnd) {
                callShowDialog("Congratulation you win!", "Ok");
                System.exit(0);
            }
        }
    }

    @Override
    public void render(float delta) {
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

        update(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
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
    public void show() {

    }
}

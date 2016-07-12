package com.mygdx.game.Enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by gio on 7/10/16.
 */
public class PinkCell extends Enemy {

    public static final Color PINK_CELL_COLOR = Color.PINK;

    public PinkCell(Vector2 position, int radius) {
        super(position, radius, 1.5f, PINK_CELL_COLOR);
    }

    @Override
    public void dispose() {}
}

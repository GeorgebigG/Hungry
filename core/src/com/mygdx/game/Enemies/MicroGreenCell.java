package com.mygdx.game.Enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by gio on 7/9/16.
 */
public class MicroGreenCell extends Enemy {

    public static final Color MICRO_GREEN_CELL_COLOR = Color.GREEN;

    public MicroGreenCell(Vector2 position, int radius) {
        super(position, radius, 1, MICRO_GREEN_CELL_COLOR);
    }

    @Override
    public void dispose() {}
}

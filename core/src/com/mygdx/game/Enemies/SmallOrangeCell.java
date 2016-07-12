package com.mygdx.game.Enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by gio on 7/11/16.
 */
public class SmallOrangeCell extends Enemy {

    public static final Color SMALL_ORANGE_CELL_COLOR = Color.ORANGE;

    public SmallOrangeCell(Vector2 position, int radius) {
        super(position, radius, 1.5f, SMALL_ORANGE_CELL_COLOR);
    }

    @Override
    public void dispose() {}
}

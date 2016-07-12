package com.mygdx.game.Enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by gio on 7/8/16.
 */
public class NanoRedCell extends Enemy {

    public static final Color NANO_RED_CELL_COLOR = Color.RED;

    public NanoRedCell(Vector2 position, int radius) {
        super(position, radius, 0.5f, NANO_RED_CELL_COLOR);
    }

    @Override
    public void dispose() {}
}

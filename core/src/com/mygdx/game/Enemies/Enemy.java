package com.mygdx.game.Enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by gio on 7/8/16.
 */
public abstract class Enemy {
    public Rectangle enemyBody;
    public float speed;
    public Color color;

    public Enemy(Vector2 position, int radius, float speed, Color color) {
        enemyBody = new Rectangle(position.x, position.y, radius, radius);
        this.color = color;
        this.speed = speed;
    }

    public boolean intersect(Rectangle player) {
        if (enemyBody.overlaps(player)) return true;
        return false;
    }

    public void update(Rectangle player, Array<Enemy> allEnemies) {
        move(player, allEnemies);
    }

    public void move(Rectangle player, Array<Enemy> allEnemies) {
        if (player.width >= enemyBody.width && speed < 0) speed = -speed;
        else if (player.width < enemyBody.width && speed > 0) speed = -speed;

        if (enemyBody.x > player.x) {
            enemyBody.x += speed;
            for (Enemy cell : allEnemies) {
                if (cell == this) continue;
                if (intersect(cell.enemyBody)) enemyBody.x -= speed;
            }
        }
        else if (enemyBody.x < player.x) {
            enemyBody.x -= speed;
            for (Enemy cell : allEnemies) {
                if (cell == this) continue;
                if (intersect(cell.enemyBody)) enemyBody.x += speed;
            }
        }
        if (enemyBody.y > player.y) {
            enemyBody.y += speed;
            for (Enemy cell : allEnemies) {
                if (cell == this) continue;
                if (intersect(cell.enemyBody)) enemyBody.y -= speed;
            }
        }
        else if (enemyBody.y < player.y) {
            enemyBody.y -= speed;
            for (Enemy cell : allEnemies) {
                if (cell == this) continue;
                if (intersect(cell.enemyBody)) enemyBody.y += speed;
            }
        }
    }

    public abstract void dispose();
}


















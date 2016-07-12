package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Enemies.Enemy;
import com.mygdx.game.Enemies.PinkCell;
import com.mygdx.game.Main;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by gio on 7/9/16.
 */
public abstract class Screen implements com.badlogic.gdx.Screen {

    protected Main game;
    protected ShapeRenderer renderer;
    protected SpriteBatch batch;

    protected Viewport gameport;
    protected OrthographicCamera gamecam;

    protected Rectangle player;
    protected int playerSpeed = 5;
    protected Color playerColor;

    protected Stage stage;
    private Label ballSize;

    private Image up, down, left, right;

    protected int smallCellRadius = 10;
    protected int largeCellRadius = 55;

    protected int playerRadiusAtStart = 20;
    protected int playerRadiusAtTheEnd = 60;

    protected Array<Enemy> smallEnemies;
    protected Array<Enemy> largeEnemies;
    protected Array<Enemy> allEnemies;

    protected long lastTimeCreatedSmallEnemy;
    protected long lastTimeCreatedLargeEnemy;

    protected static final long smallEnemySpawnTime = 1000000000l;
    protected static final long largeEnemySpawnTime = 8000000000l;

    private enum Shape { CIRCLE, SQUARE; }
    private Shape shape = Shape.CIRCLE;

    public Screen(Main game) {
        this.game = game;
        batch = game.batch;
        renderer = new ShapeRenderer();

        Gdx.gl.glClearColor(0, 0, 0, 1);

        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        gameport = new FitViewport(Main.WIDTH, Main.HEIGHT, gamecam);

        player = new Rectangle(gamecam.viewportWidth / 2, gamecam.viewportHeight / 2, playerRadiusAtStart, playerRadiusAtStart);
        playerColor = new Color(1, 1, 0, 1);

        stage = new Stage(new FitViewport(Main.WIDTH, Main.HEIGHT, new OrthographicCamera()), game.batch);
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        ballSize = new Label("Size: " + player.width, new Label.LabelStyle(new BitmapFont(), new Color(0xffffff)));

        table.add(ballSize).expandX().pad(10);

        //createMoveButtons();
        stage.addActor(table);

        smallEnemies = new Array<Enemy>();
        largeEnemies = new Array<Enemy>();
        allEnemies = new Array<Enemy>();

        lastTimeCreatedSmallEnemy = TimeUtils.nanoTime();
        lastTimeCreatedLargeEnemy = TimeUtils.nanoTime();
    }

    public abstract void update(float dt);

    protected void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) player.y -= playerSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) player.y += playerSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) player.x -= playerSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) player.x += playerSpeed;

        if (player.x < 0) player.x = 0;
        else if (player.x > Main.WIDTH - player.width) player.x = Main.WIDTH - player.width;
        if (player.y < 0) player.y = 0;
        else if (player.y > Main.HEIGHT - player.height) player.y = Main.HEIGHT - player.height;
    }

    protected void callShowDialog(String message, String buttonText) {
        Main.isRunning = true;
        game.dialog.showDialog(message, buttonText);
    }

    protected void drawBackground() {
        renderer.setColor(0, 0, 1, 1);
        renderer.rect(0, 0, Main.WIDTH, Main.HEIGHT);
    }

    @Override
    public void dispose() {
        game.dispose();
        renderer.dispose();
        stage.dispose();
    }

    protected void drawPlayer() {
        renderer.setColor(playerColor);
        if (shape == Shape.SQUARE) {
            if (player.width > 0)
                renderer.rect(player.x, player.y, player.width, player.height);
        }
        else
            if  (player.width > 0)
                renderer.circle(getDrawingPlayerPosition().x, getDrawingPlayerPosition().y, getDrawingPlayerPosition().width);
    }

    protected void drawSmallEnemies() {
        for (Enemy cell : smallEnemies) {
            renderer.setColor(cell.color);
            if (shape == Shape.CIRCLE)
                renderer.circle(getCirclePosition(cell.enemyBody).x, getCirclePosition(cell.enemyBody).y, getCirclePosition(cell.enemyBody).width);
            else
                renderer.rect(cell.enemyBody.x, cell.enemyBody.y, cell.enemyBody.width, cell.enemyBody.height);
        }
    }

    protected void drawLargeEnemies() {
        for (Enemy cell : largeEnemies) {
            renderer.setColor(cell.color);
            if (shape == Shape.CIRCLE)
                renderer.circle(getCirclePosition(cell.enemyBody).x, getCirclePosition(cell.enemyBody).y, getCirclePosition(cell.enemyBody).width);
            else
                renderer.rect(cell.enemyBody.x, cell.enemyBody.y, cell.enemyBody.width, cell.enemyBody.height);
        }
    }

    protected Rectangle getDrawingPlayerPosition() {
        return new Rectangle(player.x + player.width / 2, player.y + player.height / 2, player.width / 2, player.height / 2);
    }

    protected Rectangle getCirclePosition(Rectangle r) {
        return new Rectangle(r.x + r.width / 2, r.y + r.height / 2, r.width / 2, r.height / 2);
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
    }

    protected boolean smallEnemyIntersects() {
        Iterator<Enemy> i = smallEnemies.iterator();
        while (i.hasNext()) {
            Enemy cell = i.next();
            if (cell.intersect(player)) {
                player.width += cell.enemyBody.width / 4;
                player.height += cell.enemyBody.width / 4;
                i.remove();

                ballSize.setText("Size: " + player.width);

                return true;
            }

            if (cell.enemyBody.x <= 0 - cell.enemyBody.width) { i.remove(); return true; }
            if (cell.enemyBody.x >= Main.WIDTH) { i.remove(); return true; }
            if (cell.enemyBody.y <= 0 - cell.enemyBody.height) { i.remove(); return true; }
            if (cell.enemyBody.y >= Main.HEIGHT) { i.remove(); return true; }
        }

        return false;
    }

    protected boolean largeEnemyIntersect() {
        Iterator<Enemy> k = largeEnemies.iterator();
        while (k.hasNext()) {
            Enemy cell = k.next();
            if (cell.intersect(player)) {
                if (playerIsBigger(cell.enemyBody)) {
                    player.width += cell.enemyBody.width / 4;
                    player.height += cell.enemyBody.width / 4;
                    k.remove();
                    return true;
                } else {
                    player.width -= cell.enemyBody.width / 4;
                    player.height -= cell.enemyBody.width / 4;
                }

                if (player.width <= playerRadiusAtStart) {
                    callShowDialog("Game Over! please try again.", "Ok");
                    game.setScreen(new FirstScreen(game));
                }

                ballSize.setText("Size: " + player.width);
            }

            if (cell.enemyBody.x <= 0 - cell.enemyBody.width) { k.remove(); return true; }
            if (cell.enemyBody.x >= Main.WIDTH) { k.remove(); return true; }
            if (cell.enemyBody.y <= 0 - cell.enemyBody.height) { k.remove(); return true; }
            if (cell.enemyBody.y >= Main.HEIGHT){ k.remove(); return true; }
        }

        return false;
    }

    public void addSmallEnemy(Enemy cell) {
        smallEnemies.add(cell);
        allEnemies.add(cell);
        lastTimeCreatedSmallEnemy = TimeUtils.nanoTime();
    }

    public void addLargeEnemy(Enemy cell) {
        largeEnemies.add(cell);
        allEnemies.add(cell);
        lastTimeCreatedLargeEnemy = TimeUtils.nanoTime();
    }

    public Vector2 createCordinats(int radius) {
        Random rand = new Random();
        int x = rand.nextInt(Main.WIDTH - radius), y = rand.nextInt(Main.HEIGHT - radius);
        Rectangle rect = new Rectangle(x, y, radius, radius);
        for (Enemy cell : allEnemies) {
            while (rect.overlaps(cell.enemyBody)) {
                x = rand.nextInt(Main.WIDTH - radius);
                y = rand.nextInt(Main.HEIGHT - radius);
                rect = new Rectangle(x, y, radius, radius);
            }
        }

        while (rect.overlaps(player)) {
            x = rand.nextInt(Main.WIDTH - radius);
            y = rand.nextInt(Main.HEIGHT - radius);
            rect = new Rectangle(x, y, radius, radius);
        }

        return new Vector2(x, y);
    }

    public void createMoveButtons() {
        up = new Image(new Texture("up.png"));
        up.setSize(50, 50);
        up.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.y += playerSpeed;
                return true;
            }
        });
        down = new Image(new Texture("down.png"));
        down.setSize(50, 50);
        down.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.y -= playerSpeed;
                return true;
            }
        });
        left = new Image(new Texture("left.png"));
        left.setSize(50, 50);
        left.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.x -= playerSpeed;
                return true;
            }
        });
        right = new Image(new Texture("right.png"));
        right.setSize(50, 50);
        right.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.x += playerSpeed;
                return true;
            }
        });
    }

    protected boolean playerIsBigger(Rectangle another) {
        if (player.width >= another.width) {
            return true;
        }

        return  false;
    }
}

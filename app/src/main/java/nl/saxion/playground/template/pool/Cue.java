/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type Cue.
 */
public class Cue extends Entity {

    private float newX, newY, x, y;
    private boolean visible;
    private Paint whitePaint;
    private Bitmap bitmap;
    private Game game;


    /**
     * Instantiates a new Cue.
     *
     * @param visible the visible
     * @param game    the game
     */
    public Cue(boolean visible, Game game) {
        this.visible = visible;
        this.game = game;

    }

    private static double prevAngle = 0;

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);

        return rotatedBitmap;
    }

    @Override
    public void draw(GameView gameView) {
            gv.getCanvas().drawLine(this.x, this.y, this.newX, this.newY, game.whitePaint);
        }
    }

    /**
     * Gets visible.
     *
     * @return the visible
     */
    public boolean getVisible() {
        return this.visible;
    }

    /**
     * Sets visible.
     *
     * @param visible the visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public float getX() {
        return this.x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public float getY() {
        return this.y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Sets new x.
     *
     * @param newX the new x
     */
    public void setNewX(float newX) {
        this.newX = newX;
    }

    /**
     * Sets new y.
     *
     * @param newY the new y
     */
    public void setNewY(float newY) {
        this.newY = newY;
    }
}

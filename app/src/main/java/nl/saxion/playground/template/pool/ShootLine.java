/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */


package nl.saxion.playground.template.pool;

import android.graphics.Color;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type Shoot line.
 */
public class ShootLine extends Entity {

    private float newX, newY, x, y;
    private boolean visible;
    private Game game;

    /**
     * Instantiates a new Shoot line.
     *
     * @param visible the visible
     * @param game    the game
     */
    public ShootLine(boolean visible, Game game) {
        this.visible = visible;
        this.game = game;
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void draw(GameView gv) {
        if (visible) {
            gv.getCanvas().drawLine(this.x, this.y, this.newX, this.newY, game.redPaint);
        }
    }

    /**
     * Reflect boolean.
     *
     * @return the boolean
     */
    public boolean reflect() {
        if (this.newX > game.getPlayWidth() || this.newX <= 0) {
            return true;
        }
        if (this.newY > game.getPlayHeight() || this.newY <= 0) {
            return true;
        }
        return false;
    }

    public void setColor(float r, float g, float b) {
    game.redPaint.setColor(Color.argb(255, (int)r, (int)g, (int)b));
    }

    public float getNewX() {
        return this.newX;
    }

    public float getNewY() {
        return this.newY;
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
     * Sets y.
     *
     * @param y the y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Get visible boolean.
     *
     * @return the boolean
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

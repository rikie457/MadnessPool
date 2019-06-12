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

    private boolean visible;
    private Game game;
    private Vector2 vector2 = new Vector2(), newvector2 = new Vector2();

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
            gv.getCanvas().drawLine((float) this.vector2.getX(), (float) this.vector2.getY(), (float) this.newvector2.getX(), (float) this.newvector2.getY(), game.redPaint);
        }
    }

    /**
     * Reflect boolean.
     *
     * @return the boolean
     */
    public boolean reflect() {
        if (this.newvector2.getX() > game.getPlayWidth() || this.newvector2.getX() <= 0) {
            return true;
        }
        if (this.newvector2.getY() > game.getPlayHeight() || this.newvector2.getY() <= 0) {
            return true;
        }
        return false;
    }

    public void setColor(float r, float g, float b) {
        game.redPaint.setColor(Color.argb(255, (int) r, (int) g, (int) b));
    }

    public Vector2 getVector2() {
        return vector2;
    }


    public Vector2 getNewvector2() {
        return newvector2;
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

}

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
 * The type Hole.
 */
public class Hole extends Entity {
    private double x;
    private double y;
    private double radius;
    private Game game;

    /**
     * Instantiates a new Hole.
     *
     * @param game   the game
     * @param x      the x
     * @param y      the y
     * @param radius the radius
     */
    public Hole(Game game, double x, double y, double radius) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.radius = radius;
        game.transparent.setColor(Color.TRANSPARENT);
    }

    @Override
    public int getLayer() {
        return 1;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Gets radius hole.
     *
     * @return the radius hole
     */
    public double getRadiusHole() {
        return radius;
    }

    @Override
    public void draw(GameView gv) {
        gv.getCanvas().drawCircle((float) this.x, (float) this.y, (float) this.radius, game.transparent);
    }
}

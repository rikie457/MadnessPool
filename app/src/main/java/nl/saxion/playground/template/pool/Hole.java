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
    private Vector2 vector2 = new Vector2();
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
        this.vector2.set(x, y);
        this.radius = radius;
        Game.transparent.setColor(Color.TRANSPARENT);
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public Vector2 getVector2() {
        return vector2;
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
        gv.getCanvas().drawCircle((float) this.vector2.getX(), (float) this.vector2.getY(), (float) this.radius, Game.transparent);
    }
}

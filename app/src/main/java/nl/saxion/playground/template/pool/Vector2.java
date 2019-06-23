/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool;

import java.io.Serializable;

/**
 * The Vector2.
 * This allows to store the x y in a single point
 * this is useful if you want to calculate with 2 points or store them;
 */
public class Vector2 implements Serializable {
    private double x, y;

    /**
     * Instantiates a new Vector2.
     *
     * @param x the x
     * @param y the y
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Instantiates a new Vector 2.
     *
     * @param v2 the v 2
     */
    public Vector2(Vector2 v2) {
        this.x = v2.getX();
        this.y = v2.getY();
    }

    /**
     * Instantiates a new Vector2.
     */
    public Vector2() {

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
     * Sets x.
     *
     * @param x the x
     */
    public void setX(double x) {
        this.x = x;
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
     * Sets y.
     *
     * @param y the y
     */
    public void setY(double y) {
        this.y = y;
    }


    /**
     * Set.
     *
     * @param x the x
     * @param y the y
     */
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Add x.
     *
     * @param x the x
     */
    public void addX(double x) {
        this.x += x;
    }

    /**
     * Add y.
     *
     * @param y the y
     */
    public void addY(double y) {
        this.y += y;
    }

    /**
     * Add.
     *
     * @param x the x
     * @param y the y
     */
    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Add.
     *
     * @param xy the xy
     */
    public void add(double xy) {
        this.x += xy;
        this.y += xy;
    }
}

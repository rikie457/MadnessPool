/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool;

import java.io.Serializable;

/**
 * The type Vector2.
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

    public Vector2(Vector2 v2) {
        this.x = v2.getX();
        this.y = v2.getY();
    }

    /**
     * Instantiates a new Vector2.
     */
    public Vector2() {

    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void addX(double x) {
        this.x += x;
    }

    public void addY(double y) {
        this.y += y;
    }

    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     *
      * @param xy the value to be added to both of these x and ys
     */
    public void add(double xy) {
        this.x += xy;
        this.y += xy;
    }
}

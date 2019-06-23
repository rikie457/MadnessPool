/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool;

import nl.saxion.playground.template.pool.balls.Ball;

/**
 * The type Utility.
 */
public class Utility {
    /**
     * Gets distance not squared.
     *
     * @param x1 the x 1
     * @param y1 the y 1
     * @param x2 the x 2
     * @param y2 the y 2
     * @return the distance not squared
     */
    public static double getDistanceNotSquared(double x1, double y1, double x2, double y2) {
        double xd = x1 - x2;
        double yd = y1 - y2;
        double distance = (xd * xd) + (yd * yd);
        return distance;
    }

    /**
     * Random double from range double.
     *
     * @param min the min
     * @param max the max
     * @return the double
     */
    public static double randomDoubleFromRange(double min, double max) {
        return (Math.random() * (max - min + 1) + min);
    }

    public static Vector2 getClosestPoint(Wall wall, Ball ball) {
        double x1 = wall.getVector2().getX();
        double y1 = wall.getVector2().getY();
        double x2 = wall.getEndVector2().getX();
        double y2 = wall.getEndVector2().getY();
        double bx = ball.getVector2().getX();
        double by = ball.getVector2().getY();


        double A = bx - x1;
        double B = by - y1;
        double C = x2 - x1;
        double D = y2 - y1;

        double dot = (A * C) + (B * D);
        double len_sqrt = (C * C) + (D * D);
        double param = -1;

        if (len_sqrt != 0) {
            param = dot / len_sqrt;
        }

        double cx;
        double cy;
        if (param < 0) {
            cx = x1;
            cy = y1;
        } else if (param > 1) {
            cx = x2;
            cy = y2;
        } else {
            cx = x1 + param * C;
            cy = y1 + param * D;
        }
        return new Vector2(cx, cy);
    }


    public static double getDistanceFromClosestPoint(Vector2 closestpoint, Ball ball) {
        double bx = ball.getVector2().getX();
        double by = ball.getVector2().getY();
        double cx = closestpoint.getX() - 10;
        double cy = closestpoint.getY() - 10;
        double xx = bx - cx;
        double yy = by - cy;
        return Math.sqrt((xx * xx) + (yy * yy));
    }


    public static int getRandIntInRange(int left, int right) {
        return (int) (left + (Math.random() * (right - left + 1)));
    }
}

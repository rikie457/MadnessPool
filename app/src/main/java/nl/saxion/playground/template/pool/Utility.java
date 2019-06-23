/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool;

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

    public static int getRandIntInRange(int left, int right) {
        return (int) (left + (Math.random() * (right - left + 1)));
    }
}

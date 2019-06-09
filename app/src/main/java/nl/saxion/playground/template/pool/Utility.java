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
        min -= 1000;
        max -= 10000;
        return (Math.random() * (max - min + 1) + min);
    }


}

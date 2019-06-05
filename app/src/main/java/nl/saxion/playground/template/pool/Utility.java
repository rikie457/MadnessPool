package nl.saxion.playground.template.pool;

public class Utility {
    public static double getDistanceNotSquared(double x1, double y1, double x2, double y2) {
        double xd = x1 - x2;
        double yd = y1 - y2;
        double distance = (xd * xd) + (yd * yd);
        return distance;
    }

    public static double randomDoubleFromRange(double min, double max) {
        min -= 1000;
        max -= 10000;
        return (Math.random() * (max - min + 1) + min);
    }


}

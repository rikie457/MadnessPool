package nl.saxion.playground.template.pool;

public class Utility {
    public static double getDistance(double x1, double y1, double x2, double y2) {
        double xd = getXDistance(x1, x2);
        double yd = getYDistance(y1, y2);
        double distance = (xd * xd) + (yd * yd);
        return distance;
    }

    public static double getXDistance(double x1, double x2) {
        return x1 - x2;

    }

    public static double getYDistance(double y1, double y2) {
        return y1 - y2;
    }

    public static double randomDoubleFromRange(double min, double max) {
        return (Math.random() * (max - min + 1) + min);
    }


}

package nl.saxion.playground.template.pool;

import java.math.BigInteger;
import java.util.ArrayList;

import nl.saxion.playground.template.pool.balls.Ball;

/**
 * The type Info.
 */
public class Info {

    /**
     * The constant wallcollisionCounter.
     */
    public static BigInteger wallcollisionCounter = BigInteger.valueOf(0);
    /**
     * The constant ballcollisionCounter.
     */
    public static BigInteger ballcollisionCounter = BigInteger.valueOf(0);
    /**
     * The constant refreshCounter.
     */
    public static BigInteger refreshCounter = BigInteger.valueOf(0);
    /**
     * The constant highestspeedx.
     */
    public static double highestspeedx, /**
     * The Highestspeedy.
     */
    highestspeedy;
    /**
     * The Minmass.
     */
    public double minmass, /**
     * The Maxmass.
     */
    maxmass, /**
     * The Ballcount.
     */
    ballcount;


    /**
     * Add to wall collision counter.
     */
    public static void addToWallCollisionCounter() {
        wallcollisionCounter = wallcollisionCounter.add(BigInteger.valueOf(1));
    }

    /**
     * Add to ball collision counter.
     */
    public static void addToBallCollisionCounter() {
        ballcollisionCounter = ballcollisionCounter.add(BigInteger.valueOf(1));
    }

    /**
     * Add to refresh counter.
     */
    public static void addToRefreshCounter() {
        refreshCounter = refreshCounter.add(BigInteger.valueOf(1));
    }

    /**
     * Update highest speed x.
     *
     * @param balllist the balllist
     */
    public static void updateHighestSpeedX(ArrayList<Ball> balllist) {
        for (int i = 0; i < balllist.size(); i++) {
            if (balllist.get(i).getSpeedX() > highestspeedx) {
                highestspeedx = balllist.get(i).getSpeedX();
            }
        }
    }

    /**
     * Update highest speed y.
     *
     * @param balllist the balllist
     */
    public static void updateHighestSpeedY(ArrayList<Ball> balllist) {
        for (int i = 0; i < balllist.size(); i++) {
            if (balllist.get(i).getSpeedY() > highestspeedy) {
                highestspeedy = balllist.get(i).getSpeedY();
            }
        }
    }

    /**
     * Gets lowest mass.
     *
     * @param balllist the balllist
     */
    public void getLowestMass(ArrayList<Ball> balllist) {
        double smallest = balllist.get(0).getMass();

        for (int i = 0; i < balllist.size(); i++) {
            if (balllist.get(i).getMass() < smallest) {
                smallest = balllist.get(i).getMass();
            }
        }
        this.minmass = smallest;
    }

    /**
     * Gets highest mass.
     *
     * @param balllist the balllist
     */
    public void getHighestMass(ArrayList<Ball> balllist) {
        double biggest = balllist.get(0).getMass();

        for (int i = 0; i < balllist.size(); i++) {
            if (balllist.get(i).getMass() > biggest) {
                biggest = balllist.get(i).getMass();
            }
        }
        this.maxmass = biggest;
    }

    /**
     * Gets ball count.
     *
     * @param balllist the balllist
     */
    public void getBallCount(ArrayList<Ball> balllist) {
        this.ballcount = balllist.size();
    }
}

package nl.saxion.playground.template.pool;

import java.math.BigInteger;
import java.util.ArrayList;

public class Info {

    public static BigInteger wallcollisionCounter = BigInteger.valueOf(0);
    public static BigInteger ballcollisionCounter = BigInteger.valueOf(0);
    public static BigInteger refreshCounter = BigInteger.valueOf(0);
    public static double highestspeedx, highestspeedy;
    public double minmass, maxmass, ballcount;


    public static void addToWallCollisionCounter() {
        wallcollisionCounter = wallcollisionCounter.add(BigInteger.valueOf(1));
    }

    public static void addToBallCollisionCounter() {
        ballcollisionCounter = ballcollisionCounter.add(BigInteger.valueOf(1));
    }

    public static void addToRefreshCounter() {
        refreshCounter = refreshCounter.add(BigInteger.valueOf(1));
    }

    public static void updateHighestSpeedX(ArrayList<Ball> balllist) {
        for (int i = 0; i < balllist.size(); i++) {
            if (balllist.get(i).getSpeedX() > highestspeedx) {
                highestspeedx = balllist.get(i).getSpeedX();
            }
        }
    }

    public static void updateHighestSpeedY(ArrayList<Ball> balllist) {
        for (int i = 0; i < balllist.size(); i++) {
            if (balllist.get(i).getSpeedY() > highestspeedy) {
                highestspeedy = balllist.get(i).getSpeedY();
            }
        }
    }

    public void getLowestMass(ArrayList<Ball> balllist) {
        double smallest = balllist.get(0).getMass();

        for (int i = 0; i < balllist.size(); i++) {
            if (balllist.get(i).getMass() < smallest) {
                smallest = balllist.get(i).getMass();
            }
        }
        this.minmass = smallest;
    }

    public void getHighestMass(ArrayList<Ball> balllist) {
        double biggest = balllist.get(0).getMass();

        for (int i = 0; i < balllist.size(); i++) {
            if (balllist.get(i).getMass() > biggest) {
                biggest = balllist.get(i).getMass();
            }
        }
        this.maxmass = biggest;
    }

    public void getBallCount(ArrayList<Ball> balllist) {
        this.ballcount = balllist.size();
    }

//    public void draw(Graphics g) {
//        g.setColor(Color.GREEN);
//        g.drawString("Frame: " + refreshCounter.toString(), 10, 10);
//        g.drawString("Wall Collisions: " + wallcollisionCounter.toString(), 10, 25);
//        g.drawString("Ball Collisions: " + ballcollisionCounter.toString(), 10, 40);
//        g.drawString("Amount of Balls: " + ballcount, 10, 55);
//        g.drawString("Lowest mass: " + minmass, 10, 70);
//        g.drawString("Highest mass: " + maxmass, 10, 85);
//        g.drawString("Highest speed X: " + highestspeedx, 10, 100);
//        g.drawString("Highest speed Y: " + highestspeedy, 10, 115);
//        g.drawString("\u00a9 Tycho Engberink 2018-" + LocalDate.now().getYear(), (int) Main.width - 180, (int) Main.height - 10);
//    }
}

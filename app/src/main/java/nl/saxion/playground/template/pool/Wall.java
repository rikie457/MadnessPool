package nl.saxion.playground.template.pool;

import android.view.MotionEvent;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.pool.balls.Ball;

public class Wall extends Entity {
    private double x, y;
    private double width, height;
    private int wallId;

    private static int wallIdCreate = 1;

    ArrayList<Ball> balls;
    ArrayList<Hole> holes;

    public Wall (ArrayList<Ball> balls, ArrayList<Hole> holes) {
        this.balls = balls;
        this.holes = holes;
        this.wallId = this.wallIdCreate;

        this.wallIdCreate++;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getWallId() {
        return wallId;
    }
}

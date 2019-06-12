package nl.saxion.playground.template.pool;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.pool.balls.Ball;

public class Wall extends Entity {
    private float x;
    private float y;
    private double height;
    private double width;

    ArrayList<Ball> balls;

    public Wall (ArrayList<Ball> balls) {
        this.balls = balls;
    }
}

package nl.saxion.playground.template.pool;

import java.util.ArrayList;

import nl.saxion.playground.template.pool.balls.Ball;

public class Player {
    private boolean isPlaying;
    private int balltype;
    private ArrayList<Ball> scoredBalls;

    public ArrayList<Ball> getScoredBalls() {
        return scoredBalls;
    }

    public void addBallToScoredBalls(Ball ball) {
        this.scoredBalls.add(ball);
    }
}

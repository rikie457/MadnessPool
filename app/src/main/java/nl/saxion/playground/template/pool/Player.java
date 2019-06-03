package nl.saxion.playground.template.pool;

import java.util.ArrayList;

import nl.saxion.playground.template.pool.balls.Ball;

public class Player {
    private int balltype = -1;
    private ArrayList<Ball> scoredballs = new ArrayList<>();



    public int getBalltype() {
        return balltype;
    }

    public void setBalltype(int balltype) {
        this.balltype = balltype;
    }

    public ArrayList<Ball> getScoredballs() {
        return scoredballs;
    }
}

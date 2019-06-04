package nl.saxion.playground.template.pool;

import java.util.ArrayList;

import nl.saxion.playground.template.pool.balls.Ball;

public class Player {
    private int balltype = -1;
    private ArrayList<Ball> scoredballs = new ArrayList<>();
    private int playerId;

    public Player (int playerId) {
        this.playerId = playerId;
    }

    public int getBalltype() {
        return balltype;
    }

    public void setBalltype(int balltype) {
        this.balltype = balltype;
    }

    public ArrayList<Ball> getScoredballs() {
        return scoredballs;
    }

    public void resetScoredballs() {
        scoredballs.clear();
    }

    public int getPlayerId() {
        return this.playerId;
    }
}

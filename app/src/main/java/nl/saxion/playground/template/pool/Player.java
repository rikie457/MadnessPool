package nl.saxion.playground.template.pool;

import java.util.ArrayList;

import nl.saxion.playground.template.pool.balls.Ball;

/**
 * The type Player.
 */
public class Player {
    private int balltype = -1;
    private ArrayList<Ball> scoredballs = new ArrayList<>();
    private int playerId;

    /**
     * Instantiates a new Player.
     *
     * @param playerId the player id
     */
    public Player (int playerId) {
        this.playerId = playerId;
    }

    /**
     * Gets balltype.
     *
     * @return the balltype
     */
    public int getBalltype() {
        return balltype;
    }

    /**
     * Sets balltype.
     *
     * @param balltype the balltype
     */
    public void setBalltype(int balltype) {
        this.balltype = balltype;
    }

    /**
     * Gets scoredballs.
     *
     * @return the scoredballs
     */
    public ArrayList<Ball> getScoredballs() {
        return scoredballs;
    }

    /**
     * Reset scoredballs.
     */
    public void resetScoredballs() {
        scoredballs.clear();
    }

    /**
     * Gets player id.
     *
     * @return the player id
     */
    public int getPlayerId() {
        return this.playerId;
    }
}

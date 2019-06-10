/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool;

import java.io.Serializable;
import java.util.ArrayList;

import nl.saxion.playground.template.pool.balls.Ball;

/**
 * The type Player.
 */
public class Player implements Serializable {
    private int balltype = -1;
    private ArrayList<Ball> scoredballs;
    private int playerId;

    /**
     * Instantiates a new Player.
     *
     * @param playerId the player id
     */
    public Player(int playerId) {
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
     * Sets scoredballs.
     *
     * @param scoredballs the scoredballs
     */
    public void setScoredballs(ArrayList<Ball> scoredballs) {
        this.scoredballs = scoredballs;
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

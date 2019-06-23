package nl.saxion.playground.template.pool.powerup;

import android.graphics.Color;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.Vector2;
import nl.saxion.playground.template.pool.balls.WhiteBall;

/**
 * The type Powerup.
 */
public abstract class Powerup extends Entity {

    private Game game;

    /**
     * The Vector 2.
     */
    protected Vector2 vector2 = new Vector2();
    private double radius;
    private WhiteBall ball;

    /**
     * The Invisable.
     */
    boolean invisable = false,

    /**
     * The Collected.
     */
    collected = false;

    /**
     * Instantiates a new Powerup.
     *
     * @param game the game
     * @param x    the x
     * @param y    the y
     * @param ball the ball
     */
    public Powerup(Game game, double x, double y, WhiteBall ball) {
        this.game = game;
        this.vector2.set(x, y);
        this.radius = 15f;
        this.ball = ball;
        Game.powerupPaint.setColor(Color.GREEN);
    }

    private void checkCollisionWithBall() {
        if(!invisable) {
            if (!game.getCueBallScored() && Math.sqrt(Utility.getDistanceNotSquared(this.vector2.getX(), this.vector2.getY(), this.ball.getVector2().getX() + this.ball.getRadius(), this.ball.getVector2().getY() + this.ball.getRadius())) - (30) <= 0 && !game.getCueBallInHand()) {
                resolveColission();
            }
        }
    }

    // the only powerup that is on a lower layer than this, is not really a powerup, it is the gravity well
    // and it resides on layer 1, because they look ugly as sin when they're painted over anything else than the background
    @Override
    public int getLayer() {
        return 5;
    }

    @Override
    public void tick() {
        checkCollisionWithBall();
    }

    @Override
    public void draw(GameView gv) {
        if (!invisable) {
            gv.getCanvas().drawCircle((float) this.vector2.getX(), (float) this.vector2.getY(), (float) this.radius, Game.powerupPaint);
        }
    }

    protected void removePowerup() {
        game.removeEntity(this);
        game.getPowerups().remove(this);
    }

    /**
     * Resolve colission.
     */
    protected void resolveColission() {

    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Create power up.
     */
    public void createPowerUp() {
    }
}

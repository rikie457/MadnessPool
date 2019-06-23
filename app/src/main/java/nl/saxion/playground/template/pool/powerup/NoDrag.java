package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

/**
 * The type No drag.
 */
public class NoDrag extends Powerup {
    private WhiteBall whiteBall;
    static private Bitmap bitmap;
    private Game game;
    private double x, y, radius;
    private int currentturn, intialturn;
    private boolean applied;

    /**
     * Instantiates a new No drag.
     *
     * @param game the game
     * @param x    the x
     * @param y    the y
     * @param ball the ball
     */
    public NoDrag(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
        this.x = x;
        this.y = y;
        this.radius = game.getPowerupsize();
    }

    /**
     * checks if a powerup is collected, if one is collected the powerup will stay active for 2 turns.
     */
    @Override
    public void tick() {
        super.tick();
        this.currentturn = game.getTurns();
        if (this.collected) {
            if (this.intialturn + 2 == this.currentturn) {
                applyDrag();
                removePowerup();
            } else {
                if (!this.applied) {
                    removeDrag();
                    this.applied = true;
                }
            }
        }
    }

    /**
     * Apply's the standard amount of drag to all balls.
     */

    public void applyDrag() {
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            double friction = ball.getFriction();
            ball.setFriction(friction * .9965);
        }
    }

    /**
     * Removes the drag of all balls.
     */

    public void removeDrag() {
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            ball.setFriction(.9999);
        }
    }

    /**
     * Collision handeler
     */

    public void resolveColission() {
        this.intialturn = game.getTurns();
        this.invisable = true;
        this.collected = true;
    }

    /**
     * Draws the powerup on the pool table.
     */

    public void draw(GameView gameView) {
        if (!invisable) {
            if (bitmap == null) {
                bitmap = gameView.getBitmapFromResource(R.drawable.nodrag);
            }
            gameView.drawBitmap(bitmap, (float) x, (float) y, (float) this.radius, (float) this.radius);
        }
    }

    /**
     * Creates the powerup on a random location on the pool table.
     */

    @Override
    public void createPowerUp() {
        NoDrag noDrag = new NoDrag(game, (float) Utility.randomDoubleFromRange(100, game.getWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall);
        game.getPowerups().add(noDrag);
        game.addEntity(noDrag);
    }
}
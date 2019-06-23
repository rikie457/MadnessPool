package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.WhiteBall;

/**
 * The type Gravity pocket.
 */
public class GravityPocket extends Powerup {
    /**
     * The Bitmap.
     */
    static Bitmap bitmap;
    private Game game;
    private WhiteBall whiteBall;
    private int currentturn, intialturn;
    private boolean applied;

    /**
     * Instantiates a new Gravity pocket.
     *
     * @param game the game
     * @param x    the x
     * @param y    the y
     * @param ball the ball
     */
    public GravityPocket(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
    }

    @Override
    public void tick() {
        super.tick();
        this.currentturn = game.getTurns();
        if (this.collected) {
            if (this.intialturn + 2 == this.currentturn) {
                removeEffect();
                removePowerup();
            } else {
                if (!this.applied) {
                    applyEffect();
                    this.applied = true;
                }
            }
        }
    }

    /**
     * Apply effect.
     */
    public void applyEffect() {
        game.setPocketGravity(true);
    }

    /**
     * Remove effect.
     */
    public void removeEffect() {
        game.setPocketGravity(false);
    }

    @Override
    public void draw(GameView gv) {
        if(!invisable) {
            if (bitmap == null) {
                bitmap = gv.getBitmapFromResource(R.drawable.gravitypocket);
            }
            gv.drawBitmap(bitmap, (float) vector2.getX(), (float) vector2.getY(), game.getPowerupsize(), game.getPowerupsize());
        }
    }

    /**
     * Sets the powerup to the right settings on collision
     */
    @Override
    public void resolveColission() {
        this.intialturn = game.getTurns();
        this.invisable = true;
        this.collected = true;
    }

    @Override
    public void createPowerUp() {
        GravityPocket gravityPocket = new GravityPocket(game, (float) Utility.randomDoubleFromRange(100, game.getWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall);
        game.getPowerups().add(gravityPocket);
        game.addEntity(gravityPocket);
    }
}

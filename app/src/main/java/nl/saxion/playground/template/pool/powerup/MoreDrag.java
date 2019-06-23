package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

/**
 * The More drag.
 *
 * Adds more friction to ball
 */
public class MoreDrag extends Powerup {
    /**
     * The Bitmap.
     */
    static Bitmap bitmap;
    private Game game;
    private WhiteBall whiteBall;
    private int currentturn, intialturn;
    private boolean applied;

    /**
     * Instantiates a new More drag.
     *
     * @param game the game
     * @param x    the x
     * @param y    the y
     * @param ball the ball
     */
    public MoreDrag(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
    }

    @Override
    public void tick() {
        super.tick();
        this.currentturn = game.getTurns();
        //Check if collected
        if (this.collected) {
            //Check if lifetime is not max than 2 turns
            if (this.intialturn + 2 == this.currentturn) {
                removeDrag();
                removePowerup();
            } else {
                //apply if not applied
                if (!this.applied) {
                    applyDrag();
                    this.applied = true;
                }
            }
        }
    }

    /**
     * Apply drag.
     */
    public void applyDrag() {
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            double friction = ball.getFriction();
            ball.setFriction(friction * .999);
        }
    }

    /**
     * Remove drag.
     */
    public void removeDrag() {
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            ball.setFriction(.9965);
        }
    }

    @Override
    public void draw(GameView gv) {
        if(!invisable) {
            if (bitmap == null) {
                bitmap = gv.getBitmapFromResource(R.drawable.drag);
            }
            gv.drawBitmap(bitmap, (float) vector2.getX(), (float) vector2.getY(), game.getPowerupsize(), game.getPowerupsize());
        }
    }

    @Override
    public void resolveColission() {
        this.intialturn = game.getTurns();
        this.invisable = true;
        this.collected = true;
    }


    @Override
    public void createPowerUp() {
        MoreDrag moredrag = new MoreDrag(game, (float) Utility.randomDoubleFromRange(100, game.getWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall);
        game.getPowerups().add(moredrag);
        game.addEntity(moredrag);
    }
}

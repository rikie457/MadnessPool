package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

/**
 * The type Speed boost.
 */
public class SpeedBoost extends Powerup {
    private WhiteBall whiteBall;
    static private Bitmap bitmap;
    private Game game;
    private double x,y,radius,maxspeed = 2;
    private int currentturn, intialturn;
    private boolean speedBoost;

    /**
     * Instantiates a new Speed boost.
     *
     * @param game the game
     * @param x    the x
     * @param y    the y
     * @param ball the ball
     */
    public SpeedBoost(Game game, double x, double y, WhiteBall ball) {
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
                removeSpeedBoost();
                removePowerup();
            } else {
                if (!this.speedBoost) {
                    applySpeedBoost();
                    this.speedBoost = true;
                }
            }
        }
    }

    /**
     * Apply's a speedboost to all balls with a maximum of 2.
     */

    public void applySpeedBoost(){
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            double speedx = ball.getSpeedX();
            double speedy = ball.getSpeedY();
            ball.setSpeedX((float) speedx * 4);
            ball.setSpeedY((float) speedy * 4);
            if (speedx == maxspeed | speedy == maxspeed){
                ball.setSpeedX((float) maxspeed);
                ball.setSpeedY((float) maxspeed);
            }
        }
    }

    /**
     * Removes the speedboost of all balls.
     */

    public void removeSpeedBoost(){
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            double speedx = ball.getSpeedX();
            double speedy = ball.getSpeedY();
            ball.setSpeedX((float) speedx);
            ball.setSpeedY((float) speedy);
        }
    }

    /**
     * Draws the powerup on the pool table.
     */

    public void draw(GameView gameView) {
        if (!invisable) {
            if (bitmap == null) {
                bitmap = gameView.getBitmapFromResource(R.drawable.speedboost);
            }
            gameView.drawBitmap(bitmap, (float) x, (float) y, (float) this.radius, (float) this.radius);
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
     * Creates the powerup on a random location on the pool table.
     */

    @Override
    public void createPowerUp() {
        SpeedBoost speedBoost = new SpeedBoost(game, (float) Utility.randomDoubleFromRange(100, game.getWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall);
        game.getPowerups().add(speedBoost);
        game.addEntity(speedBoost);
    }
}

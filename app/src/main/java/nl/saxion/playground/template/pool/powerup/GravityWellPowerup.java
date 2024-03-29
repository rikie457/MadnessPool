package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.GravityWell;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.WhiteBall;

/**
 * The type Gravity well powerup.
 */
public class GravityWellPowerup extends Powerup {
    private WhiteBall whiteBall;
    static private Bitmap bitmap;
    private Game game;
    private double x, y, radius;
    private int currentTurn, initialTurn;
    private boolean used;

    /**
     * Instantiates a new Gravity well powerup.
     *
     * @param game the game
     * @param x    the x
     * @param y    the y
     * @param ball the ball
     */
    public GravityWellPowerup(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
        this.x = x;
        this.y = y;
        this.radius = game.getPowerupsize();
    }

    @Override
    public void tick() {
        super.tick();
        this.currentTurn = game.getTurns();
        if (this.collected) {
            if (!used) {
                GravityWell gravityWell = new GravityWell(
                        game,
                        game.getBalls()
                );
                game.addEntity(gravityWell);
                used = true;
            }
        } else if (this.initialTurn + 2 == this.currentTurn) {
            removePowerup();
        }
    }

    public void draw(GameView gameView) {
        if (!invisable) {
            if (bitmap == null) {
                bitmap = gameView.getBitmapFromResource(R.drawable.gravity_well_powerup);
            }
            gameView.drawBitmap(bitmap, (float) x, (float) y, (float) this.radius, (float) this.radius);
        }
    }

    @Override
    public void resolveColission() {
        this.initialTurn = game.getTurns();
        this.invisable = true;
        this.collected = true;
    }

    @Override
    public void createPowerUp() {
        GravityWellPowerup gravityWellPowerup =
                new GravityWellPowerup(game,
                        (float) Utility.randomDoubleFromRange(100, game.getWidth() - 100),
                        (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100),
                        this.whiteBall
                );
        game.getPowerups().add(gravityWellPowerup);
        game.addEntity(gravityWellPowerup);
    }
}
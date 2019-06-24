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
public class RandomOther extends Powerup {
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
    public RandomOther(Game game, double x, double y, WhiteBall ball) {
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
                // spawn a random power-up somewhere
                int poweruptype = 0;
                do {
                    poweruptype = (int) Utility.randomDoubleFromRange(0, game.getPowerupCreatorPowerups().size() - 1);
                }
                while(game.getPowerupCreatorPowerups().get(poweruptype) instanceof RandomOther);

                Powerup powerup = game.getPowerupCreatorPowerups().get(poweruptype);
                powerup.createPowerUp();

                used = true;
            }
        } else if (this.initialTurn + 2 == this.currentTurn) {
            removePowerup();
        }
    }

    public void draw(GameView gameView) {
        if (!invisable) {
            if (bitmap == null) {
                bitmap = gameView.getBitmapFromResource(R.drawable.random_other_powerup);
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
        RandomOther randomOther =
                new RandomOther(game,
                        (float) Utility.randomDoubleFromRange(100, game.getWidth() - 100),
                        (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100),
                        this.whiteBall
                );
        game.getPowerups().add(randomOther);
        game.addEntity(randomOther);
    }
}
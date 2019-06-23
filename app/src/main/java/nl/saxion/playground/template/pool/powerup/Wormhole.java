package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

/**
 * The Wormhole.
 * <p>
 * Everytime a ball hits this object it will be teleported to another location on the table
 */
public class Wormhole extends Powerup {

    /**
     * The Bitmap.
     */
    static Bitmap bitmap;
    private Game game;
    private WhiteBall whiteBall;
    private int currentturn, intialturn;


    /**
     * Instantiates a new Wormhole.
     *
     * @param game the game
     * @param x    the x
     * @param y    the y
     * @param ball the ball
     */
    public Wormhole(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
        this.vector2.set(x, y);
    }

    @Override
    public void tick() {
        super.tick();
        this.currentturn = game.getTurns();
        if (this.intialturn + 2 == this.currentturn) {
            game.removeEntity(this);
        } else {
            if (this.collected) {
                teleport();
            }
        }
    }

    /**
     * Teleport.
     */
    public void teleport() {
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            if (Math.sqrt(Utility.getDistanceNotSquared(this.vector2.getX(), this.vector2.getY(), ball.getVector2().getX() + ball.getRadius(), ball.getVector2().getY() + ball.getRadius())) - (30) <= 0 && !game.getCueBallInHand()) {
                ball.getVector2().set(Utility.randomDoubleFromRange(100, game.getWidth() - 100), Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100));
            }
        }
    }

    @Override
    public void resolveColission() {
        this.intialturn = game.getTurns();
        this.collected = true;
    }


    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.wormhole);
        }
        gv.drawBitmap(bitmap, (float) vector2.getX(), (float) vector2.getY(), game.getPowerupsize(), game.getPowerupsize());
    }

    @Override
    public void createPowerUp() {
        Wormhole wormhole = new Wormhole(game, (float) Utility.randomDoubleFromRange(100, game.getWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall);
        game.getPowerups().add(wormhole);
        game.addEntity(wormhole);
    }
}

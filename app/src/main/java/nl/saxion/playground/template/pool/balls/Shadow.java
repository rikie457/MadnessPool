package nl.saxion.playground.template.pool.balls;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;

/**
 * The type Shadow.
 */
public class Shadow extends Entity {
    /**
     * Instantiates a new Shadow.
     *
     * @param ball the ball
     * @param game the game
     */
    public Shadow(Ball ball, Game game) {
        this.ball = ball;
        this.offset = -(29.5 * (1000 / game.getWidth()));
        this.scalingFactor = (float)2.95;
        this.game = game;
    }

    private Ball ball;
    private double offset;
    private double scalingFactor;
    private static Bitmap shadowBitmap;
    private Game game;

    @Override
    public void draw(GameView gameView) {
        if(ball instanceof WhiteBall) {
            if(!((WhiteBall)ball).getVisible()) return;
        }
        if(shadowBitmap == null) {
            shadowBitmap = gameView.getBitmapFromResource(R.drawable.ball_shadow);
        }

        float x = (float)(ball.getVector2().getX() + offset);
        float y = (float)(ball.getVector2().getY() + offset);
        float width = (float)(ball.getWidth() * scalingFactor);
        float height = (float)(ball.getHeight() * scalingFactor);

        gameView.drawBitmap(
                shadowBitmap,
                x, y,
                width, height
        );
    }

    @Override
    public int getLayer() {
        return 2;
    }
}

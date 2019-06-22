package nl.saxion.playground.template.pool.balls;

import android.graphics.Bitmap;
import android.util.Log;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Vector2;

public class Shadow extends Entity {
    public Shadow(Ball ball, Game game) {
        this.ball = ball;
        this.offset = -(29.5 * (1000/game.getPlayWidth()));
        this.scalingFactor = (float)2.95;
        this.game = game;
    }

    private Ball ball;
    private double offset;
    private double scalingFactor;
    private static Bitmap shadowBitmap;
    private double shadowAngle;
    private Game game;

    @Override
    public void draw(GameView gameView) {
        if(ball instanceof WhiteBall) {
            if(!((WhiteBall)ball).getVisible()) return;
        }
        if(shadowBitmap == null) {
            this.shadowBitmap = gameView.getBitmapFromResource(R.drawable.ball_shadow);
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

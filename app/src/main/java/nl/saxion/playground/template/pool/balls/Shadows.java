package nl.saxion.playground.template.pool.balls;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Vector2;

public class Shadows extends Entity {
    private static Bitmap outer_shadow_bitmap = null;
    private ArrayList<Ball> balls, player1Balls, player2Balls;
    private Game game;
    private static float shadowOffset;

    public Shadows(Game game, ArrayList<Ball> balls) {
        this.game = game;

        this.balls = balls;

        // shadow offset, the shadow image is larger,
        // so it will be moved slightly to the top left,
        // so it aligns with the ball perfectly
        this.shadowOffset = -(8 * (1000/game.getPlayWidth()));
    }

    @Override
    public void draw(GameView gameView) {
        if(this.outer_shadow_bitmap == null) this.outer_shadow_bitmap = gameView.getBitmapFromResource(R.drawable.ball_shadow);

        // draw shadows for all visible balls on the pool table
        drawNormalBallShadows(gameView);

        // draw shadows for all the balls on the gui screen
        drawGUIBallShadows(gameView);
    }

    /**
     * @param gameView = The GameView
     */
    private void drawGUIBallShadows(GameView gameView) {
        if(this.outer_shadow_bitmap == null || this.player1Balls == null || this.player2Balls == null) return;

        float width, height;
        Vector2 location;

        // draw all the visible shadows for all of player 1's scored balls
        for(int i = 0; i < this.player1Balls.size(); i++) {
            location = new Vector2(this.player1Balls.get(i).getVector2());

            if(location.getY() < game.getPlayWidth()) continue;

            location.add(this.shadowOffset);

            width = (float) (this.player1Balls.get(i).getWidth() * 1.5);
            height = (float) (this.player1Balls.get(i).getHeight() * 1.5);

            gameView.drawBitmap(
                    this.outer_shadow_bitmap,
                    (float) location.getX(),
                    (float) location.getY(),
                    width,
                    height
            );
        }

        // draw all the visible shadows for all of player 2's scored balls
        for(int i = 0; i < this.player2Balls.size(); i++) {
            location = new Vector2(this.player2Balls.get(i).getVector2());

            if(location.getY() < game.getPlayWidth()) continue;

            location.add(this.shadowOffset);

            width = (float)(this.player2Balls.get(i).getWidth() * 1.5);
            height = (float)(this.player2Balls.get(i).getHeight() * 1.5);

            gameView.drawBitmap(
                    this.outer_shadow_bitmap,
                    (float)location.getX(),
                    (float)location.getY(),
                    width,
                    height
            );
        }
    }

    /**
     *
     * @param gameView = The GameView
     */
    private void drawNormalBallShadows(GameView gameView) {
        if(this.outer_shadow_bitmap == null || this.balls == null) return;

        float width, height;
        Vector2 location;

        // draw all the visible shadows for all balls
        for(int i = 0; i < this.balls.size(); i++) {
            if(!this.balls.get(i).hasShadow()) continue;

            location = new Vector2(this.balls.get(i).getVector2());
            location.add(this.shadowOffset);

            width = (float)(this.balls.get(i).getWidth() * 1.5);
            height = (float)(this.balls.get(i).getHeight() * 1.5);

            gameView.drawBitmap(
                    this.outer_shadow_bitmap,
                    (float)location.getX(),
                    (float)location.getY(),
                    width,
                    height
            );
        }
    }

    public boolean hasBalls() {
        return this.balls != null;
    }

    public boolean hasGUIBalls() {
        return this.player1Balls != null && this.player2Balls != null;
    }

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }

    public void setGUIBalls(ArrayList<Ball> player1Balls, ArrayList<Ball> player2Balls) {
        this.player1Balls = player1Balls;
        this.player2Balls = player2Balls;
    }
}

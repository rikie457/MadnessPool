/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.balls.Ball;

/**
 * The Gui.
 * This class allows for displaying all of the various information like
 * the scoredballs per player
 * the remaining time to place a wall
 * the powerups
 */
public class Gui extends Entity {
    static private Bitmap bitmap;
    private static Bitmap ball_inner_shadow, ball_inner_shadow_madness, toDraw;
    private double width, height;
    private Vector2 vector2 = new Vector2();
    private Game game;
    private Player player1;
    private Player player2;
    private double x_offset_left;
    private double x_offset_right;

    /**
     * Instantiates a new Gui.
     *
     * @param game    the game
     * @param player1 the player 1
     * @param player2 the player 2
     * @param x       the x
     * @param y       the y
     * @param width   the width
     * @param height  the height
     */
    public Gui(Game game, Player player1, Player player2, double x, double y, double width, double height) {
        this.game = game;
        this.player1 = player1;
        this.player2 = player2;
        this.vector2.set(x, y);
        this.width = width;
        this.height = height;
        Game.whitePaint.setTextSize(20);

        // the gui balls left and right side' offsets
        this.x_offset_left = 90;
        this.x_offset_right = 680;
    }

    // layer 0, because this is a different area on the screen,
    // we don't have to account for anything but the shootLine
    // which should be drawn above this layer anyway
    // the wall placement timer shall be drawn on layer 1
    public int getLayer() {
        return 0;
    }

    @Override
    public void draw(GameView gv) {
        float x = (float) this.vector2.getX();
        float y = (float) this.vector2.getY();
        gv.getCanvas().drawRect(x, y, (float) this.width, (float) this.height, Game.blackPaint);
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.shelf);
        }

        Paint colorCurrentPlayer = new Paint();
        colorCurrentPlayer.setColor(Color.argb(255, 128, 0, 255));
        colorCurrentPlayer.setStrokeWidth(4);
        colorCurrentPlayer.setTextSize(20);

        if (game.getCurrentplayer() == this.player1) {
            gv.getCanvas().drawText(">", x + 10, y + 50, colorCurrentPlayer);
        } else {
            gv.getCanvas().drawText("<", x + 980, y + 50, colorCurrentPlayer);
        }

        gv.getCanvas().drawText("Player 1", x + 20, y + 50, (game.getCurrentplayer() == this.player1) ? colorCurrentPlayer : Game.whitePaint);
        gv.drawBitmap(bitmap, x + 90, y + 25, 230, 50);
        gv.getCanvas().drawText("Player 2", x + 910, y + 50, (game.getCurrentplayer() == this.player2) ? colorCurrentPlayer : Game.whitePaint);
        gv.drawBitmap(bitmap, x + 680, y + 25, 230, 50);

        if (ball_inner_shadow == null && !game.getMadness()) {
            ball_inner_shadow = gv.getBitmapFromResource(R.drawable.ball_inner_shadow);
        }
        if(ball_inner_shadow_madness == null && game.getMadness()) {
            ball_inner_shadow_madness = gv.getBitmapFromResource(R.drawable.ball_inner_shadow_madness);
        }

        // get right shadow overlay bitmap for current gameMode
        toDraw = ((game.getMadness()) ? ball_inner_shadow_madness : ball_inner_shadow);

        ArrayList<Ball> player1Balls = player1.getScoredballs();
        ArrayList<Ball> player2Balls = player2.getScoredballs();

        //boolean testScenario = false;
        // testScenario == false = player1's balls are striped
        // testScenario == true  = player1's balls are solid
        // TESTING
//        for(Ball ball : game.getBalls()) {
//            if(ball.getType() == ((!testScenario) ? 2 : 1)) {
//                player1Balls.add(ball);
//            } else if(ball.getType() == ((testScenario) ? 2 : 1)) {
//                player2Balls.add(ball);
//            }
//        }
//        // store the ball size for the drawing method

        // current ball in the loop
        Ball ball;

        for (int i = 0;
             i < player1Balls.size()
                     & player1.getBalltype() != -1;
             i++) {

            ball = player1Balls.get(i);
            if(ball.getBitmap(i) == null) continue;

            // if it ever changes mid-game (idk)
            if(ballSize == ball.getWidth()) ballSize = ball.getWidth();

            int index = ball.getId();
            if(index > 7) index -= 8;

            // index above explained
            // 0 t/m 6 == solid
            // 8 t/m 14 == striped
            // so if index (ball.getId() initially) is larger than 7 (which is the id of the black ball)
            // if is a solid ball, and it's index should be mapped to 0 as well, to always have balls
            // be drawn at the right location

            drawBall(
                    ball.getBitmap(i),
                    x + x_offset_left + index * ball.getWidth(),
                    y,
                    gv

            );
        }

        for (int i = 0;
             i < player2Balls.size()
                     & player2.getBalltype() != -1;
             i++) {

            ball = player2Balls.get(i);
            if(ball.getBitmap(i) == null) continue;

            // if it ever changes mid-game (idk)
            if(ballSize != ball.getWidth()) ballSize = ball.getWidth();

            // index in the balls array on this plank, because the id is higher for striped balls, so we have to give it a negative
            // offset of -7
            int index = ball.getId();
            if(index > 7) index -= 8;

            // player2's balls are always on the right (wrm check je voor ballType...,
            // wtf Tycho.................................. je bent af.
            drawBall(
                    ball.getBitmap(i),
                    x + x_offset_right + index * ball.getWidth(),
                    y,
                    gv
            );
        }
    }

    /**
     * draw the current GUI ball with
     * it's shadow overlay that is specified in this.toDraw
     * @param bitmap
     * @param x
     * @param y
     * @param width
     * @param height
     * @param gv
     */

    private static double ball_x_offset = 10;
    private static double ball_y_offset = 17;
    private static double ballSize = 30f;

    private void drawBall(Bitmap bitmap, double x, double y, GameView gv) {
        gv.drawBitmap(
                bitmap,
                (float)(x + ball_x_offset),
                (float)(y + ball_y_offset + ballSize / 2),
                (float)ballSize,
                (float)ballSize
        );
        gv.drawBitmap(
                toDraw,
                (float)((x + ball_x_offset) / 1.0005),
                (float)((y + ball_y_offset + ballSize / 2) / 1.0005),
                (float)(ballSize * 1.03),
                (float)(ballSize * 1.03)
        );
    }
}



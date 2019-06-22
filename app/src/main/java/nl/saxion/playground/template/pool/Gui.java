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
 * The type Gui.
 */
public class Gui extends Entity {
    static private Bitmap bitmap;
    private static Bitmap ball_inner_shadow, ball_outer_shadow;
    private double width, height;
    private Vector2 vector2 = new Vector2();
    private Game game;
    private Player player1;
    private Player player2;

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

    }

    // layer 0, because this is a different area on the screen so we don't have to account for anything but the shootLine
    // which should be drawn over this anyway
    // the wall placement timer shall be drawn on layer 1
    public int getLayer() {
        return 0;
    }

    @Override
    public void draw(GameView gv) {
        float x = (float) this.vector2.getX();
        float y = (float) this.vector2.getY();
        //THIS IS NOT HOW TO SCALE THE GUI I KNOW!
        gv.getCanvas().drawRect(x, y, (float) this.width, (float) this.height, Game.blackPaint);
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.shelf);
        }

        Paint colorCurrentPlayer = new Paint();
        colorCurrentPlayer.setColor(Color.argb(255, 128, 0, 255));
        colorCurrentPlayer.setStrokeWidth(4);
        colorCurrentPlayer.setTextSize(20);

        if (game.getCurrentplayer() == this.player1) {
            gv.getCanvas().drawText(">", (float) x + 10, (float) y + 50, colorCurrentPlayer);
        } else {
            gv.getCanvas().drawText("<", (float) x + 980, (float) y + 50, colorCurrentPlayer);
        }

        gv.getCanvas().drawText("Player 1", (float) x + 20, (float) y + 50, (game.getCurrentplayer() == this.player1) ? colorCurrentPlayer : Game.whitePaint);
        gv.drawBitmap(bitmap, (float) x + 90, (float) y + 25, 230, 50);
        gv.getCanvas().drawText("Player 2", (float) x + 910, (float) y + 50, (game.getCurrentplayer() == this.player2) ? colorCurrentPlayer : Game.whitePaint);
        gv.drawBitmap(bitmap, (float) x + 680, (float) y + 25, 230, 50);

        if(this.ball_inner_shadow == null) {
            this.ball_inner_shadow = gv.getBitmapFromResource(R.drawable.ball_inner_shadow);
        }

        ArrayList<Ball> player1balls = this.player1.getScoredballs();
        ArrayList<Ball> player2balls = this.player2.getScoredballs();

        if (player1balls.size() != 0 && this.player1.getBalltype() != -1) {
            for (int j = 0; j < this.player1.getScoredballs().size(); j++) {
                Ball ball = player1balls.get(j);
                if (ball.getBitmap(j) != null) {
                    if (this.player1.getBalltype() == 1) {
                        gv.drawBitmap(ball.getBitmap(j),
                                (float) x + 60 + ball.getId() * (float) ball.getWidth() + 10,
                                (float) y + (float) ball.getRadius() + 15,
                                (float) (ball.getWidth()),
                                (float) (ball.getHeight()));
                        gv.drawBitmap(ball_inner_shadow,
                                (float) x + 60 + ball.getId() * (float) ball.getWidth() + 10,
                                (float) y + (float) ball.getRadius() + 15,
                                (float) (ball.getWidth()),
                                (float) (ball.getHeight()));
                    } else {
                        if (ball.getBitmap(j) != null) {
                            gv.drawBitmap(ball.getBitmap(j),
                                    (float) x + 650 + ball.getId() * (float) ball.getWidth() + 10,
                                    (float) y + (float) ball.getRadius() + 15,
                                    (float) (ball.getWidth()),
                                    (float) (ball.getHeight()));
                            gv.drawBitmap(ball_inner_shadow,
                                    (float) x + 650 + ball.getId() * (float) ball.getWidth() + 10,
                                    (float) y + (float) ball.getRadius() + 15,
                                    (float) (ball.getWidth()),
                                    (float) (ball.getHeight()));
                        }
                    }
                }
            }
        }

        if (player2balls.size() != 0 && this.player2.getBalltype() != -1) {
            for (int j = 0; j < player2balls.size(); j++) {
                Ball ball = player2balls.get(j);
                if (this.player2.getBalltype() == 2) {
                    if (ball.getBitmap(j) != null) {
                        gv.drawBitmap(ball.getBitmap(j),
                                (float) x + 410 + ball.getId() * (float) ball.getWidth() + 10,
                                (float) y + (float) ball.getRadius() + 15,
                                (float) (ball.getWidth()) - 500,
                                (float) (ball.getHeight()));
                        gv.drawBitmap(ball_inner_shadow,
                                (float) x + 410 + ball.getId() * (float) ball.getWidth() + 10,
                                (float) y + (float) ball.getRadius() + 15,
                                (float) (ball.getWidth()) - 500,
                                (float) (ball.getHeight()));
                    }
                } else {
                    if (ball.getBitmap(j) != null) {
                        gv.drawBitmap(ball.getBitmap(j),
                                (float) x - 180 + ball.getId() * (float) ball.getWidth() + 10,
                                (float) y + (float) ball.getRadius() + 15,
                                (float) (ball.getWidth()) - 500,
                                (float) (ball.getHeight()));
                        gv.drawBitmap(ball_inner_shadow,
                                (float) x - 180 + ball.getId() * (float) ball.getWidth() + 10,
                                (float) y + (float) ball.getRadius() + 15,
                                (float) (ball.getWidth()) - 500,
                                (float) (ball.getHeight()));
                    }
                }
            }
        }
    }


}



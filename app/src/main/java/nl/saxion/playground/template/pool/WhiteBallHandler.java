/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool;

import android.view.MotionEvent;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

/**
 * The type White ball handler.
 */
public class WhiteBallHandler extends Entity {

    /**
     * The Balls.
     */
    ArrayList<Ball> balls;
    /**
     * The Holes.
     */
    ArrayList<Hole> holes;
    private boolean ballReplaced = false;
    private boolean canContinue = false;
    private boolean movingBall = false;
    private int timer = 0;
    private Game game;
    private WhiteBall whiteBall;

    /**
     * Instantiates a new White ball handler.
     *
     * @param game  the game
     * @param balls the balls
     * @param holes the holes
     */
    public WhiteBallHandler(Game game, ArrayList<Ball> balls, ArrayList<Hole> holes) {
        this.game = game;
        this.balls = balls;
        this.holes = holes;
    }

    @Override
    public void tick() {
        super.tick();
        checkMovingBalls();

        if (this.canContinue && this.timer < 10) {
            this.timer++;
        }

        if (canContinue && timer == 10) {
            this.timer = 0;
            this.canContinue = false;
            game.setCueBallInHand(false);
            game.resetCueBallScored();
            this.ballReplaced = false;
            game.removeEntity(this);
        }
    }

    @Override
    public void draw(GameView gv) {
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            game.setCueBallInHand(true);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            game.setCueBallInHand(false);
        }

        if (!this.ballReplaced && game.getCueBallScored() && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_DOWN) {
            this.whiteBall.getVector2().set(touch.x - this.whiteBall.getWidth() / 2, touch.y - this.whiteBall.getHeight() / 2);
            this.whiteBall.setSpeedX(0);
            this.whiteBall.setSpeedY(0);
            this.ballReplaced = true;
            this.whiteBall.setCollision(true);
            game.addEntity(this.whiteBall);
        }

        if (this.ballReplaced && game.getCueBallScored() && fingerOnhWhiteBall(event) && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.movingBall = true;
            this.whiteBall.getVector2().set(touch.x - this.whiteBall.getWidth() / 2, touch.y - this.whiteBall.getHeight() / 2);
        }

        if (this.ballReplaced && !this.movingBall && !fingerOnhWhiteBall(event) && !game.getCueBallInHand() && event.getAction() == MotionEvent.ACTION_UP) {
            this.canContinue = true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            this.movingBall = false;
        }
    }

    /**
     * Is valid position boolean.
     *
     * @param event the event
     * @return the boolean
     */
    public boolean isValidPosition(MotionEvent event) {
        boolean isValid = true;

        for (int i = 0; i < this.balls.size(); i++) {
            Ball ball = this.balls.get(i);
            System.out.println(this.whiteBall);
            double distSqr = Utility.getDistanceNotSquared((event.getX() - this.whiteBall.getWidth()) + this.whiteBall.getRadius(),
                    (event.getY() - this.whiteBall.getHeight()) + this.whiteBall.getRadius(), ball.getVector2().getX(), ball.getVector2().getY());

            if (this.whiteBall == balls.get(i)) {
                continue;
            }
            if (distSqr <= (this.whiteBall.getRadius() + balls.get(i).getRadius()) * (this.whiteBall.getRadius() + balls.get(i).getRadius()) && this.whiteBall.getCollision()) {
                isValid = false;
            }
        }

        for (int i = 0; i < this.holes.size(); i++) {
            Hole hole = this.holes.get(i);
            double distSqr = Utility.getDistanceNotSquared((event.getX() - this.whiteBall.getWidth()) + this.whiteBall.getRadius(),
                    (event.getY() - this.whiteBall.getHeight()) + this.whiteBall.getRadius(), (hole.getX() - 18), (hole.getY() - 18));

            if (distSqr <= (this.whiteBall.getRadius() + 20) * (this.whiteBall.getRadius() + 20) && this.whiteBall.getCollision()) {
                isValid = false;
            }
        }
        return isValid;
    }

    private boolean fingerOnhWhiteBall(MotionEvent event) {
        return event.getX() > this.whiteBall.getVector2().getX() - 30 && event.getX() < this.whiteBall.getVector2().getX() + this.whiteBall.getWidth() + 30 &&
                event.getY() > this.whiteBall.getVector2().getY() - 30 && event.getY() < this.whiteBall.getVector2().getY() + this.whiteBall.getHeight() + 30;
    }

    /**
     * Check moving balls.
     */
    public void checkMovingBalls() {
        if (game.getMovingBalls().size() == 0) {
            game.scoreCueBall();
        } else {
            for (int i = 0; i < game.getMovingBalls().size(); i++) {
                if (game.getMovingBalls().get(i).getId() == 16) {
                    game.getMovingBalls().remove(i);
                }
            }
        }
    }

    /**
     * Sets white ball.
     *
     * @param whiteBall the white ball
     */
    public void setWhiteBall(WhiteBall whiteBall) {
        this.whiteBall = whiteBall;
    }
}

/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool.handlers;

import android.view.MotionEvent;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Hole;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.Wall;
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
    private ArrayList<Hole> holes;
    private boolean ballReplaced = false;
    private boolean canContinue = false;
    private boolean movingBall = false;
    private boolean fingerOnBall = false;
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
            whiteBall.setVisible(true);
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
            this.fingerOnBall = true;
            this.whiteBall.getVector2().set(touch.x - this.whiteBall.getWidth() / 2, touch.y - this.whiteBall.getHeight() / 2);
        }

        if (this.ballReplaced && game.getCueBallScored() && this.fingerOnBall && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.whiteBall.getVector2().set(touch.x - this.whiteBall.getWidth() / 2, touch.y - this.whiteBall.getHeight() / 2);
        }

        if (this.ballReplaced && !this.movingBall && !fingerOnhWhiteBall(event) && !game.getCueBallInHand() && event.getAction() == MotionEvent.ACTION_UP) {
            this.canContinue = true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            this.movingBall = false;
            this.fingerOnBall = false;
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
        double ballRadius;

        for (int i = 0; i < this.balls.size(); i++) {
            Ball ball = this.balls.get(i);
            double distSqr = Utility.getDistanceNotSquared((event.getX() - this.whiteBall.getWidth()) + this.whiteBall.getRadius(),
                    (event.getY() - this.whiteBall.getHeight()) + this.whiteBall.getRadius(), ball.getVector2().getX(), ball.getVector2().getY());

            if (this.whiteBall == balls.get(i)) {
                continue;
            }
            ballRadius = balls.get(i).getRadius();
            if (distSqr <= (this.whiteBall.getRadius() + ballRadius) * (this.whiteBall.getRadius() + ballRadius) && this.whiteBall.getCollision()) {
                isValid = false;
            }
        }

        for (int i = 0; i < this.holes.size(); i++) {
            Hole hole = this.holes.get(i);
            double distSqr = Utility.getDistanceNotSquared((event.getX() - this.whiteBall.getWidth()) + this.whiteBall.getRadius(),
                    (event.getY() - this.whiteBall.getHeight()) + this.whiteBall.getRadius(), (hole.getVector2().getX() - 18), (hole.getVector2().getY() - 18));

            if (distSqr <= (this.whiteBall.getRadius() + 20) * (this.whiteBall.getRadius() + 20) && this.whiteBall.getCollision()) {
                isValid = false;
            }
        }

        /**
         *Muren links en rechts
         */

        if (event.getX() - this.whiteBall.getRadius() < game.getWidth() * 0.07) {
            isValid = false;
        } else if (event.getX() + this.whiteBall.getRadius() > game.getWidth() * 0.93) {
            isValid = false;
        }

        /**
         * Muren boven en onder
         */
        if (event.getY() - this.whiteBall.getRadius() < game.getPlayHeight() * 0.12) {
            isValid = false;
        } else if (event.getY() + this.whiteBall.getRadius() > game.getPlayHeight() * 0.88) {
            isValid = false;
        }

        /**
         * PlaceableWalls
         */
        for (int i = 0; i < game.getWalls().size(); i++) {

            Wall wall = game.getWalls().get(i);

            double side1 = Math.sqrt(Math.pow(event.getX() - wall.getVector2().getX(), 2) + Math.pow(event.getY() - wall.getVector2().getY(), 2));


            double side2 = Math.sqrt(Math.pow(event.getX() - wall.getEndVector2().getX(), 2) + Math.pow(event.getY() - wall.getEndVector2().getY(), 2));

            double base = Math.sqrt(Math.pow(wall.getEndVector2().getX() - wall.getVector2().getX(), 2) + Math.pow(wall.getEndVector2().getY() - wall.getVector2().getY(), 2));

            if (this.whiteBall.getRadius() + this.whiteBall.getWidth() / 2 > side1 || this.whiteBall.getRadius() + this.whiteBall.getWidth() / 2 > side2) return false;

            double angle1 = Math.atan2(wall.getEndVector2().getX() - wall.getVector2().getX(), wall.getEndVector2().getY() - wall.getVector2().getY()) - Math.atan2(event.getX() - wall.getVector2().getX(), event.getY() - wall.getVector2().getY());

            double angle2 = Math.atan2(wall.getVector2().getX() - wall.getEndVector2().getX(), wall.getVector2().getY() - wall.getEndVector2().getY()) - Math.atan2(event.getX() - wall.getEndVector2().getX(), event.getY() - wall.getEndVector2().getY());

            if (angle1 > Math.PI / 2 || angle2 > Math.PI / 2)
                return true;

            double semiperimeter = (side1 + side2 + base) / 2;

            double areaOfTriangle = Math.sqrt(semiperimeter * (semiperimeter - side1) * (semiperimeter - side2) * (semiperimeter - base));

            double height = 2 * areaOfTriangle / base;

            isValid = !(height < this.whiteBall.getRadius() + this.whiteBall.getWidth() / 2);
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
    private void checkMovingBalls() {
        if (!game.checkMovementForAllBalls()) {
            game.scoreCueBall();
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

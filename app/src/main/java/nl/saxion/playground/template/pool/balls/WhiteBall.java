/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool.balls;

import android.view.MotionEvent;

import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.ShootLine;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.Vector2;

import static java.lang.Math.PI;

/**
 * The type White ball.
 */
public class WhiteBall extends Ball {

    private ShootLine line;
    private boolean shot;
    private Vector2 origin, end;

    public WhiteBall(Game game, int[] drawables, double x, double y, double width, double height, int type, ShootLine line) {
        super(game, drawables, x, y, width, height, type);
        this.line = line;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isShot()) {
            game.roundChecker(this);
        }
    }

    @Override
    public void draw(GameView gv) {
        super.draw(gv);
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        if (this.line != null && !game.checkMovementForAllBalls() && !game.getCueBallScored()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.line.setVisible(true);
                initOriginAndEnd(touch);

            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (!this.line.getVisible()) {
                    initOriginAndEnd(touch);
                    this.line.setVisible(true);
                }

                updateEnd(touch);

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                double mag = Math.abs(Utility.getDistanceNotSquared(this.origin.getX(), this.origin.getY(), touch.x, touch.y)) * 2;
                this.line.setVisible(false);

                this.speedX = 0.00001 * (this.vector2.getX() + mag * Math.cos(Math.toRadians(Math.atan2(this.origin.getY() - this.end.getY(), this.origin.getX() - this.end.getX()) * 180 / PI)));
                this.speedY = 0.00001 * (this.vector2.getX() + mag * Math.sin(Math.toRadians(Math.atan2(this.origin.getY() - this.end.getY(), this.origin.getX() - this.end.getX()) * 180 / PI)));
                this.shot = true;
            }
        }
    }

    /**
     * @param touch has all information about the initial touch
     */
    private void initOriginAndEnd(GameModel.Touch touch) {
        this.origin = new Vector2(touch.x, touch.y);
        this.end = new Vector2(touch.x, touch.y);

        // init drawable shootLine
        this.line.getVector2().set(this.vector2.getX() + this.radius, this.vector2.getY() + this.radius);
    }

    /**
     * @param touch has all the information about the touch
     */
    public void updateEnd(GameModel.Touch touch) {
        this.end.set(touch.x, touch.y);

        // update drawable shootLine
        double xOffset = this.vector2.getX() - this.origin.getX() + this.radius;
        double yOffset = this.vector2.getY() - this.origin.getY() + this.radius;

        this.line.getNewvector2().set(this.end.getX() + xOffset, this.end.getY() + yOffset);

        // update line color
        int mag = (int) (Math.sqrt(Math.abs(Utility.getDistanceNotSquared(this.origin.getX(), this.origin.getY(), touch.x, touch.y))) * 0.20);

        this.line.setColor(mag, 128 - mag / 2, 255 - mag);
    }

    public boolean isShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }
}

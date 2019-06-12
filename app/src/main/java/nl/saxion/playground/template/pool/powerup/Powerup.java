package nl.saxion.playground.template.pool.powerup;

import android.graphics.Color;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public abstract class Powerup extends Entity {

    private Game game;
    private float x, y, radius;
    private WhiteBall ball;
    private ArrayList<Ball> balls;

    public Powerup(Game game, float x, float y, WhiteBall ball, ArrayList<Ball> balls) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.radius = 15f;
        this.ball = ball;
        this.balls = balls;
        Game.powerupPaint.setColor(Color.GREEN);
    }

    private void checkCollisionWithBall() {
        if (Math.sqrt(Utility.getDistanceNotSquared(this.getX(), this.getY(), this.ball.getVector2().getX() + this.ball.getRadius(), this.ball.getVector2().getY() + this.ball.getRadius())) - (30) <= 0 && !game.getCueBallInHand()) {
            //Apply properties of power up to all balls. (Needed for functionality)
            game.removeEntity(this);
        }
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void tick() {
        checkCollisionWithBall();
    }

    @Override
    public void draw(GameView gv) {
        gv.getCanvas().drawCircle(this.x, this.y, this.radius, Game.powerupPaint);
    }

    public float getX() {
        return x;
    }


    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

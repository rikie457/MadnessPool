package nl.saxion.playground.template.pool.powerup;

import android.graphics.Color;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.Vector2;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public abstract class Powerup extends Entity {

    private Game game;
    protected Vector2 vector2 = new Vector2();
    private double radius;
    private WhiteBall ball;
    private ArrayList<Ball> balls;

    public Powerup(Game game, double x, double y, WhiteBall ball) {
        this.game = game;
        this.vector2.set(x, y);
        this.radius = 15f;
        this.ball = ball;
        Game.powerupPaint.setColor(Color.GREEN);
    }

    private void checkCollisionWithBall() {
        if (Math.sqrt(Utility.getDistanceNotSquared(this.vector2.getX(), this.vector2.getY(), this.ball.getVector2().getX() + this.ball.getRadius(), this.ball.getVector2().getY() + this.ball.getRadius())) - (30) <= 0 && !game.getCueBallInHand()) {
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
        gv.getCanvas().drawCircle((float) this.vector2.getX(), (float) this.vector2.getY(), (float) this.radius, Game.powerupPaint);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}

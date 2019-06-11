package nl.saxion.playground.template.pool.powerup;

import java.util.ArrayList;

import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public class TestPowerup extends Powerup {


    public TestPowerup(Game game, float x, float y, WhiteBall ball, ArrayList<Ball> balls) {
        super(game, x, y, ball, balls);
    }

}

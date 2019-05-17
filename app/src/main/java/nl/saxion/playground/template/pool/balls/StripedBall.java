package nl.saxion.playground.template.pool.balls;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Hole;

public class StripedBall extends Ball {

    public StripedBall(Game game, ArrayList<Ball> balls, ArrayList<Hole> holes, ArrayList<Ball> sunkenBalls, double x, double y, double width, double height, int image) {
        super(game, balls, holes, sunkenBalls, x, y, width, height, image, 2);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void draw(GameView gv) {
        super.draw(gv);
    }
}

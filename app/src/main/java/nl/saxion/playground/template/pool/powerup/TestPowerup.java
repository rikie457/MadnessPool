package nl.saxion.playground.template.pool.powerup;

import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public class TestPowerup extends Powerup {

    private Game game;
    private WhiteBall whiteBall;

    public TestPowerup(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
    }

    public void createPowerUp() {
        game.addEntity(new TestPowerup(game, (float) Utility.randomDoubleFromRange(100, game.getPlayWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall));
    }

    @Override
    public void resolveColission() {
        game.removeEntity(this);
    }
}

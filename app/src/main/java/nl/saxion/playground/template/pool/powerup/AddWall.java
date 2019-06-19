package nl.saxion.playground.template.pool.powerup;

import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.WhiteBall;


public class AddWall extends Powerup {

    private Game game;
    private WhiteBall whiteBall;

    public AddWall(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
    }


    @Override
    public void resolveColission() {
        game.getInactiveplayer();
        game.removeEntity(this);
    }

    @Override
    public void createPowerUp() {
        AddWall addwall = new AddWall(game, (float) Utility.randomDoubleFromRange(100, game.getPlayWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall);
        game.getPowerups().add(addwall);
        game.addEntity(addwall);
    }
}

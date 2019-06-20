package nl.saxion.playground.template.pool.powerup;

import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.WhiteBall;
import nl.saxion.playground.template.pool.handlers.WallHandler;


public class AddWall extends Powerup {

    private Game game;
    private WhiteBall whiteBall;
    private WallHandler wallHandler;
    private int currentturn;
    private int intialturn;

    public AddWall(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
        this.wallHandler = this.game.getWallHandler();
    }

    @Override
    public void tick() {
        super.tick();
        this.currentturn = game.getTurns();
        if (this.collected) {
            if (this.intialturn + 2 == this.currentturn) {
                game.removeEntity(this);
            } else {
                    applyEffect();
            }
        }
    }

    @Override
    public void resolveColission() {
        this.collected = true;
        this.intialturn = game.getTurns();
    }

    public void applyEffect(){
        this.wallHandler.setEntryType(2);
        game.addEntity(wallHandler);
    }

    @Override
    public void createPowerUp() {
        AddWall addwall = new AddWall(game, (float) Utility.randomDoubleFromRange(100, game.getPlayWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall);
        game.getPowerups().add(addwall);
        game.addEntity(addwall);
    }
}

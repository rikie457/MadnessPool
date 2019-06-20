package nl.saxion.playground.template.pool;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;
import nl.saxion.playground.template.pool.powerup.NoDrag;
import nl.saxion.playground.template.pool.powerup.Powerup;
import nl.saxion.playground.template.pool.powerup.SpeedBoost;
import nl.saxion.playground.template.pool.powerup.TestPowerup;

public class PowerupCreator extends Entity {
    private Game game;
    private ArrayList<Ball> balls;
    private ArrayList<Powerup> powerups = new ArrayList<>();
    private WhiteBall whiteball;
    private int tickCount;

    public PowerupCreator(Game game, WhiteBall whiteball, ArrayList<Ball> balls) {
        this.game = game;
        this.balls = balls;
        this.whiteball = whiteball;
    }

    @Override
    public void tick() {
        float gameTime = (float) ++tickCount / game.ticksPerSecond();
        //Every 1 minute a new powerup 1 = 1 sec 60 = 60 sec
        if (gameTime == 5) {
            this.tickCount = 0;
            int random = (int) Utility.randomDoubleFromRange(1, 2);
            //50% chance of spawning
            if (random == 2) {
                int poweruptype = (int) Utility.randomDoubleFromRange(0, this.powerups.size() - 1);
                Powerup powerup = this.powerups.get(poweruptype);
                //check which class is chosen
                if (powerup instanceof TestPowerup) {
                    //generate a new powerup.
                    game.addEntity(new TestPowerup(game, (float) Utility.randomDoubleFromRange(100, game.getPlayWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteball));
                } else if (powerup instanceof SpeedBoost) {
                    game.addEntity(new SpeedBoost(game,(float) Utility.randomDoubleFromRange(100, game.getPlayWidth() -100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteball));
                }else if (powerup instanceof NoDrag) {
                    game.addEntity(new NoDrag(game,(float) Utility.randomDoubleFromRange(100, game.getPlayWidth() -100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteball));
                }
                powerup.createPowerUp();
            }

        }
    }

    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }
}

package nl.saxion.playground.template.pool;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;
import nl.saxion.playground.template.pool.powerup.Powerup;

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
        if (gameTime == 15 && game.getPowerups().size() <= 10) {
            this.tickCount = 0;
            int random = (int) Utility.randomDoubleFromRange(1, 2);
            //50% chance of spawning
            if (random == 2) {
                int poweruptype = (int) Utility.randomDoubleFromRange(0, this.powerups.size() - 1);
                Powerup powerup = this.powerups.get(poweruptype);
                powerup.createPowerUp();
            }

        }
    }

    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }
}

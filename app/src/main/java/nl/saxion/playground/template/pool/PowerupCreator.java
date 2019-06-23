package nl.saxion.playground.template.pool;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;
import nl.saxion.playground.template.pool.powerup.Powerup;

/**
 * The Powerup creator.
 * <p>
 * This class allows the spawning of powerups.
 * These powerups are added to the array of possible powerups in de game class
 */
public class PowerupCreator extends Entity {
    private Game game;
    private ArrayList<Ball> balls;
    private ArrayList<Powerup> powerups = new ArrayList<>();
    private WhiteBall whiteball;
    private int tickCount;

    /**
     * Instantiates a new Powerup creator.
     *
     * @param game      the game
     * @param whiteball the whiteball
     * @param balls     the balls
     */
    public PowerupCreator(Game game, WhiteBall whiteball, ArrayList<Ball> balls) {
        this.game = game;
        this.balls = balls;
        this.whiteball = whiteball;
    }

    @Override
    public void tick() {
        float gameTime = (float) ++tickCount / game.ticksPerSecond();
        //If 15 seconds passed and the size is not 10
        if (gameTime == 15 && game.getPowerups().size() > 11) {
            this.tickCount = 0;
            int random = (int) Utility.randomDoubleFromRange(1, 2);
            //50% chance of spawning
            if (random == 2) {
                //Random picking a powerup to spawn
                int poweruptype = (int) Utility.randomDoubleFromRange(0, this.powerups.size() - 1);
                Powerup powerup = this.powerups.get(poweruptype);
                powerup.createPowerUp();
            }

        }
    }

    /**
     * Gets powerups.
     *
     * @return the powerups
     */
    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }
}

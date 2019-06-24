package nl.saxion.playground.template.pool;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;
import nl.saxion.playground.template.pool.powerup.AddBallFromShelf;
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
    private ArrayList<Player> players = null;

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
        //If 15 seconds passed and the size is smaller or equal of 10
        if (gameTime == 10 && game.getPowerups().size() <= 10) {
            this.tickCount = 0;
            int random = (int) Utility.randomDoubleFromRange(1, 2);
            //50% chance of spawning
            if (random == 2) {
                //Random picking a powerup to spawn
                int poweruptype = getRandomPowerupType();

                if(players == null) {
                    players = game.getPlayers();
                }
                // change the powerup to spawn to another random powerup
                // IF the powerup is AddBallFromShelf, because this one powerup
                // shouldn't spawn if the players don't have any balls
                // on their shelves yet
                if (players.get(0).getScoredballs().size() == 0 && players.get(1).getScoredballs().size() == 0) {
                    // get any other powerup than the AddBallFromShelf powerup
                    while(this.getPowerups().get(poweruptype) instanceof AddBallFromShelf) {
                        poweruptype = getRandomPowerupType();
                    }
                }

                Powerup powerup = this.powerups.get(poweruptype);
                powerup.createPowerUp();
            }

        }
    }

    private int getRandomPowerupType() {
        return (int) Utility.randomDoubleFromRange(0, this.powerups.size() - 1);
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

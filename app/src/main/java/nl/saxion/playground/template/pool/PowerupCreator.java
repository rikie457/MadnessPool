package nl.saxion.playground.template.pool;

import java.util.ArrayList;
import java.util.Random;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;
import nl.saxion.playground.template.pool.powerup.Powerup;

public class PowerupCreator extends Entity {
    private Game game;
    private ArrayList<Ball> balls;
    private ArrayList<Powerup> powerups = new ArrayList<>();
    private WhiteBall whiteball;
    private Random random = new Random();
    private int tickCount;

    public PowerupCreator(Game game, WhiteBall whiteball, ArrayList<Ball> balls) {
        this.game = game;
        this.balls = balls;
        this.whiteball = whiteball;
    }

    @Override
    public void tick() {
        float gameTime = (float) ++tickCount / game.ticksPerSecond();
        //Every 1 minute a new powerup
        if (gameTime == 4) {
            this.tickCount = 0;
            System.out.println("4 SECONDS");
            int random = (int) Utility.randomDoubleFromRange(1, 2);
            System.out.println(random);
            if (random == 2) {
                System.out.println("Test");
                int poweruptype = (int) Utility.randomDoubleFromRange(0, this.powerups.size() - 1);
                Powerup powerup = this.powerups.get(poweruptype);
                System.out.println(powerup);
                try {
                    Powerup newpowerup = powerup.getClass().newInstance();
                    game.addEntity(newpowerup);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }

        } }

    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }
}

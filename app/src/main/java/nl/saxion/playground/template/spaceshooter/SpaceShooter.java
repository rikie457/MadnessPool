package nl.saxion.playground.template.spaceshooter;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;

import java.util.Random;

public class SpaceShooter extends GameModel {

    private Random random = new Random();
    private int tickCount;

    public SpaceShooter(float aspectRatio) {
        // Create a Platformer with a virtual screen that is at least 100 wide and 100 high.
        super(Math.max(100f*aspectRatio, 100f), Math.max(100f, 100f/aspectRatio));

        addEntity(new Stars(this));
        addEntity(new Ship(this));
        addEntity(new HitChecker(this));

        // Add an anonymous Entity that will create new rocks at random (but on average
        // decreasing) intervals.
        addEntity(new Entity() {
            @Override
            public void tick() {
                float gameTime = (float)++tickCount / ticksPerSecond();
                // start with one rock every 2s. every 1s after 2m. every 0.66s after 4m. etc.
                float avgTimeBetweenRocks = 2f*120f / (120f+gameTime);
                while (random.nextFloat() < (1f/avgTimeBetweenRocks) / ticksPerSecond() ) {
                    addEntity(new Rock(SpaceShooter.this));
                }
            }
        });
    }
}

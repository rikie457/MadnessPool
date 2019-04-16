package nl.saxion.playground.template.spaceshooter;
import nl.saxion.playground.template.lib.GameModel;

public class SpaceShooter extends GameModel {

    public SpaceShooter(float aspectRatio) {
        // Create a Platformer with a virtual screen that is at least 100 wide and 100 high.
        super(Math.max(100f*aspectRatio, 100f), Math.max(100f, 100f/aspectRatio));

        addEntity(new Stars(this));
        addEntity(new Ship(this));
        addEntity(new Redness(this));
        addEntity(new RockCreator(this));
    }
}

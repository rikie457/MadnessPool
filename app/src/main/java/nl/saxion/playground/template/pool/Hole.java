package nl.saxion.playground.template.pool;

import android.graphics.Color;
import android.graphics.Paint;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type Hole.
 */
public class Hole extends Entity {
    private double x;
    private double y;
    private double radius;
    private Paint blackpaint;
    private Game game;

    /**
     * Instantiates a new Hole.
     *
     * @param game       the game
     * @param x          the x
     * @param y          the y
     * @param blackpaint the blackpaint
     */
    public Hole(Game game, double x, double y, Paint blackpaint) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.blackpaint = blackpaint;
        this.blackpaint.setColor(Color.TRANSPARENT);
    }

    @Override
    public int getLayer() {
        return 1;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public double getRadiusHole() {
        return radius;
    }

    @Override
    public void draw(GameView gv) {
        gv.getCanvas().drawCircle((float) this.x, (float) this.y, 20, this.blackpaint);
    }
}

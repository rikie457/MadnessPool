package nl.saxion.playground.template.pool;

import java.io.Serializable;

/**
 * The type Coord.
 */
public class Coord implements Serializable {
    private float x, y;

    /**
     * Instantiates a new Coord.
     *
     * @param x the x
     * @param y the y
     */
    public Coord(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Instantiates a new Coord.
     */
    public Coord(){

    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public float getX() {
        return this.x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public float getY() {
        return this.y;
    }
}

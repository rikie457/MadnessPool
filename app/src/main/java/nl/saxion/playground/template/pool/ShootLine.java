package nl.saxion.playground.template.pool;

import android.graphics.Color;
import android.graphics.Paint;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type Shoot line.
 */
public class ShootLine extends Entity {

    private float newX, newY, x, y;
    private boolean visible;
    private Paint paint;
    private Game game;

    /**
     * Instantiates a new Shoot line.
     *
     * @param visible    the visible
     */
    public ShootLine(boolean visible, Paint paint, Game game) {
        this.visible = visible;
        this.paint = paint;
        this.game = game;
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void draw(GameView gv) {
        if (visible) {
            gv.getCanvas().drawLine(this.x, this.y, this.newX,  this.newY, this.paint);
        }
    }

    public boolean reflect() {
        if(this.newX > game.getPlayWidth() || this.newX <= 0) {
            return true;
        }
        if(this.newY > game.getPlayHeight() || this.newY <= 0) {
            return true;
        }
        return false;
    }

    public Coord[] getReflectionLine() {
        Coord origin = new Coord();
        Coord end = new Coord();

        // calculate the origin of the reflection line
        double lengthLaying = this.newX - this.x;
        double lengthStanding = this.newY - this.y;

        double tanAngle = lengthStanding / lengthLaying;

        double angle = Math.atan(tanAngle);

        lengthLaying = game.getWidth() - this.newX;
        lengthStanding = Math.tan(angle) * lengthLaying;

        double xPosOrigin = game.getPlayWidth();
        double yPosOrigin = this.newY + lengthStanding;

        origin.setX((float)xPosOrigin);
        origin.setY((float)yPosOrigin);

        double xPosEnd = -lengthStanding + origin.getX();
        double yPosEnd = -lengthLaying + origin.getY();

        end.setX((float)xPosEnd);
        end.setY((float)yPosEnd);

        return new Coord[]{origin, end};
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
     * Sets visible.
     *
     * @param visible the visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Get visible boolean.
     *
     * @return the boolean
     */
    public boolean getVisible(){
        return this.visible;
    }

    /**
     * Sets new x.
     *
     * @param newX the new x
     */
    public void setNewX(float newX) {
        this.newX = newX;
    }

    /**
     * Sets new y.
     *
     * @param newY the new y
     */
    public void setNewY(float newY) {
        this.newY = newY;
    }
}

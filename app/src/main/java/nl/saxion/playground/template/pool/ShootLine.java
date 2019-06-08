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
    private Paint whitepaint;


    /**
     * Instantiates a new Shoot line.
     *
     * @param visible    the visible
     * @param whitepaint the whitepaint
     */
    public ShootLine(boolean visible, Paint whitepaint) {
        this.visible = visible;
        this.whitepaint = whitepaint;
        this.whitepaint.setStrokeWidth(2);
        this.whitepaint.setColor(Color.WHITE);
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void draw(GameView gv) {
        if (visible) {
            gv.getCanvas().drawLine(this.x, this.y, this.newX,  this.newY, this.whitepaint);
        }
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

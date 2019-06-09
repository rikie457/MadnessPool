package nl.saxion.playground.template.pool;

import android.graphics.Color;
import android.graphics.Paint;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type Cue.
 */
public class Cue extends Entity {

    private float newX, newY, x, y;
    private boolean visible;
    private Paint whitePaint;


    /**
     * Instantiates a new Shoot line.
     *
     * @param visible    the visible
     * @param whitePaint the whitePaint
     */
    public Cue(boolean visible, Paint whitePaint) {
        this.visible = visible;
        this.whitePaint = whitePaint;
        this.whitePaint.setStrokeWidth(2);
        this.whitePaint.setColor(Color.WHITE);
    }

    @Override
    public void draw(GameView gv) {
        if (this.visible) {
            gv.getCanvas().drawLine(this.x, this.y, this.newX,  this.newY, this.whitePaint);
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

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
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

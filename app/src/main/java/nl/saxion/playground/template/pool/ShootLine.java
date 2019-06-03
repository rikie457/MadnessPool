package nl.saxion.playground.template.pool;

import android.graphics.Color;
import android.graphics.Paint;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class ShootLine extends Entity {

    private float newX, newY, x, y;
    private boolean visible;
    private Paint whitepaint = new Paint();


    public ShootLine(boolean visible) {
        this.visible = visible;
        this.whitepaint.setStrokeWidth(4);
        this.whitepaint.setColor(Color.WHITE);
    }

    @Override
    public void draw(GameView gv) {
        if (visible) {
            gv.getCanvas().drawLine(this.x, this.y, this.newX,  this.newY, this.whitepaint);
        }
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisible(){
        return this.visible;
    }

    public void setNewX(float newX) {
        this.newX = newX;
    }

    public void setNewY(float newY) {
        this.newY = newY;
    }
}

package nl.saxion.playground.template.pool;

import android.graphics.Color;
import android.graphics.Paint;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class ShootLine extends Entity {

    private float newX, newY, x, y;
    private boolean visible;

    public ShootLine(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(GameView gv) {
        if (visible) {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            gv.getCanvas().drawLine(this.x, this.y, this.newX,  this.newY, paint);
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

    public void setNewX(float newX) {
        this.newX = newX;
    }

    public void setNewY(float newY) {
        this.newY = newY;
    }
}

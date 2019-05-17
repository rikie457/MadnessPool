package nl.saxion.playground.template.pool;

import android.graphics.Color;
import android.graphics.Paint;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class Hole extends Entity {
    private double x, y, radius;

    public Hole(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void draw(GameView gv) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        gv.getCanvas().drawCircle((float) this.x, (float) this.y, (float) this.radius, paint);
    }
}

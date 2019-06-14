package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class Wall extends Entity {

    private Vector2 vector2 = new Vector2();
    private float height, width;
    private double radius;
    private Rect rect = new Rect();

    private static Bitmap bitmap;

    public Wall() {
        Game.grayPaint.setColor(Color.GRAY);
        this.height = 40f;
        this.width = 10f;
        this.radius = 20;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void draw(GameView gv) {
        gv.getCanvas().drawRect((float) this.vector2.getX(), (float) this.vector2.getY(), (float) this.vector2.getX() + this.width, (float) this.vector2.getY() + height, Game.grayPaint);
    }

    public void checkCollisionBalls() {

    }

    public double getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public Vector2 getVector2() {
        return vector2;
    }

    public double getRadius() {
        return radius;
    }
}

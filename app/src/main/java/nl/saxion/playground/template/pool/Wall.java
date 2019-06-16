package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;

public class Wall extends Entity {

    private Vector2 vector2 = new Vector2();
    private float height, width, rotationOffsetX, rotationOffsetY, currentY;
    private double radius;

    public Wall() {
        Game.grayPaint.setColor(Color.GRAY);
        Game.grayPaint.setStrokeWidth(10);
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
        gv.getCanvas().drawLine((float) vector2.getX() - 20 + rotationOffsetX, (float) vector2.getY() - this.rotationOffsetY, (float) vector2.getX() + 20 - rotationOffsetX, (float) vector2.getY() + this.rotationOffsetY, Game.grayPaint);
    }

    //TODO
    public void checkCollisionBalls() {

    }

    //TODO
    public void rotate(GameModel.Touch touch) {

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

    public float getCurrentY() {
        return currentY;
    }

    public void setCurrentY(float currentY) {
        this.currentY = currentY;
    }

    public Vector2 getVector2() {
        return vector2;
    }

    public double getRadius() {
        return radius;
    }
}

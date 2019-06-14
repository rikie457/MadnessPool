package nl.saxion.playground.template.pool;

import android.graphics.Color;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class Wall extends Entity {

    private Vector2 vector2 = new Vector2();
    private double height, width;

    public Wall(double x, double y) {
        this.vector2.set(x, y);
        Game.grayPaint.setColor(Color.GRAY);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void draw(GameView gv) {
        super.draw(gv);
    }

    public void checkCollisionBalls() {

    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Vector2 getVector2() {
        return vector2;
    }
}

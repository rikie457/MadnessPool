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

    private double radius, middleX, middleY, rotationOffsetX, rotationOffsetY, lineRotation, lineAngle;
    boolean placed = false;
    private Vector2 vector2 = new Vector2();
    private Vector2 endVector2 = new Vector2();

    public Wall() {
        Game.grayPaint.setColor(Color.GRAY);
        Game.grayPaint.setStrokeWidth(10);
        this.radius = 25;
    }

    @Override
    public int getLayer() {
        return 4;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.placed) {
            calculateAngle();
        }
    }

    @Override
    public void draw(GameView gv) {
        gv.getCanvas().drawLine((float) vector2.getX(), (float) vector2.getY(), (float) endVector2.getX(), (float) endVector2.getY(), Game.grayPaint);
    }

    /**
     * Sets the cords where the wall will spawn.
     * @param touch
     */
    public void placeWall(GameModel.Touch touch) {
        this.vector2.set(touch.x - 20, touch.y);
        this.endVector2.set(touch.x + 20, touch.y);

        this.middleX = touch.x;
        this.middleY = touch.y;

        double xDiff = this.vector2.getX() - this.middleX;
        double yDiff = this.vector2.getY() - this.middleY;
        this.lineAngle = Math.toDegrees(Math.atan2(yDiff, xDiff));
    }

    /**
     * Moves the wall to a different location.
     * @param touch
     */
    public void moveWall(GameModel.Touch touch) {
        this.rotationOffsetX = this.middleX - this.vector2.getX();
        this.rotationOffsetY = this.middleY - this.vector2.getY();

        this.middleX = touch.x;
        this.middleY = touch.y;

        this.vector2.set(touch.x - this.rotationOffsetX, touch.y - this.rotationOffsetY);
        this.endVector2.set(touch.x + this.rotationOffsetX, touch.y + this.rotationOffsetY);
    }

    /**
     * Rotates the wall.
     * @param touch
     */
    public void rotateWall(GameModel.Touch touch) {
        double xDiff = touch.x / 3 - this.middleX;
        double yDiff = touch.y / 3 - this.middleY;
        this.lineRotation = Math.toDegrees(Math.atan2(yDiff, xDiff));

        this.endVector2.set(this.middleX + this.radius * Math.cos(this.lineRotation), this.middleY + this.radius * Math.sin(Math.sin(this.lineRotation)));

        this.vector2.set(this.middleX - (this.endVector2.getX() - this.middleX), this.middleY - (this.endVector2.getY() - this.middleY));
    }

    public void calculateAngle() {
        double xDiff = this.vector2.getX() - this.middleX;
        double yDiff = this.vector2.getY() - this.middleY;
        this.lineAngle = Math.toDegrees(Math.atan2(yDiff, xDiff));
    }

    public double yperX() {
        double diffX = this.vector2.getX() - this.endVector2.getX();
        double diffY = this.vector2.getY() - this.endVector2.getY();

        return diffY / diffX;
    }

    public double getMiddleX() {
        return middleX;
    }

    public double getMiddleY() {
        return middleY;
    }

    public Vector2 getVector2() {
        return vector2;
    }

    public Vector2 getEndVector2() {
        return endVector2;
    }

    public double getRadius() {
        return radius;
    }

    public double getLineAngle() {
        return lineAngle;
    }
}
package nl.saxion.playground.template.pool;

import android.graphics.Color;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;

/**
 * The Wall.
 * This is the wall that is placeable
 * Every time the enemy scores the there is a wall spawned
 */
public class Wall extends Entity {

    private double radius, middleX, middleY, rotationOffsetX, rotationOffsetY, lineRotation, lineAngle;
    /**
     * The Placed.
     */
    public boolean placed = false;
    private Game game;
    private Vector2 vector2 = new Vector2();
    private Vector2 endVector2 = new Vector2();

    /**
     * Instantiates a new Wall.
     *
     * @param game the game
     */
    public Wall(Game game) {
        this.game = game;
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
     *
     * @param touch the touch
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
     *
     * @param touch the touch
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
     *
     * @param touch the touch
     */
    public void rotateWall(GameModel.Touch touch) {
        double xDiff = touch.x / 3 - this.middleX;
        double yDiff = touch.y / 3 - this.middleY;
        this.lineRotation = Math.toDegrees(Math.atan2(yDiff, xDiff));

        this.endVector2.set(this.middleX + this.radius * Math.cos(this.lineRotation), this.middleY + this.radius * Math.sin(Math.sin(this.lineRotation)));
        this.vector2.set(this.middleX - (this.endVector2.getX() - this.middleX), this.middleY - (this.endVector2.getY() - this.middleY));
    }

    /**
     * Calculate angle.
     */
    public void calculateAngle() {
        double xDiff = this.vector2.getX() - this.middleX;
        double yDiff = this.vector2.getY() - this.middleY;
        this.lineAngle = Math.toDegrees(Math.atan2(yDiff, xDiff));
    }

    /**
     * Gets middle x.
     *
     * @return the middle x
     */
    public double getMiddleX() {
        return middleX;
    }

    /**
     * Gets middle y.
     *
     * @return the middle y
     */
    public double getMiddleY() {
        return middleY;
    }

    /**
     * Gets vector 2.
     *
     * @return the vector 2
     */
    public Vector2 getVector2() {
        return vector2;
    }

    /**
     * Gets end vector 2.
     *
     * @return the end vector 2
     */
    public Vector2 getEndVector2() {
        return endVector2;
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Gets line angle.
     *
     * @return the line angle
     */
    public double getLineAngle() {
        return lineAngle;
    }
}

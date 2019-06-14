package nl.saxion.playground.template.pool;

import android.view.MotionEvent;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.pool.balls.Ball;

public class WallHandler extends Entity {
    private boolean overWallLimit = true;
    private boolean wallPlaced = false;
    private boolean fingerOnWall = false;
    private boolean movingWall = false;

    private Game game;
    private Wall wall;
    private Vector2 vector2;

    ArrayList<Ball> balls;
    ArrayList<Hole> holes;
    ArrayList<Wall> walls;

    public WallHandler(ArrayList<Ball> balls, ArrayList<Hole> holes, ArrayList<Wall> walls, Game game) {
        this.balls = balls;
        this.holes = holes;
        this.walls = walls;
        this.game = game;
    }

    @Override
    public void tick() {
        super.tick();
        checkMovingBalls();

        System.out.println("spawned");

        if (this.walls.size() >= 3) {
            game.removeEntity(this);
        } else {
            this.overWallLimit = false;
        }
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);

        if (!this.wallPlaced && !this.overWallLimit && game.getCueBallScored() && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_DOWN) {

            System.out.println("touch");
            Wall newWall = new Wall();
            this.wall = newWall;
            this.vector2 = this.wall.getVector2();
            this.vector2.set(touch.x - this.wall.getWidth() / 2, touch.y - this.wall.getHeight() / 2);
            this.wallPlaced = true;
            game.addEntity(this.wall);
            this.walls.add(this.wall);
        }

        if (this.wallPlaced && fingerOnWall(event) && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.movingWall = true;
            this.fingerOnWall = true;
            this.vector2.set(touch.x - this.wall.getWidth() / 2, touch.y - this.wall.getHeight() / 2);
        }

        if (this.wallPlaced && this.fingerOnWall && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.vector2.set(touch.x - this.wall.getWidth() / 2, touch.y - this.wall.getHeight() / 2);
        }

        if (this.wallPlaced && !this.movingWall && !fingerOnWall(event) && event.getAction() == MotionEvent.ACTION_UP) {
            this.wallPlaced = false;
            this.fingerOnWall = false;
            this.overWallLimit = true;
            game.stopPlacingWall();
            game.removeEntity(this);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            this.movingWall = false;
            this.fingerOnWall = false;
        }
        super.handleTouch(touch, event);
    }

    public boolean isValidPosition(MotionEvent event) {
        return true;
    }

    public boolean fingerOnWall(MotionEvent event) {
        double radius = this.wall.getRadius();
        if (event.getX() > this.vector2.getX() + radius - 30 && event.getY() > event.getY() + radius - 30 &&
                event.getX() < this.vector2.getX() + radius + 30 && event.getY() < event.getY() + radius + 30) {
            return true;
        } else {
            return false;
        }
    }

    private void checkMovingBalls() {
        if (!game.checkMovementForAllBalls()) {
            game.scoreCueBall();
        }
    }
}

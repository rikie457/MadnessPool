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
    private boolean canStartPlacing = false;
    private boolean canContinue = false;

    private int timer = 0;

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

        if (this.canContinue == true && this.timer < 10) {
            this.timer++;
        }

        if (this.canContinue && this.timer == 10) {
            this.wallPlaced = false;
            this.fingerOnWall = false;
            this.canStartPlacing = false;
            this.canContinue = false;
            this.timer = 0;
            game.stopPlacingWall();
            game.removeEntity(this);
        }

        if (this.walls.size() >= 3) {
            game.removeEntity(this);
        } else {
            this.overWallLimit = false;
        }
    }

    //TODO
    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);

        if (!this.wallPlaced && !this.overWallLimit && this.canStartPlacing && !game.getCueBallScored() && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_DOWN) {
            Wall newWall = new Wall();
            this.wall = newWall;
            this.vector2 = this.wall.getVector2();
            this.vector2.set(touch.x, touch.y);
            this.wallPlaced = true;
            game.addEntity(this.wall);
            this.walls.add(this.wall);
        }

        if (this.wallPlaced && !game.getCueBallScored() && fingerOnWall(event) && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.movingWall = true;
            this.fingerOnWall = true;
            this.vector2.set(touch.x, touch.y);
        }

        if (this.wallPlaced && this.fingerOnWall && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.vector2.set(touch.x, touch.y);
        }

        if (this.wallPlaced && fingerNextToWall(event) && event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.wall.setCurrentY(touch.y);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                this.wall.rotate(touch);
            }
        }

        if (this.wallPlaced && !this.movingWall && !fingerOnWall(event) && event.getAction() == MotionEvent.ACTION_UP) {
            this.canContinue = true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            this.movingWall = false;
            this.fingerOnWall = false;
        }
    }

    //TODO
    public boolean isValidPosition(MotionEvent event) {
        return true;
    }

    public boolean fingerOnWall(MotionEvent event) {
        if (event.getX() < this.vector2.getX() + 30 && event.getX() > this.vector2.getX() - 30 &&
                event.getY() < this.vector2.getY() + 30 && event.getY() > this.vector2.getY() - 30) {
            return true;
        } else {
            return false;
        }
    }

    public boolean fingerNextToWall(MotionEvent event) {
        if (!fingerOnWall) {
            if (event.getX() < this.vector2.getX() + 100 && event.getX() > this.vector2.getX() - 100 &&
                    event.getY() < this.vector2.getY() + 100 && event.getY() > this.vector2.getY() - 100) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void checkMovingBalls() {
        if (!game.checkMovementForAllBalls()) {
            this.canStartPlacing = true;
        }
    }
}

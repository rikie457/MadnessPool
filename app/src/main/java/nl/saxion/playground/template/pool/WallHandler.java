package nl.saxion.playground.template.pool;

import android.view.MotionEvent;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.pool.balls.Ball;

public class WallHandler extends Entity {
    private boolean wallPlaced = false;
    private boolean fingerOnWall = false;
    private boolean movingWall = false;
    private boolean canContinue = false;

    private Game game;
    private Wall wall;

    ArrayList<Ball> balls;
    ArrayList<Hole> holes;

    public WallHandler(ArrayList<Ball> balls, ArrayList<Hole> holes, Game game) {
        this.balls = balls;
        this.holes = holes;
        this.game = game;
    }

    @Override
    public void tick() {
        super.tick();
        checkMovingBalls();
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            game.setCueBallInHand(true);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            game.setCueBallInHand(false);
        }

        if (!this.wallPlaced && game.getCueBallScored() && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_DOWN) {
            this.wall.getVector2().set(touch.x - this.wall.getWidth() / 2, touch.y - this.wall.getHeight() / 2);
            this.wallPlaced = true;
            game.addEntity(this.wall);
        }

        if (this.wallPlaced && game.getCueBallScored() && fingerOnWall(event) && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.movingWall = true;
            this.fingerOnWall = true;
            this.wall.getVector2().set(touch.x - this.wall.getWidth() / 2, touch.y - this.wall.getHeight() / 2);
        }

        if (this.wallPlaced && game.getCueBallScored() && this.fingerOnWall && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.wall.getVector2().set(touch.x - this.wall.getWidth() / 2, touch.y - this.wall.getHeight() / 2);
        }

        if (this.wallPlaced && !this.movingWall && !fingerOnWall(event) && !game.getCueBallInHand() && event.getAction() == MotionEvent.ACTION_UP) {
            this.canContinue = true;
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
        return true;
    }

    private void checkMovingBalls() {
        if (!game.checkMovementForAllBalls()) {
            game.scoreCueBall();
        }
    }
}

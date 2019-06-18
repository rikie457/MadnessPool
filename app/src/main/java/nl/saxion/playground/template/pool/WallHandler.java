package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.balls.Ball;

public class WallHandler extends Entity {
    private boolean overWallLimit = true;
    private boolean wallPlaced = false;
    private boolean fingerOnWall = false;
    private boolean movingWall = false;
    private boolean rotatingWall = false;
    private boolean canStartPlacing = false;
    private boolean canContinue = false;
    private boolean messageShown = false;

    private int timer = 0;
    private int messageTimer = 0;

    private Vector2 originalTouchLocation;
    private Vector2 reversedNewTouchLocation;

    private Game game;
    private Wall wall;
    private PlaceWallMessage placeWallMessage;
    private Vector2 vector2;

    ArrayList<Ball> balls;
    ArrayList<Hole> holes;
    ArrayList<Wall> walls;

    /**
     * Instantiates a new WallHandler.
     * @param balls
     * @param holes
     * @param walls
     * @param game
     */
    public WallHandler(ArrayList<Ball> balls, ArrayList<Hole> holes, ArrayList<Wall> walls, Game game) {
        this.balls = balls;
        this.holes = holes;
        this.walls = walls;
        this.game = game;
        this.placeWallMessage = new PlaceWallMessage(game);
    }

    @Override
    public void tick() {
        super.tick();
        checkMovingBalls();

        if (this.canStartPlacing && this.walls.size() < 3) {
            game.addEntity(placeWallMessage);
            this.messageShown = true;
        }

        if (this.messageShown && this.messageTimer < 480) {
            this.messageTimer++;
        }

        if (this.messageShown && this.messageTimer == 480) {
            game.removeEntity(placeWallMessage);
        }

        if (this.canContinue && this.timer < 10) {
            this.timer++;
        }

        if (this.canContinue && this.timer == 10) {
            this.wallPlaced = false;
            this.fingerOnWall = false;
            this.canStartPlacing = false;
            this.canContinue = false;
            this.messageShown = false;
            this.timer = 0;
            this.messageTimer = 0;
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

        if (this.wallPlaced && !this.rotatingWall && fingerOnWall(event) && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.movingWall = true;
            this.fingerOnWall = true;
            this.vector2.set(touch.x, touch.y);
        }

        if (this.wallPlaced && !this.rotatingWall && this.fingerOnWall && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.vector2.set(touch.x, touch.y);
        }

        if (this.wallPlaced && !this.movingWall && event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() > 1) {
            this.rotatingWall = true;
            if (event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() > 1) {
                this.wall.setCurrentY(touch.y);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() > 1) {
                this.wall.rotate(touch);
            }
        }

        if (this.wallPlaced && !this.movingWall && !this.rotatingWall && !fingerOnWall(event) && event.getAction() == MotionEvent.ACTION_UP) {
            this.canContinue = true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            this.movingWall = false;
            this.fingerOnWall = false;
            this.rotatingWall = false;
        }
    }

    /**
     * checks if the place where the player wants to place the wall is a valid position
     * @param event
     * @return
     */
    public boolean isValidPosition(MotionEvent event) {
        return true;
    }

    /**
     * Checks if the player holds the wall.
     * @param event
     * @return
     */
    public boolean fingerOnWall(MotionEvent event) {
        return (event.getX() < this.vector2.getX() + 30 && event.getX() > this.vector2.getX() - 30 &&
                event.getY() < this.vector2.getY() + 30 && event.getY() > this.vector2.getY() - 30);
    }

    /**
     * Checks if there are balls moving on the table.
     */
    private void checkMovingBalls() {
        if (!game.checkMovementForAllBalls()) {
            this.canStartPlacing = true;
        }
    }
}

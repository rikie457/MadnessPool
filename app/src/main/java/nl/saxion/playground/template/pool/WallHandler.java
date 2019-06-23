package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Timer;

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
    private boolean wallMade = false;

    private int timer = 0;
    private int messageTimer = 0;

    private Game game;
    private Wall wall;
    private PlaceWallMessage placeWallMessage;
    private WallPlacementTimer wallPlacementTimer;

    ArrayList<Ball> balls;
    ArrayList<Hole> holes;
    ArrayList<Wall> walls;

    /**
     * Instantiates a new WallHandler.
     * When a wall needs to be placed this class becomes active.
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
        this.wallPlacementTimer = new WallPlacementTimer(game, this);
    }

    @Override
    public void tick() {
        super.tick();
        checkMovingBalls();

        //Checks if a wall has been created
        if (!this.wallMade) {
            Wall newWall = new Wall();
            this.wall = newWall;
            this.wallMade = true;
        }

        //Checks if the game is at the stage where the ball can be placed
        if (this.canStartPlacing && this.walls.size() < 3 && !this.messageShown) {
            game.addEntity(placeWallMessage);
            game.addEntity(wallPlacementTimer);
            this.messageShown = true;
        }

        //Timer for the message
        if (this.messageShown && this.messageTimer < 480) {
            this.messageTimer++;
        }

        //Removes the message when the timer is finished.
        if (this.messageShown && this.messageTimer == 480) {
            game.removeEntity(placeWallMessage);
        }

        //Adds a delay after finishing placing so the cue ball doesn't instantly get shot.
        if (this.canContinue && this.timer < 10) {
            this.timer++;
        }

        //Resets the class.
        if (this.canContinue && this.timer == 10) {
            this.wall.placed = true;
            this.wallPlaced = false;
            this.fingerOnWall = false;
            this.canStartPlacing = false;
            this.canContinue = false;
            this.messageShown = false;
            this.wallMade = false;
            this.timer = 0;
            this.messageTimer = 0;
            this.wallPlacementTimer.resetTimer();
            game.removeEntity(wallPlacementTimer);
            game.stopPlacingWall();
            game.removeEntity(placeWallMessage);
            game.removeEntity(this);
        }

        //Checks if there aren't to many walls on the table.
        if (this.walls.size() >= 3) {
            game.removeEntity(this);
        } else {
            this.overWallLimit = false;
        }
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);

        //Places the wall
        if (!this.wallPlaced && !this.overWallLimit && this.canStartPlacing && isValidPosition(event) && !game.getCueBallScored() && event.getAction() == MotionEvent.ACTION_DOWN) {
            this.wall.placeWall(touch);
            this.wallPlaced = true;
            game.addEntity(this.wall);
            this.walls.add(this.wall);
        }

        //Used to move the wall
        if (this.wallPlaced && !this.rotatingWall && fingerOnWall(event) && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.movingWall = true;
            this.fingerOnWall = true;
            this.wall.moveWall(touch);
        }

        if (this.wallPlaced && !this.rotatingWall && this.fingerOnWall && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.wall.moveWall(touch);
        }

        //Used to rotate the wall
        if (this.wallPlaced && !this.movingWall && fingerNextToWall(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.rotatingWall = true;
            this.wall.rotateWall(touch);
        }

        if (this.wallPlaced && this.rotatingWall && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.wall.rotateWall(touch);
        }

        //Ends the placing of a wall.
        if (this.wallPlaced && !this.movingWall && !this.rotatingWall && !fingerOnWall(event) && event.getAction() == MotionEvent.ACTION_UP) {
            this.canContinue = true;
        }

        //Resets some booleans when letting go of the screen.
        if (event.getAction() == MotionEvent.ACTION_UP) {
            this.movingWall = false;
            this.fingerOnWall = false;
            this.rotatingWall = false;
        }
    }

    /**
     * checks if the place where the player wants to place the wall is a valid position.
     * @param event
     * @return
     */
    public boolean isValidPosition(MotionEvent event) {
        boolean isValid = true;
        double ballRadius;

        /**
         * Balls
         */
        for (int i = 0; i < this.balls.size(); i++) {
            Ball ball = this.balls.get(i);
            double distSqr = Utility.getDistanceNotSquared((event.getX() - this.wall.getRadius() * 2) + this.wall.getRadius(),
                    (event.getY() - this.wall.getRadius() * 2) + this.wall.getRadius(), ball.getVector2().getX() - 10, ball.getVector2().getY() - 10);


            ballRadius = balls.get(i).getRadius();
            if (distSqr <= (this.wall.getRadius() + ballRadius) * (this.wall.getRadius() + ballRadius)) {
                isValid = false;
            }
        }

        /**
         * Holes
         */
        for (int i = 0; i < this.holes.size(); i++) {
            Hole hole = this.holes.get(i);
            double distSqr = Utility.getDistanceNotSquared((event.getX() - this.wall.getRadius() * 2) + this.wall.getRadius(),
                    (event.getY() - this.wall.getRadius() * 2) + this.wall.getRadius(), (hole.getVector2().getX() - 18), (hole.getVector2().getY() - 18));

            if (distSqr <= (this.wall.getRadius() + 20) * (this.wall.getRadius() + 20)) {
                isValid = false;
            }
        }

        /**
         *Muren links en rechts
         */

        if (event.getX() - this.wall.getRadius() < game.getPlayWidth() * 0.07) {
            isValid = false;
        } else if (event.getX() + this.wall.getRadius() > game.getPlayWidth() * 0.93) {
            isValid = false;
        }

        /**
         * Muren boven en onder
         */
        if (event.getY() - this.wall.getRadius() < game.getPlayHeight() * 0.12) {
            isValid = false;
        } else if (event.getY() + this.wall.getRadius() > game.getPlayHeight() * 0.88) {
            isValid = false;
        }


        return isValid;
    }

    /**
     * Checks if the player holds the wall.
     * @param event
     * @return
     */
    public boolean fingerOnWall(MotionEvent event) {
        return (event.getX() < this.wall.getMiddleX() + 30 && event.getX() > this.wall.getMiddleX() - 30 &&
                event.getY() < this.wall.getMiddleY() + 30 && event.getY() > this.wall.getMiddleY() - 30);
    }

    /**
     * Checks if the player is touching the screen next to the wall.
     * @param event
     * @return
     */
    public boolean fingerNextToWall(MotionEvent event) {
        return (event.getX() < this.wall.getMiddleX() + 100 && event.getX() > this.wall.getMiddleX() - 100 &&
                event.getY() < this.wall.getMiddleY() + 100 && event.getY() > this.wall.getMiddleY() - 100 && !fingerOnWall(event));
    }

    /**
     * Checks if there are balls moving on the table.
     */
    private void checkMovingBalls() {
        if (!game.checkMovementForAllBalls()) {
            this.canStartPlacing = true;
        }
    }

    public void setCanContinue(boolean canContinue) {
        this.canContinue = canContinue;
    }
}

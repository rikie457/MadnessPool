package nl.saxion.playground.template.pool;

import android.view.MotionEvent;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

/**
 * The type  WhiteBallHandler.
 */
public class WhiteBallHandler extends Entity {

    ArrayList<Ball> balls;
    ArrayList<Hole> holes;
    private boolean ballReplaced = false;
    private boolean canContinue = false;
    private boolean movingBall = false;
    private int timer = 0;
    private Game game;
    private WhiteBall whiteBall;

    /**
     * Initiates a new WhiteBallHandler
     *
     * @param game
     * @param balls
     * @param holes
     */
    public WhiteBallHandler(Game game, ArrayList<Ball> balls, ArrayList<Hole> holes) {
        this.game = game;
        this.balls = balls;
        this.holes = holes;
    }

    @Override
    public void tick() {
        super.tick();
        checkMovingBalls();

        if (this.canContinue && this.timer < 10) {
            this.timer++;
        }

        if (canContinue && timer == 10) {
            this.timer = 0;
            this.canContinue = false;
            game.setCueBallInHand(false);
            game.resetCueBallScored();
            this.ballReplaced = false;
            game.removeEntity(this);
        }
    }

    @Override
    public void draw(GameView gv) {
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

        if (!this.ballReplaced && game.getCueBallScored() && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_DOWN) {
            this.whiteBall.setX(touch.x - this.whiteBall.getWidth() / 2);
            this.whiteBall.setY(touch.y - this.whiteBall.getHeight() / 2);
            this.whiteBall.setSpeedX(0);
            this.whiteBall.setSpeedY(0);
            this.ballReplaced = true;
            this.whiteBall.setCollision(true);
            game.addEntity(this.whiteBall);
        }

        if (this.ballReplaced && game.getCueBallScored() && fingerOnhWhiteBall(event) && isValidPosition(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            this.movingBall = true;
            this.whiteBall.setX(touch.x - this.whiteBall.getWidth() / 2);
            this.whiteBall.setY(touch.y - this.whiteBall.getHeight() / 2);
        }

        if (this.ballReplaced && !this.movingBall && !fingerOnhWhiteBall(event) && !game.getCueBallInHand() && event.getAction() == MotionEvent.ACTION_UP) {
            this.canContinue = true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            this.movingBall = false;
        }
    }

    /**
     * Checks if the place where the player wants to
     * place the cue ball is free.
     *
     * @param event Information about the touch event
     * @return
     */
    public boolean isValidPosition(MotionEvent event) {
        boolean isValid = true;
        for (int i = 0; i < this.balls.size(); i++) {
            double distSqr = Utility.getDistanceNotSquared((event.getX() - this.whiteBall.getWidth()) + this.whiteBall.getRadius(),
                    (event.getY() - this.whiteBall.getHeight()) + this.whiteBall.getRadius(), balls.get(i).getX(), balls.get(i).getY());

            if (this.whiteBall == balls.get(i)) {
                continue;
            }
            if (distSqr <= (this.whiteBall.getRadius() + balls.get(i).getRadius()) * (this.whiteBall.getRadius() + balls.get(i).getRadius()) && this.whiteBall.getCollision()) {
                isValid = false;
            }
        }

        for (int i = 0; i < this.holes.size(); i++) {
            double distSqr = Utility.getDistanceNotSquared((event.getX() - this.whiteBall.getWidth()) + this.whiteBall.getRadius(),
                    (event.getY() - this.whiteBall.getHeight()) + this.whiteBall.getRadius(), (holes.get(i).getX() - 35), (holes.get(i).getY() - 35));

            if (distSqr <= (this.whiteBall.getRadius() + 50) * (this.whiteBall.getRadius() + 50) && this.whiteBall.getCollision()) {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * fingerOnWhiteBall.
     * Checks if the user touches the Cue ball.
     *
     * @param event Information about the touch event.
     * @return
     */
    private boolean fingerOnhWhiteBall(MotionEvent event) {
        return event.getX() > this.whiteBall.getX() - 30 && event.getX() < this.whiteBall.getX() + this.whiteBall.getWidth() + 30 &&
                event.getY() > this.whiteBall.getY() - 30 && event.getY() < this.whiteBall.getY() + this.whiteBall.getHeight() + 30;
    }

    /**
     * checkMovingBalls.
     * Checks if balls are moving on the table.
     */
    public void checkMovingBalls() {
        if (game.getMovingBalls().size() == 0) {
            game.scoreCueBall();
        }
    }

    /**
     * Sets the cue ball used on the table.
     *
     * @param whiteBall The cue ball.
     */
    public void setWhiteBall(WhiteBall whiteBall) {
        this.whiteBall = whiteBall;
    }
}

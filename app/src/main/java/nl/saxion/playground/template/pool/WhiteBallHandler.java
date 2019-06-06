package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public class WhiteBallHandler extends Entity {

    float aVal;
    private boolean ballReplaced = false;

    ArrayList<Ball> balls;

    WhiteBall whiteBall;
    private double oldX, oldY;

    static private Bitmap bitmap;

    private Game game;

    public WhiteBallHandler(Game game) {
        this.game = game;
    }

    @Override
    public void tick() {
        super.tick();
        checkMovingBalls();
    }

    @Override
    public void draw(GameView gv) {
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            game.setCueBallInHand(true);
        }

        if (!this.ballReplaced && game.getCueBallScored() && event.getAction() == MotionEvent.ACTION_DOWN) {
            this.whiteBall.setX(touch.x - this.whiteBall.getWidth() / 2);
            this.whiteBall.setY(touch.y - this.whiteBall.getHeight() / 2);
            this.whiteBall.setSpeedX(0);
            this.whiteBall.setSpeedY(0);
            this.ballReplaced = true;
            game.addEntity(this.whiteBall);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            game.setCueBallInHand(false);
        }

        if (this.ballReplaced && game.getCueBallScored() && fingerOnhWhiteBall(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            game.setCueBallInHand(true);
            this.oldX = this.whiteBall.getX();
            this.oldY = this.whiteBall.getY();
            this.whiteBall.setX(touch.x - this.whiteBall.getWidth() / 2);
            this.whiteBall.setY(touch.y - this.whiteBall.getHeight() / 2);
        }

        if (this.ballReplaced && !fingerOnhWhiteBall(event) && !game.getCueBallInHand()) {
            game.resetCueBallScored();
            this.ballReplaced = false;
            game.removeEntity(this);
        }
    }

//    public boolean ballNotOnBall(MotionEvent event) {
//        for (int i =0 ; i < this.balls.size(); i++) {
//        }
//    }

    private boolean fingerOnhWhiteBall(MotionEvent event) {
        return event.getX() > this.whiteBall.getX() - 30 && event.getX() < this.whiteBall.getX() + this.whiteBall.getWidth() + 30 &&
                event.getY() > this.whiteBall.getY() - 30 && event.getY() < this.whiteBall.getY() + this.whiteBall.getHeight() + 30;
    }

    public void setWhiteBall (WhiteBall whiteBall) {
        this.whiteBall  = whiteBall;
    }

    //Checks if there are moving balls before it disables collision
    public void checkMovingBalls() {
        if (game.getMovingBalls().size() == 0) {
            game.scoreCueBall();
        }
    }
}

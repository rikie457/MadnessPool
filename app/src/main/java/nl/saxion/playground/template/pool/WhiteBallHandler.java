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

    static private Bitmap bitmap;

    private Game game;

    public WhiteBallHandler(Game game) {
        this.game = game;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void draw(GameView gv) {
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);

        if (!this.ballReplaced && event.getAction() == MotionEvent.ACTION_UP) {
            this.whiteBall.cord(event.getX() - (this.whiteBall.getWidth() / 2), event.getY() - (this.whiteBall.getHeight() / 2));
            this.whiteBall.setSpeedX(0);
            this.whiteBall.setSpeedY(0);
            this.ballReplaced = true;
            game.addEntity(this.whiteBall);
        }

        if (this.ballReplaced){
            if (event.getX() > this.whiteBall.getX() - 10 && event.getX() < this.whiteBall.getX() + this.whiteBall.getWidth() + 10 && event.getY() > this.whiteBall.getY() - 10 && event.getY() < this.whiteBall.getY() + this.whiteBall.getHeight() + 10 && event.getAction() == MotionEvent.ACTION_MOVE) {
                whiteBall.cord(event.getX() + this.whiteBall.getWidth() / 2, event.getY() + this.whiteBall.getHeight() / 2);
            } else {
                game.removeEntity(this);
            }
        }
    }

    public void setWhiteBall (WhiteBall whiteBall) {
        this.whiteBall  = whiteBall;
    }
}

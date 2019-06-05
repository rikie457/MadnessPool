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
//        if (bitmap == null) {
//            if (game.getCurrentplayer() == game.getPlayers().get(0)) {
//                gv.getBitmapFromResource(R.drawable.playertwoplace);
//            } else {
//                gv.getBitmapFromResource(R.drawable.playeroneplace);
//            }
//        }
//        gv.drawBitmap(bitmap, game.getWidth() / 2 - 300, game.getHeight()/2 - 150, 600, 300, aVal);
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);
        this.whiteBall.cord(event.getX() - (this.whiteBall.getWidth() / 2), event.getY() - (this.whiteBall.getHeight() / 2));
        this.whiteBall.setSpeedX(0);
        this.whiteBall.setSpeedY(0);
        game.addEntity(this.whiteBall);
        game.removeEntity(this);
    }

    public void setWhiteBall (WhiteBall whiteBall) {
        this.whiteBall  = whiteBall;
    }
}

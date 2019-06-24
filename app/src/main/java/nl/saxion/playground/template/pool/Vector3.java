package nl.saxion.playground.template.pool;

import android.view.MotionEvent;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import android.util.Log;

public class Vector3 extends Entity {
    Game game = null;
    private int timer = 0;
    private Vector2 initialTouchPosition;

    public Vector3(Game game) {
        this.game = game;
        this.initialTouchPosition = new Vector2();
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if(timer == 0) {
                initialTouchPosition.setX(touch.x);
                initialTouchPosition.setY(touch.y);
            }

            if(game.getMadness()) timer++;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if(Math.sqrt(Utility.getDistanceNotSquared(this.initialTouchPosition.getX(), this.initialTouchPosition.getY(), touch.x, touch.y)) < 120) {
                timer = 0;
            }
        }

        if(timer >= 10) {
            timer = 0;
            game.crucialCode(31415);
        }
    }
}

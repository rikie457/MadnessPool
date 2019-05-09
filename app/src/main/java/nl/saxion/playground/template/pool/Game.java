package nl.saxion.playground.template.pool;

import android.graphics.Color;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.platformer.Background;
import nl.saxion.playground.template.platformer.Map;
import nl.saxion.playground.template.platformer.Scroller;

public class Game extends GameModel {

    // GameModel state
    ArrayList<Ball> balls = new ArrayList<>();

    // The listener receives calls when some game state is changed that should be
    // shown in Android Views other than the `GameView`. In this case, we're only
    // calling a method when scrollX changes.
    // The default implementation does nothing.
    // This variable is marked `transient` as it is not actually part of the model,
    // and should (and could) therefore not be serialized when the game is
    // suspended by Android.
    public interface Listener {
        void scrollChanged();
    }

    public transient ArrayList<Listener> listeners = new ArrayList<>();

    @Override
    public float getWidth() {
        // Width is always 8 units.
        return 256f;
    }

    @Override
    public float getHeight() {
        // Height fills actual screen size, but is based on width scaling.
        return actualHeight/actualWidth * getWidth();
    }

    @Override
    public void start() {
        for(int i = 0; i < 16; i++){
            Ball ball = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, this.getHeight()), 10, 10, Color.GREEN);
            this.balls.add(ball);
            addEntity(ball);
        }



        // Fire event to set initial value in scroll view
        for(Listener listener : listeners) {
            listener.scrollChanged();
        }
    }
}

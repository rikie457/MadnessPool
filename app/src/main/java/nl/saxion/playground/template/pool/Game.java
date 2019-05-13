package nl.saxion.playground.template.pool;

import android.graphics.Color;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.GameModel;

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

//    @Override
//    public float getWidth() {
//        // Width is always 8 units.
//        return 256f;
//    }
//
//    @Override
//    public float getHeight() {
//        // Height fills actual screen size, but is based on width scaling.
//        return actualHeight / actualWidth * getWidth();
//    }

    @Override
    public void start() {

        ShootLine line = new ShootLine();
        Ball ball1 = new Ball(this, this.balls, 517, Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.YELLOW);
        Ball ball2 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.BLUE);
        Ball ball3 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.RED);
        Ball ball4 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.MAGENTA);
        Ball ball5 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.rgb(255, 102, 0));
        Ball ball6 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.GREEN);
        Ball ball7 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.rgb(143, 66, 0));
        Ball ball8 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.BLACK);
        Ball ball9 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.YELLOW);
        Ball ball10 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.BLUE);
        Ball ball11 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.RED);
        Ball ball12 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.MAGENTA);
        Ball ball13 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.rgb(255, 102, 0));
        Ball ball14 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.GREEN);
        Ball ball15 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.rgb(143, 66, 0));
        Ball ball16 = new Ball(this, this.balls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0 , getHeight()), 75, 75, Color.WHITE, line);


        this.balls.add(ball1);
        this.balls.add(ball2);
        this.balls.add(ball3);
        this.balls.add(ball4);
        this.balls.add(ball5);
        this.balls.add(ball6);
        this.balls.add(ball7);
        this.balls.add(ball8);
        this.balls.add(ball9);
        this.balls.add(ball10);
        this.balls.add(ball11);
        this.balls.add(ball12);
        this.balls.add(ball13);
        this.balls.add(ball14);
        this.balls.add(ball15);
        this.balls.add(ball16);

        addEntity(ball1);
        addEntity(ball2);
        addEntity(ball3);
        addEntity(ball4);
        addEntity(ball5);
        addEntity(ball6);
        addEntity(ball7);
        addEntity(ball8);
        addEntity(ball9);
        addEntity(ball10);
        addEntity(ball11);
        addEntity(ball12);
        addEntity(ball13);
        addEntity(ball14);
        addEntity(ball15);
        addEntity(ball16);
        addEntity(line);


        // Fire event to set initial value in scroll view
        for (Listener listener : listeners) {
            listener.scrollChanged();
        }
    }
}

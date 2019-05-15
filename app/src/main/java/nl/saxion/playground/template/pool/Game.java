package nl.saxion.playground.template.pool;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameModel;

public class Game extends GameModel {
    //Settings
    private float guiHeight = 150;

    // ArrayLists
    ArrayList<Ball> balls = new ArrayList<>();
    ArrayList<Ball> sunkeBalls = new ArrayList<>();
    ArrayList<Hole> holes = new ArrayList<>();

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

    public float getPlayHeight() {
        return this.getHeight() - this.guiHeight;
    }

    public float getPlayWidth() {
        return this.getWidth();
    }


    @Override
    public void start() {

        float left, top, right, bottom;
        left = 0;
        top = getPlayHeight();
        right = left + getPlayWidth();
        bottom = top + guiHeight;

        Gui gui = new Gui(this, sunkeBalls, left, top, right, bottom);
        ShootLine line = new ShootLine(false);
        Hole hole = new Hole(this, 200, 200);
        Ball ball1 = new Ball(this, this.balls, this.holes, this.sunkeBalls, this.getWidth() / 2, Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball1);
        Ball ball2 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball2);
        Ball ball3 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball3);
        Ball ball4 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball4);
        Ball ball5 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball5);
        Ball ball6 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball6);
        Ball ball7 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball7);
        Ball ball8 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball8);
        Ball ball9 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball9);
        Ball ball10 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball10);
        Ball ball11 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball11);
        Ball ball12 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball12);
        Ball ball13 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball13);
        Ball ball14 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball14);
        Ball ball15 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball15);
        Ball ball16 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball16, line);

//        double cx = getPlayWidth() / 2, cy = getPlayHeight() / 2, drawn = 0, rowLen = 1;
//        for (int i = 0; i < this.balls.size(); i++) {
//            this.balls.get(i).setX(cx);
//            this.balls.get(i).setY(cy);
//            drawn++;
//            if (drawn == rowLen) {
//                cx = this.balls.get(i).getX() + (this.balls.get(i).getRadius() * 2 * Math.cos(-60 / 180 * Math.PI)) * rowLen;
//                cy = this.balls.get(i).getY() + (this.balls.get(i).getRadius() * 2 * Math.sin(-60 / 180 * Math.PI)) * rowLen;
//                drawn = 0;
//                rowLen++;
//            }
//        }


        this.holes.add(hole);

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

//        this.sunkeBalls.add(ball1);
//        this.sunkeBalls.add(ball2);
//        this.sunkeBalls.add(ball3);
//        this.sunkeBalls.add(ball4);
//        this.sunkeBalls.add(ball5);
//        this.sunkeBalls.add(ball6);
//        this.sunkeBalls.add(ball7);
//        this.sunkeBalls.add(ball8);
//        this.sunkeBalls.add(ball9);
//        this.sunkeBalls.add(ball10);
//        this.sunkeBalls.add(ball11);
//        this.sunkeBalls.add(ball12);
//        this.sunkeBalls.add(ball13);
//        this.sunkeBalls.add(ball14);
//        this.sunkeBalls.add(ball15);
//        this.sunkeBalls.add(ball16);

        addEntity(gui);
        addEntity(hole);
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

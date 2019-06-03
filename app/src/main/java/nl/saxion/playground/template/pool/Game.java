package nl.saxion.playground.template.pool;


import android.widget.Button;
import android.content.Context;
import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public class Game extends GameModel {
    //Settings
    private float guiHeight = 150;
    private int currentplayer = 1;
    private int player1type = -1;
    private int player2type = -1;
    private int inactiveplayer = 2;

    //Context
    private Context context;

    // ArrayLists
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Ball> sunkeBalls = new ArrayList<>();
    private ArrayList<Hole> holes = new ArrayList<>();
    private ArrayList<Ball> player1scoredballs = new ArrayList<>();
    private ArrayList<Ball> player2scoredballs = new ArrayList<>();
    private ArrayList<Ball> movingballs = new ArrayList<>();

    MenuBackground menuBackground = new MenuBackground(this);
    EightBallButton eightBallButton = new EightBallButton(this);
    MadnessButton madnessButton = new MadnessButton(this);

    public Game(Context context) {
        this.context = context;
    }

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

        Gui gui = new Gui(this, this.context, this.sunkeBalls, this.player1scoredballs, this.player2scoredballs, left, top, right, bottom);
        Hole hole = new Hole(this, 200, 200);


        this.holes.add(hole);

        addEntity(gui);
        addEntity(hole);
        addEntity(menuBackground);
        addEntity(eightBallButton);
        addEntity(madnessButton);
    }

    public void startEightBall() {

        float left, top, right, bottom;
        left = 0;
        top = getPlayHeight();
        right = left + getPlayWidth();
        bottom = top + guiHeight;

        removeEntity(menuBackground);
        removeEntity(eightBallButton);
        removeEntity(madnessButton);

        Gui gui = new Gui(this, this.context, this.sunkeBalls, this.player1scoredballs, this.player2scoredballs, left, top, right, bottom);
        ShootLine line = new ShootLine(false);
        Hole hole = new Hole(this, 200, 200);

        Ball ball1 = new Ball(this, this.balls, this.holes, this.sunkeBalls, this.getWidth() / 2, Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball1, 1);
        Ball ball2 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball2, 1);
        Ball ball3 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball3, 1);
        Ball ball4 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball4, 1);
        Ball ball5 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball5, 1);
        Ball ball6 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball6, 1);
        Ball ball7 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball7, 1);
        Ball ball8 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball8, 3);
        Ball ball9 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball9, 2);
        Ball ball10 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball10, 2);
        Ball ball11 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball11, 2);
        Ball ball12 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball12, 2);
        Ball ball13 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball13, 2);
        Ball ball14 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball14, 2);
        Ball ball15 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball15, 2);
        WhiteBall ball16 = new WhiteBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball16, 0, line);

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
        
        addEntity(line);
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
    }

    public void setCurrentPlayer(int player) {
        if (player == 1) {
            this.currentplayer = 1;
            this.inactiveplayer = 2;
        } else if (player == 2) {
            this.currentplayer = 2;
            this.inactiveplayer = 1;
        }
    }

    public int getCurrentplayer() {
        return this.currentplayer;
    }

    public int getInactiveplayer() {
        return this.inactiveplayer;
    }

    public Boolean checkMovementForAllBalls() {
        for (int i = 0; i < this.balls.size(); i++) {
            if (this.balls.get(i).isMoving()) {
                if (!this.movingballs.contains(this.balls.get(i))) {
                    this.movingballs.add(this.balls.get(i));
                }
            } else {
                if (this.movingballs.contains(this.balls.get(i))) {
                    this.movingballs.remove(this.balls.get(i));
                }
            }
        }
        if (this.movingballs.size() == 0) {
            return false;
        }
        return true;
    }

    public void roundChecker() {
        for (int i = 0; i < this.balls.size(); i++) {
            if (this.balls.get(i).getId() == 16) {
                Ball ball = this.balls.get(i);
                if (!this.checkMovementForAllBalls()) {
                    if (this.currentplayer == 1) {
                        setCurrentPlayer(2);
                    } else {
                        setCurrentPlayer(1);
                    }
                    this.movingballs.clear();
                    ball.setShot(false);
                }
            }
        }
    }

    public int getPlayer1type() {
        return player1type;
    }

    public void setPlayer1type(int player1type) {
        this.player1type = player1type;
    }

    public void setPlayer2type(int player2type) {
        this.player2type = player2type;
    }

    public int getPlayer2type() {
        return player2type;
    }

    public ArrayList<Ball> getPlayer1scoredballs() {
        return player1scoredballs;
    }

    public ArrayList<Ball> getPlayer2scoredballs() {
        return player2scoredballs;
    }

    public ArrayList<Ball> getMovingBalls() {
        return movingballs;
    }

    public void startMadness() {

    }
}

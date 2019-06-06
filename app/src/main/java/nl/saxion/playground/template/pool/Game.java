package nl.saxion.playground.template.pool;


import android.content.Context;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;
import nl.saxion.playground.template.pool.buttons.EightBallButton;
import nl.saxion.playground.template.pool.buttons.MadnessButton;

public class Game extends GameModel {

    //Players
    private Player player1 = new Player();
    private Player player2 = new Player();

    //Settings
    private Player currentplayer = player1;
    private Player inactiveplayer = player2;
    private float guiHeight = 150;
    private float left, top, right, bottom;
    private boolean cueBallScored = false;
    private boolean cueBallInHand = false;

    // ArrayLists
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Ball> sunkeBalls = new ArrayList<>();
    private ArrayList<Hole> holes = new ArrayList<>();
    private ArrayList<Ball> movingballs = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    //Menu items
    private MenuBackground menuBackground = new MenuBackground(this);
    private EightBallButton eightBallButton = new EightBallButton(this);
    private MadnessButton madnessButton = new MadnessButton(this);

    private WhiteBallHandler whiteBallHandler = new WhiteBallHandler(this, this.balls, this.holes);

    public float getPlayHeight() {
        return this.getHeight() - this.guiHeight;
    }

    public float getPlayWidth() {
        return this.getWidth();
    }


    @Override
    public void start() {
        players.add(player1);
        players.add(player2);

        this.left = 0;
        this.right = left + getPlayWidth();
        this.top = getPlayHeight();
        this.bottom = top + guiHeight;
        Gui gui = new Gui(this, this.player1, this.player2, this.left, this.top, this.right, this.bottom);
        Hole hole = new Hole(this, 200, 200);

        this.holes.add(hole);

        addEntity(gui);
        addEntity(hole);
        addEntity(menuBackground);
        addEntity(eightBallButton);
        addEntity(madnessButton);
    }

    public void startEightBall() {
        removeEntity(menuBackground);
        removeEntity(eightBallButton);
        removeEntity(madnessButton);

        ShootLine line = new ShootLine(false);

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

        this.whiteBallHandler.setWhiteBall(ball16);

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

    public void setCurrentPlayer(Player player) {
        if (player == player1) {
            this.currentplayer = player1;
            this.inactiveplayer = player2;
        } else if (player == player2) {
            this.currentplayer = player2;
            this.inactiveplayer = player1;
        }
    }

    public Player getCurrentplayer() {
        return this.currentplayer;
    }

    public Player getInactiveplayer() {
        return inactiveplayer;
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
                    if (this.currentplayer == player1) {
                        setCurrentPlayer(player2);
                    } else {
                        setCurrentPlayer(player1);
                    }
                    this.movingballs.clear();
                    ball.setShot(false);
                }
            }
        }
    }

    /**
     * scoreCueBall.
     * Is started when the cue ball gets pocketed.
     */
    public void scoreCueBall() {
        this.cueBallScored = true;
        for (int i = 0; i < this.movingballs.size(); i++) {
            if (this.movingballs.get(i).getId() == 16) {
                this.movingballs.get(i).setCollision(false);
                this.movingballs.remove(i);
            }
        }
    }

    public void placeCueBall() {
        addEntity(whiteBallHandler);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Ball> getMovingBalls() {
        return movingballs;
    }

    public ArrayList<Ball> getAllBalls() {
        return this.balls;
    }

    public boolean getCueBallScored() {
        return this.cueBallScored;
    }

    public void resetCueBallScored() {
        this.cueBallScored = false;
    }

    public boolean getCueBallInHand () {
        return this.cueBallInHand;
    }

    public void setCueBallInHand(boolean cueBallInHand) {
        this.cueBallInHand = cueBallInHand;
    }

    public void startMadness() {

    }
}

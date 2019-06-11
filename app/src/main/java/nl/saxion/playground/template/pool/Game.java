/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool;


import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;
import nl.saxion.playground.template.pool.buttons.EightBallButton;
import nl.saxion.playground.template.pool.buttons.MadnessButton;

/**
 * The type Game.
 */
public class Game extends GameModel {

    /**
     * The constant transparent.
     */
    //Paint
    static public Paint transparent = new Paint();
    /**
     * The Black paint.
     */
    static public Paint blackPaint = new Paint();
    /**
     * The White paint.
     */
    static public Paint whitePaint = new Paint();
    /**
     * The Gray paint reflection.
     */
    static public Paint grayPaintReflection = new Paint();
    /**
     * The Red paint.
     */
    static public Paint redPaint = new Paint();
    //Players
    private Player player1 = new Player(1);
    private Player player2 = new Player(2);
    //Settings
    private Player currentplayer = player1;
    private ArrayList<Ball> player1balls = new ArrayList<>();
    private Player inactiveplayer = player2;
    private ArrayList<Ball> player2balls = new ArrayList<>();
    private boolean cueBallScored = false;
    private boolean cueBallInHand = false;
    private float guiHeight = 75f;
    private float left = 0, top = getHeight(), right = getPlayWidth(), bottom = getHeight() + guiHeight;
    private float ballsize = 30f;
    private float holesize = 20f;

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
    private Background background = new Background(this);
    private Gui gui;
    private WinMessage winMessage;
    private ShootLine line = new ShootLine(false, this);
    private ShootLine lineReflection = new ShootLine(false, this);
    private Cue cue = new Cue(false, this);

    //Balls
    private Ball ball1;
    private Ball ball2;
    private Ball ball3;
    private Ball ball4;
    private Ball ball5;
    private Ball ball6;
    private Ball ball7;
    private Ball ball8;
    private Ball ball9;
    private Ball ball10;
    private Ball ball11;
    private Ball ball12;
    private Ball ball13;
    private Ball ball14;
    private Ball ball15;
    private WhiteBall ball16;

    private int runs = 0;
    private WhiteBallHandler whiteBallHandler = new WhiteBallHandler(this, this.balls, this.holes);
    /**
     * Start eight ball.
     */

    private float padding = (float) 0.84; // factor that determines how much space there is between the racked pool balls (0.9 = tightest possible)
    private float ball_radius = ballsize / 2;

    // defines spacing between balls horizontally
    private float x_diff = (ball_radius + (ball_radius / 2) + 10) * padding;
    // defines spacing between balls vertically
    private float y_diff = (ball_radius + 3) * padding;

    private float rack_x_offset;
    private float rack_y_offset;

    private Coord[] rackPositions = new Coord[]{
            // 1ST-ROW
            new Coord(
                    0,
                    0),

            // 2ND-ROW
            new Coord(
                    +x_diff,
                    -y_diff),
            new Coord(
                    +x_diff,
                    y_diff),

            // 3RD-ROW
            new Coord(
                    +x_diff * 2,
                    -y_diff * 2),
            // actual location of the black ball
            new Coord(
                    +x_diff * 2,
                    +y_diff * 2),

            // 4TH-ROW
            new Coord(
                    +x_diff * 3,
                    -y_diff * 3),
            new Coord(
                    +x_diff * 3,
                    -y_diff * 1),
            new Coord(
                    +x_diff * 3,
                    +y_diff * 1),
            new Coord(
                    +x_diff * 3,
                    +y_diff * 3),

            // 5TH-ROW
            new Coord(
                    +x_diff * 4,
                    -y_diff * 4),
            new Coord(
                    +x_diff * 4,
                    -y_diff * 2),
            new Coord(
                    +x_diff * 4,
                    +0),
            new Coord(
                    +x_diff * 4,
                    +y_diff * 2),
            new Coord(
                    +x_diff * 4,
                    +y_diff * 4),

            // WHITE-BALL
            new Coord(
                    -500 + ball_radius,
                    0),

            // (BLACK-BALL)
            new Coord(
                    +x_diff * 2,
                    +0)
    };

    /**
     * Gets play height.
     *
     * @return the play height
     */
    public float getPlayHeight() {
        return this.getHeight() - this.guiHeight;
    }

    /**
     * Gets play width.
     *
     * @return the play width
     */
    public float getPlayWidth() {
        return this.getWidth();
    }

    @Override
    public float getWidth() {
        return 1000f;
    }

    @Override
    public float getHeight() {
        // Height fills actual screen size, but is based on width scaling.
        return (float)(actualHeight / (double)actualWidth * getWidth());
    }

    @Override
    public void start() {
        blackPaint.setColor(Color.BLACK);

        // shootLine reflection colors
        grayPaintReflection.setColor(Color.argb(255, 50, 50, 50));
        grayPaintReflection.setStrokeWidth(2);

        // shootLine colors
        redPaint.setColor(Color.argb(255, 255, 0, 0));
        redPaint.setStrokeWidth(3);

        // cue colors
        whitePaint.setColor(Color.argb(255, 255, 255, 255));
        whitePaint.setStrokeWidth(4);


        this.players.add(player1);
        player1.setScoredballs(this.player1balls);
        this.players.add(player2);
        player2.setScoredballs(this.player2balls);

        this.left = 0;
        this.right = left + getPlayWidth();
        this.top = getPlayHeight();
        this.bottom = top + guiHeight;
        this.gui = new Gui(this, this.player1, this.player2, this.left, this.top, this.right, this.bottom);

        addEntity(menuBackground);
        addEntity(eightBallButton);
        addEntity(madnessButton);

        if (runs < 1) {
            Hole hole1 = new Hole(this, getPlayWidth() * 0.08, this.getHeight() * 0.12, holesize);
            Hole hole2 = new Hole(this, getPlayWidth() * 0.505, this.getHeight() * 0.12, holesize);
            Hole hole3 = new Hole(this, getPlayWidth() * 0.921, this.getHeight() * 0.12, holesize);
            Hole hole4 = new Hole(this, getPlayWidth() * 0.08, this.getHeight() * 0.75, holesize);
            Hole hole5 = new Hole(this, getPlayWidth() * 0.505, this.getHeight() * 0.75, holesize);
            Hole hole6 = new Hole(this, getPlayWidth() * 0.921, this.getHeight() * 0.75, holesize);

            this.holes.add(hole1);
            this.holes.add(hole2);
            this.holes.add(hole3);
            this.holes.add(hole4);
            this.holes.add(hole5);
            this.holes.add(hole6);

            addEntity(background);
            addEntity(hole1);
            addEntity(hole2);
            addEntity(hole3);
            addEntity(hole4);
            addEntity(hole5);
            addEntity(hole6);
            addEntity(gui);


        }
        System.out.println("Runs:" + runs);
        runs++;
    }

    private int getRandIntInRange(int left, int right) {
        return (int) (left + (Math.random() * (right - left + 1)));
    }

    /**
     * Swap.
     *
     * @param arrayInt the array int
     * @param a        the a
     * @param b        the b
     */
// swaps the elements at loc a and b in array 'arrayInt'
    public void swap(ArrayList<Integer> arrayInt, int a, int b) {
        if (a == b) return;
        int temp = arrayInt.get(a);
        arrayInt.set(a, arrayInt.get(b));
        arrayInt.set(b, temp);
    }

    /**
     * Rack balls.
     *
     * @param balls the balls
     */
    public void rackBalls(ArrayList<Ball> balls) {
        // balls 0 through 6 are SOLID BALLS (0 t/m 13)
        // balls 7 through 13 are STRIPED BALLS (0 t/m 13)
        // ball at 14 is WHITE BALL (index 14)
        // ball at 15 is BLACK BALL (index 15)

        this.rack_x_offset = (getPlayWidth() / 4) * 3;
        this.rack_y_offset = (getPlayHeight() / 2);

        int whiteBallIndex = 0, blackBallIndex = 0;

        int[] sideBallIndecis = new int[] {13, 9, 8, 5, 4, 3, 2, 1};
        int[] normalBallIndecis = new int[] {12, 11, 10, 7, 6, 0};
        ArrayList<Integer> solidBallIndecis = new ArrayList<>();
        ArrayList<Integer> stripedBallIndecis = new ArrayList<>();

        for(int i = 0; i < balls.size(); i++) {
            switch(balls.get(i).getType()) {
                case 0: {
                    // white ball
                    whiteBallIndex = i;
                    break;
                }
                case 1: {
                    // solid ball
                    solidBallIndecis.add(i);
                    break;
                }
                case 2: {
                    // striped ball
                    stripedBallIndecis.add(i);
                    break;
                }
                case 3: {
                    // black ball
                    blackBallIndex = i;
                    break;
                }
            }
        }

        rackPositions[whiteBallIndex] = new Coord(getPlayWidth() / 4, this.rack_y_offset);

        for (int i = 0; i < 7; i++) {
            int a, b;
            a = getRandIntInRange(0, 6);
            b = getRandIntInRange(0, 6);
            swap(solidBallIndecis, a, b);

            a = getRandIntInRange(0, 6);
            b = getRandIntInRange(0, 6);
            swap(stripedBallIndecis, a, b);
        }

        int currentSolid = 0, currentStriped = 0;

        for (int i = 0; i < sideBallIndecis.length; i += 2, currentSolid++, currentStriped++) {
            int randBoolean = getRandIntInRange(0, 1);

            // 1st side
            balls.get(solidBallIndecis.get(currentSolid)).setCoord(rackPositions[sideBallIndecis[i + (1 - randBoolean)]]);

            // 2nd side
            balls.get(stripedBallIndecis.get(currentStriped)).setCoord(rackPositions[sideBallIndecis[i + randBoolean]]);
        }

        // assign positions to these 'normal' balls
        for (int i = 0; i < normalBallIndecis.length; i++) {
            Ball currentBall;

            if (i % 2 == 1) currentBall = balls.get(solidBallIndecis.get(currentSolid));
            else currentBall = balls.get(stripedBallIndecis.get(currentStriped));

            currentBall.setCoord(rackPositions[normalBallIndecis[i]]);

            if (i % 2 == 1) currentSolid++;
            else currentStriped++;
        }

        // initialize the black ball
        balls.get(blackBallIndex).setCoord(rackPositions[15]);

        // initialize the white ball
        balls.get(whiteBallIndex).setCoord(rackPositions[14]);

        // add the x- and y-offsets to the ball's coords
        for(Ball ball : balls) {
            if (ball.getType() != 0)
                ball.addCoord(rack_x_offset, rack_y_offset);
        }
    }

    /**
     * Start eight ball.
     */
    public void startEightBall() {
        removeEntity(menuBackground);
        removeEntity(eightBallButton);
        removeEntity(madnessButton);

        this.ball1 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 1);
        this.ball2 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 1);
        this.ball3 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 1);
        this.ball4 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 1);
        this.ball5 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 1);
        this.ball6 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 1);
        this.ball7 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 1);
        this.ball8 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 3);
        this.ball9 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 2);
        this.ball10 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 2);
        this.ball11 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 2);
        this.ball12 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 2);
        this.ball13 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 2);
        this.ball14 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 2);
        this.ball15 = new Ball(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 2);
        this.ball16 = new WhiteBall(this, this.balls, this.holes, this.players, getPlayWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 0, this.line, this.lineReflection, this.cue);

        this.whiteBallHandler.setWhiteBall(ball16);

        this.ball1.resetLastisertedid();
        this.balls.add(ball1);
        this.balls.add(ball2);
        this.balls.add(ball3);
        this.balls.add(ball4);
        this.balls.add(ball5);
        this.balls.add(ball6);
        this.balls.add(ball7);
        this.balls.add(ball9);
        this.balls.add(ball10);
        this.balls.add(ball11);
        this.balls.add(ball12);
        this.balls.add(ball13);
        this.balls.add(ball14);
        this.balls.add(ball15);
        this.balls.add(ball16);
        this.balls.add(ball8);

        // puts the balls in the rack
        rackBalls(this.balls);

        addEntity(line);
        addEntity(lineReflection);
        addEntity(cue);

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

    /**
     * Sets current player.
     *
     * @param player the player
     */
    public void setCurrentPlayer(Player player) {
        if (player == player1) {
            this.currentplayer = player1;
            this.inactiveplayer = player2;
        } else if (player == player2) {
            this.currentplayer = player2;
            this.inactiveplayer = player1;
        }
    }

    /**
     * Gets currentplayer.
     *
     * @return the currentplayer
     */
    public Player getCurrentplayer() {
        return this.currentplayer;
    }

    /**
     * Gets inactiveplayer.
     *
     * @return the inactiveplayer
     */
    public Player getInactiveplayer() {
        return inactiveplayer;
    }

    /**
     * Check movement for all balls boolean.
     *
     * @return the boolean
     */
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

    /**
     * Round checker.
     */
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
     * Score cue ball.
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

    /**
     * Place cue ball.
     */
    public void placeCueBall() {
        addEntity(whiteBallHandler);
    }


    /**
     * Gets moving balls.
     *
     * @return the moving balls
     */
    public ArrayList<Ball> getMovingBalls() {
        return movingballs;
    }

    /**
     * Gets all balls.
     *
     * @return the all balls
     */
    public ArrayList<Ball> getAllBalls() {
        return this.balls;
    }

    /**
     * Gets cue ball scored.
     *
     * @return the cue ball scored
     */
    public boolean getCueBallScored() {
        return this.cueBallScored;
    }

    /**
     * Reset cue ball scored.
     */
    public void resetCueBallScored() {
        this.cueBallScored = false;
    }

    /**
     * Gets cue ball in hand.
     *
     * @return the cue ball in hand
     */
    public boolean getCueBallInHand() {
        return this.cueBallInHand;
    }

    /**
     * Sets cue ball in hand.
     *
     * @param cueBallInHand the cue ball in hand
     */
    public void setCueBallInHand(boolean cueBallInHand) {
        this.cueBallInHand = cueBallInHand;
    }


    /**
     * Start madness.
     */
    public void startMadness() {

    }

    /**
     * Winner screen.
     *
     * @param winnerId the winner id
     */
    public void winnerScreen(int winnerId) {
        removeEntity(whiteBallHandler);
        for (int i = 0; i < balls.size(); i++) {
            removeEntity(this.balls.get(i));
        }
        this.setWinMessage(new WinMessage(this, winnerId));
        addEntity(menuBackground);
        addEntity(this.winMessage);
    }

    /**
     * Sets win message.
     *
     * @param winMessage the win message
     */
    public void setWinMessage(WinMessage winMessage) {
        this.winMessage = winMessage;
    }

    /**
     * Reset.
     */
    public void reset() {

        player1.setBalltype(-1);
        player1.resetScoredballs();
        player2.setBalltype(-1);
        player2.resetScoredballs();

        this.balls.clear();
        this.movingballs.clear();

        this.ball1 = null;
        this.ball2 = null;
        this.ball3 = null;
        this.ball4 = null;
        this.ball5 = null;
        this.ball6 = null;
        this.ball7 = null;
        this.ball8 = null;
        this.ball9 = null;
        this.ball10 = null;
        this.ball11 = null;
        this.ball12 = null;
        this.ball13 = null;
        this.ball14 = null;
        this.ball15 = null;
        this.ball16 = null;

        setCurrentPlayer(player1);

        removeEntity(menuBackground);
        this.gui = null;
        start();
    }
}

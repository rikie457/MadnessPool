package nl.saxion.playground.template.pool;


import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;
import nl.saxion.playground.template.pool.buttons.EightBallButton;
import nl.saxion.playground.template.pool.buttons.MadnessButton;

/**
 * The type Game.
 */
public class Game extends GameModel {

    //Players
    private Player player1 = new Player(1);
    private Player player2 = new Player(2);

    //Paints
    private Paint blackPaint = new Paint();
    private Paint whitePaint = new Paint();
    private Paint grayPaintReflection = new Paint();
    private Paint redPaint = new Paint();

    //Settings
    private Player currentplayer = player1;
    private Player inactiveplayer = player2;
    private float guiHeight = 75f;
    private float left, top, right, bottom;
    private float ballsize = 30f;

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

    private boolean userInterfaceSpawned = false;
    private boolean playersAdded = false;

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
        // Width is always 8 units.
        return 1000f;
    }

    @Override
    public float getHeight() {
        // Height fills actual screen size, but is based on width scaling.
        return actualHeight / actualWidth * getWidth();
    }


    @Override
    public void start() {
        this.blackPaint.setColor(Color.BLACK);
        this.whitePaint.setColor(Color.WHITE);
        this.redPaint.setColor(Color.RED);
        this.grayPaintReflection.setColor(Color.GRAY);

        this.grayPaintReflection.setStrokeWidth(2);
        this.redPaint.setStrokeWidth(4);

        if (!playersAdded) {
            players.add(player1);
            players.add(player2);
            playersAdded = true;
        }

        this.left = 0;
        this.right = left + getPlayWidth();
        this.top = getPlayHeight();
        this.bottom = top + guiHeight;

        if (!userInterfaceSpawned) {
            Gui gui = new Gui(this, this.player1, this.player2, this.left, this.top, this.right, this.bottom, whitePaint, blackPaint);
            Hole hole = new Hole(this, 50, 50, blackPaint);
            addEntity(gui);
            addEntity(hole);
            this.holes.add(hole);
        }

        addEntity(menuBackground);
        addEntity(eightBallButton);
        addEntity(madnessButton);
    }

    /**
     * Start eight ball.
     */

    private float padding = (float)0.84; // factor that determines how much space there is between the racked pool balls (0.9 = tightest possible)
    private float ball_radius = ballsize/2;

    // defines spacing between balls horizontally
    private float x_diff = (ball_radius + (ball_radius / 2) + 10) * padding;
    // defines spacing between balls vertically
    private float y_diff = (ball_radius + 3) * padding;

    private float rack_x_offset = 700;
    private float rack_y_offset = 200;

    private Coord[] rackPositions = new Coord[] {
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
                    -150 + ball_radius,
                    ball_radius - 50),

            // (BLACK-BALL)
            new Coord(
                    +x_diff * 2,
                    +0)
    };

    private int getRandIntInRange(int left, int right) {
        return (int)(left + (Math.random() * (right - left + 1)));
    }

    // swaps the elements at loc a and b in array 'arrayInt'
    public void swap(ArrayList<Integer> arrayInt, int a, int b) {
        if(a == b) return;
        int temp = arrayInt.get(a);
        arrayInt.set(a, arrayInt.get(b));
        arrayInt.set(b, temp);
    }

    /**
     *
     * @param balls array of balls to be racked
     */
    public void rackBalls(ArrayList<Ball> balls) {
        // balls 0 through 6 are SOLID BALLS (0 t/m 13)
        // balls 7 through 13 are STRIPED BALLS (0 t/m 13)
        // ball at 14 is WHITE BALL (index 14)
        // ball at 15 is BLACK BALL (index 15)

        int[] sideBallIndecis = new int[] {13, 9, 8, 5, 4, 3, 2, 1};
        int[] normalBallIndecis = new int[] {12, 11, 10, 7, 6, 0};

        ArrayList<Integer> solidBallIndecis = new ArrayList<>();
        ArrayList<Integer> stripedBallIndecis = new ArrayList<>();

        for(int i = 0; i < 7; i++) {
            solidBallIndecis.add(i);
            stripedBallIndecis.add(i + 7);
        }

        for(int i = 0; i < 7; i++) {
            int a, b;
            a = getRandIntInRange(0, 6);
            b = getRandIntInRange(0, 6);
            swap(solidBallIndecis, a, b);

            a = getRandIntInRange(0, 6);
            b = getRandIntInRange(0, 6);
            swap(stripedBallIndecis, a, b);
        }

        int currentSolid = 0, currentStriped = 0;

        for(int i = 0; i < sideBallIndecis.length; i+=2, currentSolid++, currentStriped++) {
            int randBoolean = getRandIntInRange(0, 1);

            // 1st side
            balls.get(solidBallIndecis.get(currentSolid)).setCoord(rackPositions[sideBallIndecis[i + (1 - randBoolean)]]);

            // 2nd side
            balls.get(stripedBallIndecis.get(currentStriped)).setCoord(rackPositions[sideBallIndecis[i + randBoolean]]);
        }

        // assign positions to these 'normal' balls
        for(int i = 0; i < normalBallIndecis.length; i++) {
            Ball currentBall;

            if(i % 2 == 1) currentBall = balls.get(solidBallIndecis.get(currentSolid));
            else currentBall = balls.get(stripedBallIndecis.get(currentStriped));

            currentBall.setCoord(rackPositions[normalBallIndecis[i]]);

            if(i % 2 == 1) currentSolid++;
            else currentStriped++;
        }

        int whiteBallIndex = 14, blackBallIndex = 15;

        // initialize the black ball
        balls.get(15).setCoord(rackPositions[blackBallIndex]);

        // initialize the white ball
        balls.get(14).setCoord(rackPositions[whiteBallIndex]);

        // add the x- and y-offsets to the ball's coords
        for(Ball ball : balls) {
            ball.addCoord(rack_x_offset, rack_y_offset);
        }

        if(false) {
            int i = 0;
            for (Ball ball : balls) {
                final String TAG = "Game.java [302]";
                Log.e(TAG, "\nball [" + i + "]:\n" + ball.toString());
                i++;
            }
        }
    }

    public void startEightBall() {
        removeEntity(menuBackground);
        removeEntity(eightBallButton);
        removeEntity(madnessButton);

        ShootLine line = new ShootLine(false, redPaint, this);
        ShootLine lineReflection = new ShootLine(false, grayPaintReflection, this);
        Cue cue = new Cue(false, whitePaint);

        Ball ball1 = new Ball(this, this.balls, this.holes, this.sunkeBalls, this.getPlayWidth() / 2, Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball1, 1);
        Ball ball2 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball2, 1);
        Ball ball3 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball3, 1);
        Ball ball4 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball4, 1);
        Ball ball5 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball5, 1);
        Ball ball6 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball6, 1);
        Ball ball7 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball7, 1);
        Ball ball8 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball8, 3);
        Ball ball9 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball9, 2);
        Ball ball10 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball10, 2);
        Ball ball11 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball11, 2);
        Ball ball12 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball12, 2);
        Ball ball13 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball13, 2);
        Ball ball14 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball14, 2);
        Ball ball15 = new Ball(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball15, 2);
        WhiteBall ball16 = new WhiteBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball16, 0, line, lineReflection, cue);

        ball1.resetLastisertedid();
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
     * Gets players.
     *
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
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
        for (int i = 0; i < balls.size(); i++) {
            removeEntity(this.balls.get(i));
        }
        WinMessage winMessage = new WinMessage(this, winnerId);
        addEntity(menuBackground);
        addEntity(winMessage);
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
        this.sunkeBalls.clear();

        setCurrentPlayer(player1);
        setCurrentPlayer(player2);

        removeEntity(menuBackground);
        start();
    }
}

package nl.saxion.playground.template.pool;


import android.graphics.Color;
import android.graphics.Paint;

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

            /**
             * holes bovenkant pooltafel.
             */

            Hole hole = new Hole(this,140,getHeight()-955, blackPaint);
            Hole hole1 = new Hole(this,getWidth()/2,getHeight()-970, blackPaint);
            Hole hole2 = new Hole(this,getWidth() - 230/2,230/2, blackPaint);

            /**
             * holes onderkant pooltafel.
             */
            Hole hole3 = new Hole(this,getWidth()/8 -85,getHeight() - 260, blackPaint);
            Hole hole4 = new Hole(this,getWidth()/2,getHeight() - 245, blackPaint);
            Hole hole5 = new Hole(this,getWidth() - 145,getHeight() - 280, blackPaint);

            addEntity(new Background(this));
            addEntity(hole);
            addEntity(hole1);
            addEntity(hole2);
            addEntity(hole3);
            addEntity(hole4);
            addEntity(hole5);

            this.holes.add(hole);
            this.holes.add(hole1);
            this.holes.add(hole2);
            this.holes.add(hole3);
            this.holes.add(hole4);
            this.holes.add(hole5);
        }
        addEntity(menuBackground);
        addEntity(eightBallButton);
        addEntity(madnessButton);
    }

    /**
     * Start eight ball.
     */
    private float padding = (float)1; // factor that determines how much space there is between the racked pool balls (1 = tightest possible)
    private float ball_radius= 75/2;
    private float x_offset = ball_radius / 2 + 10 * padding;
    private float y_offset = 1 * padding;
    private float x_diff = ball_radius + x_offset;
    private float y_diff = ball_radius + y_offset;
    private float rack_x_offset = 500;
    private float rack_y_offset = -150;

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

            // (BLACK-BALL)
            new Coord(
                    +x_diff * 2,
                    +0),

            // WHITE-BALL
            new Coord(
                    -1300 + ball_radius,
                    +getHeight() / 2 + ball_radius - 50)
    };

    private static int currentSolid = 0, currentStriped = 8;

    private void getNextSolid() {
        currentSolid++;
    }

    private void getNextStriped() {
        currentStriped++;
    }

    private int getRandIntInRange(int left, int right) {
        return (int)(left + (Math.random() * (right - left + 1)));
    }

    // initializes the 'currentStriped' and 'currentSolid' indecis, which are used in the rackBalls() function
    // to consistently cycle through all balls
    public void resetRackPositions() {
        this.currentStriped = 8;
        this.currentSolid = 0;
    }

    // swaps the elements at loc a and b in array 'arrayInt'
    public void swap(int[] arrayInt, int a, int b) {
        if(a == b) return;
        int temp = arrayInt[a];
        arrayInt[a] = arrayInt[b];
        arrayInt[b] = temp;
    }

    public void rackBalls(ArrayList<Ball> balls) {
        resetRackPositions();

        // add offsets to the coordinates to center the balls
        for(int i = 0; i < rackPositions.length; i++) {
            rackPositions[i].setX(rackPositions[i].getX() + getWidth() / 2 + ball_radius + rack_x_offset);
            rackPositions[i].setY(rackPositions[i].getY() + getHeight() / 2 + ball_radius + rack_y_offset);
        }

        // balls 0 through 6 are SOLID BALLS (0 t/m 14)
        // balls 7 through 13 are STRIPED BALLS (0 t/m 14)
        // ball at 7 == BLACK BALL (index 14)
        // ball at 15 is WHITE BALL (index 15)

        int[] sideBallIndecis = new int[] {
            13, 9,
            8, 5,
            4, 3,
            2, 1
        };
        int[] normalBallIndecis = new int[] {
            12, 11, 10, 7, 6, 0
        };
        int blackBallIndex = 14,
                whiteBallIndex = 15;

        for(int i = 0; i < sideBallIndecis.length; i+=2) {
            int randBoolean = getRandIntInRange(0, 1);

            // 1st side
            balls.get(this.currentSolid).setX(
                    rackPositions[sideBallIndecis[i + (1 - randBoolean)]].getX()
            );
            balls.get(this.currentSolid).setY(
                    rackPositions[sideBallIndecis[i + (1 - randBoolean)]].getY()
            );

            // second side
            balls.get(this.currentStriped).setX(
                    rackPositions[sideBallIndecis[i + randBoolean]].getX()
            );
            balls.get(this.currentStriped).setY(
                    rackPositions[sideBallIndecis[i + randBoolean]].getY()
            );

            getNextSolid();
            getNextStriped();
        }

        // shuffle the ball indecis in the 'normal' range
        for(int i = 0, a, b; i < normalBallIndecis.length; i++) {
            a = getRandIntInRange(0, normalBallIndecis.length - 1);
            b = getRandIntInRange(0, normalBallIndecis.length - 1);

            swap(normalBallIndecis, a, b);
        }

        // assign positions to these 'normal' balls
        for(int i = 0; i < normalBallIndecis.length; i++) {
            int currentBall;

            if(i % 2 == 1) {
                currentBall = this.currentSolid;
            }
            else {
                currentBall = this.currentStriped;
            }

            balls.get(currentBall).setX(
                    rackPositions[normalBallIndecis[i]].getX()
            );

            balls.get(currentBall).setY(
                    rackPositions[normalBallIndecis[i]].getY()
            );

            if(i % 2 == 1) {
                getNextSolid();
            } else {
                getNextStriped();
            }
        }

        // initialize the black ball
        balls.get(7).setX(rackPositions[blackBallIndex].getX());
        balls.get(7).setY(rackPositions[blackBallIndex].getY());

        // initialize the white ball
        balls.get(15).setX(rackPositions[whiteBallIndex].getX());
        balls.get(15).setY(rackPositions[whiteBallIndex].getY());
    }


    public void startEightBall() {
        removeEntity(menuBackground);
        removeEntity(eightBallButton);
        removeEntity(madnessButton);

        ShootLine line = new ShootLine(false, whitePaint);

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
        WhiteBall ball16 = new WhiteBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getPlayWidth()), Utility.randomDoubleFromRange(0, getPlayHeight()), ballsize, ballsize, R.drawable.ball16, 0, line);



        ball1.resetLastisertedid();
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


        // puts the balls in the rack
        rackBalls(this.balls);
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

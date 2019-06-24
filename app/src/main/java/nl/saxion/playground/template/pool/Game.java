/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

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
import nl.saxion.playground.template.pool.handlers.WallHandler;
import nl.saxion.playground.template.pool.handlers.WhiteBallHandler;
import nl.saxion.playground.template.pool.messages.WinMessage;
import nl.saxion.playground.template.pool.powerup.AddBallFromShelf;
import nl.saxion.playground.template.pool.powerup.AddWall;
import nl.saxion.playground.template.pool.powerup.GravityPocket;
import nl.saxion.playground.template.pool.powerup.GravityWellPowerup;
import nl.saxion.playground.template.pool.powerup.MoreDrag;
import nl.saxion.playground.template.pool.powerup.NoDrag;
import nl.saxion.playground.template.pool.powerup.Powerup;
import nl.saxion.playground.template.pool.powerup.RandomOther;
import nl.saxion.playground.template.pool.powerup.RemoveBall;
import nl.saxion.playground.template.pool.powerup.SpeedBoost;
import nl.saxion.playground.template.pool.powerup.Wormhole;


/**
 * The type Game.
 * Here is all the logic voor the game itself
 * the gamemode is determined here and the logic for each action.
 */
public class Game extends GameModel {


    /**
     * The constant powerupPaint.
     */
    public static Paint powerupPaint = new Paint();
    /**
     * The Transparent.
     */
    static Paint transparent = new Paint();
    /**
     * The Black paint.
     */
    static Paint blackPaint = new Paint();
    /**
     * The White paint.
     */
    static Paint whitePaint = new Paint();
    /**
     * The Gray paint.
     */
    static Paint grayPaint = new Paint();
    /**
     * The Red paint.
     */
    static Paint redPaint = new Paint();
    private static Paint grayPaintReflection = new Paint();

    //Players
    private Player player1 = new Player(1);
    private Player player2 = new Player(2);
    private Player currentplayer = player1;
    private ArrayList<Ball> player1balls = new ArrayList<>();
    private Player inactiveplayer = player2;
    private ArrayList<Ball> player2balls = new ArrayList<>();

    //Settings
    private boolean cueBallScored = false;
    private boolean cueBallInHand = false;
    private boolean allmoving = false;
    private boolean playerScored = false;
    private boolean isMadness = false;
    private boolean placingWall = false;
    private boolean pocketGravity = false;
    private float guiHeight = 75f;
    private float left = 0, top = getHeight(), right = getWidth(), bottom = getHeight() + guiHeight;
    private float ballsize = 30f;
    private float holesize = 20f;
    private float powerupsize = 30f;
    private int turns;

    // Top Overlay pool table
    private TableTopOverlay table_top_overlay;

    // ArrayLists
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<Hole> holes = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Wall> walls = new ArrayList<>();
    private ArrayList<Powerup> powerups = new ArrayList<>();


    //Drawables ball
    private int[] drawables = {R.drawable.ball1, R.drawable.ball2, R.drawable.ball3, R.drawable.ball4, R.drawable.ball5, R.drawable.ball6, R.drawable.ball7, R.drawable.ball8, R.drawable.ball9, R.drawable.ball10, R.drawable.ball11, R.drawable.ball12, R.drawable.ball13, R.drawable.ball14, R.drawable.ball15, R.drawable.ball16};


    //Menu items
    private MenuBackground menuBackground = new MenuBackground(this);
    private EightBallButton eightBallButton = new EightBallButton(this);
    private MadnessButton madnessButton = new MadnessButton(this);
    private Background background = new Background(this);
    private Gui gui;
    private WinMessage winMessage;
    private ShootLine line = new ShootLine(false, this);

    private int runs = 0;
    private WhiteBallHandler whiteBallHandler = new WhiteBallHandler(this, this.balls, this.holes);
    private WallHandler wallHandler = new WallHandler(this.balls, this.holes, this.walls, this);
    private PowerupCreator powerupCreator;
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

    private Vector2[] rackPositions = new Vector2[]{
            // 1ST-ROW
            new Vector2(
                    0,
                    0),

            // 2ND-ROW
            new Vector2(
                    +x_diff,
                    -y_diff),
            new Vector2(
                    +x_diff,
                    y_diff),

            // 3RD-ROW
            new Vector2(
                    +x_diff * 2,
                    -y_diff * 2),
            // actual location of the black ball
            new Vector2(
                    +x_diff * 2,
                    +y_diff * 2),

            // 4TH-ROW
            new Vector2(
                    +x_diff * 3,
                    -y_diff * 3),
            new Vector2(
                    +x_diff * 3,
                    -y_diff * 1),
            new Vector2(
                    +x_diff * 3,
                    +y_diff * 1),
            new Vector2(
                    +x_diff * 3,
                    +y_diff * 3),

            // 5TH-ROW
            new Vector2(
                    +x_diff * 4,
                    -y_diff * 4),
            new Vector2(
                    +x_diff * 4,
                    -y_diff * 2),
            new Vector2(
                    +x_diff * 4,
                    +0),
            new Vector2(
                    +x_diff * 4,
                    +y_diff * 2),
            new Vector2(
                    +x_diff * 4,
                    +y_diff * 4),

            // WHITE-BALL
            new Vector2(
                    -500 + ball_radius,
                    0),

            // (BLACK-BALL)
            new Vector2(
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

    @Override
    public float getWidth() {
        return 1000f;
    }

    @Override
    public float getHeight() {
        // Height fills actual screen size, but is based on width scaling.
        return (float) (actualHeight / (double) actualWidth * getWidth());
    }

    /**
     * Gets powerups.
     *
     * @return the powerups
     */
    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }

    public ArrayList<Powerup> getPowerupCreatorPowerups() { return this.powerupCreator.getPowerups(); }


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
        this.right = left + getWidth();
        this.top = getPlayHeight();
        this.bottom = top + guiHeight;
        this.gui = new Gui(this, this.player1, this.player2, this.left, this.top, this.right, this.bottom);

        addEntity(menuBackground);
        addEntity(eightBallButton);
        addEntity(madnessButton);

        if (runs < 1) {
            //Cant use array because of different coordinates
            Hole hole1 = new Hole(this, this.getWidth() * 0.08, this.getPlayHeight() * 0.12, holesize);
            Hole hole2 = new Hole(this, this.getWidth() * 0.505, this.getPlayHeight() * 0.11, holesize);
            Hole hole3 = new Hole(this, this.getWidth() * 0.921, this.getPlayHeight() * 0.11, holesize);
            Hole hole4 = new Hole(this, this.getWidth() * 0.08, this.getPlayHeight() * 0.88, holesize);
            Hole hole5 = new Hole(this, this.getWidth() * 0.505, this.getPlayHeight() * 0.88, holesize);
            Hole hole6 = new Hole(this, this.getWidth() * 0.921, this.getPlayHeight() * 0.88, holesize);

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
        runs++;
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

        this.rack_x_offset = (getWidth() / 3) * 2 - ball_radius;
        this.rack_y_offset = (getPlayHeight() / 2) - ball_radius;
        this.rackPositions[14] = new Vector2(getWidth() / 4, this.rack_y_offset);

        int whiteBallIndex = 0, blackBallIndex = 0;

        int[] sideBallIndecis = new int[]{13, 9, 8, 5, 4, 3, 2, 1};
        int[] normalBallIndecis = new int[]{12, 11, 10, 7, 6, 0};
        ArrayList<Integer> solidBallIndecis = new ArrayList<>();
        ArrayList<Integer> stripedBallIndecis = new ArrayList<>();

        for (int i = 0; i < balls.size(); i++) {
            switch (balls.get(i).getType()) {
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

        for (int i = 0; i < 7; i++) {
            int a, b;
            a = Utility.getRandIntInRange(0, 6);
            b = Utility.getRandIntInRange(0, 6);
            swap(solidBallIndecis, a, b);

            a = Utility.getRandIntInRange(0, 6);
            b = Utility.getRandIntInRange(0, 6);
            swap(stripedBallIndecis, a, b);
        }

        int currentSolid = 0, currentStriped = 0;

        for (int i = 0; i < sideBallIndecis.length; i += 2, currentSolid++, currentStriped++) {
            int randBoolean = Utility.getRandIntInRange(0, 1);

            // 1st side
            balls.get(solidBallIndecis.get(currentSolid)).setVector2(new Vector2(rackPositions[sideBallIndecis[i + (1 - randBoolean)]]));

            // 2nd side
            balls.get(stripedBallIndecis.get(currentStriped)).setVector2(new Vector2(rackPositions[sideBallIndecis[i + randBoolean]]));
        }

        // assign positions to these 'normal' balls
        for (int i = 0; i < normalBallIndecis.length; i++) {
            Ball currentBall;

            if (i % 2 == 1) currentBall = balls.get(solidBallIndecis.get(currentSolid));
            else currentBall = balls.get(stripedBallIndecis.get(currentStriped));

            currentBall.setVector2(new Vector2(rackPositions[normalBallIndecis[i]]));

            if (i % 2 == 1) currentSolid++;
            else currentStriped++;
        }

        // initialize the white ball
        balls.get(whiteBallIndex).setVector2(new Vector2(rackPositions[14]));

        // initialize the black ball
        balls.get(blackBallIndex).setVector2(new Vector2(rackPositions[15]));

        // add the x- and y-offsets to the ball's coords
        for (Ball ball : balls) {
            if (ball.getType() != 0)
                ball.getVector2().add(rack_x_offset, rack_y_offset);
        }
    }

    /**
     * Init balls.
     */
    public void initBalls() {
        for (int i = 0; i < 16; i++) {
            int type = -1;
            if (i != 15) {
                if (i <= 6) {
                    type = 1;
                } else if (i == 7) {
                    type = 3;
                } else if (i <= 14) {
                    type = 2;
                }
                Ball ball = new Ball(this, this.drawables, getWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, type);
                this.balls.add(ball);
                addEntity(ball);
            } else {
                WhiteBall ball = new WhiteBall(this, drawables, getWidth() / 2, getPlayHeight() / 2, ballsize, ballsize, 0, this.line);
                this.balls.add(ball);
                addEntity(ball);
            }
        }
    }

    public int[] getDrawables() {
        return this.drawables;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    private void resetBalls() {
        for (int i = 0; i < this.balls.size(); i++) {
            this.balls.remove(i);
        }
    }

    private void resetPowerups() {
        for (int i = 0; i < this.getPowerups().size(); i++) {
            Powerup powerup = this.getPowerups().get(i);
            removeEntity(powerup);
            this.getPowerups().remove(i);
        }

    }

    /**
     * Start eight ball.
     */
    public void startEightBall() {
        removeEntity(menuBackground);
        removeEntity(eightBallButton);
        removeEntity(madnessButton);

        if (table_top_overlay == null) {
            table_top_overlay = new TableTopOverlay(this);
            addEntity(table_top_overlay);
        }

        initBalls();
        for (int i = 0; i < this.balls.size(); i++) {
            if (i == 15) {
                WhiteBall whiteball = (WhiteBall) this.balls.get(i);
                this.whiteBallHandler.setWhiteBall(whiteball);
            }
        }

        // puts the balls in the rack
        rackBalls(this.balls);
        addEntity(line);
    }

    /**
     * Start madness.
     */
    public void startMadness() {
        this.isMadness = true;
        removeEntity(menuBackground);
        removeEntity(eightBallButton);
        removeEntity(madnessButton);

        if (table_top_overlay == null) {
            table_top_overlay = new TableTopOverlay(this);
            addEntity(table_top_overlay);
        }

        initBalls();
        for (int i = 0; i < this.balls.size(); i++) {
            if (i == 15) {
                WhiteBall whiteball = (WhiteBall) this.balls.get(i);
                //Create powerupcreator for powerup spawning
                this.powerupCreator = new PowerupCreator(this, whiteball, this.balls);
                //Add powerup to array of spawnable powerups
                powerupCreator.getPowerups().add(new AddWall(this, 250, 250, whiteball));
                powerupCreator.getPowerups().add(new SpeedBoost(this, 250, 250, whiteball));
                powerupCreator.getPowerups().add(new NoDrag(this, 250, 250, whiteball));
                powerupCreator.getPowerups().add(new Wormhole(this, 250, 250, whiteball));
                powerupCreator.getPowerups().add(new MoreDrag(this, 250, 250, whiteball));
                powerupCreator.getPowerups().add(new GravityPocket(this, 250, 250, whiteball));
                powerupCreator.getPowerups().add(new RemoveBall(this, 250, 250, whiteball));
                powerupCreator.getPowerups().add(new GravityWellPowerup(this, 250, 250, whiteball));
                powerupCreator.getPowerups().add(new RandomOther(this, 250, 250, whiteball));
                powerupCreator.getPowerups().add(new AddBallFromShelf(this, 250, 250, whiteball));

                this.whiteBallHandler.setWhiteBall(whiteball);
            }
        }

        // puts the balls in the rack
        rackBalls(this.balls);
        addEntity(line);
        addEntity(powerupCreator);
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
    public int getTurns() {
        return turns;
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
     * Gets powerupsize.
     *
     * @return the powerupsize
     */
    public float getPowerupsize() {
        return powerupsize;
    }

    /**
     * Check movement for all balls boolean.
     *
     * @return the boolean
     */
    public Boolean checkMovementForAllBalls() {
        for (int i = 0; i < this.balls.size(); i++) {
            if (this.balls.get(i).isMoving()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets balls.
     *
     * @return the balls
     */
    public ArrayList<Ball> getBalls() {
        return balls;
    }

    /**
     * Round checker.
     *
     * @param ball the ball
     */
    public void roundChecker(WhiteBall ball) {
        if (!this.checkMovementForAllBalls()) {
            if (this.currentplayer == player1 && !this.playerScored) {
                setCurrentPlayer(player2);
                turns++;
            } else if (!this.playerScored) {
                setCurrentPlayer(player1);
                turns++;
            }
            this.allmoving = false;
            ball.setShot(false);
            this.playerScored = false;
        } else {
            this.allmoving = true;
        }
        if (this.walls.size() > 0 && !this.playerScored && !this.checkMovementForAllBalls()) {
            for (int i = 0; i < this.walls.size(); i++) {
                removeEntity(this.walls.get(i));
            }
            this.walls.clear();
        }
    }


    /**
     * Score cue ball.
     */
    public void scoreCueBall() {
        this.cueBallScored = true;
        for (int i = 0; i < this.balls.size(); i++) {
            if (this.balls.get(i).getType() == 0) {
                WhiteBall whiteBall = (WhiteBall) this.balls.get(i);
                if (whiteBall.isMoving()) {
                    whiteBall.setCollision(false);
                }
            }
        }
    }

    /**
     * starts the placement of walls
     */
    public void startPlacingWall() {
        this.placingWall = true;
        addEntity(wallHandler);
    }

    /**
     * Stops the placement of walls
     */
    public void stopPlacingWall() {
        this.placingWall = false;
    }

    /**
     * checks if a wall is being placed.
     *
     * @return placingWall boolean.
     */
    public boolean getPlacingWall() {
        return this.placingWall;
    }

    /**
     * Place cue ball.
     */
    public void placeCueBall() {
        addEntity(whiteBallHandler);

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
     * Gets holes.
     *
     * @return the holes
     */
    public ArrayList<Hole> getHoles() {
        return holes;
    }

    /**
     * Gets walls.
     *
     * @return the walls
     */
    public ArrayList<Wall> getWalls() {
        return this.walls;
    }

    /**
     * Sets player scored.
     *
     * @param scored the scored
     */
    public void setPlayerScored(boolean scored) {
        this.playerScored = scored;
    }


    /**
     * Gets madness.
     *
     * @return the madness
     */
    public boolean getMadness() {
        return this.isMadness;
    }

    /**
     * Is pocket gravity boolean.
     *
     * @return the boolean
     */
    public boolean isPocketGravity() {
        return pocketGravity;
    }

    /**
     * Sets pocket gravity.
     *
     * @param pocketGravity the pocket gravity
     */
    public void setPocketGravity(boolean pocketGravity) {
        this.pocketGravity = pocketGravity;
    }

    /**
     * Winner screen.
     *
     * @param winnerId the winner id
     */
    public void winnerScreen(int winnerId) {
        this.whiteBallHandler.removeHandler();
        this.wallHandler.removeHandler();

        if (getMadness()) {
            removeEntity(powerupCreator);
        }
        for (int i = 0; i < balls.size(); i++) {
            this.balls.get(i).removeBall();
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

        if (this.walls.size() != 0) {
            for (int i = 0; i < this.walls.size(); i++) {
                removeEntity(this.walls.get(i));
            }
            this.walls.clear();
        }

        this.balls.clear();
        resetBalls();
        setCurrentPlayer(player1);
        resetPowerups();

        removeEntity(menuBackground);
        if (powerupCreator != null) {
            removeEntity(powerupCreator);
            powerupCreator = null;
        }

        for (int i = 0; i < this.balls.size(); i++) {
            this.balls.get(i).setMoving(false);
        }

        this.gui = null;
        this.isMadness = false;
        this.pocketGravity = false;
        Ball.lastisertedid = 0;
        start();
    }
}



package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Player;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.Vector2;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;
import java.util.ArrayList;
import android.util.Log;

/**
 * The type Gravity well powerup.
 */
public class AddBallFromShelf extends Powerup {
    private WhiteBall whiteBall;
    static private Bitmap bitmap;
    private Game game;
    private double x, y, radius;
    private int currentTurn, initialTurn;
    private boolean used;

    /**
     * Instantiates a new Gravity well powerup.
     *
     * @param game the game
     * @param x    the x
     * @param y    the y
     * @param ball the ball
     */
    public AddBallFromShelf(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
        this.x = x;
        this.y = y;
        this.radius = game.getPowerupsize();
    }

    @Override
    public void tick() {
        super.tick();
        this.currentTurn = game.getTurns();
        if (this.collected) {
            if (!used) {
                used = true;
                // remove a ball from the player's shelf and put it somewhere
                // on a VALID POSITION on the table

                Player currentPlayer = game.getCurrentplayer();
                ArrayList<Ball> scoredBallsForCurrentPlayer = currentPlayer.getScoredballs();

                if(scoredBallsForCurrentPlayer.size() > 0) {
                    int index = Utility.getRandIntInRange(0, scoredBallsForCurrentPlayer.size() - 1);

                    // get a random location
                    Vector2 randomLocation = new Vector2();
                    do{
                        randomLocation.set(
                                (int)Utility.randomDoubleFromRange(100, game.getWidth() - 100),
                                (int)Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100)
                        );
                    }
                    while(!validPosition(randomLocation));

                    // copy the properties from the scored ball over to the 'new' ball
                    final Ball theBallEveryOneHates = scoredBallsForCurrentPlayer.get(index);
                    theBallEveryOneHates.setVector2(randomLocation);
                    theBallEveryOneHates.setSpeedX(0);
                    theBallEveryOneHates.setSpeedY(0);
                    theBallEveryOneHates.resetShadow();

                    // add the ball to the game
                    scoredBallsForCurrentPlayer.remove(index);
                    game.addEntity(theBallEveryOneHates);
                }
            }
        } else if (this.initialTurn + 2 == this.currentTurn) {
            removePowerup();
        }
    }

    private boolean validPosition(Vector2 position) {
        for(Ball ball : game.getBalls()) {
            double ballRadius = ball.getRadius();
            double distance = Utility.getDistanceNotSquared(
                    ball.getVector2().getX() + ballRadius,
                    ball.getVector2().getY() + ballRadius,
                    position.getX() + ballRadius,
                    position.getY() + ballRadius
            );

            if(distance < ballRadius * 2) {
                return false;
            }
        }
        return true;
    }

    public void draw(GameView gameView) {
        if (!invisable) {
            if (bitmap == null) {
                bitmap = gameView.getBitmapFromResource(R.drawable.add_ball_from_shelf_powerup);
            }
            gameView.drawBitmap(bitmap, (float) x, (float) y, (float) this.radius, (float) this.radius);
        }
    }

    @Override
    public void resolveColission() {
        this.initialTurn = game.getTurns();
        this.invisable = true;
        this.collected = true;
    }

    @Override
    public void createPowerUp() {
        AddBallFromShelf addBallFromShelf =
                new AddBallFromShelf(game,
                        (float) Utility.randomDoubleFromRange(100, game.getWidth() - 100),
                        (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100),
                        this.whiteBall
                );
        game.getPowerups().add(addBallFromShelf);
        game.addEntity(addBallFromShelf);
    }
}
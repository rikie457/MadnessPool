package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Player;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

/**
 * The type Remove ball.
 */
public class RemoveBall extends Powerup {
    private WhiteBall whiteBall;
    private Player player;
    static private Bitmap bitmap;
    private Game game;
    private double x, y, radius;
    private boolean applied = false;

    private ArrayList<Ball> balls;

    /**
     * Instantiates a new Remove ball.
     *
     * @param game the game
     * @param x    the x
     * @param y    the y
     * @param ball the ball
     */
    public RemoveBall(Game game, double x, double y, WhiteBall ball) {
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
        if (this.collected && !this.applied) {
            applyEffect();
            this.applied = true;
        }
    }


    @Override
    public int getLayer() {
        return 1;
    }

    /**
     * Scores one of the balls
     */
    public void applyEffect() {
        this.balls = game.getBalls();
        this.player = game.getCurrentplayer();
        boolean removed = false;
        int counter = 0;

        //If there is no set ball type this will randomly set a ball type.
        if (player.getBalltype() == -1) {
            player.setBalltype((int) Math.floor(Math.random() * 2) + 1);

            if (player.getBalltype() == 1) {
                game.getInactiveplayer().setBalltype(2);
            } else if (player.getBalltype() == 2) {
                game.getInactiveplayer().setBalltype(1);
            }
        }

        //Scores a ball if there is one available.
        if (this.balls.size() > 1) {
            while (!removed && counter < this.balls.size()) {
                if (this.balls.get(counter).getType() == this.player.getBalltype() && !player.getScoredballs().contains(this.balls.get(counter))) {
                    removed = true;
                    this.balls.get(counter).setMoving(false);
                    player.getScoredballs().add(this.balls.get(counter));
                    this.balls.get(counter).removeBall();
                }
                counter++;
            }
            //If all normal balls of a player are scored it will score the 8 ball.
            if (!removed) {
                for (int i = 0; i < this.balls.size(); i++) {
                    if (this.balls.get(i).getType() == 3) {
                        this.balls.get(i).setMoving(false);
                        this.balls.get(i).removeBall();
                        game.winnerScreen(player.getPlayerId());
                    }
                }
            }
        }
        this.applied = false;
        removePowerup();
    }

    public void resolveColission() {
        this.invisable = true;
        this.collected = true;
    }

    public void draw(GameView gameView) {
        if (!invisable) {
            if (bitmap == null) {
                bitmap = gameView.getBitmapFromResource(R.drawable.removeball);
            }
            gameView.drawBitmap(bitmap, (float) x, (float) y, (float) this.radius, (float) this.radius);
        }
    }

    @Override
    public void createPowerUp() {
        RemoveBall removeBall = new RemoveBall(game, (float) Utility.randomDoubleFromRange(100, game.getWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall);
        game.getPowerups().add(removeBall);
        game.addEntity(removeBall);
    }
}
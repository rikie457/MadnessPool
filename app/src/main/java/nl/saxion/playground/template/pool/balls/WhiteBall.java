package nl.saxion.playground.template.pool.balls;

import android.view.MotionEvent;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Coord;
import nl.saxion.playground.template.pool.Cue;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Hole;
import nl.saxion.playground.template.pool.Player;
import nl.saxion.playground.template.pool.ShootLine;
import nl.saxion.playground.template.pool.Utility;

import static java.lang.Math.PI;


/**
 * The type White ball.
 */
public class WhiteBall extends Ball {

    private ShootLine line;
    private ShootLine lineReflection;
    private Cue cue;

    private float newXReversed;
    private float newYReversed;

    /**
     * Instantiates a new White ball.
     *
     * @param game           the game
     * @param balls          the balls
     * @param holes          the holes
     * @param players        the players
     * @param x              the x
     * @param y              the y
     * @param width          the width
     * @param height         the height
     * @param type           the type
     * @param line           the line
     * @param lineReflection the line reflection
     * @param cue            the cue
     */
    public WhiteBall(Game game,
                     ArrayList<Ball> balls, ArrayList<Hole> holes, ArrayList<Player> players,
                     double x, double y, double width, double height, int type,
                     ShootLine line, ShootLine lineReflection, Cue cue) {
        super(game, balls, holes, players, x, y, width, height, type);
        this.line = line;
        this.lineReflection = lineReflection;
        this.cue = cue;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void draw(GameView gv) {
        super.draw(gv);
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        if (this.line != null && this.cue != null && !game.checkMovementForAllBalls() && !game.getCueBallScored()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.cue.setVisible(true);
                this.line.setVisible(true);

                this.oldX = (float) this.x;
                this.oldY = (float) this.y;
                this.cue.setX((float) this.oldX + (float) this.radius);
                this.cue.setY((float) this.oldY + (float) this.radius);
                this.cue.setNewX((float) this.newX);
                this.cue.setNewY((float) this.newY);

                this.line.setX((float) this.oldX + (float) this.radius);
                this.line.setY((float) this.oldY + (float) this.radius);

                this.newXReversed = this.cue.getX() - (touch.x - this.cue.getX());
                this.newYReversed = this.cue.getY() - (touch.y - this.cue.getY());

                this.line.setNewX(this.newXReversed);
                this.line.setNewY(this.newYReversed);

                if (this.line.reflect()) {
                    this.lineReflection.setVisible(true);
                    Coord[] reflection = this.line.getReflectionLine();
                    this.lineReflection.setX(reflection[0].getX());
                    this.lineReflection.setY(reflection[0].getY());
                    this.lineReflection.setNewX(reflection[1].getX());
                    this.lineReflection.setNewY(reflection[1].getY());
                } else this.lineReflection.setVisible(false);

            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (!this.line.getVisible()) {
                    this.line.setVisible(true);
                    this.oldX = (float) this.x;
                    this.oldY = (float) this.y;
                    this.line.setX((float) this.oldX + (float) this.radius);
                    this.line.setY((float) this.oldY + (float) this.radius);
                }

                if (!this.cue.getVisible()) {
                    this.cue.setVisible(true);
                    this.oldX = (float) this.x;
                    this.oldY = (float) this.y;
                    this.cue.setX((float) this.oldX + (float) this.radius);
                    this.cue.setY((float) this.oldY + (float) this.radius);
                }

                this.newX = touch.x;
                this.newY = touch.y;

                this.newXReversed = this.cue.getX() - (touch.x - this.cue.getX());
                this.newYReversed = this.cue.getY() - (touch.y - this.cue.getY());

                this.cue.setNewX((float) this.newX);
                this.cue.setNewY((float) this.newY);

                this.line.setNewX((float) this.newXReversed);
                this.line.setNewY((float) this.newYReversed);

                if (this.line.reflect()) {
                    this.lineReflection.setVisible(true);
                    Coord[] reflection = this.line.getReflectionLine();
                    this.lineReflection.setX(reflection[0].getX());
                    this.lineReflection.setY(reflection[0].getY());
                    this.lineReflection.setNewX(reflection[1].getX());
                    this.lineReflection.setNewY(reflection[1].getY());
                } else this.lineReflection.setVisible(false);

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                double mag = Math.abs(Utility.getDistanceNotSquared(this.x, this.y, touch.x, touch.y)) * 2;
                this.cue.setVisible(false);
                this.line.setVisible(false);
                this.lineReflection.setVisible(false);

                this.speedX = 0.00001 * (this.x + mag * Math.cos(Math.toRadians(Math.atan2(this.oldY - this.newY, this.oldX - this.newX) * 180 / PI)));
                this.speedY = 0.00001 * (this.y + mag * Math.sin(Math.toRadians(Math.atan2(this.oldY - this.newY, this.oldX - this.newX) * 180 / PI)));
                this.shot = true;
            }
        }
    }
}

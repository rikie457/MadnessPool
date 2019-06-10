package nl.saxion.playground.template.pool.balls;

import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.logging.Logger;

import nl.saxion.playground.template.R;
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

    private Coord origin, end;

    /**
     * Instantiates a new White ball.
     *
     * @param game   the game
     * @param balls  the balls
     * @param holes  the holes
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param image  the image
     * @param type   the type
     * @param line   the line
     */
    public WhiteBall(Game game,
                     ArrayList<Ball> balls, ArrayList<Hole> holes, ArrayList<Player> players,
                     double x, double y, double width, double height,
                     int image, int type,
                     ShootLine line, ShootLine lineReflection, Cue cue) {
        super(game, balls, holes, players, x, y, width, height, image, type);
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
        if (this.bitmap == null) {
            this.bitmap = gv.getBitmapFromResource(this.image);
        }
        gv.drawBitmap(bitmap, (float) this.x, (float) this.y, (float) this.width, (float) this.height);

    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        if (this.line != null && this.cue != null && !game.checkMovementForAllBalls() && !game.getCueBallScored()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.line.setVisible(true);

                initOriginAndEnd(touch);

            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (!this.line.getVisible()) {
                    initOriginAndEnd(touch);
                    this.line.setVisible(true);
                }

                updateEnd(touch);

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                double mag = Math.abs(Utility.getDistanceNotSquared(this.origin.getX(), this.origin.getY(), touch.x, touch.y)) * 2;
                this.cue.setVisible(false);
                this.line.setVisible(false);
                this.lineReflection.setVisible(false);

                this.speedX = 0.00001 * (this.x + mag * Math.cos(Math.toRadians(Math.atan2(this.origin.getY() - this.end.getY(), this.origin.getX() - this.end.getX()) * 180 / PI)));
                this.speedY = 0.00001 * (this.y + mag * Math.sin(Math.toRadians(Math.atan2(this.origin.getY() - this.end.getY(), this.origin.getX() - this.end.getX()) * 180 / PI)));
                this.shot = true;
            }
        }
    }

    /**
     *
     * @param touch has all information about the initial touch
     */
    private void initOriginAndEnd(GameModel.Touch touch) {
        this.origin = new Coord(touch.x, touch.y);
        this.end = new Coord(touch.x, touch.y);

        // init drawable shootLine
        this.line.setX((float)this.x + (float)this.radius);
        this.line.setY((float)this.y + (float)this.radius);
    }

    /**
     *
     * @param touch has all the information about the touch
     */
    public void updateEnd(GameModel.Touch touch) {
        this.end.set(touch.x, touch.y);

        // update drawable shootLine
        float xOffset = (float)this.x - this.origin.getX() + (float)this.radius;
        float yOffset = (float)this.y - this.origin.getY() + (float)this.radius;

        this.line.setNewX(this.end.getX() + xOffset);
        this.line.setNewY(this.end.getY() + yOffset);

        // update line color
        int mag = (int)(Math.sqrt(Math.abs(Utility.getDistanceNotSquared(this.origin.getX(), this.origin.getY(), touch.x, touch.y))) * 0.20);

        this.line.setColor(mag, 128 - mag/2, 255 - mag);
    }
}

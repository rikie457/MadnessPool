package nl.saxion.playground.template.pool.balls;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Hole;
import nl.saxion.playground.template.pool.ShootLine;
import nl.saxion.playground.template.pool.Utility;

import static java.lang.Math.PI;


public class WhiteBall extends Ball {

    private ShootLine line;

    public WhiteBall(Game game, ArrayList<Ball> balls, ArrayList<Hole> holes, ArrayList<Ball> sunkenBalls, double x, double y, double width, double height, int image, int type, ShootLine line) {
        super(game, balls, holes, sunkenBalls, x, y, width, height, image, type);
        this.line = line;
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
        if (this.line != null && !game.checkMovementForAllBalls()) {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.line.setVisible(true);
                this.oldX = (float) this.x;
                this.oldY = (float) this.y;
                this.line.setX((float) this.oldX + (float) this.radius);
                this.line.setY((float) this.oldY + (float) this.radius);
                this.line.setNewX((float) this.newX);
                this.line.setNewY((float) this.newY);

            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (!this.line.getVisible()) {
                    this.line.setVisible(true);
                    this.oldX = (float) this.x;
                    this.oldY = (float) this.y;
                    this.line.setX((float) this.oldX + (float) this.radius);
                    this.line.setY((float) this.oldY + (float) this.radius);
                }

                this.newX = touch.x;
                this.newY = touch.y;
                this.line.setNewX((float) this.newX);
                this.line.setNewY((float) this.newY);

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                double mag = Math.abs(Utility.getDistance(this.x, this.y, touch.x, touch.y)) * 2;
                this.line.setVisible(false);

                this.speedX = 0.00001 * (this.x + mag * Math.cos(Math.toRadians(Math.atan2(this.oldY - this.newY, this.oldX - this.newX) * 180 / PI)));
                this.speedY = 0.00001 * (this.y + mag * Math.sin(Math.toRadians(Math.atan2(this.oldY - this.newY, this.oldX - this.newX) * 180 / PI)));
                this.shot = true;
            }
        }
    }
}
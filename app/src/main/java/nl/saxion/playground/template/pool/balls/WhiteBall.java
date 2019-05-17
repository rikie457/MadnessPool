package nl.saxion.playground.template.pool.balls;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Gui;
import nl.saxion.playground.template.pool.Hole;
import nl.saxion.playground.template.pool.ShootLine;
import nl.saxion.playground.template.pool.Utility;

import static java.lang.Math.PI;

public class WhiteBall extends Ball {

    private ShootLine line;
    private boolean moving;
    private double oldX, oldY, newX, newY;
    public int currentplayer = 1;
    private Gui gui;

    public WhiteBall(Game game, Gui gui, ArrayList<Ball> balls, ArrayList<Hole> holes, ArrayList<Ball> sunkenBalls, double x, double y, double width, double height, int image, ShootLine line) {
        super(game, balls, holes, sunkenBalls, x, y, width, height, image, 0);
        this.line = line;
        this.gui = gui;
    }

    private boolean checkMovement() {
        if (this.speedX == 0 && this.speedY == 0) {
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        this.moving = checkMovement();
    }

    @Override
    public void draw(GameView gv) {
        super.draw(gv);
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        if (this.id == 16 && this.line != null && !this.moving) {
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

                if (this.currentplayer == 1) {
                    this.currentplayer = 2;
                    gui.setCurrentPlayer(2);
                } else {
                    this.currentplayer = 1;
                    gui.setCurrentPlayer(1);
                }
            }
        }
    }
}

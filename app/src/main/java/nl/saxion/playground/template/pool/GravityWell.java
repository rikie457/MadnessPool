package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.balls.Ball;

public class GravityWell extends Entity {
    private Game game;
    private boolean visible = true;
    private static int counter = 0, nrTicks = 0;
    private double width, height;
    private ArrayList<Ball> balls;
    private double angle = 0;
    private Vector2 location;
    private static Bitmap bitmap1, bitmap2, bitmap3, current;
    private float spawnGrowth;
    private int lastTurn, myTurn;
    private int evaporateAmount = 15;

    public GravityWell(Game game, ArrayList<Ball> balls) {
        this.location = new Vector2(
                Utility.randomDoubleFromRange(100, game.getWidth() - 100),
                Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100)
        );
        this.game = game;
        this.balls = balls;
        this.width = game.getPowerupsize() * Utility.getRandIntInRange(4, 8); // 30 * (4-8) min: 120 - max: 240
        this.height = this.width;
        this.spawnGrowth = (float)-this.width;

        this.lastTurn = game.getTurns();
        this.myTurn = lastTurn + 1;
    }

    @Override
    public void tick() {
        if(nrTicks % 720 == 0) {
            getNextBitmap();
        }
        nrTicks++;

        // evaporate part of the hole every turn
        if(game.getTurns() != lastTurn && game.getTurns() != myTurn) {
            this.width -= evaporateAmount;
            this.height -= evaporateAmount;

            this.location.add(evaporateAmount/2);

            if(this.width <= 0) {
                game.removeEntity(this);
                return;
            }

            lastTurn = game.getTurns();
        }

        // sucks all nearby balls towards this hole
        attractBalls();
    }

    @Override
    public void draw(GameView gameView) {
        if(!visible) return;

        if(bitmap1 == null) {
            bitmap1 = gameView.getBitmapFromResource(R.drawable.gravity_well_1);
            current = bitmap1;
        }
        if(bitmap2 == null) {
            bitmap2 = gameView.getBitmapFromResource(R.drawable.gravity_well_2);
        }
        if(bitmap3 == null) {
            bitmap3 = gameView.getBitmapFromResource(R.drawable.gravity_well_3);
        }

        float addedObesity = getObesity();

        // draw the gravity well
        gameView.drawBitmap(current,
                (float)location.getX() - addedObesity / 2,
                (float)location.getY() - addedObesity / 2,
                (float)width + addedObesity,
                (float)height + addedObesity,
                (float)getNextAngle()
        );
    }

    private float getObesity() {
        if(spawnGrowth < 0)
            return spawnGrowth++;
        else return 0;
    }

    private double getNextAngle() {
        return angle+=0.1;
    }

    private void attractBalls() {
        double distance;
        double mass = (this.width / 200);
        double maximumDistance = this.width;
        double minimumDistance = 0;

        for(Ball ball : balls) {
            if (ball.stopGravitiy((int) (this.width * 2))) {
                continue;
            }

            Vector2 force = new Vector2();

            float ballMiddleX = (float)(ball.getVector2().getX() + ball.getRadius());
            float ballMiddleY = (float)(ball.getVector2().getY() + ball.getRadius());
            float thisMiddleX = (float)(this.location.getX() + this.width / 2);
            float thisMiddleY = (float)(this.location.getY() + this.height / 2);

            distance = Math.sqrt(Utility.getDistanceNotSquared(ballMiddleX, ballMiddleY, thisMiddleX, thisMiddleY));

            double xDifference = thisMiddleX - ballMiddleX;
            double yDifference = thisMiddleY - ballMiddleY;

            if (distance < maximumDistance && distance >= minimumDistance) {
                force.setX(mass * (xDifference) / Math.pow(distance, 2));
                force.setY(mass * (yDifference) / Math.pow(distance, 2));
                ball.applyForce(force);
            }
        }
    }

    private void getNextBitmap() {
        switch(counter) {
            case 0:
                current = bitmap1;
                break;
            case 1:
                current = bitmap2;
                break;
            case 2:
                current = bitmap3;
                break;
            case 3:
                current = bitmap2;
        }
        counter = (counter + 1) % 4;
    }

    @Override
    public int getLayer() {
        return 1;
    }
}

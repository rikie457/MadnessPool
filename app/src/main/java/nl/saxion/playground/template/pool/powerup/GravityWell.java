package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.Vector2;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public class GravityWell extends Powerup {
    private Game game;
    private WhiteBall whiteBall;
    private boolean visible = true;
    private static int counter = 0, nrTicks = 0;
    private double width, height;
    private ArrayList<Ball> balls;
    private double angle = 0;

    private static Bitmap bitmap1, bitmap2, bitmap3, current;

    public GravityWell(Game game, double x, double y, WhiteBall whiteBall, ArrayList<Ball> balls) {
        super(game, x, y, whiteBall);
        this.game = game;
        this.whiteBall = whiteBall;
        this.balls = balls;

        this.width = game.getPowerupsize() * Utility.getRandIntInRange(4, 8);
        this.height = this.width;
    }

    @Override
    public void tick() {
        super.tick();
        if(nrTicks % 720 == 0) {
            getNextBitmap();
        }
        nrTicks++;

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

        // draw the gravity well
        gameView.drawBitmap(current,
                (float)vector2.getX(),
                (float)vector2.getY(),
                (float)width,
                (float)height,
                (float)getNextAngle()
        );
    }

    private double getNextAngle() {
        return angle+=0.1;
    }

    private void attractBalls() {
        double distance;
        double mass = (this.width / 200);
        double maximumDistance = this.width;
        double minimumDistance = 0;//this.width;

        for(Ball ball : balls) {
            if(ball.stoppedLikingTheGravity((int)(this.width * 2))) {
                continue;
            }

            Vector2 force = new Vector2();

            float ballMiddleX = (float)(ball.getVector2().getX() + ball.getRadius());
            float ballMiddleY = (float)(ball.getVector2().getY() + ball.getRadius());
            float thisMiddleX = (float)(this.vector2.getX() + this.width / 2);
            float thisMiddleY = (float)(this.vector2.getY() + this.height / 2);

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

    @Override
    public void createPowerUp() {
        GravityWell gravityWell =
        new GravityWell(
                game,
                (float) Utility.randomDoubleFromRange(100, game.getPlayWidth() - 100),
                (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100),
                this.whiteBall,
                balls
        );

        game.getPowerups().add(gravityWell);
        game.addEntity(gravityWell);
    }
}

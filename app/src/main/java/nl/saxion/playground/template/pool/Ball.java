package nl.saxion.playground.template.pool;


import android.graphics.Paint;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class Ball extends Entity {

    public double speedX, speedY;
    private double mass, x, y, width, height, radius, bx, by, friction;
    private int color;
    private ArrayList<Ball> balls;
    private Game game;

    public Ball(Game game, ArrayList<Ball> balls, double x, double y, double width, double height, int color) {
        this.game = game;
        this.balls = balls;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.radius = width / 2;
        this.speedY = 125;
        this.speedX = 125;
        this.bx = game.getWidth();
        this.by = game.getHeight();
        this.color = color;
        this.mass = 10;
        this.friction = .9;
    }

    private void checkCollisionBall(ArrayList<Ball> balls) {
        for (int i = 0; i < balls.size(); i++) {
            double distSqr = Utility.getDistance(this.getX(), this.getY(), balls.get(i).getX(), balls.get(i).getY());
            double xd = Utility.getXDistance(this.getX(), balls.get(i).getX());
            double yd = Utility.getYDistance(this.getY(), balls.get(i).getY());

            if (this == balls.get(i)) {
                continue;
            }

            if (distSqr <= (this.getRadius() + balls.get(i).getRadius()) * (this.getRadius() + balls.get(i).getRadius())) {
                Info.addToBallCollisionCounter();
                double xVelocity = balls.get(i).getSpeedX() - this.getSpeedX();
                double yVelocity = balls.get(i).getSpeedY() - this.getSpeedY();
                double dotProduct = xd * xVelocity + yd * yVelocity;
                if (dotProduct > 0) {
                    double collisionScale = dotProduct / distSqr;
                    double xCollision = xd * collisionScale;
                    double yCollision = yd * collisionScale;
                    double combinedMass = this.getMass() + balls.get(i).getMass();
                    double collisionWeightA = 2 * balls.get(i).getMass() / combinedMass;
                    double collisionWeightB = 2 * this.getMass() / combinedMass;
                    this.speedX += collisionWeightA * xCollision;
                    this.speedY += collisionWeightA * yCollision;
                    balls.get(i).speedX -= collisionWeightB * xCollision;
                    balls.get(i).speedY -= collisionWeightB * yCollision;
                }
            }
        }

    }

    private void checkCollisionWall() {
        this.x = x + speedX;
        this.y = y + speedY;

        if (this.x - this.radius < 0) {
            Info.addToWallCollisionCounter();
            this.speedX = -this.speedX;
            this.x = this.radius;
        } else if (this.x + this.radius > this.bx) {
            Info.addToWallCollisionCounter();
            this.speedX = -this.speedX;
            this.x = this.bx - this.radius;
        }

        if (this.y - this.radius < 0) {
            Info.addToWallCollisionCounter();
            this.speedY = -this.speedY;
            this.y = this.radius;
        } else if (this.y + this.radius > this.by) {
            Info.addToWallCollisionCounter();
            this.speedY = -this.speedY;
            this.y = this.by - this.radius;
        }
    }


    @Override
    public void tick() {
        checkCollisionBall(this.balls);
        checkCollisionWall();
    }

    @Override
    public void draw(GameView gv) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(this.color);
        gv.getCanvas().drawCircle((float) this.x, (float) this.y, (float) this.radius, paint);
    }


    public void setColor(int color) {
        this.color = color;
    }

    public double getMass() {
        return this.mass;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSpeedX() {
        return this.speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public double getSpeedY() {
        return this.speedY;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public double getRadius() {
        return this.radius;
    }


}

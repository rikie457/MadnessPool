package nl.saxion.playground.template.pool;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;

public class Ball extends Entity {

    public static int lastisertedid = 1;
    public double speedX, speedY;
    private double mass, x, y, width, height, radius, bx, by, friction, energyloss;
    private int color, id;
    private ArrayList<Ball> balls;
    private Game game;

    public Ball(Game game, ArrayList<Ball> balls, double x, double y, double width, double height, int color) {
        this.id = lastisertedid;
        lastisertedid++;
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
        this.friction = .9999;
        this.energyloss = .65;
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
            this.x = this.radius;
            this.speedX = -this.speedX;
        } else if (this.x + this.radius > this.bx) {
            Info.addToWallCollisionCounter();
            this.speedX = -this.speedX;
            this.x = this.bx - this.radius;
            this.speedX *= this.energyloss;
        } else {
            this.x += this.speedX;
            this.speedX *= this.friction;
            if (Math.abs(this.speedX) < .8) {
                this.speedX = 0;
            }
        }

        if (this.y - this.radius < 0) {
            Info.addToWallCollisionCounter();
            this.speedY = -this.speedY;
            this.y = this.radius;
        } else if (this.y + this.radius > this.by) {
            Info.addToWallCollisionCounter();
            this.speedY = -this.speedY;
            this.y = this.by - this.radius;
            this.speedY *= this.energyloss;
        } else {
            this.y += this.speedY;
            this.speedY *= this.friction;
            if (Math.abs(this.speedY) < .8) {
                this.speedY = 0;
            }
        }
    }


    @Override
    public void tick() {
        checkCollisionBall(this.balls);
        checkCollisionWall();
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        double oldX = 0, oldY = 0, newX = 0, newY = 0;
        long timerTime = 0, startTime = 0;


        if (this.id == 16) {
            this.x = touch.x;
            this.y = touch.y;

//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//
//                oldX = event.getX();
//                oldY = event.getY();
//                //start timer
//                startTime = event.getEventTime();
//
//            } else if (event.getAction() == MotionEvent.ACTION_UP) {
//
//                //long timerTime = getTime between two event down to Up
//                newX = event.getX();
//                newY = event.getY();
//                timerTime = event.getEventTime() - startTime;
//
//                float distance = (float) Math.sqrt((newX - oldX) * (newX - oldX) + (newY - oldY) * (newY - oldY));
//                float speed = distance / timerTime;
//
//                this.speedY = speed;
//                this.speedX = speed;
//
//
//            }
        }
    }

    @Override
    public void draw(GameView gv) {
        //Draw ball
        Paint paint = new Paint();
        paint.setColor(this.color);
        gv.getCanvas().drawCircle((float) this.x, (float) this.y, (float) this.radius, paint);

        //Draw number of ball
        if (id != -1) {
            Rect bounds = new Rect();
            String text = Integer.toString(this.id);
            paint.setTextSize(24f);
            paint.setFakeBoldText(true);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            paint.getTextBounds(text, 0, text.length(), bounds);
            gv.getCanvas().drawText(text, (float) this.x, (float) this.y, paint);
        }


        // drawing the stripes
//        double stripeWidth = 2 * (int) (Math.sqrt(radius * radius - (radius / 2) * (radius / 2)));
//        double offset = radius - stripeWidth / 2;


//        final RectF ovaltop = new RectF();
//        final RectF ovalbottom = new RectF();
//        float center_x, center_y;
//        center_x = (float) this.x;
//        center_y = (float) this.y;
//
//        paint.setColor(Color.WHITE);
//        ovalbottom.set((float) (center_x - radius),
//                (float) (center_y - radius),
//                (float) (center_x + radius),
//                (float) (center_y + radius));
//
//        ovaltop.set((float) (center_x - radius),
//                (float) (center_y - radius),
//                (float) (center_x + radius),
//                (float) (center_y + radius));
//
//        gv.getCanvas().drawArc(ovalbottom, 0, 180, false, paint);
//        gv.getCanvas().drawArc(ovaltop, 180, 180, false, paint);
        // gv.getCanvas().fillArc((int) (this.x - radius + offset), (int) (this.y - radius), (int) (stripeWidth), (int) (radius), 0, 180);
        // gv.getCanvas().fillArc((int) (this.x - radius + offset), (int) (this.y), (int) (stripeWidth), (int) (radius), 0, -180);
//
        // drawing the outline
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        gv.getCanvas().drawCircle((float) (this.x), (float) (this.y), (float) (this.radius), paint);


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

/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool.balls;

import android.graphics.Bitmap;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Hole;
import nl.saxion.playground.template.pool.Player;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.Vector2;
import nl.saxion.playground.template.pool.Wall;

public class Ball extends Entity {
    public static int lastisertedid = 0;

    protected double speedX, speedY, mass, width, height, radius, bx, by, friction, energyloss;
    protected Vector2 vector2;
    protected Vector2 madnessWallVector;
    protected Game game;

    private boolean hasShadow = true;

    private static Bitmap[] bitmaps;
    private static Bitmap ball_inner_shadow;
    private int id, type;
    private boolean moving;
    private boolean collision = true;
    private int[] drawables;


    public Ball(Game game, int[] drawables, double x, double y, double width, double height, int type) {
        this.id = lastisertedid;
        lastisertedid++;
        this.game = game;
        this.width = width;
        this.height = height;
        this.radius = width / 2;
        this.speedY = 0;
        this.speedX = 0;
        this.bx = game.getPlayWidth();
        this.by = game.getPlayHeight();
        this.mass = 10;
        this.friction = .9965;
        this.energyloss = .900;
        this.type = type;
        this.vector2 = new Vector2(x, y);
        this.madnessWallVector = new Vector2();
        this.drawables = drawables;
        if (bitmaps == null) {
            bitmaps = new Bitmap[16];
        }
    }


    private void checkCollisionBall(ArrayList<Ball> balls) {
        for (int i = this.id + 1; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            double ball1x = this.vector2.getX();
            double ball1y = this.vector2.getY();
            double ball2x = ball.vector2.getX();
            double ball2y = ball.vector2.getY();

            double distSqr = Utility.getDistanceNotSquared(ball1x, ball1y, ball2x, ball2y);
            double xd = ball1x - ball2x;
            double yd = ball1y - ball2y;

            boolean colliding = distSqr <= (this.getRadius() + ball.radius) * (this.getRadius() + ball.radius) && this.collision;
            if (colliding) {

                if (this.speedX == 0 && this.speedY == 0 && ball.speedX == 0 && ball.speedY == 0) {
                    this.speedY = .5;
                    this.speedX = -.5;
                }
                double xVelocity = ball.speedX - this.speedX;
                double yVelocity = ball.speedY - this.speedY;
                double dotProduct = xd * xVelocity + yd * yVelocity;
                if (dotProduct > 0) {
                    double collisionScale = dotProduct / distSqr;
                    double xCollision = xd * collisionScale;
                    double yCollision = yd * collisionScale;
                    double combinedMass = this.getMass() + ball.getMass();
                    double collisionWeightA = 2 * ball.getMass() / combinedMass;
                    double collisionWeightB = 2 * this.getMass() / combinedMass;
                    this.speedX += collisionWeightA * xCollision;
                    this.speedY += collisionWeightA * yCollision;
                    ball.speedX -= collisionWeightB * xCollision;
                    ball.speedY -= collisionWeightB * yCollision;
                }
            }
        }
    }

    private void checkCollisionWall() {
        double x = this.vector2.getX();
        double y = this.vector2.getY();
        this.vector2.set(x += this.speedX, y += this.speedY);
        /**
         * muren rechts en links
         */
        if (x - this.radius <= game.getPlayWidth() * 0.056) {
            this.vector2.setX(game.getPlayWidth() * 0.056 + this.radius);
            this.speedX = -this.speedX;
        } else if (x + this.radius >= game.getPlayWidth() * 0.93) {
            this.speedX = -this.speedX;
            this.vector2.setX(game.getPlayWidth() * 0.93 - this.radius);
            this.speedX *= this.energyloss;
        } else {
            this.vector2.setX(x += this.speedX);
            this.speedX *= this.friction;
            if (Math.abs(this.speedX) < .1 && Math.abs(this.speedY) < .1) {
                this.speedX = 0;
            }
        }
        /**
         * muuren boven en onder
         */
        if (y - this.radius <= game.getPlayWidth() * 0.04) {
            this.speedY = -this.speedY;
            this.vector2.setY(game.getPlayWidth() * 0.04 + this.radius);
        } else if (y + this.radius > game.getPlayHeight() * 0.85) {
            this.speedY = -this.speedY;
            this.vector2.setY(game.getPlayHeight() * 0.85 - this.radius);
            this.speedY *= this.energyloss;
        } else {
            this.vector2.setY(y += this.speedY);
            this.speedY *= this.friction;
            if (Math.abs(this.speedY) < .1 && Math.abs(this.speedX) < .1) {
                this.speedY = 0;
            }
        }
    }

    private void removeBall() {
        this.game.removeEntity(this);
        this.hasShadow = false;
    }

    private void checkCollisionHole() {
        double x = this.vector2.getX();
        double y = this.vector2.getY();

        for (Hole hole : game.getHoles()) {

            double distance = Math.sqrt(Utility.getDistanceNotSquared(x + this.radius, y + this.radius, hole.getVector2().getX(), hole.getVector2().getY()));
            if (distance > this.radius) continue; // no collision

            // The ball hit the pocket!

            if (this.type == 3) {
                //is 8 ball
                if (game.getCurrentplayer().getScoredballs().size() < 7) {
                    game.winnerScreen(game.getInactiveplayer().getPlayerId());
                } else {
                    game.winnerScreen(game.getCurrentplayer().getPlayerId());
                }
            } else if (this.type == 0) {
                //is cue ball
                game.placeCueBall();

            } else {
                // A regular ball
                Player player = game.getCurrentplayer();
                if (player.getBalltype() == -1) {
                    // The first pocket ball
                    player.setBalltype(this.type);
                    game.getInactiveplayer().setBalltype(this.type == 1 ? 2 : 1);
                    player.getScoredballs().add(this);
                    game.setPlayerScored(true);
                    if (game.getMadness()) {
                        game.startPlacingWall();
                    }
                } else {
                    if (this.type == player.getBalltype()) {
                        // Right colored ball pocket
                        player.getScoredballs().add(this);
                        game.setPlayerScored(true);
                        if (game.getMadness()) {
                            game.startPlacingWall();
                        }
                    } else {
                        // Opponents ball pocketed
                        game.getInactiveplayer().getScoredballs().add(this);
                    }
                }
            }
            this.moving = false;
            removeBall();
        }
    }

    /**
     * Sends the ball in the right direction on hitting the wall.
     */
    private void checkCollisionPlaceableWalls() {
        double totalVelocity = this.speedX + this.speedY;
        double totalCordDiff, xCordDiff, yCofdDiff, newSpeedXCalc, newSpeedYCalc;
        double angleDifference;

        Wall wall;


        for (int i = 0; i < game.getWalls().size(); i++) {
            wall = game.getWalls().get(i);
            if (collisionBallWall(game.getWalls().get(i))) {


                if (wall.getLineAngle() > getAngleMovement()) {
                    angleDifference = wall.getLineAngle() - getAngleMovement();
                } else {
                    angleDifference = getAngleMovement() - wall.getLineAngle();
                }

                if (angleDifference > 180) {
                    angleDifference -= 180;
                }

                //Ball up = min, ball down = plus, ball right = 0, ball left = 180
                //wall measured from the line end
                //line end right side = 180, line end left side = 0, line end up = plus, line end down = min

                //Opposite angle line = movementAngle + 180 + 2 * angleDifference

                //this.endVector2.set(this.middleX + this.radius * Math.cos(this.lineRotation), this.middleY + this.radius * Math.sin(Math.sin(this.lineRotation)));

                this.madnessWallVector.set(this.vector2.getX() + 20 * Math.cos(getAngleMovement() + angleDifference * 2), this.vector2.getY() + 20 * Math.sin(Math.sin(getAngleMovement() + angleDifference * 2)));

                xCordDiff = this.vector2.getX() - this.madnessWallVector.getX();
                yCofdDiff = this.vector2.getY() - this.madnessWallVector.getY();

                if (xCordDiff < 0 && yCofdDiff > 0) {
                    totalCordDiff = -1 * xCordDiff + yCofdDiff;
                } else if (xCordDiff < 0 && yCofdDiff < 0) {
                    totalCordDiff = -1 * xCordDiff + -1 * yCofdDiff;
                } else if (xCordDiff > 0 && yCofdDiff < 0) {
                    totalCordDiff = xCordDiff + -1 * yCofdDiff;
                } else {
                    totalCordDiff = xCordDiff + yCofdDiff;
                }

                newSpeedXCalc = xCordDiff / totalCordDiff;
                newSpeedYCalc = yCofdDiff / totalCordDiff;
                this.speedX = newSpeedXCalc * totalVelocity;
                this.speedY = newSpeedYCalc * totalVelocity;
            }
        }
    }


    /**
     * Checks if there is collion with a wall.
     * @param wall
     * @return
     */
    public boolean collisionBallWall(Wall wall){

        double side1 = Math.sqrt(Math.pow(this.vector2.getX() - wall.getVector2().getX(),2) + Math.pow(this.vector2.getY() - wall.getVector2().getY(),2));

        double side2 = Math.sqrt(Math.pow(this.vector2.getX() - wall.getEndVector2().getX(),2) + Math.pow(this.vector2.getY() - wall.getEndVector2().getY(),2));

        double base = Math.sqrt(Math.pow(wall.getEndVector2().getX() - wall.getVector2().getX(),2) + Math.pow(wall.getEndVector2().getY() - wall.getVector2().getY(),2));

        if(this.radius + this.width/2 > side1 || this.radius + this.width/2 > side2)
            return true;

        double angle1 = Math.atan2( wall.getEndVector2().getX() - wall.getVector2().getX(), wall.getEndVector2().getY() - wall.getVector2().getY() ) - Math.atan2( this.vector2.getX() - wall.getVector2().getX(), this.vector2.getY() - wall.getVector2().getY() );

        double angle2 = Math.atan2( wall.getVector2().getX() - wall.getEndVector2().getX(), wall.getVector2().getY() - wall.getEndVector2().getY() ) - Math.atan2( this.vector2.getX() - wall.getEndVector2().getX(), this.vector2.getY() - wall.getEndVector2().getY() );

        if(angle1 > Math.PI / 2 || angle2 > Math.PI / 2)
            return false;

        double semiperimeter = (side1 + side2 + base) / 2;

        double areaOfTriangle = Math.sqrt( semiperimeter * (semiperimeter - side1) * (semiperimeter - side2) * (semiperimeter - base) );

        double height = 2*areaOfTriangle/base;

        return height < this.radius + this.width / 2;

    }

    /**
     * Checks if a ball is in the gravity field of a hole when the
     * GravityPocket powerup is active.
     */
    public void collisionPocketGravity() {
        double x = this.vector2.getX();
        double y = this.vector2.getY();

        for (Hole hole : game.getHoles()) {

            double distance = Math.sqrt(Utility.getDistanceNotSquared(x + this.radius + 150, y + this.radius + 150, hole.getVector2().getX(), hole.getVector2().getY()));
            if (distance > this.radius) { System.out.println("no collision"); } else {System.out.println("collision");}
            if (distance > this.radius) continue; // no collision

            // The ball is in a gravity field

            if (this.vector2.getX() + this.radius < hole.getVector2().getX() + hole.getRadiusHole()) {
                this.speedX += 0.01;
            } else {
                this.speedX -= 0.01;
            }

            if (this.vector2.getY() + this.radius < hole.getVector2().getY() + hole.getRadiusHole()) {
                this.speedY += 0.01;
            } else {
                this.speedY -= 0.01;
            }
        }
    }

    /**
     * Gets the angle at which the ball is moving.
     * @return
     */
    public double getAngleMovement() {
        if (checkMovement()) {
            return Math.toDegrees(Math.atan2(this.speedY, this.speedX));
        }
        else return 0.0;
    }

    public int getType() {
        return this.type;
    }


    private boolean checkMovement() {
        return this.speedX != 0 || this.speedY != 0;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + ", TYPE: " + this.type + ", XY: " + this.vector2.getX() + ", " + this.vector2.getY() + ", MOVING: " + this.moving + ", HAS_SHADOW: " + this.hasShadow;
    }

    @Override
    public void tick() {

        this.moving = checkMovement();
        checkCollisionWall();
        checkCollisionBall(game.getBalls());
        checkCollisionHole();

        if (game.getMadness()) {
            checkCollisionPlaceableWalls();
            if (game.isPocketGravity()) {
                collisionPocketGravity();
            }
        }

    }

    @Override
    public void draw(GameView gv) {
        float x = (float) this.vector2.getX();
        float y = (float) this.vector2.getY();
        if (bitmaps[this.id] == null)
            bitmaps[this.id] = gv.getBitmapFromResource(this.drawables[this.id]);
        gv.drawBitmap(bitmaps[this.id], x, y, (float) this.width, (float) this.height);

        if (ball_inner_shadow == null)
            ball_inner_shadow = gv.getBitmapFromResource(R.drawable.ball_inner_shadow);
        gv.drawBitmap(ball_inner_shadow, x, y, (float) width, (float) height);
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public double getMass() {
        return this.mass;
    }

    public void setHasShadow(boolean val) {
        this.hasShadow = val;
    }

    public boolean hasShadow() {
        return this.hasShadow;
    }

    // werkt niet, niet gebruiken
    public void setSpeedX(float xSpeed) {
        this.speedX = xSpeed;
    }

    public Vector2 getVector2() {
        return vector2;
    }

    public void setVector2(Vector2 vector2) {
        this.vector2 = vector2;
    }

    // werkt niet, niet gebruiken
    public void setSpeedY(float ySpeed) {
        this.speedY = ySpeed;
    }



    public double getRadius() {
        return this.radius;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public double getFriction() {
        return friction;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean getCollision() {
        return this.collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }


    public Bitmap getBitmap(int id) {

        return bitmaps[this.id];

    }

}

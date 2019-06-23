/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool.balls;

import android.graphics.Bitmap;
import android.util.Log;

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
    private int i = 0;

    private static Bitmap[] bitmaps;
    private static Bitmap ball_inner_shadow, ball_inner_shadow_madness;
    private int id, type;
    private boolean moving;
    private boolean collision = true;
    private int[] drawables;

    private double gravityPullsHad = 0;

    // the shadow
    private Shadow shadow;


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
        if(this.type != 0) {
            this.shadow = new Shadow(this, game);
            game.addEntity(shadow);
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

    public boolean stoppedLikingTheGravity(int maxPulls) {
        if(this.gravityPullsHad > maxPulls) return true;
        return false;
    }

    public void startLikingGravityBroAndInviteYourFriends() {
        // ok, sure, np
        for(Ball ball : game.getBalls()) {
            ball.gravityPullsHad = 0;
        }
    }

    public void removeBall() {
        this.game.removeEntity(this);
        if(shadow != null) this.game.removeEntity(shadow);
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
                removeBall();

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
        for (int i = 0; i < game.getWalls().size(); i++) {
            if (this.id == 15) {
                Wall wall = game.getWalls().get(i);
                Vector2 closestpoint = Utility.getClosestPoint(wall, this);
                if (Utility.getDistanceFromClosestPoint(closestpoint, this) - (this.radius) <= this.radius) {

                    //collision
                    double x1 = wall.getVector2().getX();
                    double y1 = wall.getVector2().getY();
                    double x2 = wall.getEndVector2().getX();
                    double y2 = wall.getEndVector2().getY();

                    if (closestpoint.getX() == x1 && closestpoint.getY() == y1) {
                        this.vector2.setX(x1 + this.radius);
                        this.vector2.setY(y1 + this.radius);
                        this.speedX = -this.speedX;
                        this.speedY = -this.speedY;
                        this.speedX *= this.energyloss;
                        this.speedY *= this.energyloss;
                    } else if (closestpoint.getX() == x2 && closestpoint.getY() == y2) {
                        this.vector2.setX(x2 + this.radius);
                        this.vector2.setY(y2 + this.radius);
                        this.speedX = -this.speedX;
                        this.speedY = -this.speedY;
                        this.speedX *= this.energyloss;
                        this.speedY *= this.energyloss;

                    }


                    Vector2 line = new Vector2();
                    Vector2 normal = new Vector2();

                    line.setX(x2 - x1);
                    line.setY(y2 - y1);
                    normal.setX(-line.getY());
                    normal.setY(line.getX());


                    double lenthofnormal = Math.sqrt((normal.getX() * normal.getX()) + (normal.getY() * normal.getY()));
                    normal.setX(normal.getX() / lenthofnormal);
                    normal.setY(normal.getY() / lenthofnormal);

                    double distanceAlongNormal = speedX * normal.getX() + speedY * normal.getY();
                    this.speedX -= 2.0 * distanceAlongNormal * normal.getX();
                    this.speedX *= this.energyloss;
                    this.speedY -= 2.0 * distanceAlongNormal * normal.getY();
                    this.speedY *= this.energyloss;


                }
            }
        }
    }


    /**
     * Gets the angle at which the ball is moving.
     *
     * @return
     */
    public double getAngleMovement() {
        if (checkMovement()) {
            return Math.toDegrees(Math.atan2(this.speedY, this.speedX));
        } else return 0.0;
    }

    public int getType() {
        return this.type;
    }


    private boolean checkMovement() {
        return this.speedX != 0 || this.speedY != 0;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + ", TYPE: " + this.type + ", XY: " + this.vector2.getX() + ", " + this.vector2.getY() + ", MOVING: " + this.moving;
    }

    @Override
    public void tick() {
        double x = this.vector2.getX();
        double y = this.vector2.getY();
        this.vector2.set(x += this.speedX, y += this.speedY);


        this.moving = checkMovement();
        checkCollisionWall();
        checkCollisionBall(game.getBalls());
        checkCollisionHole();
        if (game.getMadness()) {
            checkCollisionPlaceableWalls();
        }

    }

    private float lastAngle = (float)(Math.random() * 360);

    private float getNewRandomAngle() {
        if (moving) {
            float angleSpeed = ((float) (((Math.abs(this.speedX) + Math.abs(this.speedY)) * 50)) % 360);
            this.lastAngle += ((this.speedX < 0) ? -angleSpeed : angleSpeed);
            return this.lastAngle;
        } else return this.lastAngle;
    }

    @Override
    public void draw(GameView gv) {
        float x = (float) this.vector2.getX();
        float y = (float) this.vector2.getY();
        if (bitmaps[this.id] == null)
            bitmaps[this.id] = gv.getBitmapFromResource(this.drawables[this.id]);
        gv.drawBitmap(bitmaps[this.id], x, y, (float) this.width, (float) this.height, getNewRandomAngle());

        if (ball_inner_shadow == null)
            ball_inner_shadow = gv.getBitmapFromResource(R.drawable.ball_inner_shadow);
        if(ball_inner_shadow_madness == null)
            ball_inner_shadow_madness = gv.getBitmapFromResource(R.drawable.ball_inner_shadow_madness);

        if(Game.gameMode == Game.GameMode.MADNESS)
            gv.drawBitmap(ball_inner_shadow_madness, (float)(x / 1.0005), (float)(y / 1.0005), (float) (width * 1.03), (float) (height * 1.03));
        else
            gv.drawBitmap(ball_inner_shadow, (float)(x / 1.0005), (float)(y / 1.0005), (float) (width * 1.03), (float) (height * 1.03));
    }

    @Override
    public int getLayer() {
        return 7;
    }

    public double getMass() {
        return this.mass;
    }

    // werkt niet, niet gebruiken
    public void setSpeedX(float xSpeed) {
        this.speedX = xSpeed;
    }

    public Vector2 getVector2() {
        return vector2;
    }

    public void applyForce(Vector2 vector2) {
        this.speedX += vector2.getX();
        this.speedY += vector2.getY();
        this.gravityPullsHad++;
        Log.e("gravityPullsHad for", this.id + ": " + this.gravityPullsHad);
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

    public void setMoving(boolean moving) {
        this.moving = moving;
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

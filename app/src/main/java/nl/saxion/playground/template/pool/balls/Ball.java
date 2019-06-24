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

/**
 * The type Ball.
 * This is the ball on the screen. Everything the ball can collide with is written here.
 * Also the graphics are defined here.
 */
public class Ball extends Entity {

    /**
     * The constant lastisertedid.
     * This is the auto increment for the ID
     */
    public static int lastisertedid = 0;


    /**
     * All the required data for the collisions
     */
    private double speedX, speedY, mass, width, height, radius, bx, by, friction, energyloss;
    private boolean collision = true;
    /**
     * The Vector 2.
     */
    protected Vector2 vector2;
    private float lastAngle = (float) (Math.random() * 360);

    /**
     * The Game.
     */
    protected Game game;
    private static Bitmap[] bitmaps;
    private static Bitmap ball_inner_shadow, ball_inner_shadow_madness, crucial_bitmap;
    private int id, type;
    private boolean moving;
    boolean visible = true;
    private int[] drawables;
    private double gravityPullsHad = 0;
    private Shadow shadow;
    private int gravityTimer;
    private boolean currentTurnSet;
    private int currentTurn;

    /**
     * Instantiates a new Ball.
     *
     * @param game      the game
     * @param drawables the drawables
     * @param x         the x
     * @param y         the y
     * @param width     the width
     * @param height    the height
     * @param type      the type
     */
    public Ball(Game game, int[] drawables, double x, double y, double width, double height, int type) {
        this.id = lastisertedid;
        lastisertedid++;
        this.game = game;
        this.width = width;
        this.height = height;
        this.radius = width / 2;
        this.speedY = 0;
        this.speedX = 0;
        this.bx = game.getWidth();
        this.by = game.getPlayHeight();
        this.mass = 10;
        this.friction = .9965;
        this.energyloss = .900;
        this.type = type;
        this.vector2 = new Vector2(x, y);
        this.drawables = drawables;
        this.gravityTimer = 0;
        this.currentTurnSet = false;

        if (bitmaps == null) {
            bitmaps = new Bitmap[16];
        }
        if (this.type != 0) {
            this.shadow = new Shadow(this, game);
            game.addEntity(shadow);
        }
    }

    /**
     * Check if ball has collision with other balls.
     * If so resolve them
     */
    private void checkCollisionBall(ArrayList<Ball> balls) {

        if (this.collision) {
            //Check for all balls after this ball in the array.
            //Like id = 5 check in array for: 6 7 8 9 10 11 12 13 14 15
            for (int i = this.id + 1; i < balls.size(); i++) {
                Ball ball = balls.get(i);
                double ball1x = this.vector2.getX();
                double ball1y = this.vector2.getY();
                double ball2x = ball.vector2.getX();
                double ball2y = ball.vector2.getY();

                double distSqr = Utility.getDistanceNotSquared(ball1x, ball1y, ball2x, ball2y);
                double xd = ball1x - ball2x;
                double yd = ball1y - ball2y;

                //if the balls are colliding then resolve the collision
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
    }

    /**
     * Check for collision with outer walls and resolve them.
     */
    private void checkCollisionWall() {
        double x = this.vector2.getX();
        double y = this.vector2.getY();
        this.vector2.set(x += this.speedX, y += this.speedY);

        /**
         * Walls right and left of the screen.
         */
        if (x - this.radius <= game.getWidth() * 0.056) {
            this.vector2.setX(game.getWidth() * 0.056 + this.radius);
            this.speedX = -this.speedX;
        } else if (x + this.radius >= game.getWidth() * 0.915) {
            this.speedX = -this.speedX;
            this.vector2.setX(game.getWidth() * 0.915 - this.radius);
            this.speedX *= this.energyloss;
        } else {
            this.vector2.setX(x += this.speedX);
            this.speedX *= this.friction;
            if (Math.abs(this.speedX) < .1 && Math.abs(this.speedY) < .1) {
                this.speedX = 0;
            }
        }

        /**
         * Walls above and below the screen.
         */
        if (y - this.radius <= game.getPlayHeight() * 0.07) {
            this.speedY = -this.speedY;
            this.vector2.setY(game.getPlayHeight() * 0.07 + this.radius);
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

    /**
     * Stop the gravity boolean.
     *
     * @param maxPulls the max pulls
     * @return the boolean
     */
    public boolean stopGravitiy(int maxPulls) {
        return this.gravityPullsHad > maxPulls;
    }

    public void resetShadow() {
        this.shadow = new Shadow(this, game);
        game.addEntity(shadow)
        ;
    }

    /**
     * Start gravity so the balls get affected
     */
    public void startGravitiy() {
        for (Ball ball : game.getBalls()) {
            ball.gravityPullsHad = 0;
        }
    }

    /**
     * Remove ball.
     */
    public void removeBall() {
        this.game.removeEntity(this);
        this.game.removeEntity(shadow);
    }

    /**
     * Sets the cue ball invisible when scored.
     */
    public void setCueBallInvisible() {
        this.visible = false;
        this.collision = false;
    }

    /**
     * Check if the balls has a collision with the hole.
     * If so remove the ball and add it to the correct array of balls for the player
     */
    private void checkCollisionHole() {
        double x = this.vector2.getX();
        double y = this.vector2.getY();

        for (Hole hole : game.getHoles()) {

            double distance = Math.sqrt(Utility.getDistanceNotSquared(x + this.radius, y + this.radius, hole.getVector2().getX(), hole.getVector2().getY()));
            if (distance > this.radius) continue; // no collision

            // The ball hit the pocket!
            if (this.type == 3) {
                //is 8 ball
                this.moving = false;
                removeBall();
                if (game.getCurrentplayer().getScoredballs().size() < 7) {
                    game.winnerScreen(game.getInactiveplayer().getPlayerId());
                } else {
                    game.winnerScreen(game.getCurrentplayer().getPlayerId());
                }
            } else if (this.type == 0) {
                //is cue ball
                game.placeCueBall();
                setCueBallInvisible();

            } else {
                // A regular ball
                Player player = game.getCurrentplayer();
                if (player.getBalltype() == -1) {
                    // The first pocket ball
                    player.setBalltype(this.type);
                    game.getInactiveplayer().setBalltype(((this.type == 1) ? 2 : 1));
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
     * Sends the ball in the right direction after hitting the placeable wall.
     */
    private void checkCollisionPlaceableWalls() {
        for (int i = 0; i < game.getWalls().size(); i++) {
            Wall wall = game.getWalls().get(i);
            Vector2 closestpoint = Utility.getClosestPoint(wall, this);

            //collision
            if (Utility.getDistanceFromClosestPoint(closestpoint, this) - (this.radius + 10) <= this.radius) {
                double x1 = wall.getVector2().getX();
                double y1 = wall.getVector2().getY();
                double x2 = wall.getEndVector2().getX();
                double y2 = wall.getEndVector2().getY();

                if (closestpoint.getX() == x1 && closestpoint.getY() == y1) {
                    this.speedX -= 2.0 * this.speedX;
                    this.speedY -= 2.0 * this.speedY;
                } else if (closestpoint.getX() == x2 && closestpoint.getY() == y2) {
                    this.speedX -= 2.0 * this.speedX;
                    this.speedY -= 2.0 * this.speedY;
                } else {
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
                    this.speedY -= 2.0 * distanceAlongNormal * normal.getY();

                }
            }
        }
    }


    /**
     * Checks if a ball is in the gravity field of a hole when the
     * GravityPocket powerup is active.
     */
    public void collisionPocketGravity() {
        double x = this.vector2.getX();
        double y = this.vector2.getY();

        if (!this.currentTurnSet) {
            this.currentTurn = game.getTurns();
        }

        if (this.currentTurn < game.getTurns() && this.currentTurnSet) {
            this.gravityTimer = 0;
            this.currentTurnSet = false;
        }

        for (Hole hole : game.getHoles()) {

            double distance = Math.sqrt(Utility.getDistanceNotSquared(x + this.radius + 40, y + this.radius + 40, hole.getVector2().getX(), hole.getVector2().getY()));
            if (distance > this.radius + 40) continue; // no collision

            // The ball is in a gravity field

            if (this.gravityTimer < 1000) {
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
                this.gravityTimer++;
            }
        }
    }

    /**
     * Gets the angle at which the ball is moving.
     *
     * @return angle movement
     */
    public double getAngleMovement() {
        if (checkMovement()) {
            return Math.toDegrees(Math.atan2(this.speedY, this.speedX));
        } else return 0.0;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() {
        return this.type;
    }


    private boolean checkMovement() {
        return this.speedX != 0 || this.speedY != 0;
    }

    @Override
    public String toString() {
        return
                "ID: " + this.id
                + ", TYPE: " + this.type
                + ", XY: " + this.vector2.getX() + ", "
                + this.vector2.getY()
                + ", MOVING: " + this.moving
                + ", XYSPEEDS: " + this.speedX + ", "
                + this.speedY
                + ", collision: "
                + this.collision;
    }

    @Override
    public void tick() {
        double x = this.vector2.getX();
        double y = this.vector2.getY();
        this.vector2.add(this.speedX, this.speedY);

        this.moving = checkMovement();
        checkCollisionWall();
        checkCollisionBall(game.getBalls());
        checkCollisionHole();
        if (game.getMadness()) {
            checkCollisionPlaceableWalls();
            if (game.isPocketGravity() && !game.getPlacingWall()) {
                collisionPocketGravity();
            }
        }

    }

    private float getNewRandomAngle() {
        if (moving) {
            float angleSpeed = ((float) (((Math.abs(this.speedX) + Math.abs(this.speedY)) * 50)) % 360);
            this.lastAngle += ((this.speedX < 0) ? -angleSpeed : angleSpeed);
            return this.lastAngle;
        } else return this.lastAngle;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void draw(GameView gv) {
        float x = (float) this.vector2.getX();
        float y = (float) this.vector2.getY();

        if(game.crucialCode() && crucial_bitmap == null) {
            crucial_bitmap = gv.getBitmapFromResource(R.drawable.ee);
        }
        if(!game.getMadness()) {
            crucial_bitmap = null;
        }

        if (bitmaps[this.id] == null)
            bitmaps[this.id] = gv.getBitmapFromResource(this.drawables[this.id]);
        gv.drawBitmap(((crucial_bitmap == null || !game.crucialCode() || (this instanceof WhiteBall)) ? bitmaps[this.id] : crucial_bitmap), x, y, (float) this.width, (float) this.height, (game.getMadness()) ? getNewRandomAngle() : 0);

        if (ball_inner_shadow == null)
            ball_inner_shadow = gv.getBitmapFromResource(R.drawable.ball_inner_shadow);
        if (ball_inner_shadow_madness == null)
            ball_inner_shadow_madness = gv.getBitmapFromResource(R.drawable.ball_inner_shadow_madness);

        if (game.getMadness() && (!game.crucialCode() || (this instanceof WhiteBall)))
            gv.drawBitmap(ball_inner_shadow_madness, (float) (x / 1.0005), (float) (y / 1.0005), (float) (width * 1.03), (float) (height * 1.03));
        else
            gv.drawBitmap(ball_inner_shadow, (float) (x / 1.0005), (float) (y / 1.0005), (float) (width * 1.03), (float) (height * 1.03));
    }

    @Override
    public int getLayer() {
        return 7;
    }

    /**
     * Gets mass.
     *
     * @return the mass
     */
    public double getMass() {
        return this.mass;
    }

    /**
     * Gets vector 2.
     *
     * @return the vector 2
     */
    public Vector2 getVector2() {
        return vector2;
    }

    /**
     * Sets vector 2.
     *
     * @param vector2 the vector 2
     */
    public void setVector2(Vector2 vector2) {
        this.vector2 = vector2;
    }

    /**
     * Apply force.
     *
     * @param vector2 the vector 2
     */
    public void applyForce(Vector2 vector2) {
        this.speedX += vector2.getX();
        this.speedY += vector2.getY();
        this.gravityPullsHad++;
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public double getRadius() {
        return this.radius;
    }

    /**
     * Gets friction.
     *
     * @return the friction
     */
    public double getFriction() {
        return friction;
    }

    /**
     * Sets friction.
     *
     * @param friction the friction
     */
    public void setFriction(double friction) {
        this.friction = friction;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets speed x.
     *
     * @return the speed x
     */
    public double getSpeedX() {
        return speedX;
    }

    /**
     * Sets speed x.
     *
     * @param xSpeed the x speed
     */
    public void setSpeedX(double xSpeed) {
        this.speedX = xSpeed;
    }

    /**
     * Gets speed y.
     *
     * @return the speed y
     */
    public double getSpeedY() {
        return speedY;
    }

    /**
     * Sets speed y.
     *
     * @param ySpeed the y speed
     */
    public void setSpeedY(double ySpeed) {
        this.speedY = ySpeed;
    }

    /**
     * Is moving boolean.
     *
     * @return the boolean
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * Sets moving.
     *
     * @param moving the moving
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    /**
     * Gets collision.
     *
     * @return the collision
     */
    public boolean getCollision() {
        return this.collision;
    }

    /**
     * Sets collision.
     *
     * @param collision the collision
     */
    public void setCollision(boolean collision) {
        this.collision = collision;
    }


    /**
     * Gets bitmap.
     *
     * @param id the id
     * @return the bitmap
     */
    public Bitmap getBitmap(int id) {

        return bitmaps[this.id];

    }

}

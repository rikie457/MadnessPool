/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool.balls;

import android.graphics.Bitmap;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Hole;
import nl.saxion.playground.template.pool.Player;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.Vector2;


/**
 * TEACHER: Overall documenting your code the way it is done in this class is actually detrimental
 * to the readability of your code. You are constantly documenting the obvious:
 * //here I created an integer i
 * int i;
 * <p>
 * What IS interesting is WHY you create or need a variable. Check your code you do it the wrong
 * way constantly:
 * //the bitmaps
 * ArrayList<Bitmap> bitmaps;
 * //the constant insert id
 * int lastinsert id
 * <p>
 * This is worse than having no documentation at all.
 * I wouldn't mind being able to see who wrote this class without going into git.
 * Class header documentating missing
 */

public class Ball extends Entity {
    public static int lastisertedid = 0;
    static protected Bitmap[] bitmaps;
    protected double speedX,
            speedY;
    protected double mass,
            width,
            height,
            radius,
            bx,
            by,
            friction,
            energyloss;
    protected int color,
            id,
            type;

    /**
     * TEACHER: Having separate lists for balls, holes, players sounds like you are treating them
     * all differently, which could be the case, we don't know without actual documentation, but
     * for the collision loop I could imagine balls and holes are all just CollidableEntities.
     */

    protected Game game;
    protected boolean moving;
    protected boolean collision = true;
    protected Vector2 vector2;
    private int[] drawables;


    public Ball(Game game, int[] drawables, double x, double y, double width, double height, int type) {

        //TEACHER: having references to every other object is code smell. Why does a ball need all
        //this information? Might be better to have another entity do the collision loop instead
        //of giving a ball access to a list of all other balls. In addition, game already gives you
        //that info.

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
        this.drawables = drawables;
        if (bitmaps == null) {
            bitmaps = new Bitmap[16];
        }
    }


    private void checkCollisionBall(ArrayList<Ball> balls) {

        //TEACHER: another downside to doing the checks this way is that you are doing twice the
        //amounts of check that are needed. Given ball1,ball2 you are checking ball1-ball2 from
        //ball1 and ball2-ball1 from ball1, where one check is enough.

        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            double ball1x = this.vector2.getX();
            double ball1y = this.vector2.getY();
            double ball2x = ball.vector2.getX();
            double ball2y = ball.vector2.getY();

            double distSqr = Utility.getDistanceNotSquared(ball1x, ball1y, ball2x, ball2y);
            double xd = ball1x - ball2x;
            double yd = ball1y - ball2y;

            //TEACHER: using the correct collision loop would prevent having checks like this
            if (this == ball) {
                continue;
            }

            //TEACHER: consider self documenting your code eg
            //bool isColliding = etc
            if (distSqr <= (this.getRadius() + ball.radius) * (this.getRadius() + ball.radius) && this.collision) {

                //TEACHER: move things like the code below into separate smaller methods
                //the end result is something like
                //if (collision (ball1, ball2)) resolveCollision (ball1, ball2);
                //which is much more readable
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

    private void checkCollisionHole() {
        double x = this.vector2.getX();
        double y = this.vector2.getY();
        //TEACHER: Tycho... a couple of things that are wrong with this method:
        //No documentation
        //Ifception: Your nesting goes 7 (SEVEN!!) levels deep!!
        for (int i = 0; i < game.getHoles().size(); i++) {
            Hole hole = game.getHoles().get(i);
            if (Math.sqrt(Utility.getDistanceNotSquared(x + this.radius, y + this.radius, hole.getVector2().getX(), hole.getVector2().getY())) - (30) <= 0) {
                if (this.type != 3 && this.type != 0) {
                    for (int j = 0; j < game.getPlayers().size(); j++) {
                        Player player = game.getPlayers().get(j);
                        if (game.getCurrentplayer() == player) {
                            if (player.getBalltype() == -1) {
                                if (this.type == 1) {
                                    player.setBalltype(1);
                                    game.getInactiveplayer().setBalltype(2);
                                } else if (this.type == 2) {
                                    player.setBalltype(2);
                                    game.getInactiveplayer().setBalltype(1);
                                }
                                player.getScoredballs().add(this);
                            } else {
                                if (this.type == player.getBalltype()) {
                                    player.getScoredballs().add(this);
                                } else {
                                    game.getInactiveplayer().getScoredballs().add(this);
                                }
                            }
                        }
                    }
                } else if (this.type == 3) {
                    //is 8 ball
                    if (game.getCurrentplayer().getScoredballs().size() < 7) {
                        game.winnerScreen(game.getInactiveplayer().getPlayerId());
                    } else {
                        game.winnerScreen(game.getCurrentplayer().getPlayerId());
                    }
                } else if (this.type == 0) {
                    //is cue ball
                    game.placeCueBall();
                }
                this.moving = false;
                this.game.removeEntity(this);
            }
        }
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
        this.moving = checkMovement();
        checkCollisionWall();
        checkCollisionBall(game.getBalls());
        checkCollisionHole();

    }

    @Override
    public void draw(GameView gv) {
        float x = (float) this.vector2.getX();
        float y = (float) this.vector2.getY();
        Bitmap toDraw = null;
        if (bitmaps[this.id] == null)
            bitmaps[this.id] = gv.getBitmapFromResource(this.drawables[this.id]);
        toDraw = bitmaps[this.id];
        // draw the ball texture, which is 300x300 pixels, to account for the ball's shadow
        gv.drawBitmap(toDraw, x - 8 * (1000 / game.getWidth()), y - 8 * (1000 / game.getWidth()), (float) (this.width * 1.5), (float) (this.height * 1.5));
    }

    @Override
    public int getLayer() {
        return 1;
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

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getId() {
        return id;
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

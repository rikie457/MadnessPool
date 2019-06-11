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
import nl.saxion.playground.template.pool.Coord;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Hole;
import nl.saxion.playground.template.pool.Player;
import nl.saxion.playground.template.pool.Utility;


/**
 * The type Ball.
 */
public class Ball extends Entity {
    /**
     * temporary variables because i'm lazy and don't want to update every one of the 16 cases in the draw switch statement TYCHO!
     */
    private double xBak, yBak, widthBak, heightBak;

    /**
     * The Bitmaps.
     */
    static public ArrayList<Bitmap> bitmaps = new ArrayList<>();
    /**
     * The constant lastisertedid.
     */
    protected static int lastisertedid = 1;
    /**
     * The Bitmap 1.
     */
    static protected Bitmap bitmap1, /**
     * The Bitmap 2.
     */
    bitmap2, /**
     * The Bitmap 3.
     */
    bitmap3, /**
     * The Bitmap 4.
     */
    bitmap4, /**
     * The Bitmap 5.
     */
    bitmap5, /**
     * The Bitmap 6.
     */
    bitmap6, /**
     * The Bitmap 7.
     */
    bitmap7, /**
     * The Bitmap 8.
     */
    bitmap8, /**
     * The Bitmap 9.
     */
    bitmap9, /**
     * The Bitmap 10.
     */
    bitmap10, /**
     * The Bitmap 11.
     */
    bitmap11, /**
     * The Bitmap 12.
     */
    bitmap12, /**
     * The Bitmap 13.
     */
    bitmap13, /**
     * The Bitmap 14.
     */
    bitmap14, /**
     * The Bitmap 15.
     */
    bitmap15, /**
     * The Bitmap 16.
     */
    bitmap16;
    /**
     * The Speed x.
     */
    protected double speedX, /**
     * The Speed y.
     */
    speedY;
    /**
     * The Mass.
     */
    protected double mass, /**
     * The X.
     */
    x, /**
     * The Y.
     */
    y, /**
     * The Width.
     */
    width, /**
     * The Height.
     */
    height, /**
     * The Radius.
     */
    radius, /**
     * The Bx.
     */
    bx, /**
     * The By.
     */
    by, /**
     * The Friction.
     */
    friction, /**
     * The Energyloss.
     */
    energyloss;
    /**
     * The Color.
     */
    protected int color, /**
     * The Id.
     */
    id, /**
     * The Type.
     */
    type;
    /**
     * The Balls.
     */
    protected ArrayList<Ball> balls;
    /**
     * The Holes.
     */
    protected ArrayList<Hole> holes;
    /**
     * The Players.
     */
    protected ArrayList<Player> players;
    /**
     * The Game.
     */
    protected Game game;
    /**
     * The Moving.
     */
    protected boolean moving;
    /**
     * The Shot.
     */
    protected boolean shot;

    /**
     * The Collision.
     */
    protected boolean collision = true;
    /**
     * The Old x.
     */
    protected double oldX, /**
     * The Old y.
     */
    oldY, /**
     * The New x.
     */
    newX, /**
     * The New y.
     */
    newY;


    /**
     * Instantiates a new Ball.
     *
     * @param game    the game
     * @param balls   the balls
     * @param holes   the holes
     * @param players the players
     * @param x       the x
     * @param y       the y
     * @param width   the width
     * @param height  the height
     * @param type    the type
     */
    public Ball(Game game, ArrayList<Ball> balls, ArrayList<Hole> holes, ArrayList<Player> players, double x, double y, double width, double height, int type) {
        this.id = lastisertedid;
        lastisertedid++;
        this.game = game;
        this.balls = balls;
        this.holes = holes;
        this.players = players;
        this.x = x;
        this.y = y;
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
    }


    private void checkCollisionBall(ArrayList<Ball> balls) {

        for (int i = 0; i < balls.size(); i++) {
            double distSqr = Utility.getDistanceNotSquared(this.getX(), this.getY(), balls.get(i).getX(), balls.get(i).getY());
            double xd = this.getX() - balls.get(i).getX();
            double yd = this.getY() - balls.get(i).getY();

            if (this == balls.get(i)) {
                continue;
            }

            if (distSqr <= (this.getRadius() + balls.get(i).getRadius()) * (this.getRadius() + balls.get(i).getRadius()) && this.collision) {
                if (this.speedX == 0 && this.speedY == 0 && balls.get(i).getSpeedX() == 0 && balls.get(i).getSpeedY() == 0) {
                    this.speedY = .5;
                    this.speedX = -.5;
                }
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
        this.x += this.speedX;
        this.y += this.speedY;

        /**
         * muren rechts en links
         */
        if (this.x - this.radius <= game.getPlayWidth() * 0.056) {
            this.x = game.getPlayWidth() * 0.056 + this.radius;
            this.speedX = -this.speedX;
        } else if (this.x + this.radius >= game.getPlayWidth() * 0.93) {
            this.speedX = -this.speedX;
            this.x = game.getPlayWidth() * 0.93 - this.radius;
            this.speedX *= this.energyloss;
        } else {
            this.x += this.speedX;
            this.speedX *= this.friction;
            if (Math.abs(this.speedX) < .1 && Math.abs(this.speedY) < .1) {
                this.speedX = 0;
            }
        }
        /**
         * muuren boven en onder
         */
        if (this.y - this.radius <= game.getPlayWidth() * 0.04) {
            this.speedY = -this.speedY;
            this.y = game.getPlayWidth() * 0.04 + this.radius;
        } else if (this.y + this.radius > game.getPlayHeight() * 0.85) {
            this.speedY = -this.speedY;
            this.y = game.getPlayHeight() * 0.85 - this.radius;
            this.speedY *= this.energyloss;
        } else {
            this.y += this.speedY;
            this.speedY *= this.friction;
            if (Math.abs(this.speedY) < .1 && Math.abs(this.speedX) < .1) {
                this.speedY = 0;
            }
        }
    }

    private void checkCollisionHole() {
        for (int i = 0; i < this.holes.size(); i++) {
            if (Math.sqrt(Utility.getDistanceNotSquared(this.getX() + this.radius, this.getY() + this.radius, this.holes.get(i).getX(), this.holes.get(i).getY())) - (30) <= 0) {
                if (this.id != 8 && this.id != 16) {
                    for (int j = 0; j < this.players.size(); j++) {
                        Player player = this.players.get(j);
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
                } else if (this.id == 8) {
                    //is 8 ball
                    if (game.getCurrentplayer().getScoredballs().size() < 7) {
                        game.winnerScreen(game.getInactiveplayer().getPlayerId());
                    } else {
                        game.winnerScreen(game.getCurrentplayer().getPlayerId());
                    }
                } else if (this.id == 16) {
                    //is cue ball
                    game.placeCueBall();
                }

                this.game.removeEntity(this);
                if (game.getMovingBalls().contains(this)) {
                    game.getMovingBalls().remove(this);
                }
                if (this.id != 16) {
                    this.balls.remove(this);
                }
            }
        }
    }

    /**
     * Sets coord.
     *
     * @param coord the coord
     */
    public void setCoord(Coord coord) {
        this.x = coord.getX();
        this.y = coord.getY();
    }

    public int getType() {
        return this.type;
    }

    /**
     * Add coord.
     *
     * @param x the x
     * @param y the y
     */
    public void addCoord(float x, float y) {
        this.x += x;
        this.y += y;
    }

    private boolean checkMovement() {
        if (this.speedX == 0 && this.speedY == 0) {
            if (game.getMovingBalls().contains(this)) {
                game.getMovingBalls().remove(this);
            }
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + ", TYPE: " + this.type + ", XY: " + this.x + ", " + this.y + ", MOVING: " + this.moving;
    }

    @Override
    public void tick() {
        this.moving = checkMovement();
        checkCollisionWall();
        checkCollisionBall(this.balls);
        checkCollisionHole();
        if (this.getId() == 16) {
            if (this.isShot()) {
                game.roundChecker();
            }
        }
    }

    @Override
    public void draw(GameView gv) {
        Bitmap toDraw = null;
        switch (this.id) {
            case 1:
                if (bitmap1 == null) {
                    bitmap1 = gv.getBitmapFromResource(R.drawable.ball1);
                    bitmaps.add(bitmap1);
                }
                toDraw = bitmap1;
                //gv.drawBitmap(bitmap1, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 2:
                if (bitmap2 == null) {
                    bitmap2 = gv.getBitmapFromResource(R.drawable.ball2);
                    bitmaps.add(bitmap2);
                }
                toDraw = bitmap2;
                //gv.drawBitmap(bitmap2, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 3:
                if (bitmap3 == null) {
                    bitmap3 = gv.getBitmapFromResource(R.drawable.ball3);
                    bitmaps.add(bitmap3);
                }
                toDraw = bitmap3;
                //gv.drawBitmap(bitmap3, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 4:
                if (bitmap4 == null) {
                    bitmap4 = gv.getBitmapFromResource(R.drawable.ball4);
                    bitmaps.add(bitmap4);
                }
                toDraw = bitmap4;
                //gv.drawBitmap(bitmap4, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 5:
                if (bitmap5 == null) {
                    bitmap5 = gv.getBitmapFromResource(R.drawable.ball5);
                    bitmaps.add(bitmap5);
                }
                toDraw = bitmap5;
                //gv.drawBitmap(bitmap5, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 6:
                if (bitmap6 == null) {
                    bitmap6 = gv.getBitmapFromResource(R.drawable.ball6);
                    bitmaps.add(bitmap6);
                }
                toDraw = bitmap6;
                //gv.drawBitmap(bitmap6, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 7:
                if (bitmap7 == null) {
                    bitmap7 = gv.getBitmapFromResource(R.drawable.ball7);
                    bitmaps.add(bitmap7);
                }
                toDraw = bitmap7;
                //gv.drawBitmap(bitmap7, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 8:
                if (bitmap8 == null) {
                    bitmap8 = gv.getBitmapFromResource(R.drawable.ball8);
                    bitmaps.add(bitmap8);
                }
                toDraw = bitmap8;
                //gv.drawBitmap(bitmap8, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 9:
                if (bitmap9 == null) {
                    bitmap9 = gv.getBitmapFromResource(R.drawable.ball9);
                    bitmaps.add(bitmap9);
                }
                toDraw = bitmap9;
                //gv.drawBitmap(bitmap9, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 10:
                if (bitmap10 == null) {
                    bitmap10 = gv.getBitmapFromResource(R.drawable.ball10);
                    bitmaps.add(bitmap10);
                }
                toDraw = bitmap10;
                //gv.drawBitmap(bitmap10, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 11:
                if (bitmap11 == null) {
                    bitmap11 = gv.getBitmapFromResource(R.drawable.ball11);
                    bitmaps.add(bitmap11);
                }
                toDraw = bitmap11;
                //gv.drawBitmap(bitmap11, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 12:
                if (bitmap12 == null) {
                    bitmap12 = gv.getBitmapFromResource(R.drawable.ball12);
                    bitmaps.add(bitmap12);
                }
                toDraw = bitmap12;
                //gv.drawBitmap(bitmap12, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 13:
                if (bitmap13 == null) {
                    bitmap13 = gv.getBitmapFromResource(R.drawable.ball13);
                    bitmaps.add(bitmap13);
                }
                toDraw = bitmap13;
                //gv.drawBitmap(bitmap13, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 14:
                if (bitmap14 == null) {
                    bitmap14 = gv.getBitmapFromResource(R.drawable.ball14);
                    bitmaps.add(bitmap14);
                }
                toDraw = bitmap14;
                //gv.drawBitmap(bitmap14, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 15:
                if (bitmap15 == null) {
                    bitmap15 = gv.getBitmapFromResource(R.drawable.ball15);
                    bitmaps.add(bitmap15);
                }
                toDraw = bitmap15;
                //gv.drawBitmap(bitmap15, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
            case 16:
                if (bitmap16 == null) {
                    bitmap16 = gv.getBitmapFromResource(R.drawable.ball16);
                    bitmaps.add(bitmap16);
                }
                toDraw = bitmap16;
                //gv.drawBitmap(bitmap16, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
                break;
        }

        // draw the ball texture, which is 300x300 pixels, to account for the ball's shadow
        gv.drawBitmap(toDraw, (float) this.x - 8 * (1000 / game.getWidth()), (float) this.y - 8 * (1000 / game.getWidth()), (float) (this.width * 1.5), (float) (this.height * 1.5));
    }

    @Override
    public int getLayer() {
        return 1;
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
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return this.y;
    }


    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets speed x.
     *
     * @return the speed x
     */
    public double getSpeedX() {
        return this.speedX;
    }

    /**
     * Sets speed x.
     *
     * @param speedX the speed x
     */
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    /**
     * Sets speed x.
     *
     * @param xSpeed the x speed
     */
// werkt niet, niet gebruiken
    public void setSpeedX(float xSpeed) {
        this.speedX = xSpeed;
    }

    /**
     * Gets balls.
     *
     * @return the balls
     */
    public ArrayList<Ball> getBalls() {
        return balls;
    }

    /**
     * Gets holes.
     *
     * @return the holes
     */
    public ArrayList<Hole> getHoles() {
        return holes;
    }

    /**
     * Gets speed y.
     *
     * @return the speed y
     */
    public double getSpeedY() {
        return this.speedY;
    }

    /**
     * Sets speed y.
     *
     * @param ySpeed the y speed
     */
// werkt niet, niet gebruiken
    public void setSpeedY(float ySpeed) {
        this.speedY = ySpeed;
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
     * Is shot boolean.
     *
     * @return the boolean
     */
    public boolean isShot() {
        return shot;
    }

    /**
     * Sets shot.
     *
     * @param shot the shot
     */
    public void setShot(boolean shot) {
        this.shot = shot;
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
     * Reset lastisertedid.
     */
    public void resetLastisertedid() {
        lastisertedid = 1;
    }

    /**
     * Gets bitmap.
     *
     * @return the bitmap
     */
    public Bitmap getBitmap() {
        switch (this.id) {
            case 1:
                return bitmap1;

            case 2:
                return bitmap2;

            case 3:
                return bitmap3;

            case 4:
                return bitmap4;

            case 5:
                return bitmap5;

            case 6:
                return bitmap6;

            case 7:
                return bitmap7;

            case 8:
                return bitmap8;

            case 9:
                return bitmap9;

            case 10:
                return bitmap10;

            case 11:
                return bitmap11;

            case 12:
                return bitmap12;

            case 13:
                return bitmap13;

            case 14:
                return bitmap14;

            case 15:
                return bitmap15;
            case 16:
                return bitmap16;


        }
        return null;
    }

}

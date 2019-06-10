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
     * The constant lastisertedid.
     */
    protected static int lastisertedid = 1;
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
     * The Image.
     */
    image, /**
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


    protected ArrayList<Player> players;
    /**
     * The Game.
     */
    protected Game game;
    /**
     * The Bitmap.
     */
    protected Bitmap bitmap;
    /**
     * The Moving.
     */
    protected boolean moving;
    /**
     * The Shot.
     */
    protected boolean shot;

    protected boolean collision = true;
    protected double oldX, oldY, newX, newY;


    /**
     * Instantiates a new Ball.
     *
     * @param game   the game
     * @param balls  the balls
     * @param holes  the holes
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     * @param image  the image
     * @param type   the type
     */
    public Ball(Game game, ArrayList<Ball> balls, ArrayList<Hole> holes, ArrayList<Player> players, double x, double y, double width, double height, int image, int type) {
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
        this.image = image;
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
        if (this.x - this.radius <= 60) {
            this.x = 60 + this.radius;
            this.speedX = -this.speedX;
        } else if (this.x + this.radius >= game.getPlayWidth() - 70) {
            this.speedX = -this.speedX;
            this.x = game.getPlayWidth() - 70 - this.radius;
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
        if (this.y - this.radius <= 55) {
            this.speedY = -this.speedY;
            this.y = 55 + this.radius;
        } else if (this.y + this.radius > game.getPlayHeight() - 80) {
            this.speedY = -this.speedY;
            this.y = game.getPlayHeight() - this.radius - 80;
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
                } else if (this.id == 8){
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
     *
     * @param coord set of coordinates that will both be assigned to this.x and this.y
     */
    public void setCoord(Coord coord) {
        this.x = coord.getX();
        this.y = coord.getY();
    }

    public int getType() {
        return this.type;
    }

    /**
     *
     * @param x - to be added to this.x
     * @param y - to be added to this.y
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
        if (this.bitmap == null) {
            this.bitmap = gv.getBitmapFromResource(this.image);
        }
        gv.drawBitmap(bitmap, (float) this.x, (float) this.y, (float) this.width, (float) this.height);
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

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    /**
     * Gets speed y.
     *
     * @return the speed y
     */

    // werkt niet, niet gebruiken
    public void setSpeedX(float xSpeed) {
        this.speedX = xSpeed;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public ArrayList<Hole> getHoles() {
        return holes;
    }

    public double getSpeedY() {
        return this.speedY;
    }

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
     * Gets bitmap.
     *
     * @return the bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
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

    public boolean getCollision() {
        return this.collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    /**
     * Reset lastisertedid.
     */
    public void resetLastisertedid() {
        lastisertedid = 1;
    }

}

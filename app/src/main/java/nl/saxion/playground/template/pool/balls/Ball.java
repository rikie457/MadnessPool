package nl.saxion.playground.template.pool.balls;

import android.graphics.Bitmap;

import java.util.ArrayList;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Hole;
import nl.saxion.playground.template.pool.Info;
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
    /**
     * The Sunken balls.
     */
    protected ArrayList<Ball> sunkenBalls;
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
     * @param game        the game
     * @param balls       the balls
     * @param holes       the holes
     * @param sunkenBalls the sunken balls
     * @param x           the x
     * @param y           the y
     * @param width       the width
     * @param height      the height
     * @param image       the image
     * @param type        the type
     */
    public Ball(Game game, ArrayList<Ball> balls, ArrayList<Hole> holes, ArrayList<Ball> sunkenBalls, double x, double y, double width, double height, int image, int type) {
        this.id = lastisertedid;
        lastisertedid++;
        this.game = game;
        this.balls = balls;
        this.holes = holes;
        this.sunkenBalls = sunkenBalls;
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

            if (distSqr <= (this.getRadius() + balls.get(i).getRadius()) * (this.getRadius() + balls.get(i).getRadius())) {
                if (this.speedX == 0 && this.speedY == 0 && balls.get(i).getSpeedX() == 0 && balls.get(i).getSpeedY() == 0) {
                    this.speedY = .5;
                    this.speedX = -.5;
                }
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
        this.x += this.speedX;
        this.y += this.speedY;

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
            if (Math.abs(this.speedX) < .1 && Math.abs(this.speedY) < .1) {
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
            if (Math.abs(this.speedY) < .1 && Math.abs(this.speedX) < .1) {
                this.speedY = 0;
            }
        }
    }

    private void checkCollisionHole() {
        for (int i = 0; i < this.holes.size(); i++) {
            if (this.getClass() == Ball.class) {
                if (Math.sqrt(Utility.getDistanceNotSquared(this.x + this.radius, this.y + this.radius, this.holes.get(i).getX(), this.holes.get(i).getY())) - (this.radius) <= 0) {
                    if (this.id != 8) {
                        for (i = 0; i < game.getPlayers().size(); i++) {
                            if (game.getCurrentplayer() == game.getPlayers().get(i)) {
                                Player player = game.getPlayers().get(i);
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
                    } else {
                        //is 8 ball
                        if (game.getCurrentplayer().getScoredballs().size() < 7) {
                            game.winnerScreen(game.getInactiveplayer().getPlayerId());
                        } else {
                            game.winnerScreen(game.getCurrentplayer().getPlayerId());
                        }
                    }
                    this.game.removeEntity(this);
                    this.sunkenBalls.add(this);
                    if (game.getMovingBalls().contains(this)) {
                        game.getMovingBalls().remove(this);
                    }
                    this.balls.remove(this);
                }
            }
        }
    }

    private boolean checkMovement() {
        if (this.speedX == 0 && this.speedY == 0) {
            return false;
        }
        return true;
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
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return this.y;
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
     * Gets speed y.
     *
     * @return the speed y
     */
    public double getSpeedY() {
        return this.speedY;
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

    /**
     * Reset lastisertedid.
     */
    public void resetLastisertedid() {
        lastisertedid = 1;
    }
}

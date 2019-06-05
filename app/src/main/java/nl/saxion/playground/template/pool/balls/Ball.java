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


public class Ball extends Entity {

    protected static int lastisertedid = 1;
    protected double speedX, speedY;
    protected double mass, x, y, width, height, radius, bx, by, friction, energyloss;
    protected int color, id, image, type;
    protected ArrayList<Ball> balls;
    protected ArrayList<Hole> holes;
    protected ArrayList<Ball> sunkenBalls;
    protected Game game;
    protected Bitmap bitmap;
    protected boolean moving;
    protected boolean shot;
    protected double oldX, oldY, newX, newY;

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

                        this.game.removeEntity(this);
                        this.sunkenBalls.add(this);
                        if (game.getMovingBalls().contains(this)) {
                            game.getMovingBalls().remove(this);
                        }
                        this.balls.remove(this);
                    } else {
                        //Is 8 Ball
                    }
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

    public double getMass() {
        return this.mass;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(float x) { this.x = x; }

    public void setY(float y) { this.y = y; }

    public double getSpeedX() {
        return this.speedX;
    }

    // werkt niet, niet gebruiken
    public void setSpeedX(float xSpeed) { this.speedX = xSpeed; }

    // werkt niet, niet gebruiken
    public void setSpeedY(float ySpeed) { this.speedY = ySpeed; }

    public double getSpeedY() {
        return this.speedY;
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getId() {
        return id;
    }

    public boolean isShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public boolean isMoving() {
        return moving;
    }
}

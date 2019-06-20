package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;
import android.os.CountDownTimer;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.Vector2;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public class SpeedBoost extends Powerup {
    private WhiteBall ball;
    static private Bitmap bitmap;
    private Game game;
    private double x,y,radius,maxspeed = 2;
    private int currentturn, intialturn;
    private boolean speedBoost;

    public SpeedBoost(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.ball = ball;
        this.x = x;
        this.y = y;
        this.radius = 30f;
    }
    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void tick() {
        super.tick();
        this.currentturn = game.getTurns();
        if (this.collected) {
            if (this.intialturn + 2 == this.currentturn) {
                game.removeEntity(this);

                removeSpeedBoost();
            } else {
                if (!this.speedBoost) {
                        applySpeedBoost();
                    this.speedBoost = true;
                }
            }
        }
    }

    public void applySpeedBoost(){
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            double speedx = ball.getSpeedX();
            double speedy = ball.getSpeedY();
            ball.setSpeedX((float) speedx * 4);
            ball.setSpeedY((float) speedy * 4);
            if (speedx == maxspeed | speedy == maxspeed){
                ball.setSpeedX((float) maxspeed);
                ball.setSpeedY((float) maxspeed);
            }
        }
    }

    public void removeSpeedBoost(){
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            double speedx = ball.getSpeedX();
            double speedy = ball.getSpeedY();
            ball.setSpeedX((float) speedx);
            ball.setSpeedY((float) speedy);
        }
    }

    public void draw(GameView gameView) {
        if (bitmap == null) {
            bitmap = gameView.getBitmapFromResource(R.drawable.speedboost);
        }
        gameView.drawBitmap(bitmap, (float) x, (float) y,(float) this.radius,(float) this.radius);
    }

    public void resolveColission() {
        this.intialturn = game.getTurns();
        this.invisable = true;
        this.collected = true;
    }
}

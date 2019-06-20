package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public class NoDrag extends Powerup {
    private WhiteBall ball;
    static private Bitmap bitmap;
    private Game game;
    private double x,y,radius;
    private static boolean timerActive;
    private static int timer;

    public NoDrag(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.ball = ball;
        this.x = x;
        this.y = y;
        this.timer = 1800;
        this.radius = 30f;
    }

    @Override
    public void tick() {
        super.tick();
        timerNoDrag();
    }

    public void timerNoDrag(){
        if (this.collected) {
            this.timerActive = true;
            removeDrag();
        }

        if(this.timerActive == true){
            this.timer--;
        }

        if (this.timer == 0){
            applyDrag();
            this.game.removeEntity(this);
            this.timer = 1800;
            this.timerActive = false;
        }
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public void applyDrag() {
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            double friction = ball.getFriction();
            ball.setFriction(friction * .9965);
        }
    }

    public void removeDrag() {
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            ball.setFriction(1);
        }
    }

    public void resolveColission() {
        this.invisable = true;
        this.collected = true;
    }

    public void draw(GameView gameView) {
        if (bitmap == null) {
            bitmap = gameView.getBitmapFromResource(R.drawable.nodrag);
        }
        gameView.drawBitmap(bitmap, (float) x, (float) y,(float) this.radius,(float) this.radius);
    }
}
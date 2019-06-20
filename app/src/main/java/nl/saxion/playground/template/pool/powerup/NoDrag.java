package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public class NoDrag extends Powerup {
    private WhiteBall whiteBall;
    static private Bitmap bitmap;
    private Game game;
    private double x, y, radius;
    private int currentturn, intialturn;
    private boolean applied;

    public NoDrag(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
        this.x = x;
        this.y = y;
        this.radius = 30f;
    }

    @Override
    public void tick() {
        super.tick();
        this.currentturn = game.getTurns();
        if (this.collected) {
            if (this.intialturn + 2 == this.currentturn) {
                applyDrag();
                game.removeEntity(this);
            } else {
                if (!this.applied) {
                    removeDrag();
                    this.applied = true;
                }
            }
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
            ball.setFriction(.9999);
        }
    }

    public void resolveColission() {
        this.intialturn = game.getTurns();
        this.invisable = true;
        this.collected = true;
    }

    public void draw(GameView gameView) {
        if (!invisable) {
            if (bitmap == null) {
                bitmap = gameView.getBitmapFromResource(R.drawable.nodrag);
            }
            gameView.drawBitmap(bitmap, (float) x, (float) y, (float) this.radius, (float) this.radius);
        }
    }

    @Override
    public void createPowerUp() {
        NoDrag noDrag = new NoDrag(game, (float) Utility.randomDoubleFromRange(100, game.getPlayWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall);
        game.getPowerups().add(noDrag);
        game.addEntity(noDrag);
    }
}
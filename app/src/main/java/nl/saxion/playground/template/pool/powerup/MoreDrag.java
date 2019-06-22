package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;
import android.util.Log;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public class MoreDrag extends Powerup {
    static Bitmap bitmap;
    private Game game;
    private WhiteBall whiteBall;
    private int currentturn, intialturn;
    private boolean applied;

    public MoreDrag(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
    }

    @Override
    public void tick() {
        super.tick();
        this.currentturn = game.getTurns();
        if (this.collected) {
            if (this.intialturn + 2 == this.currentturn) {
                removeDrag();
                game.removeEntity(this);
            } else {
                if (!this.applied) {
                    applyDrag();
                    this.applied = true;
                }
            }
        }
    }

    public void applyDrag() {
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            double friction = ball.getFriction();
            ball.setFriction(friction * .999);
        }
    }

    public void removeDrag() {
        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            ball.setFriction(.9965);
        }
    }

    @Override
    public void draw(GameView gv) {
        if(!invisable) {
            if (bitmap == null) {
                bitmap = gv.getBitmapFromResource(R.drawable.drag);
            }
            gv.drawBitmap(bitmap, (float) vector2.getX(), (float) vector2.getY(), game.getPowerupsize(), game.getPowerupsize());
        }
    }

    @Override
    public void resolveColission() {
        this.intialturn = game.getTurns();
        this.invisable = true;
        this.collected = true;
    }


    @Override
    public void createPowerUp() {
        MoreDrag moredrag = new MoreDrag(game, (float) Utility.randomDoubleFromRange(100, game.getPlayWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall);
        game.getPowerups().add(moredrag);
        game.addEntity(moredrag);
    }
}

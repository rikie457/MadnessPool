package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.Vector2;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public class Wormhole extends Powerup {

    static Bitmap bitmap;
    private Game game;
    private WhiteBall whiteBall;
    private Vector2 vector2 = new Vector2();
    private int currentturn, intialturn;
    private boolean collected;


    public Wormhole(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
        this.vector2.set(x, y);
    }

    @Override
    public void tick() {
        super.tick();
        if (collected) {
            this.currentturn = game.getTurns();
            if (this.intialturn + 2 == this.currentturn) {
                game.removeEntity(this);
            } else {
                teleport();
            }
        }
    }

    public void teleport() {

        for (int i = 0; i < game.getBalls().size(); i++) {
            Ball ball = game.getBalls().get(i);
            if (Math.sqrt(Utility.getDistanceNotSquared(this.vector2.getX(), this.vector2.getY(), ball.getVector2().getX() + ball.getRadius(), ball.getVector2().getY() + ball.getRadius())) - (30) <= 0 && !game.getCueBallInHand()) {
                ball.getVector2().set(Utility.randomDoubleFromRange(100, game.getPlayWidth() - 100), Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100));
            }
        }
    }

    @Override
    public void resolveColission() {
        this.intialturn = game.getTurns();
        this.collected = true;
    }


    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.wormhole);
        }
        gv.drawBitmap(bitmap, (float) vector2.getX(), (float) vector2.getY(), game.getPowerupsize(), game.getPowerupsize());
    }

    @Override
    public void createPowerUp() {
        game.addEntity(new Wormhole(game, (float) Utility.randomDoubleFromRange(100, game.getPlayWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall));
    }
}

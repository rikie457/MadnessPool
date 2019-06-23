package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.WhiteBall;


public class AddWall extends Powerup {

    private Game game;
    private WhiteBall whiteBall;
    private boolean effectApplied = false;
    private int intialturn;
    private static Bitmap bitmap;

    public AddWall(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
    }

    @Override
    public void tick() {
        super.tick();
        int currentturn = game.getTurns();
        if (this.collected) {
            if (this.intialturn + 2 == currentturn) {
                game.removeEntity(this);
            } else if (!this.effectApplied){
                    applyEffect();
                    this.effectApplied = true;
            }
        }
    }

    @Override
    public void resolveColission() {
        this.collected = true;
        this.invisable = true;
        this.intialturn = game.getTurns();
    }

    public void applyEffect(){
        game.startPlacingWall();
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.addwall);
        }
        gv.drawBitmap(bitmap, (float) vector2.getX(), (float) vector2.getY(), game.getPowerupsize(), game.getPowerupsize());
    }

    @Override
    public void createPowerUp() {
        AddWall addwall = new AddWall(game, (float) Utility.randomDoubleFromRange(100, game.getWidth() - 100), (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100), this.whiteBall);
        game.getPowerups().add(addwall);
        game.addEntity(addwall);
    }
}

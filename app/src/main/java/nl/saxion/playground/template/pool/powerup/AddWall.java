package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.WhiteBall;


/**
 * The Add wall.
 *
 * Adds a wall
 */
public class AddWall extends Powerup {

    private Game game;
    private WhiteBall whiteBall;
    private boolean effectApplied = false;
    private int intialturn;
    private static Bitmap bitmap;

    /**
     * Instantiates a new Add wall.
     *
     * @param game the game
     * @param x    the x
     * @param y    the y
     * @param ball the ball
     */
    public AddWall(Game game, double x, double y, WhiteBall ball) {
        super(game, x, y, ball);
        this.game = game;
        this.whiteBall = ball;
    }

    @Override
    public void tick() {
        super.tick();
        int currentturn = game.getTurns();
        //Check if collected
        if (this.collected) {
            //Check if lifetime is not max than 2 turns
            if (this.intialturn + 2 == currentturn) {
                game.removeEntity(this);
                //apply if not applied
            } else if (!this.effectApplied){
                applyEffect();
                this.effectApplied = true;
            }
        }
    }

    @Override
    public void resolveColission() {
        //If collision set the needed variables.
        this.collected = true;
        this.invisable = true;
        this.intialturn = game.getTurns();
    }

    /**
     * Apply effect.
     */
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

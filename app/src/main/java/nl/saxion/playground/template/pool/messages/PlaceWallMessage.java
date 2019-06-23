package nl.saxion.playground.template.pool.messages;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;

/**
 * The type PlaceWallMEssage.
 */
public class PlaceWallMessage extends Entity {

    private int timer = 0;

    static private Bitmap bitmap1;
    static private Bitmap bitmap2;
    /**
     * The A val.
     */
    float aVal;
    private Game game;

    /**
     * Instantiates a new PlaceWallMessage.
     *
     * @param game the game
     */
    public PlaceWallMessage(Game game) {
        this.game = game;
    }

    public int getLayer() {
        return 8;
    }

    @Override
    public void tick() {
        super.tick();

        if (timer < 480) {
            timer++;
        }

        if (timer == 480) {
            timer = 0;
            game.removeEntity(this);
        }
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap1 == null) {
            bitmap1 = gv.getBitmapFromResource(R.drawable.playertwowall);
        }

        if (bitmap2 == null){
            bitmap2 = gv.getBitmapFromResource(R.drawable.playeronewall);
        }

        if (game.getCurrentplayer().getPlayerId() == 1) {
            gv.drawBitmap(bitmap1, game.getWidth() / 2 - 300, game.getHeight() / 2 - 250, 600, 600, aVal);
        } else {
            gv.drawBitmap(bitmap2, game.getWidth() / 2 - 300, game.getHeight() / 2 - 250, 600, 600, aVal);
        }
    }
}

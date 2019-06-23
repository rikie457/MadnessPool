package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type PlaceWallMEssage.
 */
public class PlaceWallMessage extends Entity {

    private int timer = 0;

    static private Bitmap bitmap;
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
        if (bitmap == null) {
            if (game.getCurrentplayer().getPlayerId() == 1) {
                bitmap = gv.getBitmapFromResource(R.drawable.playertwowall);
            } else {
                bitmap = gv.getBitmapFromResource(R.drawable.playeronewall);
            }
        }
        gv.drawBitmap(bitmap, game.getWidth() / 2 - 300, game.getHeight() / 2 - 250, 600, 600, aVal);
    }
}

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

    static private Bitmap bitmap;
    /**
     * The A val.
     */
    float aVal;
    private int entryType;
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
        return 2;
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
            if (game.getCurrentplayer().getPlayerId() == 1 && this.entryType == 1) {
                bitmap = gv.getBitmapFromResource(R.drawable.playertwowall);
            } else if (game.getCurrentplayer().getPlayerId() == 2 && this.entryType == 1){
                bitmap = gv.getBitmapFromResource(R.drawable.playeronewall);
            } else if (game.getCurrentplayer().getPlayerId() == 1 && this.entryType == 2) {
                bitmap = gv.getBitmapFromResource(R.drawable.playeronewall);
            } else if (game.getCurrentplayer().getPlayerId() == 2 && this.entryType == 2) {
                bitmap = gv.getBitmapFromResource(R.drawable.playertwowall);
            }
        }
        gv.drawBitmap(bitmap, game.getWidth() / 2 - 300, game.getHeight() / 2 - 250, 600, 600, aVal);
    }

    public void setEntryType(int entryType) {
        this.entryType = entryType;
    }
}

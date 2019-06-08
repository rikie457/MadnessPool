package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type Menu background.
 */
public class MenuBackground extends Entity {

    /**
     * The A val.
     */
    float aVal;

    static private Bitmap bitmap;

    private Game game;

    /**
     * Instantiates a new Menu background.
     *
     * @param game the game
     */
    MenuBackground(Game game) {
        this.game = game;
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.menubackground);
        }
        gv.drawBitmap(bitmap, 0, 0, game.getPlayWidth(), game.getPlayHeight(), aVal);
    }
}

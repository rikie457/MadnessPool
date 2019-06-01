package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class MenuBackground extends Entity {

    float aVal;

    static private Bitmap bitmap;

    private Game game;

    MenuBackground(Game game) {
        this.game = game;
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
        gv.drawBitmap(bitmap, 0, 0, game.getWidth(), game.getHeight(), aVal);
    }
}

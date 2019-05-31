package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Display;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class MenuBackground extends Entity {

    float aVal;

    static private Bitmap bitmap;

    private Game game;

    private DisplayMetrics displayMetrics = new DisplayMetrics();

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
            bitmap = gv.getBitmapFromResource(R.drawable.background);
        }
        gv.drawBitmap(bitmap, 0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels, aVal);
    }
}

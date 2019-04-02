package nl.saxion.playground.template.platformer;

import android.graphics.Bitmap;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.DrawView;
import nl.saxion.playground.template.R;

public class Background extends Entity {

    private static Bitmap bitmap;

    private Platformer game;


    Background(Platformer game) {
        this.game = game;
    }


    @Override
    public void draw(DrawView dv) {
        if (bitmap==null) {
            bitmap = dv.getBitmapFromResource(R.drawable.background);
        }
        float bgWidth = (float)bitmap.getWidth() / (float)bitmap.getHeight() * game.virtualHeight;
        float offset = (game.scrollX / 3f) % bgWidth; // one-third speed relative to foreground

        for(int x = 0; x <= Math.ceil(game.virtualWidth/bgWidth); x++) {
            dv.drawBitmap(bitmap, (float) x * bgWidth - offset, 0, bgWidth, game.virtualHeight);
        }
    }
}


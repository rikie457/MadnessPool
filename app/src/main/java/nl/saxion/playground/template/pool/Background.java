package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class Background extends Entity {
    private Bitmap bitmap;
    private Game game;

    @Override
    public int getLayer() {
        return 0;
    }

    public Background(Game game) {
        this.game = game;
    }

public void draw(GameView gameView){
    if (bitmap==null) {
        bitmap = gameView.getBitmapFromResource(R.drawable.pooltafel_topview);
    }
    gameView.drawBitmap(bitmap,0,0,game.getPlayWidth(),(float) game.getPlayHeight());
}
}

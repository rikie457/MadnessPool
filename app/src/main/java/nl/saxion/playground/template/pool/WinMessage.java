package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;

public class WinMessage extends Entity {

    float aVal;

    static private Bitmap bitmap;

    private Game game;
    private int winnerId;

    public WinMessage(Game game, int winnerId) {
        this.game = game;
        this.winnerId = winnerId;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            if (winnerId == 1) {
                bitmap = gv.getBitmapFromResource(R.drawable.playeronewin);
            } else {
                bitmap = gv.getBitmapFromResource(R.drawable.playertwowin);
            }
        }
        gv.drawBitmap(bitmap, game.getWidth() / 2 - 300, game.getHeight()/2 - 150, 600, 300, aVal);
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            game.removeEntity(this);
            game.reset();
        }
    }

}

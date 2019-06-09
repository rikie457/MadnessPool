package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type Win message.
 */
public class WinMessage extends Entity {

    static private Bitmap bitmap;
    /**
     * The A val.
     */
    float aVal;
    private Game game;
    private int winnerId;

    /**
     * Instantiates a new Win message.
     *
     * @param game     the game
     * @param winnerId the winner id
     */
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
        gv.drawBitmap(bitmap, 1000 / 2 - 300, 1000 / 2 - 450, 600, 300, aVal);
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            game.removeEntity(this);
            game.setWinMessage(null);
            game.reset();
        }
    }

}

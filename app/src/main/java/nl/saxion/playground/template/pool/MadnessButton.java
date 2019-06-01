package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;

public class MadnessButton extends Entity {

    float aVal;

    static private Bitmap bitmap;

    private Game game;

    private boolean buttonPressed = false;
    private int timer;

    MadnessButton (Game game) {
        this.game = game;
    }

    @Override
    public void tick() {
        super.tick();

        if (buttonPressed && timer < 60) {
            timer++;
        }

        if (buttonPressed && timer == 60) {
            game.startMadness();
        }
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.madnessbutton);
        }
        gv.drawBitmap(bitmap, game.getWidth()/2 - 300, game.getHeight()/2 - 50, 600, 300, aVal);
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);
        if (event.getX() > game.getWidth()/2 - 300 && event.getX() < game.getWidth()/2 + 300 && event.getY() > game.getHeight()/2 + 70 && event.getY() < game.getHeight()/2 + 130) buttonPressed = true;
    }
}

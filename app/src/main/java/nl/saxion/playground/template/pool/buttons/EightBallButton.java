package nl.saxion.playground.template.pool.buttons;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;

public class EightBallButton extends Entity {

    float aVal;

    static private Bitmap bitmap;

    private Game game;

    private boolean buttonPressed = false;
    private int timer = 0;

     public EightBallButton(Game game) {
        this.game = game;
    }

    @Override
    public void tick() {
        super.tick();

        if (buttonPressed) {
            buttonPressed = false;
            game.startEightBall();
        }
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.eightballbutton);
        }
        gv.drawBitmap(bitmap, game.getWidth()/2 - 300, game.getHeight()/2 - 250, 600, 300, aVal);
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);
        if (event.getX() > game.getWidth()/2 - 300 && event.getX() < game.getWidth()/2 + 300 && event.getY() > game.getHeight()/2 - 130 && event.getY() < game.getHeight()/2 - 70 && event.getAction() == MotionEvent.ACTION_UP) buttonPressed = true;
    }
}

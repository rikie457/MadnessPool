package nl.saxion.playground.template.platformer;

import android.view.MotionEvent;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;

public class Scroller extends Entity {

    private Game game;

    public float x = 0;


    public Scroller(Game game) {
        this.game = game;
    }

    // Auto-scroll
    @Override
    public void tick() {
        scroll(0.3f / game.ticksPerSecond());
    }

    // Scroll on drag
    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        scroll(-touch.deltaX);
    }

    private void scroll(float delta) {
        x = (x + delta) % Map.width;
        for(Game.Listener listener : game.listeners) {
            listener.scrollChanged();
        }
    }


}

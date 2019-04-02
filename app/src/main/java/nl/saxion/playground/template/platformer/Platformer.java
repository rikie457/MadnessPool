package nl.saxion.playground.template.platformer;
import android.view.MotionEvent;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;

public class Platformer extends GameModel {

    // GameModel state
    Map map;
    public float scrollX = 0;

    public Platformer(float aspectRatio) {
        // Create a Platformer with a virtual screen that is always 8 units wide. Height matches actual screen size.
        super(8f, 8f/aspectRatio);

        addEntity(new Background(this));
        map = new Map(this);
        addEntity(map);

        // Add an anonymous entity used for scrolling.
        addEntity(new Entity() {
            // Auto-scroll
            @Override
            public void tick() {
                scroll(0.3f / ticksPerSecond());
            }

            // Scroll on drag
            @Override
            public void handleTouch(Touch touch, MotionEvent event) {
                scroll(-touch.deltaX);
            }
        });
    }

    private void scroll(float delta) {
        scrollX = (scrollX + delta) % Map.width;
        // This event can be used by the activity to update the scrollTextView.
        event("scrolled");
    }
}

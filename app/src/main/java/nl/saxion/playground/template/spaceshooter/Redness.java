package nl.saxion.playground.template.spaceshooter;

import android.graphics.Color;
import android.graphics.Paint;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class Redness extends Entity {

    private SpaceShooter game;

    // Set to 1 when a collision is detected. Gradually decays after that.
    private float hit = 0;

    // Paint used to draw the red blur.
    Paint redPaint = new Paint();


    Redness(SpaceShooter game) {
        this.game = game;
        redPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void tick() {
        // Decay redness.
        hit = hit * 0.99f;

        // Find the ship
        Ship ship = game.getEntity(Ship.class);

        // For each rock, see if there is a collision. If there is, set redness to max.
        for(Rock rock : game.getEntities(Rock.class)) {
            if (distance(rock.xVal, rock.yVal, ship.xVal, ship.yVal) < rock.size) hit = 1;
        }
    }

    // Pythagoras!
    private float distance(float x1, float y1, float x2, float y2) {
        return (float)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    // Run hit checker after everything else
    @Override
    public int getLayer() {
        return 5;
    }

    @Override
    public void draw(GameView gv) {
        if (hit>0.01) {
            redPaint.setColor(Color.argb(Math.round(hit*150), 255, 0, 0));
            gv.getCanvas().drawPaint(redPaint);
        }
    }
}

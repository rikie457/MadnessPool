package nl.saxion.playground.template.spaceshooter;

import android.graphics.Bitmap;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.DrawView;
import nl.saxion.playground.template.R;


public class Rock extends Entity {

    float xSpeed, ySpeed, aSpeed, xVal, yVal, aVal, size;

    static private Bitmap bitmap;

    private SpaceShooter game;

    Rock(SpaceShooter game) {
        this.game = game;

        aSpeed = (float)Math.random() * 0.3f - 0.15f;
        size = (float)Math.random() * 12 + 5;

        // Generate a random starting position just outside one of the four screen edges.
        // The speed is set such that the rock will move towards the screen.

        if (Math.random() > 0.5) {
            if (Math.random() > 0.5) { // right screen edge
                xVal = game.virtualWidth + size;
                xSpeed = -0.1f * (float)Math.random() - 0.02f;
            } else { // left screen edge
                xVal = -size;
                xSpeed = 0.1f * (float)Math.random() + 0.02f;
            }
            yVal = (float)Math.random() * game.virtualHeight;
            ySpeed = 0.2f * (float)Math.random() - 0.1f;
        } else {
            if (Math.random() > 0.5) { // bottom screen edge
                yVal = game.virtualHeight + size;
                ySpeed = -0.1f * (float)Math.random() - 0.02f;
            } else { // top screen edge
                yVal = -size;
                ySpeed = 0.1f * (float)Math.random() + 0.02f;
            }
            xVal = (float)Math.random() * game.virtualWidth;
            xSpeed = 0.2f * (float)Math.random() - 0.1f;
        }

    }

    @Override
    public void tick() {
        xVal += xSpeed;
        yVal += ySpeed;
        aVal += aSpeed;

        // Remove after exiting the screen
        if (xSpeed < 0 ? xVal<-size : xVal>game.virtualWidth+size) game.removeEntity(this);
        else if (ySpeed < 0 ? yVal<-size : yVal>game.virtualHeight+size) game.removeEntity(this);
    }

    @Override
    public void draw(DrawView gv) {
        if (bitmap==null) {
            bitmap = gv.getBitmapFromResource(R.drawable.rock);
        }
        gv.drawBitmap(bitmap, xVal-size/2, yVal-size/2, size, size, aVal);
    }
}

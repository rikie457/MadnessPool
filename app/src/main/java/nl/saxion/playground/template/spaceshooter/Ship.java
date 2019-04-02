package nl.saxion.playground.template.spaceshooter;

import android.graphics.Bitmap;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.DrawView;
import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameModel;

public class Ship extends Entity {

    private final float acceleration = 0.003f;
    private final float rotationSpeed = 1f;
    private final float slowdown = 0.999f;
    private final float width = 10f;
    private final float height = 10f;
    private final float bounce = -0.8f; // 80% of original speed

    private float xSpeed, ySpeed, xVal, yVal, aVal;

    static private Bitmap bitmap;

    private SpaceShooter game;


    Ship(SpaceShooter game) {
        this.game = game;
        xVal = game.virtualWidth/2;
        yVal = game.virtualHeight/2;
    }

    @Override
    public void tick() {
        xSpeed *= slowdown;
        ySpeed *= slowdown;

        // Scan the list of current touches to see if we need to rotate or accelerate.
        boolean left=false, right=false;
        for(GameModel.Touch touch : game.touches) {
            if (touch.x < game.virtualWidth*0.333) left = true;
            else if (touch.x < game.virtualWidth*0.666) left = right = true; // middle
            else right = true;
        }

        if (left && right) { // accelerate
            xSpeed += acceleration * Math.sin(Math.toRadians(aVal));
            ySpeed -= acceleration * Math.cos(Math.toRadians(aVal));
        }
        else if (left) {
            aVal -= rotationSpeed;
        }
        else if (right) {
            aVal += rotationSpeed;
        }
        xVal += xSpeed;
        yVal += ySpeed;

        // Bounce
        if(xVal < 0) {
            xVal = 2*0 - xVal;
            xSpeed = bounce*xSpeed;
        }
        if(xVal > game.virtualWidth) {
            xVal = 2*game.virtualWidth - xVal;
            xSpeed = bounce*xSpeed;
        }
        if(yVal < 0) {
            yVal = 2*0 - yVal;
            ySpeed = bounce*ySpeed;
        }
        if(yVal > game.virtualHeight) {
            yVal = 2*game.virtualHeight - yVal;
            ySpeed = bounce*ySpeed;
        }
    }

    @Override
    public void draw(DrawView gv) {
        if (bitmap==null) {
            bitmap = gv.getBitmapFromResource(R.drawable.ship2);
        }
        gv.drawBitmap(bitmap, xVal-width/2, yVal-height/2, width, height, aVal);
    }
}

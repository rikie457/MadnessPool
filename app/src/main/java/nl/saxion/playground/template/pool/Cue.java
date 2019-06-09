package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type Cue.
 */
public class Cue extends Entity {

    private float newX, newY, x, y;
    private boolean visible;
    private Paint whitePaint;
    private Bitmap bitmap;
    private Game game;


    /**
     * Instantiates a new Shoot line.
     *
     * @param visible    the visible
     * @param whitePaint the whitePaint
     */
    public Cue(boolean visible, Paint whitePaint) {
        this.visible = visible;
        this.whitePaint = whitePaint;
        this.whitePaint.setStrokeWidth(2);
        this.whitePaint.setColor(Color.WHITE);
    }

    private static double prevAngle = 0;

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);

        return rotatedBitmap;
    }

    @Override
    public void draw(GameView gameView) {

        if (this.visible) {
            //gv.getCanvas().drawLine(this.x, this.y, this.newX,  this.newY, this.whitePaint);
            /*if (bitmap == null) {
                bitmap = gameView.getBitmapFromResource(R.drawable.pool_cue);
            }

            double angle = this.x - this.newX;
            angle = Math.toDegrees(Math.atan((this.y - this.newY) / angle));
            if(prevAngle != angle) rotateBitmap(bitmap, (float)Math.random() * 360);

            gameView.drawBitmap(bitmap, x, newY, 200, 25);

            prevAngle = angle;
            */
        }
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Sets visible.
     *
     * @param visible the visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Get visible boolean.
     *
     * @return the boolean
     */
    public boolean getVisible(){
        return this.visible;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    /**
     * Sets new x.
     *
     * @param newX the new x
     */
    public void setNewX(float newX) {
        this.newX = newX;
    }

    /**
     * Sets new y.
     *
     * @param newY the new y
     */
    public void setNewY(float newY) {
        this.newY = newY;
    }
}

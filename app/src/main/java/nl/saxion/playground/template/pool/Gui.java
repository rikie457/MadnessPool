package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class Gui extends Entity {
    private double x, y, width, height;
    private Bitmap bitmap;
    private ArrayList<Ball> scoredBalls;
    private Game game;

    public Gui(Game game, ArrayList<Ball> scoredBalls, double x, double y, double width, double height) {
        this.game = game;
        this.scoredBalls = scoredBalls;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public void draw(GameView gv) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        gv.getCanvas().drawRect((float) this.x, (float) this.y, (float) this.width, (float) this.height, paint);
        if (this.bitmap == null) {
            this.bitmap = gv.getBitmapFromResource(R.drawable.shelf);
        }
        gv.drawBitmap(bitmap, (float) this.x + gv.getWidth() / 2 - 600, (float) this.y, 1200, 160);
        if (this.scoredBalls.size() != 0) {
            for (int i = 0; i < this.scoredBalls.size(); i++) {
                gv.drawBitmap(this.scoredBalls.get(i).getBitmap(), (float) this.x + gv.getWidth() / 2 - 650 + this.scoredBalls.get(i).getId() * (float) this.scoredBalls.get(i).getWidth() + 10, (float) this.y + (float) this.scoredBalls.get(i).getRadius(), (float) this.scoredBalls.get(i).getWidth() - 500, (float) this.scoredBalls.get(i).getHeight());
            }
        }
    }

}

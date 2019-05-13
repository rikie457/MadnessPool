package nl.saxion.playground.template.pool;

import android.graphics.Color;
import android.graphics.Paint;

import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class Hole extends Entity {
    private double x;
    private double y;
    private Game game;

    public Hole(Game game, double x, double y){
        this.game = game;
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public void draw(GameView gv) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        gv.getCanvas().drawCircle(this.game.getWidth() / 2 + 100, this.game.getHeight() / 2 + 100, 50, paint);
    }
}

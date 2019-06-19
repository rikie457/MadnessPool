package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.os.CountDownTimer;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class WallPlacementTimer extends Entity {

    private double timer;
    private int tickCount;
    private float timerOffset;
    private Game game;
    private WallHandler wallHandler;

    NumberFormat formatter = new DecimalFormat("#0");

    /**
     * Instantiates a new WallPlacementTimer.
     */
    public WallPlacementTimer(Game game, WallHandler wallHandler) {
        this.game = game;
        this.wallHandler = wallHandler;
        this.timer = 30;
    }

    public int getLayer() {
        return 2;
    }

    @Override
    public void tick() {

        this.timer -= (float) tickCount / game.ticksPerSecond();

        if (this.timer < 0) {
            this.timer = 0;
        }

        if (this.timer <= 0) {
            wallHandler.setCanContinue(true);
            removeThisEntity();
        }

        if (this.timer < 10) {
            this.timerOffset = 6;
        } else {
            this.timerOffset = 12;
        }

    }

    @Override
    public void draw(GameView gv) {

        gv.getCanvas().drawText(String.valueOf(formatter.format(timer)), game.getWidth() / 2 - this.timerOffset, game.getHeight() - 25, Game.whitePaint);
    }

    /**
     * Removes this entity when it's done and stops the placement of the wall.
     */
    public void removeThisEntity() {
        wallHandler.setCanContinue(true);
        game.removeEntity(this);
    }

    public void resetTimer() {
        this.timer = 30;
    }
}

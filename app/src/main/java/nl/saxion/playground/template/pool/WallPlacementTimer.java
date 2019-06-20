package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.os.CountDownTimer;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class WallPlacementTimer extends Entity {

    private int timer = 30;
    private int tickCounter;
    private float timerOffset;
    private Game game;
    private WallHandler wallHandler;

    /**
     * Instantiates a new WallPlacementTimer.
     */
    public WallPlacementTimer(Game game, WallHandler wallHandler) {
        this.game = game;
        this.wallHandler = wallHandler;
    }

    public int getLayer() {
        return 2;
    }

    @Override
    public void tick() {

        this.tickCounter++;

        if (this.tickCounter % game.ticksPerSecond() == 0) {
            this.timer--;
        }

        if (this.timer <= 0) {
            removeThisEntity();
        }

    }

    @Override
    public void draw(GameView gv) {

        if (this.timer < 10) {
            this.timerOffset = 6;
        } else {
            this.timerOffset = 12;
        }

        if (this.timer < 0) {
            this.timer = 0;
        }

        gv.getCanvas().drawText(String.valueOf(timer), game.getWidth() / 2 - this.timerOffset, game.getHeight() - 25, Game.whitePaint);
    }

    /**
     * Removes this entity when it's done and stops the placement of the wall.
     */
    public void removeThisEntity() {
        wallHandler.setCanContinue(true);
        game.removeEntity(this);
    }

    /**
     * Resets the timer.
     */
    public void resetTimer() {
        this.timer = 30;
        this.tickCounter = 0;
    }
}

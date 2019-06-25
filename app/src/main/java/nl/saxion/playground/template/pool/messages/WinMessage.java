/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool.messages;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;

/**
 * The type Win message.
 */
public class WinMessage extends Entity {

    static private Bitmap bitmap1;
    static private Bitmap bitmap2;
    private Game game;
    private int winnerId;

    /**
     * Instantiates a new Win message.
     *
     * @param game     the game
     * @param winnerId the winner id
     */
    public WinMessage(Game game, int winnerId) {
        this.game = game;
        this.winnerId = winnerId;
    }

    public int getLayer() {
        return 9;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap1 == null) {
            bitmap1 = gv.getBitmapFromResource(R.drawable.playeronewin);
        }
        if (bitmap2 == null) {
            bitmap2 = gv.getBitmapFromResource(R.drawable.playertwowin);
        }

        if (winnerId == 1) {
            gv.drawBitmap(bitmap1, 1000 / 2 - 300, 1000 / 2 - 450, 600, 300);
        } else {
            gv.drawBitmap(bitmap2, 1000 / 2 - 300, 1000 / 2 - 450, 600, 300);
        }
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            game.removeEntity(this);
            game.setWinMessage(null);
            game.reset();
        }
    }

}

/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type Background.
 */
public class Background extends Entity {
    static private Bitmap bitmap;
    private Game game;

    /**
     * Instantiates a new Background.
     *
     * @param game the game
     */
    public Background(Game game) {
        this.game = game;
    }

    @Override
    public int getLayer() {
        return 0;
    }

    public void draw(GameView gameView) {
        if (bitmap == null) {
            bitmap = gameView.getBitmapFromResource(R.drawable.pooltafel_topview);
        }
        gameView.drawBitmap(bitmap, 0, 0, game.getPlayWidth(), game.getPlayHeight());
    }
}

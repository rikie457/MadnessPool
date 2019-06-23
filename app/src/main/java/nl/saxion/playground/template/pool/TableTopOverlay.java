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
 * The type Table top overlay.
 */
public class TableTopOverlay extends Entity {

    static private Bitmap bitmapNormal, bitmapMaddness, current;
    private Game game;

    /**
     * Instantiates a new TableTopOverlay.
     *
     * @param game the game
     */
    TableTopOverlay(Game game) {
        this.game = game;
    }

    @Override
    public int getLayer() {
        return 3;
    } // 5

    @Override
    public void draw(GameView gv) {
        if (bitmapNormal == null) {
            bitmapNormal = gv.getBitmapFromResource(R.drawable.pooltafel_topview_overlay_normal);
        }
        if(bitmapMaddness == null) {
            bitmapMaddness = gv.getBitmapFromResource(R.drawable.pooltafel_topview_overlay_madness);
        }

        if (Game.gameMode == Game.GameMode.MADNESS) {
            current = bitmapMaddness;
        } else {
            current = bitmapNormal;
        }

        gv.drawBitmap(
                current,
                0,
                0,
                game.getWidth(),
                game.getPlayHeight()
        );
    }
}

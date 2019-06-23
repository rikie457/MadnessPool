/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool.buttons;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;

/**
 * The Madness button.
 * If this button is clicked the madness 8 ball is started
 */
public class MadnessButton extends Entity {

    static private Bitmap bitmap;
    private Game game;

    /**
     * Instantiates a new Madness button.
     *
     * @param game the game
     */
    public MadnessButton(Game game) {
        this.game = game;
    }

    public int getLayer() {
        return 9;
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.madnessbutton);
        }
        gv.drawBitmap(bitmap, game.getWidth() / 2 - 300, game.getHeight() / 2 - 50, 600, 300);
    }

    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        super.handleTouch(touch, event);
        if (event.getX() > game.getWidth() / 2 - 300 && event.getX() < game.getWidth() / 2 + 300 && event.getY() > game.getHeight() / 2 + 70 && event.getY() < game.getHeight() / 2 + 130 && event.getAction() == MotionEvent.ACTION_UP)
            game.startMadness();
    }
}

/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type Activity.
 */
public class Activity extends AppCompatActivity {

    /**
     * The Game.
     */
    GameModel game;
    /**
     * The Game view.
     */
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // In this example, we're overlaying a TextView over our canvas. We could add
        // any Android Views this way.
        setContentView(R.layout.activity_main);
        gameView = findViewById(R.id.poolGame);

        // If a running game has been serialized (because it has been paused for
        // a long time, or because of an orientation change), recreate the Game
        // object from the serialized bundle.
        if (savedInstanceState != null && savedInstanceState.containsKey("game")) {
            game = (Game) savedInstanceState.getSerializable("game");
        } else {
            game = new Game();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("game", game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.setGame(game);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.setGame(null);
    }

    @Override
    public void onBackPressed() {
        ((Game) this.game).reset();
        super.onBackPressed();
    }
}

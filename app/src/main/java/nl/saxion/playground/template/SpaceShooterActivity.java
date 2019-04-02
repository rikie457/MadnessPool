package nl.saxion.playground.template;

import android.arch.lifecycle.Lifecycle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import nl.saxion.playground.template.lib.DrawView;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.spaceshooter.SpaceShooter;


public class SpaceShooterActivity extends AppCompatActivity implements GameModel.Listener {

    SpaceShooter game;
    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // In this example, we don't require a Layout or any other Android Views than
        // are custom GameCanvas.
        drawView = new DrawView(this);
        setContentView(drawView);

        // If a running game has been serialized (because it has been paused for
        // a long time, or because of an orientation change), recreate the Platformer
        // object from the serialized bundle.
        if (savedInstanceState!=null && savedInstanceState.containsKey("game")) {
            game = (SpaceShooter)savedInstanceState.getSerializable("game");
        } else {
            // Delay creating the SpaceShooter until we know the width and height of the
            // view. The game uses these dimensions to create a playing field that matches
            // the size of the actual screen.
            ViewTreeObserver viewTreeObserver = drawView.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    drawView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    game = new SpaceShooter((float)drawView.getWidth() / drawView.getHeight());

                    // If we're RESUMED at this point (we probably are) we should start the game immediately.
                    if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                        game.setListener(SpaceShooterActivity.this);
                    }
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (game!=null) {
            outState.putSerializable("game", game);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (game!=null) game.setListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (game!=null) game.setListener(null);
    }

    @Override
    public void onGameEvent(String name) {
        // We're only expecting "new" and "update" events, so we'll invalidate
        // the DrawView regardless...
        drawView.show(game);
    }
}

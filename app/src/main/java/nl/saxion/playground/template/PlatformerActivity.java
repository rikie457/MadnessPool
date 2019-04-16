package nl.saxion.playground.template;

import android.arch.lifecycle.Lifecycle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.platformer.Platformer;

public class PlatformerActivity extends AppCompatActivity implements GameModel.Listener {

    Platformer game;
    GameView gameView;
    TextView scrollText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // In this example, we're overlaying a TextView over our canvas. We could add
        // any Android Views this way.
        setContentView(R.layout.activity_platformer);

        gameView = findViewById(R.id.gameView);
        scrollText = findViewById(R.id.scrollText);

        // If a running game has been serialized (because it has been paused for
        // a long time, or because of an orientation change), recreate the Platformer
        // object from the serialized bundle.
        if (savedInstanceState!=null && savedInstanceState.containsKey("game")) {
            game = (Platformer)savedInstanceState.getSerializable("game");
        } else {
            // Delay creating the Platformer until we know the width and height of the
            // view. The game uses these dimensions to create a playing field that matches
            // the size of the actual screen.
            ViewTreeObserver viewTreeObserver = gameView.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    gameView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    game = new Platformer((float) gameView.getWidth() / (float) gameView.getHeight());
                    // If we're RESUMED at this point (we probably are) we should start the game immediately.
                    if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
                        game.setListener(PlatformerActivity.this);
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
        // Whenever the Platformer model updates, it should fire appropriate emitEvent events,
        // that we (the Activity) can relay to interested Views.

        if (name.equals("scrolled") || name.equals("new")) {
            scrollText.setText(String.format("%.2f", game.scrollX));
        }

        if (name.equals("updated") || name.equals("new")) {
            gameView.show(game);
        }
    }
}

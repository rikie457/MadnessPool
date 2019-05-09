package nl.saxion.playground.template;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Activity;
import nl.saxion.playground.template.pool.Game;

public class MainActivity extends AppCompatActivity {

    GameView poolGameCanvas;
    GameModel poolGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poolGameCanvas = findViewById(R.id.poolGame);
        poolGame = new Game();

        findViewById(R.id.poolGameText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Activity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        poolGameCanvas.setGame(poolGame);
    }

    @Override
    protected void onPause() {
        super.onPause();
        poolGameCanvas.setGame(null);
    }
}

package nl.saxion.playground.template;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.platformer.Platformer;
import nl.saxion.playground.template.spaceshooter.SpaceShooter;

public class MainActivity extends AppCompatActivity {

    GameView spaceShooterCanvas, platformerCanvas;
    GameModel spaceShooter, platformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spaceShooterCanvas = findViewById(R.id.spaceShooter);
        spaceShooter = new SpaceShooter(320f/200f);

        findViewById(R.id.spaceShooterText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SpaceShooterActivity.class));
            }
        });

        platformerCanvas = findViewById(R.id.platformer);
        platformer = new Platformer(320f/200f);

        findViewById(R.id.platformerText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PlatformerActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        spaceShooterCanvas.setGame(spaceShooter);
        platformerCanvas.setGame(platformer);
    }

    @Override
    protected void onPause() {
        super.onPause();
        spaceShooterCanvas.setGame(null);
        platformerCanvas.setGame(null);
    }
}

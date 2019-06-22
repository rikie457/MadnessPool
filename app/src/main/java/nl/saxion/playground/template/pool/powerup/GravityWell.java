package nl.saxion.playground.template.pool.powerup;

import android.graphics.Bitmap;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.Game;
import nl.saxion.playground.template.pool.Utility;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public class GravityWell extends Powerup {
    private double x;
    private double y;
    private Game game;
    private WhiteBall whiteBall;
    private boolean visible = true;
    private static int counter = 0, nrTicks = 0;
    private double width, height;

    private static Bitmap bitmap1, bitmap2, bitmap3, current;

    public GravityWell(Game game, double x, double y, WhiteBall whiteBall) {
        super(game, x, y, whiteBall);
        this.game = game;
        this.whiteBall = whiteBall;

        this.width = game.getPowerupsize() * Utility.getRandIntInRange(4, 8);
        this.height = this.width;
    }

    @Override
    public void draw(GameView gameView) {
        if(!visible) return;

        if(bitmap1 == null) {
            bitmap1 = gameView.getBitmapFromResource(R.drawable.gravity_well_1);
            current = bitmap1;
        }
        if(bitmap2 == null) {
            bitmap2 = gameView.getBitmapFromResource(R.drawable.gravity_well_2);
        }
        if(bitmap3 == null) {
            bitmap3 = gameView.getBitmapFromResource(R.drawable.gravity_well_3);
        }

        // draw the gravity well
        gameView.drawBitmap(current,
                (float)vector2.getX(),
                (float)vector2.getY(),
                (float)width,
                (float)height
        );
    }

    private void getNextBitmap() {
        switch(counter) {
            case 0:
                current = bitmap1;
                break;
            case 1:
                current = bitmap2;
                break;
            case 2:
                current = bitmap3;
                break;
            case 3:
                current = bitmap2;
        }
        counter = (counter + 1) % 4;
    }

    @Override
    public void tick() {
        super.tick();
        if(nrTicks % 720 == 0) {
            getNextBitmap();
        }
        nrTicks++;
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public void createPowerUp() {
        GravityWell gravityWell =
        new GravityWell(
                game,
                (float) Utility.randomDoubleFromRange(100, game.getPlayWidth() - 100),
                (float) Utility.randomDoubleFromRange(100, game.getPlayHeight() - 100),
                this.whiteBall
        );

        game.getPowerups().add(gravityWell);
        game.addEntity(gravityWell);
    }
}

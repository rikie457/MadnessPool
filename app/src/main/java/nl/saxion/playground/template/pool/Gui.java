package nl.saxion.playground.template.pool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;
import nl.saxion.playground.template.pool.balls.Ball;

public class Gui extends Entity {
    private double x, y, width, height;
    private Bitmap bitmap;
    private ArrayList<Ball> scoredBalls;
    private Game game;
    private Context context;
    private Paint blackPaint, whitePaint;
    private ArrayList<Player> players;
    private int currentPlayer;

    public Gui(Game game, Context context, ArrayList<Ball> scoredBalls, double x, double y, double width, double height, ArrayList<Player> players) {
        this.game = game;
        this.scoredBalls = scoredBalls;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.context = context;
        this.players = players;
        this.currentPlayer = 1;

        this.blackPaint = new Paint();
        this.whitePaint = new Paint();
        this.blackPaint.setColor(Color.BLACK);
        this.whitePaint.setColor(Color.WHITE);
        this.whitePaint.setTextSize(40);
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void draw(GameView gv) {
        //THIS IS NOT HOW TO SCALE THE GUI I KNOW!
        gv.getCanvas().drawRect((float) this.x, (float) this.y, (float) this.width, (float) this.height, this.blackPaint);
        if (this.bitmap == null) {
            this.bitmap = gv.getBitmapFromResource(R.drawable.shelf);
        }

        if (this.currentPlayer == 1) {
            gv.getCanvas().drawText(">", (float) this.x + gv.getWidth() / 2 - 1020, (float) this.y + 90, this.whitePaint);
        } else {
            gv.getCanvas().drawText("<", (float) this.x + gv.getWidth() / 2 + 990, (float) this.y + 90, this.whitePaint);
        }

        gv.getCanvas().drawText(context.getResources().getString(R.string.player1), (float) this.x + gv.getWidth() / 2 - 1000, (float) this.y + 90, this.whitePaint);
        gv.drawBitmap(bitmap, (float) this.x + gv.getWidth() / 2 - 840, (float) this.y, 600, 160);
        gv.getCanvas().drawText(context.getResources().getString(R.string.player2), (float) this.x + gv.getWidth() / 2 + 850, (float) this.y + 90, this.whitePaint);
        gv.drawBitmap(bitmap, (float) this.x + gv.getWidth() / 2 + 200, (float) this.y, 600, 160);

        for (int i = 0; i < players.size(); i++) {
            if (this.players.get(i).getScoredBalls().size() != 0) {
                if (i == 1) {
                    for (int j = 0; j < this.scoredBalls.size(); j++) {
                        gv.drawBitmap(this.scoredBalls.get(j).getBitmap(), (float) this.x + gv.getWidth() / 2 - 1020 + this.scoredBalls.get(j).getId() * (float) this.scoredBalls.get(j).getWidth() + 10, (float) this.y + (float) this.scoredBalls.get(j).getRadius(), (float) this.scoredBalls.get(j).getWidth() - 500, (float) this.scoredBalls.get(j).getHeight());
                    }
                } else {
                    for (int j = 0; j < this.scoredBalls.size(); j++) {
                        gv.drawBitmap(this.scoredBalls.get(j).getBitmap(), (float) this.x + gv.getWidth() / 2 + 870 + this.scoredBalls.get(j).getId() * (float) this.scoredBalls.get(j).getWidth() + 10, (float) this.y + (float) this.scoredBalls.get(j).getRadius(), (float) this.scoredBalls.get(j).getWidth() - 500, (float) this.scoredBalls.get(j).getHeight());
                    }
                }
            }
        }

    }

}

package nl.saxion.playground.template.pool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

public class Gui extends Entity {
    private double x, y, width, height;
    private Bitmap bitmap;
    private ArrayList<Ball> scoredBalls, player1scoredballs, player2scoredballs;
    private Game game;
    private Context context;
    private Paint blackPaint, whitePaint;

    public Gui(Game game, Context context, ArrayList<Ball> scoredBalls, ArrayList<Ball> player1scoredballs, ArrayList<Ball> player2scoredballs, double x, double y, double width, double height) {
        this.game = game;
        this.context = context;
        this.scoredBalls = scoredBalls;
        this.player1scoredballs = player1scoredballs;
        this.player2scoredballs = player2scoredballs;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.blackPaint = new Paint();
        this.whitePaint = new Paint();
        this.blackPaint.setColor(Color.BLACK);
        this.whitePaint.setColor(Color.WHITE);
        this.whitePaint.setTextSize(40);
    }

    @Override
    public void draw(GameView gv) {
        //THIS IS NOT HOW TO SCALE THE GUI I KNOW!
        gv.getCanvas().drawRect((float) this.x, (float) this.y, (float) this.width, (float) this.height, this.blackPaint);
        if (this.bitmap == null) {
            this.bitmap = gv.getBitmapFromResource(R.drawable.shelf);
        }

        if (game.getCurrentplayer() == 1) {
            gv.getCanvas().drawText(">", (float) this.x + gv.getWidth() / 2 - 1020, (float) this.y + 90, this.whitePaint);
        } else {
            gv.getCanvas().drawText("<", (float) this.x + gv.getWidth() / 2 + 990, (float) this.y + 90, this.whitePaint);
        }

        gv.getCanvas().drawText(context.getResources().getString(R.string.player1), (float) this.x + gv.getWidth() / 2 - 1000, (float) this.y + 90, this.whitePaint);
        gv.drawBitmap(bitmap, (float) this.x + gv.getWidth() / 2 - 840, (float) this.y, 600, 160);
        gv.getCanvas().drawText(context.getResources().getString(R.string.player2), (float) this.x + gv.getWidth() / 2 + 850, (float) this.y + 90, this.whitePaint);
        gv.drawBitmap(bitmap, (float) this.x + gv.getWidth() / 2 + 200, (float) this.y, 600, 160);

        if (this.player1scoredballs.size() != 0) {
            if (game.getPlayer1type() != -1) {
                if (game.getPlayer1type() == 1) {
                    for (int j = 0; j < this.player1scoredballs.size(); j++) {
                        gv.drawBitmap(this.player1scoredballs.get(j).getBitmap(), (float) this.x + gv.getWidth() / 2 - 900 + this.player1scoredballs.get(j).getId() * (float) this.player1scoredballs.get(j).getWidth() + 10, (float) this.y + (float) this.player1scoredballs.get(j).getRadius(), (float) this.player1scoredballs.get(j).getWidth() - 500, (float) this.player1scoredballs.get(j).getHeight());
                    }
                } else {
                    for (int j = 0; j < this.player1scoredballs.size(); j++) {
                        gv.drawBitmap(this.player1scoredballs.get(j).getBitmap(), (float) this.x + gv.getWidth() / 2 - 1500 + this.player1scoredballs.get(j).getId() * (float) this.player1scoredballs.get(j).getWidth() + 10, (float) this.y + (float) this.player1scoredballs.get(j).getRadius(), (float) this.player1scoredballs.get(j).getWidth() - 500, (float) this.player1scoredballs.get(j).getHeight());
                    }
                }
            }
        }

        if (this.player2scoredballs.size() != 0) {
            if (game.getPlayer2type() != -1) {
                if (game.getPlayer2type() == 2) {
                    for (int j = 0; j < this.player2scoredballs.size(); j++) {
                        gv.drawBitmap(this.player2scoredballs.get(j).getBitmap(), (float) this.x + gv.getWidth() / 2 - 460 + this.player2scoredballs.get(j).getId() * (float) this.player2scoredballs.get(j).getWidth() + 10, (float) this.y + (float) this.player2scoredballs.get(j).getRadius(), (float) this.player2scoredballs.get(j).getWidth() - 500, (float) this.player2scoredballs.get(j).getHeight());
                    }
                } else {
                    for (int j = 0; j < this.player2scoredballs.size(); j++) {
                        gv.drawBitmap(this.player2scoredballs.get(j).getBitmap(), (float) this.x + gv.getWidth() / 2 + 180 + this.player2scoredballs.get(j).getId() * (float) this.player2scoredballs.get(j).getWidth() + 10, (float) this.y + (float) this.player2scoredballs.get(j).getRadius(), (float) this.player2scoredballs.get(j).getWidth() - 500, (float) this.player2scoredballs.get(j).getHeight());
                    }
                }
            }
        }
    }

}



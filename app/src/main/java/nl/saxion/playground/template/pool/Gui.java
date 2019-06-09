package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.graphics.Paint;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type Gui.
 */
public class Gui extends Entity {
    private double x, y, width, height;
    private Bitmap bitmap;
    private Game game;
    private Player player1;
    private Player player2;
    private Paint blackPaint, whitePaint;

    /**
     * Instantiates a new Gui.
     *
     * @param game       the game
     * @param player1    the player 1
     * @param player2    the player 2
     * @param x          the x
     * @param y          the y
     * @param width      the width
     * @param height     the height
     * @param whitePaint the white paint
     * @param blackPaint the black paint
     */
    public Gui(Game game, Player player1, Player player2, double x, double y, double width, double height, Paint whitePaint, Paint blackPaint) {
        this.game = game;
        this.player1 = player1;
        this.player2 = player2;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.blackPaint = blackPaint;
        this.whitePaint = whitePaint;
        this.whitePaint.setTextSize(20);

    }

    @Override
    public void draw(GameView gv) {
        //THIS IS NOT HOW TO SCALE THE GUI I KNOW!
        gv.getCanvas().drawRect((float) this.x, (float) this.y, (float) this.width, (float) this.height, this.blackPaint);
        if (this.bitmap == null) {
            this.bitmap = gv.getBitmapFromResource(R.drawable.shelf);
        }


        if (game.getCurrentplayer() == this.player1) {
            gv.getCanvas().drawText(">", (float) this.x + 10, (float) this.y + 50, this.whitePaint);
        } else {
            gv.getCanvas().drawText("<", (float) this.x + 980, (float) this.y + 50, this.whitePaint);
        }

        gv.getCanvas().drawText("Player 1", (float) this.x + 20, (float) this.y + 50, this.whitePaint);
        gv.drawBitmap(bitmap, (float) this.x + 90, (float) this.y + 25, 230, 50);
        gv.getCanvas().drawText("Player 2", (float) this.x + 910, (float) this.y + 50, this.whitePaint);
        gv.drawBitmap(bitmap, (float) this.x + 680, (float) this.y + 25, 230, 50);

        if (this.player1.getScoredballs().size() != 0) {
            if (this.player1.getBalltype() != -1) {
                if (this.player1.getBalltype() == 1) {
                    for (int j = 0; j < this.player1.getScoredballs().size(); j++) {
                        if (this.player1.getScoredballs().get(j).getBitmap() != null) {
                            gv.drawBitmap(this.player1.getScoredballs().get(j).getBitmap(), (float) this.x + 60 + this.player1.getScoredballs().get(j).getId() * (float) this.player1.getScoredballs().get(j).getWidth() + 10, (float) this.y + (float) this.player1.getScoredballs().get(j).getRadius() + 15, (float) this.player1.getScoredballs().get(j).getWidth(), (float) this.player1.getScoredballs().get(j).getHeight());
                        }
                    }
                } else {
                    for (int j = 0; j < this.player1.getScoredballs().size(); j++) {
                        if (this.player1.getScoredballs().get(j).getBitmap() != null) {
                            gv.drawBitmap(this.player1.getScoredballs().get(j).getBitmap(), (float) this.x + 650 + this.player1.getScoredballs().get(j).getId() * (float) this.player1.getScoredballs().get(j).getWidth() + 10, (float) this.y + (float) this.player1.getScoredballs().get(j).getRadius() + 15, (float) this.player1.getScoredballs().get(j).getWidth(), (float) this.player1.getScoredballs().get(j).getHeight());
                        }
                    }
                }
            }
        }

        if (this.player2.getScoredballs().size() != 0) {
            if (this.player2.getBalltype() != -1) {
                if (this.player2.getBalltype() == 2) {
                    for (int j = 0; j < this.player2.getScoredballs().size(); j++) {
                        if (this.player2.getScoredballs().get(j).getBitmap() != null) {
                            gv.drawBitmap(this.player2.getScoredballs().get(j).getBitmap(), (float) this.x + 410 + this.player2.getScoredballs().get(j).getId() * (float) this.player2.getScoredballs().get(j).getWidth() + 10, (float) this.y + (float) this.player2.getScoredballs().get(j).getRadius() + 15, (float) this.player2.getScoredballs().get(j).getWidth() - 500, (float) this.player2.getScoredballs().get(j).getHeight());
                        }
                    }
                } else {
                    for (int j = 0; j < this.player2.getScoredballs().size(); j++) {
                        if (this.player2.getScoredballs().get(j).getBitmap() != null) {
                            gv.drawBitmap(this.player2.getScoredballs().get(j).getBitmap(), (float) this.x - 180 + this.player2.getScoredballs().get(j).getId() * (float) this.player2.getScoredballs().get(j).getWidth() + 10, (float) this.y + (float) this.player2.getScoredballs().get(j).getRadius() + 15, (float) this.player2.getScoredballs().get(j).getWidth() - 500, (float) this.player2.getScoredballs().get(j).getHeight());
                        }
                    }
                }
            }
        }
    }

}



package nl.saxion.playground.template.pool;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.GameModel;
import nl.saxion.playground.template.pool.balls.Ball;
import nl.saxion.playground.template.pool.balls.SolidBall;
import nl.saxion.playground.template.pool.balls.StripedBall;
import nl.saxion.playground.template.pool.balls.WhiteBall;

public class Game extends GameModel {
    //Settings
    private float guiHeight = 150;
    private Context context;

    // ArrayLists
    ArrayList<Ball> balls = new ArrayList<>();
    ArrayList<Ball> sunkeBalls = new ArrayList<>();
    ArrayList<Hole> holes = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<Player>();

    public Game(Context context) {
        this.context = context;
    }

    public float getPlayHeight() {
        return this.getHeight() - this.guiHeight;
    }

    public float getPlayWidth() {
        return this.getWidth();
    }


    @Override
    public void start() {

        float left, top, right, bottom;
        left = 0;
        top = getPlayHeight();
        right = left + getPlayWidth();
        bottom = top + guiHeight;

        Player player1 = new Player();
        Player player2 = new Player();
        this.players.add(player1);
        this.players.add(player2);

        Gui gui = new Gui(this, this.context, sunkeBalls, left, top, right, bottom, players);
        ShootLine line = new ShootLine(false);
        Hole hole = new Hole(200, 200, 50);

        Ball ball1 = new SolidBall(this, this.balls, this.holes, this.sunkeBalls, this.getWidth() / 2, Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball1);
        Ball ball2 = new SolidBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball2);
        Ball ball3 = new SolidBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball3);
        Ball ball4 = new SolidBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball4);
        Ball ball5 = new SolidBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball5);
        Ball ball6 = new SolidBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball6);
        Ball ball7 = new SolidBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball7);
        Ball ball8 = new SolidBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball8);
        Ball ball9 = new StripedBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball9);
        Ball ball10 = new StripedBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball10);
        Ball ball11 = new StripedBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball11);
        Ball ball12 = new StripedBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball12);
        Ball ball13 = new StripedBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball13);
        Ball ball14 = new StripedBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball14);
        Ball ball15 = new StripedBall(this, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball15);
        Ball ball16 = new WhiteBall(this, gui, this.balls, this.holes, this.sunkeBalls, Utility.randomDoubleFromRange(0, this.getWidth()), Utility.randomDoubleFromRange(0, getHeight()), 75, 75, R.drawable.ball16, line);

        this.holes.add(hole);
        this.balls.add(ball1);
        this.balls.add(ball2);
        this.balls.add(ball3);
        this.balls.add(ball4);
        this.balls.add(ball5);
        this.balls.add(ball6);
        this.balls.add(ball7);
        this.balls.add(ball8);
        this.balls.add(ball9);
        this.balls.add(ball10);
        this.balls.add(ball11);
        this.balls.add(ball12);
        this.balls.add(ball13);
        this.balls.add(ball14);
        this.balls.add(ball15);
        this.balls.add(ball16);

        addEntity(gui);
        addEntity(hole);
        addEntity(ball1);
        addEntity(ball2);
        addEntity(ball3);
        addEntity(ball4);
        addEntity(ball5);
        addEntity(ball6);
        addEntity(ball7);
        addEntity(ball8);
        addEntity(ball9);
        addEntity(ball10);
        addEntity(ball11);
        addEntity(ball12);
        addEntity(ball13);
        addEntity(ball14);
        addEntity(ball15);
        addEntity(ball16);
        addEntity(line);

        Toast.makeText(context, R.string.startmessage, Toast.LENGTH_LONG).show();
    }
}

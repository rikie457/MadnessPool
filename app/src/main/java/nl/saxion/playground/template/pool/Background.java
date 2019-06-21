/*
 * Copyright (c) 2019. Tycho Engberink, Bryan Blekkink, Bram Baggerman, Rob van Heuven.
 *
 * Alle rechten behoren tot ons. De boven genoemde gebruikers. Het kopieren van deze software is verboden.
 */

package nl.saxion.playground.template.pool;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import nl.saxion.playground.template.R;
import nl.saxion.playground.template.lib.Entity;
import nl.saxion.playground.template.lib.GameView;

/**
 * The type Background.
 */
public class Background extends Entity {
    private static double a, r, g, b;
    private boolean altRed, altGreen, altBlue;
    private int[] minValueForColors;
    private int[] maxValueForColors;
    private int RED = 0, GREEN = 1, BLUE = 2;
    private double speedStep = 0.2;
    private Game game;

    private int currentColorCycling = 0;

    /**
     * Instantiates a new Background.
     *
     * @param game the game
     */
    public Background(Game game) {
        this.game = game;
        this.a = 255;
        this.r = 0;
        this.g = 128;
        this.b = 0;

        setRandomColorLimits();
    }

    @Override
    public int getLayer() {
        return 0;
    }

    public static void setColor(int r1, int g1, int b1) {
        r = r1;
        g = g1;
        b = b1;
    }

    @Override
    public void draw(GameView gameView) {
        gameView.getCanvas().drawARGB((int)this.a, (int)this.r, (int)this.g, (int)this.b);

        if(this.game.gameMode == Game.GameMode.MADDNESS) cycleColors();
    }

    private void cycleColors() {
        if(currentColorCycling == RED) {
            if(this.r >= maxValueForColors[RED] || altRed) {
                this.r -= speedStep;
                if (this.r <= minValueForColors[RED]) {
                    while(currentColorCycling == RED)
                        currentColorCycling = Utility.getRandIntInRange(0, 2);
                    altRed = false;
                    setRandomColorLimits();
                }
            } else {
                this.r += speedStep;
                if (this.r >= maxValueForColors[RED]) {
                    while(currentColorCycling == RED)
                    currentColorCycling = Utility.getRandIntInRange(0, 2);
                    altRed = true;
                    setRandomColorLimits();
                }
            }
        } else if(currentColorCycling == GREEN) {
            if(this.g >= maxValueForColors[GREEN] || altGreen) {
                this.g -= speedStep;
                if (this.g <= minValueForColors[GREEN]) {
                    while(currentColorCycling == GREEN)
                        currentColorCycling = Utility.getRandIntInRange(0, 2);
                    altGreen = false;
                    setRandomColorLimits();
                }
            } else {
                this.g += speedStep;
                if (this.g >= maxValueForColors[GREEN]) {
                    while(currentColorCycling == GREEN)
                        currentColorCycling = Utility.getRandIntInRange(0, 2);
                    altGreen = true;
                    setRandomColorLimits();
                }
            }
        } else if(currentColorCycling == BLUE) {
            if(this.b >= maxValueForColors[BLUE] || altBlue) {
                this.b -= speedStep;
                if (this.b <= minValueForColors[BLUE]) {
                    while(currentColorCycling == BLUE)
                        currentColorCycling = Utility.getRandIntInRange(0, 2);
                        altBlue = false;
                    setRandomColorLimits();
                }
            } else {
                this.b += speedStep;
                if (this.b >= maxValueForColors[BLUE]) {
                    while(currentColorCycling == BLUE)
                        currentColorCycling = Utility.getRandIntInRange(0, 2);
                    altBlue = true;
                    setRandomColorLimits();
                }
            }
        }
    }

    private void setRandomColorLimits() {
        if(minValueForColors == null) {
            minValueForColors = new int[3];
            minValueForColors[0] = 0;
            minValueForColors[1] = 64;
            minValueForColors[2] = 128;
        }
        if(maxValueForColors == null) {
            maxValueForColors = new int[3];
            maxValueForColors[0] = 64;
            maxValueForColors[1] = 128;
            maxValueForColors[2] = 192;
        }

        for(int i = 0; i < 10; i++) {
            swap(minValueForColors);
            swap(maxValueForColors);
        }
    }

    private void swap(int[] a) {
        int locA = Utility.getRandIntInRange(0, a.length - 1);
        int locB = Utility.getRandIntInRange(0, a.length - 1);
        int temp = a[locA];
        a[locA] = a[locB];
        a[locB] = temp;
    }
}

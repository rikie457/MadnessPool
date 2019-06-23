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

import java.util.ArrayList;

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
    private double speedStep = 1;
    private ArrayList<Integer> usedColors;


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

        usedColors = new ArrayList<>();
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
        if(this.game.gameMode == Game.GameMode.MADNESS) {
            cycleColors();
            gameView.getCanvas().drawARGB((int)this.a, (int)this.r, (int)this.g, (int)this.b);
        } else {
            gameView.getCanvas().drawARGB(255, 0, 128, 0);
        }
    }



    private void cycleColors() {
        if(currentColorCycling == RED) {
            if(this.r >= maxValueForColors[RED] || altRed) {
                this.r -= speedStep;
                if (this.r <= minValueForColors[RED]) {
                    while(hasBeenUsed(currentColorCycling))
                        currentColorCycling = Utility.getRandIntInRange(0, 2);
                    altRed = false;
                }
            } else {
                this.r += speedStep;
                if (this.r >= maxValueForColors[RED]) {
                    while(hasBeenUsed(currentColorCycling))
                        currentColorCycling = Utility.getRandIntInRange(0, 2);
                    altRed = true;
                }
            }
        } else if(currentColorCycling == GREEN) {
            if(this.g >= maxValueForColors[GREEN] || altGreen) {
                this.g -= speedStep;
                if (this.g <= minValueForColors[GREEN]) {
                    while(hasBeenUsed(currentColorCycling))
                        currentColorCycling = Utility.getRandIntInRange(0, 2);
                    altGreen = false;
                }
            } else {
                this.g += speedStep;
                if (this.g >= maxValueForColors[GREEN]) {
                    while(hasBeenUsed(currentColorCycling))
                        currentColorCycling = Utility.getRandIntInRange(0, 2);
                    altGreen = true;
                }
            }
        } else if(currentColorCycling == BLUE) {
            if(this.b >= maxValueForColors[BLUE] || altBlue) {
                this.b -= speedStep;
                if (this.b <= minValueForColors[BLUE]) {
                    while(hasBeenUsed(currentColorCycling))
                        currentColorCycling = Utility.getRandIntInRange(0, 2);
                    altBlue = false;
                }
            } else {
                this.b += speedStep;
                if (this.b >= maxValueForColors[BLUE]) {
                    while(hasBeenUsed(currentColorCycling))
                        currentColorCycling = Utility.getRandIntInRange(0, 2);
                    altBlue = true;

                }
            }
        }

        if(!tooLight()) {
            tooDark();
        }

        if(usedColors.size() == 3) {
            setRandomColorLimits();
            usedColors.clear();
        }
    }

    private void tooDark() {
        if(r + g + b <= 255) {
            altRed   = false;
            altGreen = false;
            altBlue  = false;
        }
    }

    private boolean tooLight() {
        if(r + g + b >= 512) {
            if(r > g && r > b) {
                altRed = true;
                altGreen = false;
                altBlue = false;
            } else if (g > r && g > b) {
                altGreen = true;
                altBlue = false;
                altRed = false;
            } else {
                altBlue = true;
                altRed = false;
                altGreen = false;
            }
            return true;
        }
        return false;
    }

    private boolean hasBeenUsed(int color) {
        for(int i = 0; i < usedColors.size(); i++) {
            if(usedColors.get(i) == color) {
                return true;
            }
        }
        usedColors.add(color);
        return false;
    }

    private void setRandomColorLimits() {
        if(minValueForColors == null) {
            minValueForColors = new int[3];
            minValueForColors[0] = 1;
            minValueForColors[1] = 1;
            minValueForColors[2] = 255;
        }
        if(maxValueForColors == null) {
            maxValueForColors = new int[3];
            maxValueForColors[0] = 1;
            maxValueForColors[1] = 255;
            maxValueForColors[2] = 255;
        }

        for(int i = 0; i < 10; i++) {
            swap(minValueForColors);
            swap(maxValueForColors);
        }

        altRed = false;
        altGreen = false;
        altBlue = false;
    }

    private void swap(int[] a) {
        int locA = Utility.getRandIntInRange(0, a.length - 1);
        int locB = Utility.getRandIntInRange(0, a.length - 1);
        int temp = a[locA];
        a[locA] = a[locB];
        a[locB] = temp;
    }
}
package se.lithekod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.LinkedList;
import java.util.Random;

public class Map {
    public static final int SKY_HEIGHT = 50;
    public static Ground[][] dirtMap;
    LinkedList<Worm> wormList;
    Random rand;

    public Map() {
        dirtMap = new Ground[Main.DESKTOP_WIDTH][Main.DESKTOP_HEIGHT - SKY_HEIGHT];
        this.rand = new Random();
        for (int i = 0; i < Main.DESKTOP_WIDTH; i++) {
            for (int j = 0; j < Main.DESKTOP_HEIGHT - SKY_HEIGHT; j++) {
                dirtMap[i][j] = Ground.DIRT;
            }
        }
        wormList = new LinkedList<Worm>();
        for (int i = 0; i < 10; i++) {
            wormList.add(new Worm(rand));
        }
    }

    public void setCleared(float x, float y) {

        int digRadius = 5;
        int startX = (x-digRadius < 0) ? 0 : (int) x-digRadius*2;
        int endX = (x+digRadius*2 > Main.DESKTOP_WIDTH) ? Main.DESKTOP_WIDTH : (int) x+digRadius*2;
        int startY = (y-digRadius < 0) ? 0 : (int) y-digRadius;
        int endY = (y+digRadius*2 > Main.DESKTOP_HEIGHT-SKY_HEIGHT) ? Main.DESKTOP_HEIGHT-SKY_HEIGHT : (int) y+digRadius*2;
        for(int i = startX; i < endX; i++) {
            for (int j = startY ; j < endY; j++) {
                dirtMap[i][j] = Ground.CLEARED;
                GameScreen.pixmap.setColor(new Color(GameScreen.diggedMap.getPixel(i/4 % 64, j/2 % 64)));
                GameScreen.pixmap.drawPixel(i, Main.DESKTOP_HEIGHT - Map.SKY_HEIGHT - j);
            }
        }
    }

    public static boolean isCleared(float x, float y) {

        if(!isOutOfBounds((double) x, (double) y)) {
            return dirtMap[(int) x][(int) y] == Ground.CLEARED;
        }
        return false;
    }

    public int getSkyHeight() {
        return SKY_HEIGHT;
    }

    public static boolean isOutOfBounds(double x, double y) {
        if( x < 0 || x > Main.DESKTOP_WIDTH-1 || y < 0 || y > Main.DESKTOP_HEIGHT-SKY_HEIGHT-1 ) {
            return true;
        }
        return false;
    }
}

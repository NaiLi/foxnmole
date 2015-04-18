package se.lithekod;

import com.badlogic.gdx.Gdx;

public class Map {
    public static final int SKY_HEIGHT = 50;
    public Ground[][] dirtMap;

    public Map() {
        dirtMap = new Ground[Main.DESKTOP_WIDTH][Main.DESKTOP_HEIGHT - SKY_HEIGHT];
        for (int i = 0; i < Main.DESKTOP_WIDTH; i++) {
            for (int j = 0; j < Main.DESKTOP_HEIGHT - SKY_HEIGHT; j++) {
                dirtMap[i][j] = Ground.DIRT;
            }
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
            }
        }
    }

    public boolean isCleared(float x, float y) {

        return  dirtMap[(int) x][(int) y] == Ground.CLEARED;
    }

    public int getSkyHeight() {
        return SKY_HEIGHT;
    }
}

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

        int startX = (x-15 < 0) ? 0 : (int) x-15;
        int endX = (x+30 > Main.DESKTOP_WIDTH) ? Main.DESKTOP_WIDTH : (int) x+30;
        int startY = (y-15 < 0) ? 0 : (int) y-15;
        int endY = (y+30 > Main.DESKTOP_HEIGHT-SKY_HEIGHT) ? Main.DESKTOP_HEIGHT-SKY_HEIGHT : (int) y+30;
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

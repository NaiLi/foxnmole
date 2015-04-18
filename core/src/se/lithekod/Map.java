package se.lithekod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

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

    public void setCleared(float x, float y) { // TODO check bounds
        for(int i = (int) x-15; i < x+30; i++) {
            for (int j = (int) y-15; j < y+30; j++) {
                dirtMap[i][j] = Ground.CLEARED;
                Main.pixmap.setColor(new Color(Main.diggedMap.getPixel(i % 64, j % 64)));
                Main.pixmap.drawPixel(i, Main.DESKTOP_HEIGHT - Map.SKY_HEIGHT - j);
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

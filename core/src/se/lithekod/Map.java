package se.lithekod;

import com.badlogic.gdx.Gdx;

/**
 * Created by henning on 15-04-17.
 */
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
}

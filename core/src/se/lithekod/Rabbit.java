package se.lithekod;

import com.badlogic.gdx.math.Vector2;

public class Rabbit {
    private float angle = 0;
    private Vector2 pos = new Vector2(Main.DESKTOP_WIDTH-100, Main.DESKTOP_HEIGHT-Map.SKY_HEIGHT-10);
    private boolean onGround = true;

    public void update() {

        // Check if there is a hole below
        if (Map.isCleared ( pos.x, pos.y-1 )) {
            pos.y = pos.y - 1;
            onGround = false;
        } else if (Map.isCleared ( pos.x-1, pos.y )) {
            pos.x = pos.x - 1;
        } else if (onGround) {
            pos.x = pos.x - 1;
        }

        // Else move forward

    }

    public Vector2 getPos() {
        return pos;
    }
}

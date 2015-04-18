package se.lithekod;

import com.badlogic.gdx.math.Vector2;

public class Rabbit {
    private float angle = 0;
    private Vector2 pos = new Vector2(Main.DESKTOP_WIDTH - 100, Main.DESKTOP_HEIGHT - Map.SKY_HEIGHT - 10);
    private boolean onGround = true;
    private int direction;


    public Rabbit(int direction) {
        this.direction = direction;
        if (direction > 0) { // walk right
            pos.x = 0;
        } else {
            pos.x = Main.DESKTOP_WIDTH - 100;
        }
        pos.y = Main.DESKTOP_HEIGHT - Map.SKY_HEIGHT - 10;
    }

    public boolean update() {

        if (Map.isCleared(pos.x, pos.y - 1)) {
            pos.y = pos.y - 1;
            onGround = false;
        } else if (Map.isCleared(pos.x + direction, pos.y)) {
            pos.x = pos.x + direction;
        } else if (onGround) {
            pos.x = pos.x + direction;
        }

        if(Map.isOutOfBounds(pos.x, pos.y)) {
            return false;
        }
        return true;
    }

    public Vector2 getPos() {
        return pos;
    }
}

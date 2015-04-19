package se.lithekod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Rabbit {
    private float angle = 0;
    private Vector2 pos = new Vector2(Main.DESKTOP_WIDTH - 100, Main.DESKTOP_HEIGHT - Map.SKY_HEIGHT - 10);
    private boolean onGround = true;
    private int direction;
    private int speed = 50;


    public Rabbit(int direction, int speed) {
        this.direction = direction;
        this.speed = speed;
        if (direction > 0) { // walk right
            pos.x = 0;
        } else {
            pos.x = Main.DESKTOP_WIDTH - 100;
        }
        pos.y = Main.DESKTOP_HEIGHT - Map.SKY_HEIGHT - 10;
    }

    public boolean update() {

        double time = Gdx.graphics.getDeltaTime();
        int acceptance = (int) pos.y/100;

        if (onGround) {
            pos.x = pos.x + direction*speed * (float) time;
        }

        if (GameScreen.map.isWalkable(pos.x, pos.y - 2)) {
            pos.y = pos.y - speed * (float) time;
            onGround = false;
        } else if (GameScreen.map.isWalkable(pos.x + direction*acceptance, pos.y)) {
            pos.x = pos.x + direction*speed * (float) time;
        } else if (GameScreen.map.isWalkable(pos.x - direction*acceptance, pos.y)) {
            pos.x = pos.x - direction*speed * (float) time;
        } else if (GameScreen.map.isWalkable(pos.x + direction*acceptance, pos.y - Math.abs(direction)*acceptance)) {
            pos.x = pos.x + direction*speed * (float) time;
            pos.y = pos.y - Math.abs(direction*speed) * (float) time;
        } else if (GameScreen.map.isWalkable(pos.x - direction*acceptance, pos.y - Math.abs(direction)*acceptance)) {
            pos.x = pos.x - direction*speed * (float) time;
            pos.y = pos.y - Math.abs(direction*speed) * (float) time;
        } else if (GameScreen.map.isWalkable(pos.x + direction*acceptance, pos.y)) {
            pos.x = pos.x + direction*speed * (float) time;
        } else if (GameScreen.map.isWalkable(pos.x - direction*acceptance, pos.y)) {
            pos.x = pos.x - direction*speed * (float) time;
        }

        if(Map.isOutOfBounds(pos.x, pos.y)) {
            return false;
        }

        GameScreen.map.setRabbit(pos.x, pos.y);

        return true;
    }

    public Vector2 getPos() {
        return pos;
    }
}

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

        float oldX = pos.x;
        float oldY = pos.y;

        double time = Gdx.graphics.getDeltaTime();
        int acceptance = (int) pos.y/100;

        if (onGround) {
            pos.x = pos.x + direction*speed * (float) time;
        }

        if (GameScreen.map.isWalkable(pos.x, pos.y - acceptance)) { // Walk down
            pos.y = pos.y - speed * (float) time;
            onGround = false;
        }
        else if (GameScreen.map.isWalkable(pos.x + acceptance, pos.y)) { // To the side
            direction = 1;
            pos.x = pos.x + direction*speed* (float) time;
        } else if (GameScreen.map.isWalkable(pos.x - acceptance, pos.y)) { // To the other side
            direction = -1;
            pos.x = pos.x + direction*speed* (float) time;
        } else if (GameScreen.map.isWalkable(pos.x, pos.y + acceptance)) { // Walk down
            pos.y = pos.y + speed * (float) time*2;
        } else {
            float x = pos.x + (float) Math.random()*20;
            float y = pos.y + (float) Math.random()*20;
            if(GameScreen.map.isWalkable(x,y)) {
                pos.x = x;
                pos.y = y;
            }
        }

        if(Map.isOutOfBounds(pos.x, pos.y)) {
            return false;
        }

        if(!onGround) {
            GameScreen.map.removeRabbit(oldX, oldY);
        }
        GameScreen.map.setRabbit(pos.x, pos.y);

        return true;
    }

    public Vector2 getPos() {
        return pos;
    }

    public int getDirection() {
        return direction;
    }
}

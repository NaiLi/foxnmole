package se.lithekod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by henning on 15-04-18.
 */
public class Worm {
    Vector2 pos;
    float angle;
    Random rand;
    float speed;
    float crazyness;
    public static final float FALLING_SPEED = 78;

    public Worm(Random rand) {
        this.rand = rand;
        this.pos = new Vector2(Math.abs(rand.nextInt()) % Main.DESKTOP_WIDTH,
                Math.abs(rand.nextInt()) % Main.DESKTOP_HEIGHT);
        this.angle = rand.nextFloat() % (2 * (float) Math.PI);
        this.speed = Math.abs(rand.nextFloat()) % 12 + 12;
        this.crazyness = (float) Math.sqrt(rand.nextFloat() % 2);
    }

    public boolean update() {
        double time = Gdx.graphics.getDeltaTime();
        Vector2 tmp;
        if (Map.isCleared(pos.x, pos.y)) {
            tmp = new Vector2(this.pos.x, this.pos.y - FALLING_SPEED * (float) time);
        }
        else {
            tmp = new Vector2(this.pos.x + (float) (Math.cos(angle) * speed * time),
                    this.pos.y + (float) (Math.sin(angle) * speed * time));
            if (Map.isCleared(tmp.x, tmp.y)) tmp = this.pos;
        }
        if (Map.isOutOfBounds(tmp.x, tmp.y)) return true;
        this.pos = tmp;
        angle += (rand.nextFloat() % crazyness - crazyness/2) * time;
        return false;
    }
}

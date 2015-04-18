package se.lithekod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by henning on 15-04-18.
 */
public class Worm {
    Vector2 pos;
    WormState state;
    float angle;
    Random rand;
    float speed;
    float crazyness;
    public static final float FALLING_SPEED = 2;

    public Worm(Random rand) {
        this.rand = rand;
        this.pos = new Vector2(rand.nextInt() % Main.DESKTOP_WIDTH, rand.nextInt() % Main.DESKTOP_HEIGHT);
        this.state = WormState.IDLE;
        this.angle = rand.nextFloat() % (2 * (float) Math.PI);
        this.speed = rand.nextFloat() % 2 + 2;
        this.crazyness = (float) Math.sqrt(rand.nextFloat() % 2);
    }

    public boolean update() {
        double time = Gdx.graphics.getDeltaTime();
        switch (state) {
            case IDLE:
                if (rand.nextInt() % 10 == 0) {
                    this.state = WormState.DIGGING;
                }
                break;
            case DIGGING:
                Vector2 tmp;
                if (Map.isCleared(pos.x, pos.y)) {
                    this.angle = (float) Math.PI/2;
                    tmp = new Vector2(this.pos.x - FALLING_SPEED * (float) time, this.pos.y);
                }
                else {
                    tmp = new Vector2(this.pos.x + (float) Math.cos(angle) * speed,
                            this.pos.y + (float) Math.sin(angle) * speed);
                    if (Map.isCleared(tmp.x, tmp.y)) tmp = this.pos;
                }
                if (!Map.isOutOfBounds(tmp.x, tmp.y)) return true;
                angle += rand.nextFloat() % crazyness - crazyness/2;
                if (rand.nextInt() % 20 == 0) {
                    this.state = WormState.IDLE;
                }
                break;
            default:
                break;
        }
        return false;
    }

    public enum WormState {
        IDLE, DIGGING, STUNNED, FALLING
    }
}

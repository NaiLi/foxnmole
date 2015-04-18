package se.lithekod;

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

    public Worm(Random rand) {
        this.rand = rand;
        this.pos = new Vector2(rand.nextFloat() % Main.DESKTOP_WIDTH, rand.nextFloat() % Main.DESKTOP_HEIGHT);
        this.state = WormState.IDLE;
        this.angle = rand.nextFloat() % (2 * (float) Math.PI);
        this.speed = rand.nextFloat() % 2 + 2;
    }

    public void update() {
        switch (state) {
            case IDLE:
                if (rand.nextInt() % 10 == 0) {
                    this.state = WormState.DIGGING;
                }
                break;
            case DIGGING:
                if (Map.isCleared(pos.x, pos.y)) {
                    this.state = WormState.FALLING;
                }
                Vector2 tmp = new Vector2((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);

                this.pos.add((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
                if (rand.nextInt() % 20 == 0) {
                    this.state = WormState.IDLE;
                }
        }
    }

    public enum WormState {
        IDLE, DIGGING, STUNNED, FALLING
    }
}

package se.lithekod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by henning on 15-04-17.
 */
public class Player {

    private Vector2 pos = new Vector2(250, Main.DESKTOP_HEIGHT - Map.SKY_HEIGHT);
    private PlayerState state = PlayerState.IDLE;
    private float angle = 0;
    public static final float rotationSpeed = 4;
    public static float crawlingSpeed = 100;
    public static float diggingSpeed = 50;
    private static final int FAST = 5;
    private static final int SLOW = 2;

    private boolean rightPressed = false;
    private boolean leftPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean digging = false;
    public int digCounter = 50;
    public float digTimer = 0f;
    public int energy = 50000;
    public static int wormCounter = 0;

    public Player() {

    }

    public void update() {

        float deltaTime = Gdx.graphics.getDeltaTime();
        if (digCounter < 10) {
            if (digTimer > 0.2f) {
                digCounter++;
                digTimer = 0f;
            } else {
                digTimer += deltaTime;
            }
        }
        if  (rightPressed && !leftPressed){
            angle -= (deltaTime * rotationSpeed) % (2 * Math.PI);
        }
        else if (!rightPressed && leftPressed){
            angle += (deltaTime * rotationSpeed) % (2 * Math.PI);
        }

        if (angle > 2 * Math.PI){
            angle = 0;
        }
        else if (angle < 0){
            angle =  2 * (float) Math.PI;
        }

        if (upPressed) {
/*
            int steps = (int) GameScreen.playerSprite.getWidth()/2;

            int x = (int) (pos.x + ((Math.cos(angle) * pos.x)/Math.abs(Math.cos(angle) * pos.x)) * steps);
            int y = (int) (pos.y + ((Math.cos(angle) * pos.y)/Math.abs(Math.cos(angle) * pos.y)) * steps);

            if ( !Map.isOutOfBounds (x, y)) {

                if (Map.isCleared((int) (pos.x + ((Math.cos(angle) * pos.x)/Math.abs(Math.cos(angle) * pos.x)) * steps), (int) (pos.y + ((Math.cos(angle) * pos.y)/Math.abs(Math.cos(angle) * pos.y)) * steps))) {
                    state = PlayerState.MOVING;
                    crawlingSpeed = FAST;

                } else {
                    state = PlayerState.DIGGING;
                    crawlingSpeed = SLOW;
                }
            }

            pos.x += Math.cos(angle) * crawlingSpeed;
            pos.y += Math.sin(angle) * crawlingSpeed;
            */
            Vector2 tmp = new Vector2(pos);
            tmp.add((float) Math.cos(angle) * (digging ? diggingSpeed: crawlingSpeed) * deltaTime,
                    (float) Math.sin(angle) * (digging ? diggingSpeed: crawlingSpeed) * deltaTime);
            if (!Map.isOutOfBounds(tmp.x, tmp.y)) {
                if (Map.setCleared(tmp.x, tmp.y)){
                    this.pos = tmp;
                }
            }
        }
    }

    public void setRightPressed(boolean rightPressed) {

        this.rightPressed = rightPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setPos(float x, float y) {
        pos.x = x;
        pos.y = y;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setDigging(boolean digging) {
        this.digging = digging;
    }

    public boolean isDigging() {
        return digging;
    }

    public float getRotation() {
        return (float) Math.toDegrees(angle);
    }
}

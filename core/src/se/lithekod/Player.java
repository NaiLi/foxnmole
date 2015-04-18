package se.lithekod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by henning on 15-04-17.
 */
public class Player {

    private Vector2 pos = new Vector2(50, 50);
    private PlayerState state = PlayerState.IDLE;
    private float angle = 0;
    public static final float rotationSpeed = 4;
    public static final float crawlingSpeed = 5;

    private boolean rightPressed = false;
    private boolean leftPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    public Player() {

    }

    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
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
            pos.x += Math.cos(angle) * crawlingSpeed;
            pos.y += Math.sin(angle) * crawlingSpeed;
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

    public Vector2 getPos() {
        return pos;
    }

    public float getRotation() {
        return (float) Math.toDegrees(angle);
    }
}

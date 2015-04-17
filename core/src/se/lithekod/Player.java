package se.lithekod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by henning on 15-04-17.
 */
public class Player {
<<<<<<< HEAD

    private Vector2 pos = new Vector2(50, 50);
    private PlayerState state = PlayerState.IDEL;
    private float angle = 0;
    public static final float rotationSpeed = 1;

    private boolean rightPressed = false;
    private boolean leftPressed = false;

    public Player() {

    }

    public void update(){
        float deltaTime = Gdx.graphics.getDeltaTime();
        if  (rightPressed && !leftPressed){
            angle += (deltaTime * rotationSpeed) % (2 * Math.PI);
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
    }

    public void setRightPressed(boolean rightPressed) {

        this.rightPressed = rightPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public Vector2 getPos() {
        return pos;
    }
}

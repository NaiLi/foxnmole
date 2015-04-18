package se.lithekod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by henning on 15-04-18.
 */
public class GameScreen implements Screen {
    SpriteBatch batch;
    Texture playerImg;
    public static Map map;
    Player player;
    public static Sprite playerSprite;
    Texture rabbitImg;
    ArrayList<Rabbit> rabbits;
    public static Pixmap pixmap;
    public static Pixmap diggedMap;
    ShapeRenderer shapeRenderer;
    private int count = 0;
    Animation worm;
    public static final float WORM_SPEED = 0.07f;

    @Override
    public void show() {
        InputHandler inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);

        rabbits = new ArrayList<Rabbit>();
        batch = new SpriteBatch();
        playerImg = new Texture("mole_original.png");
        rabbitImg = new Texture("rabbit_sheet_single.png");
        this.map = new Map();
        this.player = new Player();
        playerSprite = new Sprite(playerImg);
        this.rabbits.add(new Rabbit(1));
        this.shapeRenderer = new ShapeRenderer();
        makeAnimation();
        pixmap = new Pixmap(Main.DESKTOP_WIDTH, Main.DESKTOP_HEIGHT - Map.SKY_HEIGHT, Pixmap.Format.RGBA8888);
        pixmap.setColor(.1f, .7f, .99f, 1);
        pixmap.fill();
        Pixmap soilMap = new Pixmap(Gdx.files.internal("Soil.png"));
        diggedMap = new Pixmap(Gdx.files.internal("Soil_digged.png"));
        for (int i = 0; i < Main.DESKTOP_WIDTH; i++ ) {
            for (int j = 0; j < Main.DESKTOP_HEIGHT - Map.SKY_HEIGHT; j++) {
                pixmap.drawPixel(i, j, soilMap.getPixel(i/4 % soilMap.getWidth(), j/4 %soilMap.getHeight()));
            }
        }
        Pixmap grassMap = new Pixmap(Gdx.files.internal("Grass.png"));
        for (int x = 0; x < Main.DESKTOP_WIDTH; x += grassMap.getWidth()) {
            pixmap.drawPixmap(grassMap, x, 0);
        }
    }

    @Override
    public void render(float delta) {
        player.update();

		updateRabbits();

		Gdx.gl.glClearColor(.1f, .7f, .99f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Check so badger dont go outside of dirt
		float badgerPositionX = player.getPos().x;
		badgerPositionX = (badgerPositionX < playerSprite.getWidth() / 2) ? playerSprite.getWidth() / 2 : badgerPositionX;
		badgerPositionX = (badgerPositionX > Main.DESKTOP_WIDTH - playerSprite.getWidth() / 2) ? (float) Main.DESKTOP_WIDTH - playerSprite.getWidth() / 2: badgerPositionX;
		float badgerPositionY = player.getPos().y;
		badgerPositionY = (badgerPositionY < playerSprite.getHeight() / 2) ? playerSprite.getHeight() / 2 : badgerPositionY;
		badgerPositionY = (badgerPositionY > Main.DESKTOP_HEIGHT - playerSprite.getHeight() / 2 - map.getSkyHeight()) ? (float) Main.DESKTOP_HEIGHT - playerSprite.getHeight() / 2 - map.getSkyHeight(): badgerPositionY;
		player.setPos(badgerPositionX, badgerPositionY);

		playerSprite.setPosition(badgerPositionX - playerSprite.getWidth() / 2, badgerPositionY - playerSprite.getHeight() / 2);
		playerSprite.setRotation(player.getRotation());
		playerSprite.setRotation(player.getRotation());

		if(count%100 == 0) {
			int dir = (count%3 == 0) ? 1 : -1;
			Rabbit r = new Rabbit(dir);
			rabbits.add(r);
		}

		// Set map is cleared
		map.setCleared(badgerPositionX, badgerPositionY);

		if (player.getRotation() > 90 && player.getRotation() < 270)
			playerSprite.setFlip(false, true);
		else playerSprite.setFlip(false, false);
		Texture ground = new Texture(pixmap);
		batch.begin();
		batch.draw(ground, 0, 0);
        playerSprite.draw(batch);
		for(int i = 0; i < rabbits.size(); i++) {
			batch.draw(rabbitImg, rabbits.get(i).getPos().x, rabbits.get(i).getPos().y);
		}
		updateWorms();
		batch.end();

		count++;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void updateRabbits() {

        ArrayList<Rabbit> tmp = new ArrayList<Rabbit>();
        for (Rabbit r : rabbits){
            if(r.update()) {
                tmp.add(r);
            }
        }
        rabbits = tmp;
    }

    private void makeAnimation() {
        Texture wormSheet = new Texture(Gdx.files.internal("Worm_Sprite.png"));
        TextureRegion[][] tmp = TextureRegion.split(wormSheet, wormSheet.getWidth() / 4, wormSheet.getHeight());
        this.worm = new Animation(WORM_SPEED, tmp[0]);
    }

    public void updateWorms() {
        shapeRenderer.setColor(Color.GREEN);
        LinkedList<Worm> tmp = new LinkedList<Worm>(map.wormList);
        for (ListIterator<Worm> i = tmp.listIterator() ; i.hasNext();){
            Worm w = i.next();
            if (w.update()) map.wormList.remove(w);
            else {
                if (w.pos.dst(player.getPos()) < 20)
                    map.wormList.remove(w);
                else {
                    TextureRegion wormFrame = worm.getKeyFrame(w.stateTime, true);
                    batch.draw(wormFrame, w.pos.x - wormFrame.getRegionWidth()/2,
                            w.pos.y - wormFrame.getRegionWidth()/2,
                            wormFrame.getRegionWidth()/2,
                            wormFrame.getRegionHeight()/2,
                            wormFrame.getRegionWidth(),
                            wormFrame.getRegionHeight(),
                            .25f, .25f,
                            (float) Math.toDegrees(w.angle));
                }
            }
        }

        for (int i = 0; i < 10 - map.wormList.size(); i++) {
            map.wormList.add(new Worm(map.rand));
        }
    }

    public class InputHandler implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode){
                case Input.Keys.UP:
                    player.setUpPressed(true);
                    return true;
                case Input.Keys.DOWN:
                    player.setDownPressed(true);
                    return true;
                case Input.Keys.LEFT:
                    player.setLeftPressed(true);
                    return true;
                case Input.Keys.RIGHT:
                    player.setRightPressed(true);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode){
                case Input.Keys.UP:
                    player.setUpPressed(false);
                    return true;
                case Input.Keys.DOWN:
                    player.setDownPressed(false);
                    return true;
                case Input.Keys.LEFT:
                    player.setLeftPressed(false);
                    return true;
                case Input.Keys.RIGHT:
                    player.setRightPressed(false);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
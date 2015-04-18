package se.lithekod;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class Main extends ApplicationAdapter {
    SpriteBatch batch;
    Texture playerImg;
    public static Map map;
    Player player;
    public static Sprite playerSprite;
	Texture rabbitImg;
	public static ArrayList<Rabbit> rabbits;
	public static int DESKTOP_HEIGHT;
	public static int DESKTOP_WIDTH;
	public static Pixmap pixmap;
	public static Pixmap diggedMap;
	ShapeRenderer shapeRenderer;
	private int count = 0;

	@Override
	public void create () {
		rabbits = new ArrayList<Rabbit>();
		DESKTOP_HEIGHT = Gdx.graphics.getHeight();
		DESKTOP_WIDTH = Gdx.graphics.getWidth();
		batch = new SpriteBatch();
		playerImg = new Texture("mole_original.png");
		rabbitImg = new Texture("rabbit_sheet_single.png");
        this.map = new Map();
        this.player = new Player();
        playerSprite = new Sprite(playerImg);
		this.rabbits.add(new Rabbit(1));
		this.shapeRenderer = new ShapeRenderer();
		InputHandler inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);


		pixmap = new Pixmap(DESKTOP_WIDTH, DESKTOP_HEIGHT - Map.SKY_HEIGHT, Pixmap.Format.RGBA8888);
		pixmap.setColor(.1f, .7f, .99f, 1);
		pixmap.fill();
		Pixmap soilMap = new Pixmap(Gdx.files.internal("Soil.png"));
		diggedMap = new Pixmap(Gdx.files.internal("Soil_digged.png"));
		for (int i = 0; i < DESKTOP_WIDTH; i += soilMap.getWidth()) {
			for (int j = 0; j < DESKTOP_HEIGHT - Map.SKY_HEIGHT; j += soilMap.getHeight()) {
				pixmap.drawPixmap(soilMap, i,j);
			}
		}
		pixmap.setColor(0, 0, 0, 0);
	}

	@Override
	public void render () {
        player.update();
		
		updateRabbits();

		Gdx.gl.glClearColor(.1f, .7f, .99f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		// Check so badger dont go outside of dirt
		float badgerPositionX = player.getPos().x;
		badgerPositionX = (badgerPositionX < playerSprite.getWidth() / 2) ? playerSprite.getWidth() / 2 : badgerPositionX;
		badgerPositionX = (badgerPositionX > DESKTOP_WIDTH - playerSprite.getWidth() / 2) ? (float) DESKTOP_WIDTH - playerSprite.getWidth() / 2: badgerPositionX;
		float badgerPositionY = player.getPos().y;
		badgerPositionY = (badgerPositionY < playerSprite.getHeight() / 2) ? playerSprite.getHeight() / 2 : badgerPositionY;
		badgerPositionY = (badgerPositionY > DESKTOP_HEIGHT - playerSprite.getHeight() / 2 - map.getSkyHeight()) ? (float) DESKTOP_HEIGHT - playerSprite.getHeight() / 2 - map.getSkyHeight(): badgerPositionY;
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
		batch.draw(ground, 0, 0);
        playerSprite.draw(batch);
		for(int i = 0; i < rabbits.size(); i++) {
			batch.draw(rabbitImg, rabbits.get(i).getPos().x, rabbits.get(i).getPos().y);
		}
		batch.end();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		updateWorms();
		shapeRenderer.end();

		count++;
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
				else
                    shapeRenderer.circle(w.pos.x - 3, w.pos.y - 3, 6);

			}
		}

		for (int i = 0; i < 10 - map.wormList.size(); i++) {
			map.wormList.add(new Worm(map.rand));
		}
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

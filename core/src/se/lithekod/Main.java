package se.lithekod;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerImg;
    Map map;
    Player player;
    Sprite playerSprite;
	Texture soil;
	Texture digged;
	public static int DESKTOP_HEIGHT;
	public static int DESKTOP_WIDTH;
	Pixmap pixmap;
	
	@Override
	public void create () {
		DESKTOP_HEIGHT = Gdx.graphics.getHeight();
		DESKTOP_WIDTH = Gdx.graphics.getWidth();
		batch = new SpriteBatch();
		playerImg = new Texture("mole_original.png");
		soil = new Texture("Soil.png");
		digged = new Texture("Soil_digged.png");
		soil.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		digged.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.map = new Map();
        this.player = new Player();
        playerSprite = new Sprite(playerImg);
		InputHandler inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);


		pixmap = new Pixmap(DESKTOP_WIDTH, DESKTOP_HEIGHT - Map.SKY_HEIGHT, Pixmap.Format.RGBA8888);
		pixmap.setColor(.1f, .7f, .99f, 1);
		pixmap.fill();
		Pixmap soilMap = new Pixmap(Gdx.files.internal("Soil.png"));
		for (int i = 0; i < DESKTOP_WIDTH; i += soilMap.getWidth()) {
			for (int j = 0; j < DESKTOP_HEIGHT - Map.SKY_HEIGHT; j += soilMap.getHeight()) {
				pixmap.drawPixmap(soilMap, i,j);
				}
			}
		for (int i = 0; i < DESKTOP_WIDTH; i++) {
			for (int j = 0; j < 50; j++) {
//				pixmap.drawPixel(i, j, );

			}

		}
		}

	}

	@Override
	public void render () {
        player.update();

		Gdx.gl.glClearColor(.1f, .7f, .99f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        playerSprite.setPosition(player.getPos().x - playerSprite.getWidth() / 2, player.getPos().y - playerSprite.getHeight() / 2);
        playerSprite.setRotation(player.getRotation());
		if (player.getRotation() > 90 && player.getRotation() < 270)
			playerSprite.setFlip(false, true);
		else playerSprite.setFlip(false, false);
		Texture tex = new Texture(pixmap);
		batch.draw(tex, 0, 0);
//		batch.draw(soil, 0, 0, 64, 64);
        //playerSprite.rotate(.5f);
//        playerSprite.getRotation()
        playerSprite.draw(batch);
		batch.end();
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

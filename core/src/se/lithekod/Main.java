package se.lithekod;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
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

		batch.draw(soil, 0, 0, 1200, 550);
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

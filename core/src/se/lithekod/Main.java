package se.lithekod;

import com.badlogic.gdx.*;

public class Main extends Game {
	public static int DESKTOP_HEIGHT;
	public static int DESKTOP_WIDTH;

	@Override
	public void create() {
		DESKTOP_HEIGHT = Gdx.graphics.getHeight();
		DESKTOP_WIDTH = Gdx.graphics.getWidth();

		setScreen(new MenuScreen());
		//setScreen(new GameScreen());


	}
}

package se.lithekod;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerImg;
    Map map;
    Player player;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		playerImg = new Texture("badlogic.jpg");
        this.map = new Map();
        this.player = new Player();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(.1f, .7f, .99f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(playerImg, 0, 0);
		batch.end();
	}
}

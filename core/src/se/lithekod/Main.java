package se.lithekod;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerImg;
    Map map;
    Player player;
    Sprite playerSprite;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		playerImg = new Texture("mole_original.png");
        this.map = new Map();
        this.player = new Player();
        playerSprite = new Sprite(playerImg);

	}

	@Override
	public void render () {
        Affine2 affine = new Affine2();
		Gdx.gl.glClearColor(.1f, .7f, .99f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        playerSprite.setCenter(100, 100);
        playerSprite.rotate(.5f);
        playerSprite.draw(batch);
		batch.end();
	}
}

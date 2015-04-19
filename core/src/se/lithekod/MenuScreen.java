package se.lithekod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by henning on 15-04-19.
 */
public class MenuScreen implements Screen {
    Texture backdrop;
    private Image backdropImage;
    private Stage stage;
    @Override
    public void show() {
        backdrop = new Texture(Gdx.files.internal("startscreen_with_tass.png"));
        backdropImage = new Image(backdrop);
        stage = new Stage();
        stage.addActor(backdropImage);
    }

    @Override
    public void dispose() {
        backdrop.dispose();
        stage.dispose();

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }
}

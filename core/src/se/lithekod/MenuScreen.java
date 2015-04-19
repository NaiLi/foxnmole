package se.lithekod;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by henning on 15-04-19.
 */
public class MenuScreen implements Screen {
    Texture backdrop;
    private Image backdropImage;
    private Stage stage;
    private Table table;
    private Skin uiSkin;
    private Label title;
    private TextButton playButton;
    private TextButton exitButton;

    @Override
    public void show() {
        backdrop = new Texture(Gdx.files.internal("startscreen_with_tass.png"));
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        title = new Label("Fox'N'Mole", uiSkin);
        playButton = new TextButton("Play", uiSkin);
        exitButton = new TextButton("Exit", uiSkin);
        backdropImage = new Image(backdrop);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage = new Stage();
        stage.addActor(backdropImage);
        table = new Table();

        table.add(title).padBottom(40).row();
        table.add(playButton).padBottom(20).row();
        table.add(exitButton).padBottom(20).row();

        table.setFillParent(true);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        backdrop.dispose();
        stage.dispose();
        uiSkin.dispose();
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

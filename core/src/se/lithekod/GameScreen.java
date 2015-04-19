package se.lithekod;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen implements Screen {
    SpriteBatch batch;
    Texture playerImg;
    public static Map map;
    public static Player player;
    public static Sprite playerSprite;
    Texture rabbitImg;
    ArrayList<Rabbit> rabbits;
    public static Pixmap pixmap;
    public static Pixmap diggedMap;
    Texture ground;
    private int count = 0;
    Animation worm;
    public static final float WORM_SPEED = 0.07f;
    Sound slurp;
    InputHandler inputHandler;

    private Stage stage;
    private Skin uiSkin;
    private Table gameBar;
    private Label title;
    private Label energyLbl;
    private Label numOfRabbitsLbl;

    public GameScreen() {

        InputHandler inputHandler = new InputHandler();
        rabbits = new ArrayList<Rabbit>();
        batch = new SpriteBatch();
        playerImg = new Texture(Gdx.files.internal("mole_original.png"));
        this.map = new Map();
        this.player = new Player();
        this.slurp = Gdx.audio.newSound(Gdx.files.internal("slurp.mp3"));
        playerSprite = new Sprite(playerImg);
        this.rabbits.add(new Rabbit(1, 50));
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
        ground = new Texture(pixmap);
        this.inputHandler = new InputHandler();
        initGameBar();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputHandler);


    }

    @Override
    public void render(float delta) {

        if(rabbits.size() < 40) {

            ground.dispose();
            player.update();

            updateRabbits();

            Gdx.gl.glClearColor(.1f, .7f, .99f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // Check so badger doesn't go outside of dirt
            float badgerPositionX = player.getPos().x;
            badgerPositionX = (badgerPositionX < playerSprite.getWidth() / 2) ? playerSprite.getWidth() / 2 : badgerPositionX;
            badgerPositionX = (badgerPositionX > Main.DESKTOP_WIDTH - playerSprite.getWidth() / 2) ? (float) Main.DESKTOP_WIDTH - playerSprite.getWidth() / 2: badgerPositionX;
            float badgerPositionY = player.getPos().y;
            badgerPositionY = (badgerPositionY < playerSprite.getHeight() / 2) ? playerSprite.getHeight() / 2 : badgerPositionY;
            badgerPositionY = (badgerPositionY > Main.DESKTOP_HEIGHT - playerSprite.getHeight() / 2 - map.getSkyHeight()) ? (float) Main.DESKTOP_HEIGHT - playerSprite.getHeight() / 2 - map.getSkyHeight(): badgerPositionY;
            player.setPos(badgerPositionX, badgerPositionY);

            playerSprite.setPosition(badgerPositionX - playerSprite.getWidth() / 2, badgerPositionY - playerSprite.getHeight() / 2);
            playerSprite.setRotation(player.getRotation());

            if(count%120 == 0) {
                int dir = (count % 3 == 0) ? 1 : -1;
                int speed = 40 + (int) (Math.random() * 30);
                Rabbit r = new Rabbit(dir, speed);
                rabbits.add(r);
            }

            // Set map is cleared
            map.setCleared(badgerPositionX, badgerPositionY);

            if (player.getRotation() > 90 && player.getRotation() < 270) {
                playerSprite.setFlip(false, true);
            }
            else playerSprite.setFlip(false, false);
            if (ground != null) {
                ground.dispose();
            }
            ground = new Texture(pixmap);
            batch.begin();
            batch.draw(ground, 0, 0);
            playerSprite.draw(batch);

            for (Rabbit rabbit : rabbits) {
                String imgUrl = (rabbit.getDirection() == 1) ? "1" : "2";
                imgUrl = "rabbit_sheet_single-" + imgUrl + ".png";
                rabbitImg = new Texture(imgUrl);
                batch.draw(rabbitImg, rabbit.getPos().x, rabbit.getPos().y);
            }
            updateWorms();
            batch.end();
            updateGameBar();

            count++;
        } else {
            gameOver();
        }
    }

    private void initGameBar() {
        stage = new Stage();
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        energyLbl = new Label("Energy level: 0", uiSkin);
        numOfRabbitsLbl = new Label("Rabbit count: 0", uiSkin);
        gameBar = new Table();
        gameBar.add(energyLbl).padTop(0).row();
        gameBar.add(numOfRabbitsLbl).padBottom(0).row();
        gameBar.setFillParent(true);
        gameBar.align(Align.topLeft);
        stage.addActor(gameBar);
    }

    public void updateGameBar() {
        energyLbl.setText("Energy level: " + player.energy / 1000);
        numOfRabbitsLbl.setText("Rabbit count: " + rabbits.size());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        ((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        this.playerImg.dispose();
        this.rabbitImg.dispose();
        this.batch.dispose();
        pixmap.dispose();
        diggedMap.dispose();
        this.ground.dispose();
        this.slurp.dispose();
    }

    public void gameOver() {
        System.out.println("Game over...... :(");

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
        LinkedList<Worm> tmp = new LinkedList<Worm>(map.wormList);
        for (ListIterator<Worm> i = tmp.listIterator() ; i.hasNext();){
            Worm w = i.next();
            if (w.update()) map.wormList.remove(w);
            else {
                if (w.pos.dst(player.getPos()) < 20){
                    slurp.play();
                    player.energy += 100;
                    if (player.wormCounter++ > 20) {
                        player.wormCounter = 0;
                    }
                    map.wormList.remove(w);
                }
                else {
                    TextureRegion wormFrame = worm.getKeyFrame(w.stateTime, true);
                    batch.draw(wormFrame, w.pos.x - wormFrame.getRegionWidth() / 2,
                            w.pos.y - wormFrame.getRegionWidth() / 2,
                            wormFrame.getRegionWidth() / 2,
                            wormFrame.getRegionHeight() / 2,
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
                case Input.Keys.SPACE:
                    player.setDigging(true);
                    return true;
                case Input.Keys.ESCAPE:
                    pause();
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
                case Input.Keys.SPACE:
                    player.setDigging(false);
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

package ca.hiphiparray.amazingmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * The main menu screen.
 * @author Chloe Nguyen
 */
public class MainMenuScreen implements Screen {

 /**The AmazingMazeGame instance to be used. */
 private final AmazingMazeGame AMAZING;
 /** Stage that contains all of the main menu options. */
 private Stage menu;
 /** The Table container for the main menu buttons. */
 private Table table;
 
 /** Skin of menu */
 private Skin skin;

 /** Play button. */
 private TextButton playButton;
 /** Help button. */
 private TextButton helpButton;
 /** Quit button. */
 private TextButton quitButton;

 /** Title of menu */
 private Label menuTitle;

 /** Style of buttons */
 private TextButtonStyle textButtonStyle; 
 /** Style of title */
 private LabelStyle textStyle;


 public MainMenuScreen(final AmazingMazeGame AMAZING) {
  this.AMAZING = AMAZING;
  menu = new Stage(new ScreenViewport(), this.AMAZING.batch);

  table = new Table();

  table.setFillParent(true);
  table.bottom();
  menu.addActor(table);

  skin = new Skin();
  textButtonStyle = new TextButtonStyle();
  textStyle= new LabelStyle(new BitmapFont(), Color.BLACK);

  menuTitle = new Label("Amazing Maze", textStyle);

  // Play
  playButton = new TextButton("Play", textButtonStyle);
  playButton.addListener(new ChangeListener() {
   @Override
   public void changed(ChangeEvent event, Actor actor) {
    if (playButton.isPressed()) {
     // INTRO OR GAME HERE
    }
   }
  });

  // Help
  helpButton = new TextButton("Help", skin.get(TextButtonStyle.class));
  helpButton.addListener(new ChangeListener() {
   @Override
   public void changed(ChangeEvent event, Actor actor) {
    if (helpButton.isPressed()) {
     //HELP HERE
    }
   }
  });

  // Quit 
  quitButton = new TextButton("Quit", skin);
  quitButton.addListener(new ChangeListener() {
   @Override
   public void changed(ChangeEvent event, Actor actor) {
    if (quitButton.isPressed()) {
     Gdx.app.exit();
    }
   }
  });
 }

 @Override
 public void show() {
  Gdx.input.setInputProcessor(menu);
  Gdx.input.setCursorCatched(false); 
 }

 @Override
 public void render(float delta) {

  Gdx.gl.glClearColor(1, 1, 1, 1);
  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 

  menu.act(Gdx.graphics.getDeltaTime());
  menu.draw();
 }

 @Override
 public void resize(int width, int height) {
 }

 private void layoutMenu (int width, int height) {
  table.clear();
  
  // Add title
  table.add(menuTitle).minHeight(height / 6).maxHeight(height / 4).prefHeight(height / 8).expand();
  table.row();

  // Add buttons.
  table.add(playButton).minSize(width / 4, height / 20).maxSize(width, height / 8).prefSize(width / 2, height / 10).padBottom(10);
  table.row();
  table.add(helpButton).minSize(width / 4, height / 20).maxSize(width, height / 8).prefSize(width / 2, height / 10).padBottom(10);
  table.row();
  table.add(quitButton).minSize(width / 4, height / 20).maxSize(width, height / 8).prefSize(width / 2, height / 10).padBottom(10);
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
  menu.dispose();
 }

}


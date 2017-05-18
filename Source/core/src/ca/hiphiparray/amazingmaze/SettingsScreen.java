package ca.hiphiparray.amazingmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * SettingsScreen allows users to adjust the settings for the game.
 *
 * @author Chloe Nguyen
 */
public class SettingsScreen implements Screen, InputProcessor {

	/** The {@link AmazingMazeGame} instance for this screen. */
	private final AmazingMazeGame game;
	/** The stage for the settings screen */
	private final Stage settings;

	/** Table for widgets and the layout */
	private Table table;

	/** Label for the settings screen header */
	private Label screenHeader;
	/** Label for the music slider */
	private Label musicSliderLabel;
	/** Label for the controls table */
	private Label controlsHeader;

	/** Slider for the music volume */
	private Slider musicSlider;

	/** Button to go back to menu */
	private TextButton backToMenuButton;

	/** Checkbox for whether game's in fullscreen or not */
	private CheckBox fullscreenCheckBox;

	/** Table for game controls */
	private Table controlsTable;

	/** Array of Labels for all possible game actions */
	private Label[] actions;

	/** Array of TextButtons for changeable game action buttons */
	private TextButton[] actionControls;

	/** To reset settings */
	private TextButton resetSettingsButton;

	/** Action currently being set. This is -1 when no actions are being set. */
	private int actionBeingSet = -1;

	/**
	 * For InputProcessors and allows for UI to process events first. Otherwise, regular InputProcessor take over.
	 */
	private InputMultiplexer multiplexer;

	/**
	 * Create a new SettingsScreen.
	 *
	 * @param game Instance of AmazingMazeGame to be used
	 */
	public SettingsScreen(final AmazingMazeGame game) {
		this.game = game;
		settings = new Stage(new ScreenViewport(), this.game.batch);
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(settings);
		multiplexer.addProcessor(this);
		table = new Table();
		table.top();
		table.setFillParent(true);
		controlsTable = new Table();

		settings.addActor(table);

		Skin skin = game.assets.skin;

		screenHeader = new Label("Settings", game.assets.skin);

		musicSlider = new Slider(0, 1, 0.1f, false, game.assets.skin);
		musicSlider.setValue(game.set.getMusicLevel());
		musicSlider.setAnimateDuration(0.25f);

		musicSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// SettingsScreen.this.game.music.setVolume(musicSlider.getValue());
			}
		});
		musicSliderLabel = new Label("Music Volume", game.assets.skin);
		fullscreenCheckBox = new CheckBox("Fullscreen", game.assets.skin);
		fullscreenCheckBox.setChecked(Gdx.graphics.isFullscreen());
		fullscreenCheckBox.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (fullscreenCheckBox.isChecked()) {
					Monitor currentMonitor = Gdx.graphics.getMonitor();
					DisplayMode currentDisplayMode = Gdx.graphics.getDisplayMode(currentMonitor);
					if (!Gdx.graphics.setFullscreenMode(currentDisplayMode)) {
						System.out.println("Failed to change to fullscreen mode.");
					} else {
						game.set.setFullscreen(true);
					}
				} else {
					if (!Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height)) {
						System.out.println("Failed to change to windowed mode.");
					} else {
						game.set.setFullscreen(false);
					}
				}
			}
		});

		controlsHeader = new Label("Controls", game.assets.skin);

		actions = new Label[] { new Label("Up", game.assets.skin), new Label("Right", game.assets.skin), new Label("Left", game.assets.skin), new Label("Down", game.assets.skin), };

		actionControls = new TextButton[] { new TextButton(Keys.toString(game.set.getUpButton()), skin), new TextButton(Keys.toString(game.set.getRightButton()), skin), new TextButton(Keys.toString(game.set.getLeftButton()), skin), new TextButton(Keys.toString(game.set.getDownButton()), skin), };

		actionControls[0].addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actionControls[0].isPressed()) {
					updateControls(0);
				}
			}

		});

		actionControls[1].addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actionControls[1].isPressed()) {
					updateControls(1);
				}
			}

		});

		actionControls[2].addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actionControls[2].isPressed()) {
					updateControls(2);
				}
			}

		});

		actionControls[3].addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actionControls[3].isPressed()) {
					updateControls(3);
				}
			}

		});

		// Reset settings button
		resetSettingsButton = new TextButton("Reset Settings", skin);
		resetSettingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (resetSettingsButton.isPressed()) {
					game.set.resetSettings();
					musicSlider.setValue(game.set.getMusicLevel());
					fullscreenCheckBox.setChecked(game.set.isFullscreen());
					resetActionControlsLabels();
				}
			}
		});

		backToMenuButton = new TextButton("Main Menu", skin);

	}

	/**
	 * Reset Labels for elements in actionControl array.
	 */
	protected void resetActionControlsLabels() {
		actionControls[0].setText(Keys.toString(game.set.getUpButton()));
		actionControls[1].setText(Keys.toString(game.set.getRightButton()));
		actionControls[2].setText(Keys.toString(game.set.getLeftButton()));
		actionControls[3].setText(Keys.toString(game.set.getDownButton()));
	}

	/**
	 * For updating key with specific action.
	 *
	 * @param Specifies which element of actionControls array
	 */
	protected void updateControls(int i) {
		resetActionControlsLabels();
		actionControls[i].setText("Press a key");
		actionBeingSet = i;
	}

	/**
	 * Layout for the settings.
	 *
	 * @param width The width of the screen.
	 * @param height The height of the screen.
	 */
	private void layoutSettings(int width, int height) {

		table.clear(); // Clear the table.

		table.add(screenHeader).expand().bottom().pad(10f);
		table.row();

		table.add(musicSliderLabel);
		table.row();

		table.add(musicSlider).pad(10f);
		table.row();

		table.add(fullscreenCheckBox);
		table.row();

		table.add(controlsHeader).pad(10f);
		table.row();

		table.add(controlsTable);

		controlsTable.clear();
		for (int i = 0; i < actions.length; i++) {
			controlsTable.add(actionControls[i]).pad(5f).fill().width(actionControls[i].getHeight() * 5);
			controlsTable.add(actions[i]).left().pad(5f);

			controlsTable.row();
		}
		table.row();

		table.add(resetSettingsButton).prefSize(width / 4, height / 20).pad(30f);
		table.row();

		table.add(backToMenuButton).expandY().bottom().minSize(width / 4, height / 20).maxSize(width, height / 5).prefSize(width / 3, height / 15).pad(10f);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		settings.act(Gdx.graphics.getDeltaTime());
		settings.draw();

		if (backToMenuButton.isPressed()) {
			game.set.writeSettings();
			game.setScreen(game.menuScreen);
		}

	}

	@Override
	public void resize(int width, int height) {
		settings.getViewport().update(width, height, true);
		layoutSettings(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		System.out.println("Hiding SettingsScreen.");
		actionBeingSet = -1;
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean keyDown(int keycode) {
		if (actionBeingSet >= 0 && actionBeingSet < actionControls.length) {

			switch (actionBeingSet) {

			case 0: // Up.
				game.set.setUpButton(keycode);
				break;

			case 1: // Right
				game.set.setRightButton(keycode);
				break;

			case 2: // Left
				game.set.setLeftButton(keycode);
				break;

			case 3: // Down
				game.set.setDownButton(keycode);
				break;

			default:
				break;

			}
			actionControls[actionBeingSet].setText(Keys.toString(keycode));
			actionBeingSet = -1;
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return true;
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

	@Override
	public void show() {
		Gdx.input.setInputProcessor(multiplexer);
	}

}

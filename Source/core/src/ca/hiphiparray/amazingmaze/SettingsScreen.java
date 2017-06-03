/********************************************************************************
 * Amazing Maze is an educational game created in Java with the libGDX library.
 * Copyright (C) 2017 Hip Hip Array
 *
 * This file is part of Amazing Maze.
 *
 * Amazing Maze is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Amazing Maze is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Amazing Maze. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ca.hiphiparray.amazingmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
 * @author Vincent Macri
 * @since 0.2
 * <br>
 * Time (Chloe): 3 hours
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

	/** Button to go back to the previous screen. */
	private TextButton backButton;

	/** Table for game controls */
	private Table controlsTable;

	/** Array of Labels for all possible game actions */
	private Label[] actions;

	/** Array of TextButtons for changeable game action buttons */
	private TextButton[] actionControls;

	/** Button to reset settings. */
	private TextButton resetSettingsButton;
	/** Button to reset save. */
	private TextButton resetSaveButton;

	/** Action currently being set. This is -1 when no actions are being set. */
	private int actionBeingSet = -1;

	/** A reference to the screen that sent the user to the settings screen. */
	private Screen sourceScreen;

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
		this.sourceScreen = game.menuScreen;
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

		screenHeader = new Label("Settings", game.assets.skin, Assets.SANS_HEADER_STYLE);

		musicSlider = new Slider(0, 1, 0.1f, false, game.assets.skin);
		musicSlider.setValue(game.save.getMusicLevel());
		musicSlider.setAnimateDuration(0.25f);

		musicSlider.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.music.setVolume(musicSlider.getValue());
				game.save.setMusicLevel(game.music.getVolume());
			}
		});
		musicSliderLabel = new Label("Music Volume", game.assets.skin);

		controlsHeader = new Label("Controls", game.assets.skin);

		actions = new Label[] {
			new Label("Up", game.assets.skin),
			new Label("Down", game.assets.skin),
			new Label("Left", game.assets.skin),
			new Label("Right", game.assets.skin),
			new Label("Pause", game.assets.skin)};

		actionControls = new TextButton[] {
			new TextButton(Keys.toString(game.save.getUpButton()), skin),
			new TextButton(Keys.toString(game.save.getDownButton()), skin),
			new TextButton(Keys.toString(game.save.getLeftButton()), skin),
			new TextButton(Keys.toString(game.save.getRightButton()), skin),
			new TextButton(Keys.toString(game.save.getPauseButton()), skin)};

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

		actionControls[4].addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actionControls[4].isPressed()) {
					updateControls(4);
				}
			}
		});

		// Reset settings button
		resetSettingsButton = new TextButton("Reset Settings", skin);
		resetSettingsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (resetSettingsButton.isPressed()) {
					game.save.resetSettings();
					musicSlider.setValue(game.save.getMusicLevel());
					resetActionControlsLabels();
				}
			}
		});

		// Reset save button.
		resetSaveButton = new TextButton("Reset Save", skin);
		resetSaveButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (resetSaveButton.isPressed()) {
					game.save.resetSave();
				}
			}
		});

		backButton = new TextButton("Back", skin);

	}

	/**
	 * Reset Labels for elements in actionControl array.
	 */
	protected void resetActionControlsLabels() {
		actionControls[0].setText(Keys.toString(game.save.getUpButton()));
		actionControls[1].setText(Keys.toString(game.save.getDownButton()));
		actionControls[2].setText(Keys.toString(game.save.getLeftButton()));
		actionControls[3].setText(Keys.toString(game.save.getRightButton()));
		actionControls[4].setText(Keys.toString(game.save.getPauseButton()));
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
		table.clear();

		table.add(screenHeader).pad(10f).colspan(2);
		table.row();

		table.add(musicSliderLabel).colspan(2);
		table.row();

		table.add(musicSlider).pad(10f).colspan(2);
		table.row();

		table.add(controlsHeader).pad(10f).colspan(2);
		table.row();

		table.add(controlsTable).colspan(2);

		controlsTable.clear();
		for (int i = 0; i < actions.length; i++) {
			controlsTable.add(actionControls[i]).pad(5f).fill().width(actionControls[i].getHeight() * 5);
			controlsTable.add(actions[i]).left().pad(5f);
			controlsTable.row();
		}
		table.row();

		table.add(resetSettingsButton).prefSize(width / 6, height / 15).padLeft(width / 64).padRight(width / 64).expandY();
		table.add(resetSaveButton).prefSize(width / 6, height / 15).padLeft(width / 64).padRight(width / 64).expandY();

		table.row();

		table.add(backButton).padBottom(10f).minSize(width / 4, height / 20).maxSize(width, height / 5).prefSize(width / 3, height / 15).colspan(2);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		settings.act(delta);
		settings.draw();

		if (backButton.isPressed()) {
			game.save.writeScores();
			game.setScreen(sourceScreen);
			setSourceScreen(game.menuScreen);
		}
	}

	/**
	 * Set the source screen for this time the settings screen is shown.
	 * It is reset to {@link AmazingMazeGame#menuScreen} after back is clicked.
	 *
	 * @param sourceScreen the screen to go back to when the user clicks {@link #backButton}.
	 */
	public void setSourceScreen(Screen sourceScreen) {
		this.sourceScreen = sourceScreen;
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
		actionBeingSet = -1;
		game.save.writeSettings();
	}

	@Override
	public void dispose() {

	}

	@Override
	public boolean keyDown(int keycode) {
		if (actionBeingSet >= 0 && actionBeingSet < actionControls.length) {
			switch (actionBeingSet) {
				case 0: // Up.
					game.save.setUpButton(keycode);
					break;

				case 1: // Down
					game.save.setDownButton(keycode);
					break;
				case 2: // Left
					game.save.setLeftButton(keycode);
					break;

				case 3: // Right
					game.save.setRightButton(keycode);
					break;
				case 4: // Pause
					game.save.setPauseButton(keycode);
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

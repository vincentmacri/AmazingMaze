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
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * The ContinueScreen class.
 *
 * @author Susie Son
 *
 * Time (Susie): 3 hours.
 */
public class ContinueScreen implements Screen {

	/** The {@link AmazingMazeGame} instance that is managing this screen. */
	private final AmazingMazeGame game;

	/** Stage that contains all of the screen components. */
	private Stage stage;
	/** Table for the labels. */
	private Table labelTable;
	/** Table for the option buttons. */
	private Table optionTable;
	/** The quit button. */
	private TextButton quitButton;
	/** The continue button. */
	private TextButton continueButton;
	/** The result label. */
	private Label resultLabel;
	/** The result description label. */
	private Label resultDescriptionLabel;

	/** The player's name. */
	private String name;

	/**
	 * The constructor for ContinueScreen.
	 *
	 * @param game the {@link AmazingMazeGame} instance that is managing this screen.
	 */
	public ContinueScreen(final AmazingMazeGame game) {
		this.game = game;
		Label.LabelStyle labelStyle = new Label.LabelStyle(game.assets.getFont(Assets.SANS_REGULAR, Assets.REGULAR_FONT_SIZE), Color.WHITE);

		stage = new Stage(new ScreenViewport(), this.game.batch);
		labelTable = new Table();
		optionTable = new Table();
		labelTable.setFillParent(true);
		labelTable.top().center();
		optionTable.setFillParent(true);
		optionTable.bottom();
		labelTable.background(new TextureRegionDrawable(new TextureRegion(this.game.assets.manager.get(Assets.MINI_BACKGROUND, Texture.class))));

		if ((game.set.getLevel() - 1) % 5 == 0) {
			resultLabel = new Label("You win!", labelStyle);
			resultDescriptionLabel = new Label("Continue playing or quit?", labelStyle);
		} else {
			resultLabel = new Label("You lose!", labelStyle);
			resultDescriptionLabel = new Label("Go back to last checkpoint or quit?", labelStyle);
		}

		continueButton = new TextButton("Continue", game.assets.skin);
		continueButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (continueButton.isPressed()) {
					game.setScreen(new MazeScreen(game, false));
				}
			}
		});

		quitButton = new TextButton("Quit", game.assets.skin);
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (quitButton.isPressed()) {
					highScoreDialog();
				}
			}
		});

		labelTable.add(resultLabel).pad(20);
		labelTable.row();
		labelTable.add(resultDescriptionLabel).pad(20);
		optionTable.add(continueButton).minSize(Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 20).maxSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 8).prefSize(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10).padBottom(10).pad(20);
		optionTable.row();
		optionTable.add(quitButton).minSize(Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 20).maxSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 8).prefSize(Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 10).pad(20).padBottom(40);
		stage.addActor(labelTable);
		stage.addActor(optionTable);
	}

	/**
	 * Displays the high score dialog.
	 */
	public void highScoreDialog() {
		Label.LabelStyle labelStyle = new Label.LabelStyle(game.assets.getFont(Assets.MONO_REGULAR, Assets.SMALL_FONT_SIZE), Color.WHITE);
		final Dialog dialog = new Dialog("High Score", game.assets.skin);
		final TextButton okButton = new TextButton("OK", game.assets.skin);
		dialog.getButtonTable().bottom();
		Label label = new Label("Enter your name:", labelStyle);
		label.setScale(.5f);
		label.setWrap(true);
		label.setAlignment(Align.center);
		final TextField nameField = new TextField("", game.assets.skin);
		dialog.add(label).width(500).pad(50);
		dialog.add(nameField);
		dialog.add(okButton).bottom();
		nameField.setTextFieldListener(new TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char key) {
				name = formatString(nameField.getText());
				if (!name.equals("")) {
					if (key == (char) 13) {
						displayHighScores();
					}
				}
			}
		});
		okButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				name = formatString(nameField.getText());
				if (!name.equals("")) {
					if (okButton.isPressed()) {
						dialog.hide();
						displayHighScores();
					}
				}
			}
		});
		dialog.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				name = formatString(nameField.getText());
				if (!name.equals("")) {
					if (keycode == Keys.ENTER) {
						displayHighScores();
						return true;
					}
				}
				return false;
			}
		});
		dialog.show(stage);
	}

	/**
	 * Gets the player's name.
	 *
	 * @return the player name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Displays the high scores.
	 */
	public void displayHighScores() {
		// TODO: High scores.
		game.setScreen(new MainMenuScreen(game));
	}

	/**
	 * Formats the string.
	 *
	 * @param s
	 *            The string being formatted.
	 * @return The formatted string.
	 */
	public String formatString(String s) {
		if (s == null)
			return "";
		return s;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCursorCatched(false);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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

	}

}

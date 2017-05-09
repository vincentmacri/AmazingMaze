/********************************************************************************
 * Amazing Maze is an educational game created in Java with the libGDX library. Copyright (C) 2017 Hip Hip Array
 *
 * This file is part of Amazing Maze.
 *
 * Amazing Maze is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Amazing Maze is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Amazing Maze. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ca.hiphiparray.amazingmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * The menu screen for the game.
 *
 * @author Chloe Nguyen
 * @author Vincent Macri
 */
public class MainMenuScreen implements Screen {

	/** The {@link AmazingMazeGame} instance that is managing this screen. */
	private final AmazingMazeGame game;

	/** Stage that contains all of the main menu options. */
	private Stage menu;
	/** The Table container for the main menu buttons. */
	private Table table;

	/** Play button. */
	private TextButton playButton;
	/** Help button. */
	private TextButton helpButton;
	/** Quit button. */
	private TextButton quitButton;

	/** Title of menu */
	private Label menuTitle;

	public MainMenuScreen(final AmazingMazeGame game) {
		this.game = game;
		menu = new Stage(new ScreenViewport(), this.game.batch);

		table = new Table();

		table.setFillParent(true);
		table.bottom();
		menu.addActor(table);

		menuTitle = new Label("Amazing Maze", game.skin);

		// Play
		playButton = new TextButton("Play", game.skin);
		playButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (playButton.isPressed()) {
					// INTRO OR GAME HERE
				}
			}
		});

		// Help
		helpButton = new TextButton("Help", game.skin);
		helpButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (helpButton.isPressed()) {
					// HELP HERE
				}
			}
		});

		// Quit
		quitButton = new TextButton("Quit", game.skin);
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
		layoutMenu(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		menu.act(Gdx.graphics.getDeltaTime());
		menu.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	private void layoutMenu(int width, int height) {
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

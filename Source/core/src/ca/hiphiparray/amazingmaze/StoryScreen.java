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

import java.io.InputStream;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ca.hiphiparray.amazingmaze.MusicManager.Song;

/**
 * The screen that tells the background story.
 *
 * @author Vincent Macri
 */
public class StoryScreen implements Screen {

	/** The {@link AmazingMazeGame} instance that is managing this screen. */
	private final AmazingMazeGame game;

	/** The stage for the story screen */
	private Stage stage;

	/** Table for the stage. */
	private Table table;

	/** The story {@link Label}. */
	private Label story;

	/**
	 * Constructor for the story screen.
	 *
	 * @param game the {@link AmazingMazeGame} instance that is managing this screen.
	 */
	public StoryScreen(final AmazingMazeGame game) {
		this.game = game;
		setupUI();
	}

	/** Helper method to setup the UI. */
	private void setupUI() {
		stage = new Stage(new ScreenViewport(), game.batch);

		table = new Table();
		table.top().center();
		table.setFillParent(true);
		stage.addActor(table);

		story = new Label(readStory(), game.assets.skin, Assets.STORY_STYLE);
		story.setWrap(true);
		table.add(story).maxWidth(Gdx.graphics.getWidth()).prefWidth(Gdx.graphics.getWidth() / 1.125f);
	}

	/**
	 * Helper method to read the game story.
	 *
	 * @return the story as a string.
	 */
	private String readStory() {
		try {
			InputStream input = Gdx.files.internal("story/story.txt").read();

			Scanner s = new Scanner(input);

			String story = s.nextLine();

			while (s.hasNextLine()) {
				story += "\n" + s.nextLine();
			}

			s.close();
			return story;
		} catch (Exception e) {
			e.printStackTrace();
			return "Error loading story";
		}
	}

	@Override
	public void show() {
		Gdx.input.setCursorCatched(false);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		game.music.setSong(Song.STORY);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}

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
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ca.hiphiparray.amazingmaze.MusicManager.Song;

/**
 * The screen to display high scores.
 *
 * @author Vincent Macri
 */
public class HighScoresScreen implements Screen {

	/** The {@link AmazingMazeGame} instance managing this screen. */
	private final AmazingMazeGame game;
	/** The stage for the high scores screen */
	private final Stage stage;

	/** Table for widgets and the layout */
	private Table table;

	/**
	 * Create the high scores screen.
	 *
	 * @param game the {@link AmazingMazeGame} instance managing this screen.
	 */
	public HighScoresScreen(final AmazingMazeGame game) {
		this.game = game;
		this.stage = new Stage(new ScreenViewport(), this.game.batch);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		game.music.setSong(Song.MENU);

		table = new Table();
		table.setFillParent(true);
		table.top();
		stage.addActor(table);

		Label header = new Label("High Scores", game.assets.skin, Assets.SANS_HEADER_STYLE);
		table.add(header).pad(Gdx.graphics.getHeight() / 20).colspan(3);
		table.row();

		HighScore[] scores = game.set.getHighScores();
		for (int i = scores.length - 1; i >= 0; i--) {
			Label position = new Label(Integer.toString(scores.length - i) + ". ", game.assets.skin);
			Label name = new Label(scores[i].getName(), game.assets.skin);
			Label score = new Label(Integer.toString(scores[i].getScore()), game.assets.skin);
			if (scores[i].getScore() < 0) {
				position.setText("");
				score.setText("");
			}

			table.add(position).padBottom(Gdx.graphics.getHeight() / 50);
			table.add(name).left().padBottom(Gdx.graphics.getHeight() / 50).padRight(Gdx.graphics.getWidth() / 32);
			table.add(score).padBottom(Gdx.graphics.getHeight() / 50).right();
			table.row();
		}

		TextButton menuButton = new TextButton("Main Menu", game.assets.skin);
		menuButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub

			}
		});
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

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
